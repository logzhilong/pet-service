#!/usr/bin/env python
#coding=utf-8

cfg = {}

def load(fileName):
	import ConfigParser
	print 'config file : %s' % fileName
	with open(fileName,'rw') as c :
		parser = ConfigParser.ConfigParser()
		parser.readfp(c)
		for group in parser.sections():
			itm = {}
			print group
			for key,val in parser.items(group):
				itm[key]=val
			cfg[group] = itm
		print cfg

if __name__ == '__main__' :
	import sys
	print sys.argv[1]
	load(sys.argv[1])
