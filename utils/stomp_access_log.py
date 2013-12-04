#!/usr/bin/env python
#coding=utf-8
import stomp,sys,time,Queue,thread
import mod_conf
import mod_log as lm

buf = Queue.Queue()

log={}
common_cfg={}
self_cfg={}

def writeLog():
	while True:
		message = buf.get()
		log.info(message)

class MyListener(object):
	def on_error(self,headers,message):
		log.info("error : %s" % message)
	def on_message(self,headers,message):
		buf.put(message)

class Main :
	def start_link(self):
		thread.start_new_thread(writeLog(),())
		print 'start write log thread ...'
		conn = stomp.Connection([(common_cfg['common']['mq_host'],int(common_cfg['common']['mq_port']))])
		conn.set_listener('',MyListener())
		while True:
			if not conn.is_connected():
				conn.start()
				conn.connect(wait=True)
				conn.subscribe( destination=('/topic/%s' % self_cfg['self']['mq_destination']), id=1, ack='auto' )
				print 'connection success'
			time.sleep(30)
		conn.disconnect()

if(__name__=='__main__'):
	global common_cfg 
	global self_cfg 
	global log 
	common_cfg = mod_conf.load('stomp.ini')

	self_cfg_path = 'stomp_access_log.ini'
	if len(sys.argv) > 1 :
		self_cfg_path = sys.argv[1]
	print 'config path : %s' % self_cfg_path

	self_cfg = mod_conf.load(self_cfg_path)
	LOG_LEVEL = lm.level[self_cfg['self']['log_level']]
	log = lm.LoggerFactory(self_cfg['self']['log_file'],'access_log',LOG_LEVEL,int(self_cfg['self']['log_split_h'])).getLog()

	print common_cfg
	print self_cfg

	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',int(self_cfg['self']['daemon_port'])) , logRequests=True )
	Main().start_link()

