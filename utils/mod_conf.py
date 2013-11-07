#!/usr/bin/env python
#coding=utf-8

def load(fileName):
	cfg = {}
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
	return cfg

if __name__ == '__main__' :
	import sys
	size = len(sys.argv)
	if size > 1 :
		for i in range(1,size):
			print sys.argv[i]
			cfg = load(sys.argv[i])
			print cfg







