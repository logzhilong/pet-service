package com.momoplan.pet.commons.cache;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.utils.SpringContextHolder;

public class MapperOnCacheSupport {

	private static Logger logger = LoggerFactory.getLogger(MapperOnCacheSupport.class);

	protected Gson myGson = new Gson();
	
	protected String getMapperName(Class<?> clazz) {
		return StringUtils.uncapitalize(clazz.getSimpleName()) + "Mapper";
	}
	
	@SuppressWarnings("unchecked")
	protected <T,K> int insertOrUpdate(T t, K pk,String method) throws Exception {
		RedisPool redisPool = SpringContextHolder.getBean(RedisPool.class);
		ShardedJedis jedis = redisPool.getConn();
		Class<?> clazz = t.getClass();
		String mapper = getMapperName(clazz);
		String cacheKey = mapper + "." + pk;
		String json = null;
		Object obj = null;
		int r = 0;
		try {
			obj = SpringContextHolder.getBean(mapper);
			r = (Integer)obj.getClass().getMethod(method, clazz).invoke(obj, t);
		} catch (Exception e) {
			logger.debug("缓存 "+method+" 方法异常 : " + e.getMessage());
			logger.debug("param : mapper="+mapper+" ; cacheKey="+cacheKey);
			throw e;
		} finally {
			if ( redisPool != null && jedis != null && obj!=null ) {
				if(method.startsWith("update")){
					t = (T) obj.getClass().getMethod("selectByPrimaryKey", String.class).invoke(obj, pk);
				}
				json = myGson.toJson(t);
				logger.debug("写入缓存"+method+" :  k="+cacheKey+" ; v="+json);
				jedis.set(cacheKey, json);
				redisPool.closeConn(jedis);
			}
		}
		return r;
	}
}
