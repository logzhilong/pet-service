package com.momoplan.pet.commons.keyutils;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class KeyUtilsSupport {
	
	protected static Map<String,String> nsMap = new LinkedHashMap<String,String>();
	
	protected static Map<String,String> _nsMap = new LinkedHashMap<String,String>();
	
	protected static String short_id_sys_key = "0";
	
	static{
		initNSMAP();
	}
	
	private static void initNSMAP(){
		
		for(int i=10;i<37;i++){
			_nsMap.put((char)(i+54)+"", i+"");
			nsMap.put(i+"", (char)(i+54)+"");
		}
		
		_nsMap.put("@", "37");
		_nsMap.put("*", "38");
		_nsMap.put("_", "39");

		nsMap.put("37", "@");
		nsMap.put("38", "*");
		nsMap.put("39", "_");
		
	}
	
	protected static String getSeed4zk(String date,ZooKeeper zk) throws Exception {
		String path = "/short.id.speed";//EPHEMERAL
		Stat statDir = zk.exists(path, false);
		if(statDir==null){
			path = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		path = path+"/"+date;
		statDir = zk.exists(path, false);
		if(statDir==null){
			path = zk.create(path, null, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		//创建顺序节点
		String lock = zk.create(path+"/speed=", null, Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		String speed = lock.split("=")[1];
		BigInteger ii = new BigInteger(speed);
		System.err.println(lock+" 种子 "+ii.toString());
		return ii.toString();
	}
	
		
}