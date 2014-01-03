#/usr/bin/env python
#coding=utf-8
import string,datetime,traceback,os.path,re,tornado.httpserver,tornado.ioloop,tornado.options,tornado.web,unicodedata,mysql.connector,json,uuid 

conf = ('192.168.99.51','pet_statistic','pet','123456')


biz_yijifen_template =  "insert into biz_yijifen (id,cd,ct,callback)values('${id}','${cd}','${ct}','${callback}')"

class MainHandler(tornado.web.RequestHandler):

	def initialize(self, db):
		db.set_autocommit(True)
		self.db = db
	def get(self):
		res = {}
		try:
			conn = self.db
			ct = self.get_argument('eventtime')			#事件发生时间
			mac = self.get_argument('IDFV')			#IDFV值
			callback = self.get_argument('callback_url')		#http%3a%2f%2fwww.baidu.com
			param = dict(zip(('id','ct','callback','cd'),(mac,ct,callback,datetime.date.today().strftime('%Y-%m-%d'))))	
			print param
			cursor = conn.cursor()
                	try:
                        	template = string.Template(biz_yijifen_template)
                        	sql = template.safe_substitute(param)
                        	print sql
                        	cursor.execute(sql)        
                	except Exception,err:
                        	print err
        		cursor.close()			
			res = dict(success=True)		
		except:
			eid =  uuid.uuid4().hex
			err = traceback.format_exc()
			print "id=%s ; err=%s" % (eid,err)
			res = dict(success=False,message=eid)		
		finally:
			j = json.dumps(res)
			self.write(j)

def getConnection():
	(host,db,user,pwd) = conf
	return mysql.connector.connect(user=user,password=pwd,host=host,database=db)

if __name__ == "__main__":
	application = tornado.web.Application([ (r"/", MainHandler,dict(db=getConnection())), ])
	application.listen(8888)
	tornado.ioloop.IOLoop.instance().start()


table = """
CREATE TABLE `biz_yijifen` (
  `id` varchar(128) NOT NULL,
  `cd` varchar(128) DEFAULT NULL,
  `ct` varchar(128) DEFAULT NULL,
  `callback` varchar(3000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
"""
