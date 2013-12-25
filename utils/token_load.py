#/usr/bin/env python
#coding=utf-8

import os.path,os,pprint,shutil,sys,uuid,string,time
import traceback
import StringIO
import json
import mod_conf
import mod_log as lm
import mysql.connector
import datetime
import redis

common_cfg = mod_conf.load('access_log_analysis.ini')
LOG_LEVEL = lm.level['debug']
log = lm.LoggerFactory(common_cfg['common']['log_file'],'counter',LOG_LEVEL).getLog()

token_map = {}

def visit(arg,dirname,names) :
	log.debug( 'dirname=%s ; arg=%s' % (dirname,arg) )
	for name in names :
		if arg != name and name.startswith('pet_access') :
			subname = os.path.join(dirname,name)
			log.debug( subname )
			analysis(subname)	

def analysis(path) :
	try:
		with open(path) as f :
			line = f.readline()
			while line:
				try:
					(d,in_out) = line[1:11],line[25:]
					map = json.loads(in_out)
					channel = map.get('input').get('channel')
					token = map.get('input').get('token')
					if channel is None:
						channel = 'Default'
					if token :
						k = token + '_' + d
						token_map[k] = {'id':k,'token':token,'channel':channel,'cd':d}				
				except Exception,err:
					pass			
				line = f.readline()
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

biz_token_template = "insert into biz_token (id,token,cd,channel)values('${id}','${token}','${cd}','${channel}')"
def push_token(host,user,pwd,db):
	log.debug('token ------->')
	conn = Datasource(host,user,pwd,db).getConnection()
	conn.set_autocommit(True)
	cursor = conn.cursor()
	for (k,v) in token_map.items() :
		try:
			template = string.Template(biz_token_template)
			sql = template.safe_substitute(v)
			print sql
			cursor.execute(sql)	
		except Exception,err:
			print err
	cursor.close()
	conn.close()		
	log.debug('today token count : %s' % len(token_map))
	token_map.clear()
	log.debug('token <-------')

def runner() :
	os.path.walk(common_cfg['common']['data_dir'],visit,'pet_access')

	host = common_cfg['common']['host']
	user = common_cfg['common']['user']
	pwd = common_cfg['common']['password']
	db = common_cfg['common']['database']
	
	push_token(host,user,pwd,db)


if __name__ == '__main__' :
	LOG_LEVEL = lm.level['debug']
	log = lm.LoggerFactory(common_cfg['common']['log_file'],'counter',LOG_LEVEL).getLog()
	print common_cfg
	runner()

