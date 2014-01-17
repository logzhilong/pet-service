#!/usr/bin/env python
#coding=utf-8
import stomp,sys,time,json,string,uuid,traceback
import mod_conf
import mod_log as lm

from pyxmpp2.client import Client
from pyxmpp2.etree import ElementTree
from pyxmpp2.interfaces import EventHandler, event_handler, QUIT
from pyxmpp2.iq import Iq
from pyxmpp2.jid import JID
from pyxmpp2.settings import XMPPSettings
from pyxmpp2.streamevents import AuthorizedEvent, DisconnectedEvent

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
			p.push()
		except:
			err = traceback.format_exc()
			log.debug(err)


class MyHandler(EventHandler):
	def __init__(self,xml):
		self.xml = xml
	@event_handler(AuthorizedEvent)
	def handle_authorized(self, event):
		iq = Iq(ElementTree.XML(self.xml))
		log.info("send_xml --> {0}".format(ElementTree.tostring(iq.as_xml())))
		event.stream.send(iq)
		event.stream.disconnect()
	@event_handler(DisconnectedEvent)
	def handle_disconnected(self, event):
		return QUIT
	@event_handler()
	def handle_all(self, event):
		log.info(u"-- {0}".format(event))

# {
# 	"id":"FE268CB8F4E548A4BA54E95097063760",
# 	"name":"??",
# 	"src":"ency",
# 	"state":"NEW",
# 	"ct":"2013-12-03 16:15:25",
# 	"cb":"root",
# 	"et":"2013-12-03 16:15:25",
# 	"eb":"root"
# }
class PushXmpp :
	msg_tmp = '''
		<iq xmlns="jabber:client" from='${from_jid}' to='${pubsub_name}' type='set' id='${key}'>
		  <pubsub xmlns='http://jabber.org/protocol/pubsub'>
		    <publish node='${pubsub_node}'>
		        <item id='itm_${key}'>
		            <entry xmlns='http://www.w3.org/2005/Atom'>
		                <id>${id}</id>
		                <type>${src}</type>
		                <expir>${expir}</expir>
		                <body><![CDATA[${name}]]></body>
		            </entry>
		        </item>
		    </publish>
		  </pubsub>
		</iq>
		'''
	def __init__(self,param):
		self.from_jid=self_cfg['self']['from_jid']
		self.from_pwd=self_cfg['self']['from_pwd']
		self.pubsub_name=self_cfg['self']['pubsub_name']
		self.pubsub_node=self_cfg['self']['pubsub_node']
		self.xmpp_server=self_cfg['self']['xmpp_server']
		self.xmpp_port=self_cfg['self']['xmpp_port']
		self.param = param
	def build(self):
		p = json.loads(self.param)
		p['key'] = uuid.uuid1().hex
		p['from_jid'] = self.from_jid
		p['pubsub_name'] = self.pubsub_name
		p['pubsub_node'] = self.pubsub_node
		log.debug("revice param = %s" % self.param)
		template = string.Template(self.msg_tmp)
		self.msg = template.safe_substitute(p)
		log.info("build msg => %s" % self.msg)
	def push(self):
		settings = XMPPSettings({
                    u"password": self.from_pwd,
                    u"starttls": True,
                    u"tls_verify_peer": False,
                    u'server':self.xmpp_server,
                    u'c2s_port':int(self.xmpp_port),
               })
		xml = self.msg
		client = Client(JID(self.from_jid), [MyHandler(xml.encode('utf-8'))], settings)
		client.connect()
		client.run()


class Main :
	def start_link(self):
		conn = stomp.Connection([(common_cfg['common']['mq_host'],int(common_cfg['common']['mq_port']))])
		conn.set_listener('',MyListener())
		while True:
			if not conn.is_connected():
				log.info('connect to stomp server ...')
				conn.start()
				conn.connect(wait=True)
				conn.subscribe( destination=('/queue/%s' % self_cfg['self']['mq_destination']), id=1, ack='auto' )
				log.info('connect success...')
			log.info('heart beat...')
			time.sleep(30)
		conn.disconnect()

if(__name__=='__main__'):
	common_cfg = mod_conf.load('stomp.ini')
	self_cfg_path = 'stomp_push_xmpp_pubsub.ini'
	if len(sys.argv) > 1 :
		self_cfg_path = sys.argv[1]
	print 'config path : %s' % self_cfg_path
	self_cfg = mod_conf.load(self_cfg_path)
	LOG_LEVEL = lm.level[self_cfg['self']['log_level']]
	log = lm.LoggerFactory(self_cfg['self']['log_file'],'push_xmpp',LOG_LEVEL).getLog()
	print common_cfg
	print self_cfg
	
	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',int(self_cfg['self']['daemon_port'])) , logRequests=True )
	Main().start_link()

