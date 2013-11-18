#/usr/bin/env python
#coding=utf-8

import os.path,os,pprint,shutil,sys,uuid,string,time
import traceback
import StringIO
import json
import mod_conf
import mod_log as lm
import mysql.connector

log={}
common_cfg={}

total = {}
result = {}

def visit(arg,dirname,names) :
	log.debug( 'dirname=%s ; arg=%s' % (dirname,arg) )
	for name in names :
		if arg != name and ( not name.endswith('.done') and name.startswith('pet_access') ) :
			subname = os.path.join(dirname,name)
			log.debug( subname )
			analysis(subname)	
			shutil.move(subname,'%s.done' % subname)

def analysis(path) :
	try:
		with open(path) as f :
			line = f.readline()
			t = 0
			while line:
				(d,in_out) = line[1:11],line[25:]
				map = json.loads(in_out)
				service = map.get('input').get('service')	
				method = map.get('input').get('method')	
				channel = map.get('input').get('channel')
				if channel is None:
					channel = 'Default'	
				k = service,method,channel,d,map.get('output').get('success')
				c = result.get(k)
				if c is None:
					c = 1
				else:
					c+=1
				if service!=None :
					result[k] = c	
				line = f.readline()
				t+=1
			fileName = os.path.split(path)[1]
			total[fileName]=t
	except Exception,err:
		fp = StringIO.StringIO()
		traceback.print_exc(file=fp)
		t = fp.getvalue()
		log.debug( '------------------------' )
		log.debug( t )
		log.debug( '------------------------' )

class Datasource:
	def __init__(self,host,user,pwd,db):
		self.host = host
		self.user = user
		self.pwd = pwd
		self.db = db
	def getConnection(self):
		return mysql.connector.connect(user=self.user,password=self.pwd,host=self.host,database=self.db)

def runner() :
	os.path.walk(common_cfg['common']['data_dir'],visit,'pet_access')
	count = 0
	if len(total) <= 0 :
		log.debug('heart beat ... << Nothing todo >>')
		return 'Nothing todo'
		
	for (k,v) in total.items():
		count+=v

	host = common_cfg['common']['host']
	user = common_cfg['common']['user']
	pwd = common_cfg['common']['password']
	db = common_cfg['common']['database']
	
	conn = Datasource(host,user,pwd,db).getConnection()
	cursor = conn.cursor()

	keys = result.keys()
	keys.sort()
	sql_insert = common_cfg['common']['sql_insert']
	for k in keys:
		rl = list(k)
		rl.append(result.get(k))
		t = tuple(rl)
		template = string.Template(sql_insert)
		#service,method,channel,d,map.get('output').get('success')
		#('${id}','${service}','${method}','${channel}','${cd}','${success}','${counter}')			
		param = dict(zip(('service','method','channel','cd','success'),k))
		param['id'] = uuid.uuid1().hex
		param['counter'] = result.get(k)
		sql = template.safe_substitute(param)
		cursor.execute(sql)

	cursor.close()
	conn.commit()
	conn.close()
	log.debug( 'insert_rows=%s ; pv=%s' % ( len(result),count ) )
	total.clear()
	result.clear()	
	return 'success'

if __name__ == '__main__' :

	common_cfg = mod_conf.load('access_log_analysis.ini')
	LOG_LEVEL = lm.level['debug']
	log = lm.LoggerFactory(common_cfg['common']['log_file'],'counter',LOG_LEVEL).getLog()
	print common_cfg

	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',int(common_cfg['common']['daemon_port'])) , logRequests=True )

	while True:
		runner()
		time.sleep(60)
