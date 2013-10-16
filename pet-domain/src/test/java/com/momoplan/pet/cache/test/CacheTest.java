package com.momoplan.pet.cache.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.AbstractTest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;

public class CacheTest extends AbstractTest {
	
	@Resource
	RedisPool redisPool = null;
	@Autowired 
	MapperOnCache mapperOnCache = null;
	
	//@Test
	public void testPool() {
		ShardedJedis jedis = redisPool.getConn();
		jedis.setex("fuck",300,"fuck you fuck you");
		redisPool.closeConn(jedis);
		System.out.println("OK..."+jedis.get("fuck"));
	}
	
	@Test
	public void testMapper() throws Exception{
		Long id = new Long("701");
		SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class,id);
		System.out.println(user.toString());
	}
	
}
