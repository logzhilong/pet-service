package com.momoplan.pet.cache.test;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import redis.clients.jedis.Jedis;

import com.momoplan.pet.commons.cache.pool.StorePool;

public class Test {
	
	
	public static void main(String[] args) throws JSONException {
		StorePool store = new StorePool();
		store.setStoreServer("123.178.27.74:6379");
		store.init();
		Jedis jedis = store.getConn();
		Set<String> oldKeys = jedis.keys("no*apper*");
		String[] oks = oldKeys.toArray(new String[oldKeys.size()]);
		System.out.println(oks.length);
		for(String k:oks){
			System.out.println(k);
		}
		String lf = jedis.lpop("lfuck");
		System.out.println(lf);
		
		JSONArray jsonArray = new JSONArray();
		for(String json : new String[]{"{'a':[{'xxx':1}]}","{'b':'bbb'}"}){
			JSONObject jsonObj = new JSONObject(json);
			jsonArray.put(jsonObj);
		}
		System.out.println(jsonArray.toString());
		
		for(int i=0;i<10;i++){
			if(i==3)
				continue;
			System.out.println(i);
		}
	}
}
