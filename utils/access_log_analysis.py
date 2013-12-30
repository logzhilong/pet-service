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

report_channel_counter_sql = """
select  r.channel , 
	r.new_user , 
	r.new_register , 
	r.all_register, 
	r.all_user , 
	r.new_pv , 
	r.all_pv ,
        (r.new_register/r.new_user) as new_rate /*今日注册率*/,
        (all_register/all_user) as all_rate /*总注册率*/,
	r.new_online,
	r.all_online
from  (
 select
    c0.channel /*渠道*/,
    (select count(id) from biz_imei c1 where c1.channel=c0.channel and c1.cd='${cd}' group by c1.channel) as new_user /*今日新增*/,
    (select sum(c2.counter) from biz_service_counter c2 where c2.channel=c0.channel and c2.method='register' and c2.cd='${cd}' ) as new_register /*今日注册*/,
    (select sum(c3.counter) from biz_service_counter c3 where c3.channel=c0.channel and c3.method='register' ) as all_register /*总注册数*/,
    (select count(0) from biz_imei c4 where c4.channel=c0.channel ) as all_user /*总用户数*/,
    (select sum(c5.counter) from biz_service_counter c5 where c5.channel=c0.channel and c5.cd='${cd}' ) as new_pv /*今日PV*/,
    (select sum(c6.counter) from biz_service_counter c6 where c6.channel=c0.channel ) as all_pv/*总PV*/,
    (select count(c7.token) from biz_token c7 where c7.channel=c0.channel and c7.cd='${cd}' ) as new_online /*今日上线*/,
    (select count(c8.token) from biz_token c8 where c8.channel=c0.channel ) as all_online /*总上线*/
 from biz_service_counter c0
 group by c0.channel
)as r
"""

common_cfg = mod_conf.load('access_log_analysis.ini')
LOG_LEVEL = lm.level['debug']
log = lm.LoggerFactory(common_cfg['common']['log_file'],'counter',LOG_LEVEL).getLog()

total = {}
result = {}
imei_map = {}
token_map = {}
#131125:暂时没用，记录数据的提取时间范围
scop = {'min':'9999-99-99','max':'0000-00-00'}

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
				if scop['min'] > d : scop['min'] = d
				if scop['max'] < d : scop['max'] = d
				map = json.loads(in_out)
				service = map.get('input').get('service')
				method = map.get('input').get('method')
				channel = map.get('input').get('channel')
				imei = map.get('input').get('imei')
				token = map.get('input').get('token')
				mac = map.get('input').get('mac')
				if channel is None:
					channel = 'Default'
				# imei 是统计激活用户用的，要排除重复的；
				# 本次统计重复用字典排除，全局的重复通过数据库主键排除
				if imei == "iphone" :
					if mac :
						imei_map[mac] = {'id':mac,'channel':channel,'cd':d}
				elif imei:
					imei_map[imei] = {'id':imei,'channel':channel,'cd':d}
					
				if token :
					k = token + '_' + d
					token_map[k] = {'id':k,'token':token,'channel':channel,'cd':d}				

				k = service,method,channel,d,map.get('output').get('success')
				c = result.get(k)
				if not c:
					c = 1
				else:
					c+=1
				if service :
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

biz_imei_template = "insert into biz_imei (id,cd,channel)values('${id}','${cd}','${channel}')"
def push_imei(host,user,pwd,db):
	log.debug('imei ------->')
	conn = Datasource(host,user,pwd,db).getConnection()
	conn.set_autocommit(True)
	cursor = conn.cursor()
	for (k,v) in imei_map.items() :
		try:
			template = string.Template(biz_imei_template)
			sql = template.safe_substitute(v)
			print sql
			cursor.execute(sql)	
		except Exception,err:
			pass
	cursor.close()
	conn.close()		
	log.debug('today imei count : %s' % len(imei_map))
	imei_map.clear()
	log.debug('imei <-------')

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
			pass
	cursor.close()
	conn.close()		
	log.debug('today token count : %s' % len(token_map))
	token_map.clear()
	log.debug('token <-------')

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
	
	push_imei(host,user,pwd,db)
	push_token(host,user,pwd,db)

	conn = Datasource(host,user,pwd,db).getConnection()
	cursor = conn.cursor()

	keys = result.keys()
	keys.sort()
	sql_insert = common_cfg['common']['sql_insert']
	#print 'result >>>>>>>>>>>'
	#print result
	#print 'result <<<<<<<<<<<'
	for k in keys:
		template = string.Template(sql_insert)
		#key 的结构固定为 ： service,method,channel,d,map.get('output').get('success')
		#sql参数 ('${id}','${service}','${method}','${channel}','${cd}','${success}','${counter}')			
		param = dict(zip(('service','method','channel','cd','success'),k))
		param['id'] = uuid.uuid1().hex
		param['counter'] = result.get(k)
		sql = template.safe_substitute(param)
		#print "k >>>>>>>>>>>>>>"
		#print k
		#print param
		#print "k <<<<<<<<<<<<<<"
		cursor.execute(sql)
	cursor.close()
	conn.commit()
	conn.close()
	log.debug( 'insert_rows=%s ; pv=%s' % ( len(result),count ) )
	total.clear()
	result.clear()	
	#如异常，重试3次
	for i in range(0,3):
		try:
			report_channel_counter()
			break;
		except:
			error = traceback.format_exc()		
			log.debug( (">>>>>>>>>>>>>> %s" % i) )
			log.debug(error)
			log.debug( ("<<<<<<<<<<<<<< %s" % i) )
			time.sleep(2)
	return 'success'

def report_channel_counter(cmd=False):
	redisCli = redis.Redis( host=common_cfg['common']['store_host'],port=int(common_cfg['common']['store_port']),password=common_cfg['common']['store_password'] )
	#如果是命令行执行，则只初始化昨天的数据,否则统计 scop.min ~ scop.max 区间的数据
	d = datetime.date.today()+datetime.timedelta(-1)
	cd = d.strftime('%Y-%m-%d')
	param = {'cd':cd}
	template = string.Template(report_channel_counter_sql)
	sql = template.safe_substitute(param)
	scop_json = json.dumps(param)
	log.debug('scop = %s' % scop_json)
	#把范围放在缓存中，后台显示时取出即可
	#131125:这里画蛇填足了，只取前一天的数据就可以了
	redisCli.set('report_scope',scop_json)
	#scop['min'] = '9999-99-99'
        #scop['max'] = '0000-00-00'
	log.debug(sql)
	host = common_cfg['common']['host']
	user = common_cfg['common']['user']
	pwd = common_cfg['common']['password']
	db = common_cfg['common']['database']
	conn = Datasource(host,user,pwd,db).getConnection()
	cursor = conn.cursor()
	log.debug('构建报表数据')
	cursor.execute(sql)
	report_key = common_cfg['common']['report_key']
	log.debug( '清除历史报表 key=%s' % report_key )
	redisCli.delete(report_key)
	for ( channel,new_user,new_register,all_register,all_user,new_pv,all_pv,new_rate,all_rate,new_online,all_online ) in cursor :
		if not channel : channel = 0 
		if not new_user : new_user = 0 
		else : new_user = int(new_user)	
		if not new_register : new_register = 0 
		else : new_register = int(new_register)
		if not all_register : all_register = 0 
		else : all_register = int(all_register)
		if not all_user	: all_user = 0 
		else : all_user = int(all_user)
		if not new_pv : new_pv = 0 
		else : new_pv = int(new_pv)
		if not all_pv : all_pv = 0 
		else : all_pv = int(all_pv)
		if not new_rate	: new_rate = 0.0 
		else : new_rate = float(new_rate)
		if not all_rate : all_rate = 0.0 
		else : all_rate = float(all_rate)
		if not new_online : new_online = 0 
		else : new_online = int(new_online)
		if not all_online : all_online = 0 
		else : all_online = int(all_online)
		k=('channel','new_user','new_register','all_register','all_user','new_pv','all_pv','new_rate','all_rate','new_online','all_online')
		v=(channel,new_user,new_register,all_register,all_user,new_pv,all_pv,new_rate,all_rate,new_online,all_online)
		vo = dict(zip(k,v))
		j = json.dumps(vo)
		redisCli.lpush(report_key,j)
	log.debug('完成报表构建')
	cursor.close()
	conn.close()
	return 'success'

if __name__ == '__main__' :
	LOG_LEVEL = lm.level['debug']
	log = lm.LoggerFactory(common_cfg['common']['log_file'],'counter',LOG_LEVEL).getLog()
	print common_cfg

	from SimpleXMLRPCServer import SimpleXMLRPCServer
	server = SimpleXMLRPCServer(('127.0.0.1',int(common_cfg['common']['daemon_port'])) , logRequests=True )

	while True:
		runner()
		time.sleep(120)

