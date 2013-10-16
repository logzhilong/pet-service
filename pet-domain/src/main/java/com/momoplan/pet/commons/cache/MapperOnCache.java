package com.momoplan.pet.commons.cache;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.cache.utils.SpringContextHolder;

/**
 * 在 cache 基础上实现 mapper 单个对象的 增、删、改、查
 * @author liangchuan
 */
@Component
public class MapperOnCache extends MapperOnCacheSupport {

	private static Logger logger = LoggerFactory.getLogger(MapperOnCache.class);
	/**
	 * <p>插入数据
	 * <p>不会过滤值为 null 的属性
	 * @param t &nbsp;&nbsp;要插入的对象,如果有 null 值会报异常
	 * @param pk &nbsp;&nbsp;要插入的对象中的主键，做缓存的KEY
	 * @throws Exception
	 */
	public <T> int insert(T t, String pk) throws Exception {
		return insertOrUpdate(t,pk,"insert");
	}
	/**
	 * <p>插入数据
	 * <p>会过滤掉值为 null 的属性
	 * @param t &nbsp;&nbsp;要插入的对象
	 * @param pk &nbsp;&nbsp;要插入的对象中的主键，做缓存的KEY
	 * @throws Exception
	 */
	public <T> int insertSelective(T t, String pk) throws Exception {
		return insertOrUpdate(t,pk,"insertSelective");
	}
	/**
	 * <p>更新数据
	 * <p>不会过滤值为 null 的属性
	 * @param t &nbsp;&nbsp;要更新的对象,如果有 null 值会报异常
	 * @param pk &nbsp;&nbsp;要更新的对象中的主键，做缓存的KEY
	 * @throws Exception
	 */
	public <T> int updateByPrimaryKey(T t, String pk) throws Exception {
		return insertOrUpdate(t,pk,"updateByPrimaryKey");
	}
	/**
	 * <p>更新数据
	 * <p>会过滤值为 null 的属性
	 * @param t &nbsp;&nbsp;要更新的对象
	 * @param pk &nbsp;&nbsp;要更新的对象中的主键，做缓存的KEY
	 * @throws Exception
	 */
	public <T,K> int updateByPrimaryKeySelective(T t, K pk) throws Exception {
		return insertOrUpdate(t,pk,"updateByPrimaryKeySelective");
	}
	/**
	 * <p>删除数据
	 * @param clazz &nbsp;&nbsp;要删除的 PO 
	 * @param pk &nbsp;&nbsp;PO的主键
	 * @throws Exception
	 */
	public <T> int deleteByPrimaryKey(Class<T> clazz,String pk) throws Exception {
		RedisPool redisPool = SpringContextHolder.getBean(RedisPool.class);
		ShardedJedis jedis = redisPool.getConn();
		String mapper = getMapperName(clazz);
		String cacheKey = mapper + "." + pk;
		int r = 0;
		try {
			logger.debug("删除缓存 deleteByPrimaryKey : k="+cacheKey);
			Object obj = SpringContextHolder.getBean(mapper);
			r = (Integer)obj.getClass().getMethod("deleteByPrimaryKey", String.class).invoke(obj, pk);
			logger.debug("删除数据 deleteByPrimaryKey : pk="+pk);
		} catch (Exception e) {
			logger.debug("缓存 deleteByPrimaryKey 方法异常 : " + e.getMessage());
			logger.debug("param : mapper="+mapper+" ; cacheKey="+cacheKey);
			throw e;
		} finally {
			if (redisPool != null && jedis != null) {
				jedis.del(cacheKey);
				redisPool.closeConn(jedis);
			}
		}
		return r;
	}
	
	public <T,K> T selectByPrimaryKey(Class<T> clazz,K pk) throws Exception {
		RedisPool redisPool = SpringContextHolder.getBean(RedisPool.class);
		ShardedJedis jedis = redisPool.getConn();
		String mapper = getMapperName(clazz);
		String cacheKey = mapper + "." + pk;
		T t = null;
		String json = null;
		try {
			if(jedis!=null){
				try {
					json = jedis.get(cacheKey);
				} catch (Exception e) {
					logger.debug("连接缓存失败：" + e.getMessage());
				}
			}
			if (StringUtils.isEmpty(json)) {
				Object obj = SpringContextHolder.getBean(mapper);
				t = (T) obj.getClass().getMethod("selectByPrimaryKey", pk.getClass()).invoke(obj, pk);
				if(t!=null && jedis!=null){
					String j = myGson.toJson(t);
					jedis.set(cacheKey, j);
				}
				logger.debug("数据库取值 : " + t);
			} else {
				t = myGson.fromJson(json, clazz);
				logger.debug("缓存取值 : " + json);
			}
			return t;
		} catch (Exception e) {
			logger.debug("param : mapper="+mapper+" ; cacheKey="+cacheKey);
			logger.error("缓存 selectByPrimaryKey 方法异常 : ",e);
			throw e;
		} finally {
			if (redisPool != null && jedis != null) {
				redisPool.closeConn(jedis);
			}
		}
	}

}
