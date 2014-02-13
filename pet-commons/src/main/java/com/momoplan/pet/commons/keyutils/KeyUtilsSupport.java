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
		
		_nsMap.put("_", "10");
		nsMap.put("10", "_");
		
		nsMap.put("11","B");
		nsMap.put("12","A");
		nsMap.put("13","C");
		nsMap.put("14","V");
		nsMap.put("15","E");
		nsMap.put("16","Q");
		nsMap.put("17","X");
		nsMap.put("18","O");
		nsMap.put("19","@");
		nsMap.put("20","J");
		nsMap.put("21","K");
		nsMap.put("22","L");
		nsMap.put("23","M");
		nsMap.put("24","W");
		nsMap.put("25","H");
		nsMap.put("26","P");
		nsMap.put("27","F");
		nsMap.put("28","R");
		nsMap.put("29","S");
		nsMap.put("30","T");
		nsMap.put("31","U");
		nsMap.put("32","D");
		nsMap.put("33","N");
		nsMap.put("34","G");
		nsMap.put("35","Y");
		nsMap.put("36","Z");

		_nsMap.put("B","11");
		_nsMap.put("A","12");
		_nsMap.put("C","13");
		_nsMap.put("V","14");
		_nsMap.put("E","15");
		_nsMap.put("Q","16");
		_nsMap.put("X","17");
		_nsMap.put("O","18");
		_nsMap.put("@","19");
		_nsMap.put("J","20");
		_nsMap.put("K","21");
		_nsMap.put("L","22");
		_nsMap.put("M","23");
		_nsMap.put("W","24");
		_nsMap.put("H","25");
		_nsMap.put("P","26");
		_nsMap.put("F","27");
		_nsMap.put("R","28");
		_nsMap.put("S","29");
		_nsMap.put("T","30");
		_nsMap.put("U","31");
		_nsMap.put("D","32");
		_nsMap.put("N","33");
		_nsMap.put("G","34");
		_nsMap.put("Y","35");
		_nsMap.put("Z","36");
		
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