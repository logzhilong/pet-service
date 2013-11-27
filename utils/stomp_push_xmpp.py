#!/usr/bin/env python
#coding=utf-8
import os,stomp,sys,time,Queue,json,string,urllib,urllib2,traceback
import mod_conf
import mod_log as lm

log={}
common_cfg={}
self_cfg={}

class MyListener(object):
	def on_error(self,headers,message):
		log.info("error : %s" % message)
	def on_message(self,headers,message):
		log.debug("message : %s" % message)
		try:
			p = PushXmpp(message)	
			p.build()
			p.post()
		except:
			err = traceback.format_exc()
			log.debug(err)

class PushXmpp :
	head={'Content-Type':'text/xml','charset':'utf-8'}
	msg_tmp= '''
		<message to="${to}${domain}" from="${from}${domain}" 
			 type="chat" msgtype="${msgtype}" 
			 msgTime="${msgtime}" fromNickname="${fromNickname}" 
			 fromHeadImg="${fromHeadImg}"
			 contentType="${contentType}"
			 content="${content}"
			 contentID="${contentID}" 
			 picID="${picID}"
		>
			<body><![CDATA[${body}]]></body>
		</message>
		'''
	def __init__(self,param):
		self.url = common_cfg['common']['xmpp_url']
		self.param = param

	def build(self):
		log.debug("revice param = %s" % self.param)
		p = json.loads(self.param)
		template = string.Template(self.msg_tmp)
		if p.has_key('picID') == False :
			p['picID'] = ''
		self.msg = template.safe_substitute(p)
	
	def post(self):
		msg = self.msg.encode("utf-8")
		log.info("xml=%s" % msg)
		request = urllib2.Request(url=self.url,headers=self.head,data=msg) 
		res = urllib2.urlopen(request).read()
		log.info("post=> %s" % self.param)

class Main :
	def start_link(self):
		conn = stomp.Connection([(common_cfg['common']['mq_host'],int(common_cfg['common']['mq_port']))])
		conn.set_listener('',MyListener())
		conn.start()
		conn.connect(wait=True)
		conn.subscribe( destination=('/queue/%s' % self_cfg['self']['mq_destination']), id=1, ack='auto' )
		while True:
			if not conn.is_connected():
				log.info('connect to stomp server ...')
				conn.start()
				conn.connect(wait=True)
				conn.subscribe( destination=('/queue/%s' % self_cfg['self']['mq_destination']), id=1, ack='auto' )
				log.info('connect success...')
			time.sleep(30)
		conn.disconnect()

if(__name__=='__main__'):
	common_cfg = mod_conf.load('stomp.ini')
	self_cfg_path = 'stomp_push_xmpp.ini'
	if len(sys.argv) > 1 :
		self_cfg_path = sys.argv[1]
	pring 'config path : %s' % self_cfg_path
	self_cfg = mod_conf.load(self_cfg_path)
	LOG_LEVEL = lm.level[self_cfg['self']['log_level']]
	log = lm.LoggerFactory(self_cfg['self']['log_file'],'push_xmpp',LOG_LEVEL).getLog()
	print common_cfg
	print self_cfg

	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',int(self_cfg['self']['daemon_port'])) , logRequests=True )
	Main().start_link()

