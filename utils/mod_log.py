#!/usr/bin/env python
#coding=utf-8
import logging,logging.handlers,datetime

'''
#Example:
import log_mod as lm
log = lm.LoggerFactory('test222.log','modelName',lm.level['debug']).getLog()
log.debug('xxx')
'''

level={"info":logging.INFO,"debug":logging.DEBUG,"error":logging.ERROR}

class LoggerFactory:

        def __init__(self,fileName,title,level,h=24):
                self.fileName = fileName
                self.title = title
                self.level = level
                self.h = h
                print "param fileName=%s" % fileName
                print "param title=%s" % title
                print "param level=%s" % level
                print "param h=%s" % h
	def getLog(self):
		logging.basicConfig()
		logger = logging.getLogger(self.title)
		logger.setLevel(self.level)
		filehandler = logging.handlers.TimedRotatingFileHandler(self.fileName, 'H', self.h, 0)
		filehandler.suffix = "%Y%m%d-%H%M.log"
		logger.addHandler(filehandler)
		return Log(logger)

class Log:
	fmt = '%Y-%m-%d %H:%M:%S'
	def __init__(self,logger):
		self.logger = logger
	def debug(self,text):
		today = datetime.datetime.today()
		text = "[%s] -- %s" % (today.strftime(self.fmt),text)	
		self.logger.debug( text )
	def info(self,text):
		today = datetime.datetime.today()
		text = "[%s] -- %s" % (today.strftime(self.fmt),text)	
		self.logger.info( text )
	def error(self,text):
		today = datetime.datetime.today()
		text = "[%s] -- %s" % (today.strftime(self.fmt),text)	
		self.logger.error( text )

