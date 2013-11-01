#!/usr/bin/env python
#coding=utf-8

import mysql.connector

class Config:
	def getConfig(self,cfgPath):	
		import ConfigParser
		dbmap = {}
		with open(cfgPath,'rw') as cfg:
			config = ConfigParser.ConfigParser()
			config.readfp(cfg)
			dbmap = {
				'common':{
					'host':config.get('common','host'),
					'user':config.get('common','user'),
					'password':config.get('common','password')
				},
				'source':{
					'database':config.get('source','database'),
					'fields':config.get('source','fields'),
					'sql':config.get('source','sql')
				},
				'target':{
					'database':config.get('target','database'),
					'table':config.get('target','table'),
					'sql':config.get('target','sql')
				}
			}
		return dbmap

class Datasource:
	def __init__(self,host,user,pwd,db):
		self.host = host
		self.user = user
		self.pwd = pwd
		self.db = db
	def getConnection(self):
		return mysql.connector.connect(user=self.user,password=self.pwd,host=self.host,database=self.db)

if __name__ == '__main__' :
	import string,sys
	cfg_file = sys.argv[1]
	print cfg_file
	print '###############'
	cfg = Config().getConfig(cfg_file)
	host = cfg['common']['host']
	user = cfg['common']['user']
	pwd = cfg['common']['password']
	s_db = cfg['source']['database']
	t_db = cfg['target']['database']
	
	s_sql = cfg['source']['sql']
	s_fields = cfg['source']['fields']
	

	t_tbl = cfg['target']['table']
	t_sql = cfg['target']['sql']
	
	t_sql_tmp = string.Template(t_sql)

	s_sql = s_sql % s_fields
	
	s_fields = string.split(s_fields,',')
	
	t_del = 'delete from %s' % t_tbl

	s_conn = Datasource(host,user,pwd,s_db).getConnection()
	t_conn = Datasource(host,user,pwd,t_db).getConnection()
	
	s_cursor = s_conn.cursor()
	t_cursor = t_conn.cursor()
	t_cursor.execute(t_del)
	s_cursor.execute(s_sql)
	
	for res in s_cursor:
		row = {}
		for i in range(0,len(s_fields)):
			val = ('%s' % res[i]).encode('utf-8')
			row[s_fields[i]] = val
		t_insert = t_sql_tmp.safe_substitute(row)
		t_cursor.execute(t_insert)
		print row	
		
	s_cursor.close()
	t_cursor.close()

	s_conn.commit()
	s_conn.close()

	t_conn.commit()
	t_conn.close()

