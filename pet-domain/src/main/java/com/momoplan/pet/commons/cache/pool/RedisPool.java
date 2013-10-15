package com.momoplan.pet.commons.cache.pool;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 缓存连接池
 * @author liangchuan
 */
public class RedisPool {
	
	private ShardedJedisPool shardedJedisPool = null;
	
	private boolean cacheEnable = false;
	
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
