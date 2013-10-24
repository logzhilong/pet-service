package com.momoplan.pet.commons.cache.pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 缓存连接池
 * @author liangchuan
 */
public class StorePool {
	
	private static Logger logger = LoggerFactory.getLogger(StorePool.class);
	
	@Value("#{config['store.server']}")
	private String storeServer = null;
	@Value("#{config['store.pwd']}")
	private String storePwd = "abc123";
	
	private JedisPool storePool = null;
	
	private int timeout = 4000;
	@Value("#{config['cache.pool.max.active']}")
	private Integer cachePoolMaxActive = 200;
	@Value("#{config['cache.pool.max.idle']}")
	private Integer cachePoolMaxIdle = 50;
	@Value("#{config['cache.pool.max.wait']}")
	private Long cachePoolMaxWait = 2000L;
	@Value("#{config['cache.pool.test.on.borrow']}")
	private Boolean cachePoolTestOnBorrow = false;
	private JedisPoolConfig jedisPoolConfig = null;
	private void initJedisPoolConfig(){
		jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxActive(cachePoolMaxActive);
		jedisPoolConfig.setMaxIdle(cachePoolMaxIdle);
		jedisPoolConfig.setMaxWait(cachePoolMaxWait);
		jedisPoolConfig.setTestOnBorrow(cachePoolTestOnBorrow);
	}
	@PostConstruct
	public void init(){
		try{
			initJedisPoolConfig();
			String host = storeServer.split(":")[0];
			String port = storeServer.split(":")[1];
			storePool = new JedisPool(jedisPoolConfig,host,Integer.parseInt(port),timeout,storePwd);
			logger.error("cache 初始化成功");
		}catch(Exception e){
			logger.error("cache 初始化失败",e);
		}
	}
	
	/**
	 * 获取缓存服务器连接对象
	 * @return
	 */
	public Jedis getConn() {
		Jedis jedis = null;
		try{
			jedis = storePool.getResource();
		}catch(Exception e){
			System.err.println("获取缓存连接出现异常: err="+e.getMessage());
			e.printStackTrace();
		}
		return jedis;
	}
	
	/**
	 * 关闭连接
	 * @param jedis
	 */
	public void closeConn(Jedis jedis){
		if(jedis!=null){
			storePool.returnResource(jedis);
		}
	}

	public void setex(String key,String value,int seconds){
		Jedis j = null;
		try{
			j = getConn();
			j.setex(key, seconds, value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
	}
	public void set(String key,String value){
		Jedis j = null;
		try{
			j = getConn();
			j.set(key, value);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
	}

	public String get(String key){
		Jedis j = null;
		try{
			j = getConn();
			return j.get(key);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
		return null;
	}

	public List<String> get(String ... key){
		Jedis j = null;
		try{
			j = getConn();
			List<String> list = new ArrayList<String>();
			for(String k : key){
				String v = j.get(k);
				if(StringUtils.isNotEmpty(v))
					list.add(v);
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
		return null;
	}
	
	public void del(String ... keys){
		Jedis j = null;
		try{
			j = getConn();
			j.del(keys);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
	}
	
	public Set<String> keys(String pattern){
		Jedis j = null;
		try{
			j = getConn();
			return j.keys(pattern);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
		return null;
	}

	public void lpush(String key,String ... values){
		Jedis j = null;
		try{
			j = getConn();
			j.lpush(key, values);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
	}

	public List<String> lrange(String key,long start,long end){
		Jedis j = null;
		try{
			j = getConn();
			List<String> list = j.lrange(key, start, end);
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
		return null;
	}

	public List<String> lpop(String key,int size){
		Jedis j = null;
		try{
			j = getConn();
			List<String> list = null;
			for(int i=0;i<size;i++){
				String str = j.lpop(key);
				if(str==null)
					return list;
				if(list==null)
					list = new ArrayList<String>(size);
				list.add(str);
			}
			return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeConn(j);
		}
		return null;
	}

	
	
	public String getStoreServer() {
		return storeServer;
	}
	public void setStoreServer(String storeServer) {
		this.storeServer = storeServer;
	}
	
}
