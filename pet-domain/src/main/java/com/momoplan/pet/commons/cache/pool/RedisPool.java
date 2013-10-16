package com.momoplan.pet.commons.cache.pool;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 缓存连接池
 * @author liangchuan
 */
@Component
public class RedisPool {
	
	private Logger logger = LoggerFactory.getLogger(RedisPool.class);
	
	private ShardedJedisPool shardedJedisPool = null;
	
	@Value("#{config['cache.enable']}")
	private boolean cacheEnable = false;
	@Value("#{config['cache.pwd']}")
	private String cachePwd = null;

	@Value("#{config['cache.pool.max.active']}")
	private Integer cachePoolMaxActive = 200;
	@Value("#{config['cache.pool.max.idle']}")
	private Integer cachePoolMaxIdle = 50;
	@Value("#{config['cache.pool.max.wait']}")
	private Long cachePoolMaxWait = 2000L;
	@Value("#{config['cache.pool.test.on.borrow']}")
	private Boolean cachePoolTestOnBorrow = false;
	
	private String cacheServer = null;
	private JedisPoolConfig jedisPoolConfig = null;
	private void initJedisPoolConfig(){
		jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxActive(cachePoolMaxActive);
		jedisPoolConfig.setMaxIdle(cachePoolMaxIdle);
		jedisPoolConfig.setMaxWait(cachePoolMaxWait);
		jedisPoolConfig.setTestOnBorrow(cachePoolTestOnBorrow);
	}

	@Autowired
	public RedisPool(String cacheServer) {
		super();
		this.cacheServer = cacheServer;
	}
	
	@PostConstruct
	public void init(){
		logger.debug("cacheEnable = "+cacheEnable);
		logger.debug("cacheServer = "+cacheServer);
		if( cacheEnable && StringUtils.isNotEmpty(cacheServer) )
		try{
			initJedisPoolConfig();
			logger.debug("cachePwd = "+cachePwd);
			String[] hostPortArr = cacheServer.split(",");
			List<JedisShardInfo> JedisShardInfoList = new ArrayList<JedisShardInfo>();
			for(String portHost : hostPortArr){
				String[] ph = portHost.split(":");
				JedisShardInfo jedisShardInfo = new JedisShardInfo(ph[0].trim(),Integer.parseInt(ph[1].trim()));
				if(StringUtils.isNotEmpty(cachePwd))
					jedisShardInfo.setPassword(cachePwd);
				JedisShardInfoList.add(jedisShardInfo);
			}
			shardedJedisPool = new ShardedJedisPool(jedisPoolConfig,JedisShardInfoList);
			logger.error("cache 初始化成功");
		}catch(Exception e){
			logger.error("cache 初始化失败",e);
		}
	}
	
	/**
	 * 获取缓存服务器连接对象
	 * @return
	 */
	public ShardedJedis getConn() {
		ShardedJedis jedis = null;
		if(cacheEnable){
			try{
				jedis = shardedJedisPool.getResource();
			}catch(Exception e){
				System.err.println("获取缓存连接出现异常: err="+e.getMessage());
				e.printStackTrace();
			}
		}
		return jedis;
	}
	
	/**
	 * 关闭连接
	 * @param jedis
	 */
	public void closeConn(ShardedJedis jedis){
		if(jedis!=null){
			shardedJedisPool.returnResource(jedis);
		}
	}

	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
		this.shardedJedisPool = shardedJedisPool;
	}

	public void setCacheEnable(boolean cacheEnable) {
		System.err.println("缓存启动参数：cache.enable="+cacheEnable);
		this.cacheEnable = cacheEnable;
	}
	
}
