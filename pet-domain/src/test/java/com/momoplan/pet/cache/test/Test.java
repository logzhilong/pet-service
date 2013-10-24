package com.momoplan.pet.cache.test;

import java.util.Set;

import redis.clients.jedis.Jedis;

import com.momoplan.pet.commons.cache.pool.StorePool;

public class Test {
	
	
	public static void main(String[] args) {
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
	}
}
