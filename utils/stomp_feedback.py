#!/usr/bin/env python
#coding=utf-8
import logging,logging.handlers,datetime,os
import stomp,sys,time,Queue,json,string,urllib,urllib2,traceback

sys.path.append("./lib")
import Utils
import log_mod as lm
#用一个端口来标识这个进程,重复的进程，必须使用不同的端口才能打开
daemon_port = 40001

#消息服务器IP
mq_host = '127.0.0.1'
#消息服务器端口,走stomp协议
mq_port = 61613
#监听的队列
mq_destination = 'pet_push_to_xmpp'
#推送服务的接口
xmpp_url = 'http://127.0.0.1:5280/rest'

#日志文件
LOG_FILE = "pet_push_xmpp_task.log"
#日志中的模块名
MODEL_NAME='push_xmpp'
#日志级别
LOG_LEVEL = lm.level['debug']

log = lm.LoggerFactory(LOG_FILE,MODEL_NAME,LOG_LEVEL).getLog()

utils = Utils.CommonUtils()

class MyListener(object):
	def on_error(self,headers,message):
		log.info("error : %s" % message)
	def on_message(self,headers,message):
		log.debug("message : %s" % message)
		p = PushXmpp(message)	
		p.build()
		p.post()

class PushXmpp :
	url = xmpp_url
	head={'Content-Type':'text/xml','charset':'utf-8'}
	msg_tmp= '''
		<message to="${to}${domain}" 
			 from="${from}${domain}" 
			 type="chat" 
			 msgtype="${msgtype}" 
			 msgTime="${msgtime}" 
			 fromNickname="${fromNickname}" 
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
	def start_link(self,sn):
		conn = stomp.Connection( [(mq_host,mq_port)] )
		conn.set_listener('',MyListener())
		conn.start()
		conn.connect(wait=True)
		conn.subscribe( destination=('/queue/%s' % mq_destination), id=1, ack='auto' )
		#守护进程	
		#Utils.createDaemon(sn)
		while True:
			time.sleep(30)
		conn.disconnect()

if(__name__=='__main__'):
	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer( ( '127.0.0.1',daemon_port ) , logRequests=True )
	Main().start_link(daemon_port)

