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
		p = PushXmpp(message)	
		p.build()
		p.post()

class PushXmpp :
	url = common_cfg['common']['xmpp_url']
	head={'Content-Type':'text/xml','charset':'utf-8'}
	msg_tmp= '''
		<message to="${to}${domain}" from="${from}${domain}" 
			 type="chat" msgtype="${msgtype}" 
			 msgTime="${msgtime}" fromNickname="${fromNickname}" 
			 fromHeadImg="${fromHeadImg}">
			<body>${body}</body>
		</message>
		'''
	def __init__(self,param):
		self.param = param

	def build(self):
		try:
			log.debug("revice param = %s" % self.param)
			p = json.loads(self.param)
			template = string.Template(self.msg_tmp)
			self.msg = template.safe_substitute(p)
		except:
			exstr = traceback.format_exc()
			log.error(exstr)
	
	def post(self):
		msg = self.msg.encode("utf-8")
		log.info("xml=%s" % msg)
		request = urllib2.Request(url=self.url,headers=self.head,data=msg) 
		res = urllib2.urlopen(request).read()
		log.info("post=> %s" % self.param)

class Main :
	def start_link(self):
		conn = stomp.Connection([(common_cfg['common']['mq_host'],common_cfg['common']['mq_port'])])
		conn.set_listener('',MyListener())
		conn.start()
		conn.connect(wait=True)
		conn.subscribe( destination=('/queue/%s' % self_cfg['self']['mq_destination']), id=1, ack='auto' )
		while True:
			time.sleep(30)
		conn.disconnect()

if(__name__=='__main__'):
	common_cfg = mod_conf.load('stomp.ini')
	self_cfg = mod_conf.load(sys.argv[1])
	LOG_LEVEL = lm.level[self_cfg['self']['log_level']]
	log = lm.LoggerFactory(self_cfg['self']['log_file'],'push_xmpp',LOG_LEVEL).getLog()
	
	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',self_cfg['self']['daemon_port']) , logRequests=True )
	Main().start_link()

