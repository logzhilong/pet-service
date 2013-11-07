#!/usr/bin/env python
#coding=utf-8
import os,stomp,sys,time,json,string,urllib,urllib2,traceback
import mod_conf
import mod_log as lm

log={}
common_cfg={}
self_cfg={}

class MyListener(object):
	def on_error(self,headers,message):
		log.info("error : %s" % message)
	def on_message(self,headers,message):
		log.info(message)

class Main :
	def start_link(self):
		conn = stomp.Connection([(common_cfg['common']['mq_host'],int(common_cfg['common']['mq_port']))])
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
	log = lm.LoggerFactory(self_cfg['self']['log_file'],'access_log',LOG_LEVEL).getLog()

	print common_cfg
	print self_cfg

	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',int(self_cfg['self']['daemon_port'])) , logRequests=True )
	Main().start_link()

