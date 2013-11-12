package com.momoplan.pet.commons.cache;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.utils.SpringContextHolder;

public class MapperOnCacheSupport {

	private static Logger logger = LoggerFactory.getLogger(MapperOnCacheSupport.class);
	
	protected static int EX_SECONDS = 60*60*24;//24小时
	
	protected Gson myGson = MyGson.getInstance();
	
	protected String getMapperName(Class<?> clazz) {
		return StringUtils.uncapitalize(clazz.getSimpleName()) + "Mapper";
	}
	
	@SuppressWarnings("unchecked")
	protected <T,K> int insertOrUpdate(T t, K pk,String method) throws Exception {
		RedisPool redisPool = SpringContextHolder.getBean(RedisPool.class);
		ShardedJedis jedis = redisPool.getConn();
		Class<?> clazz = t.getClass();
		logger.debug("clazz : "+clazz.getName());
		logger.debug("clazz : "+clazz.getSimpleName());
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
				try{
					if(method.startsWith("update")){
						t = (T) obj.getClass().getMethod("selectByPrimaryKey", pk.getClass()).invoke(obj, pk);
					}
					json = myGson.toJson(t);
					logger.debug("写入缓存"+method+" :  k="+cacheKey+" ; v="+json);
					jedis.setex(cacheKey, EX_SECONDS, json);
					redisPool.closeConn(jedis);
				}catch(Exception e){
					logger.debug("缓存 "+method+" 方法异常 : " + e.getMessage());
					logger.debug("param : mapper="+mapper+" ; cacheKey="+cacheKey);
					if(method.startsWith("update")){
						throw e;
					}
				}
			}
		}
		return r;
	}
}
