package com.momoplan.pet.commons.repository.states;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.ShardedJedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesCriteria;
/**
 * 用户动态后台存储对象
 * @author liangc
 *
 */
public class StatesUserStatesRepository implements CacheKeysConstance{
	
	private static Logger logger = LoggerFactory.getLogger(StatesUserStatesRepository.class);
	
	private RedisPool redisPool= null;
	
	private MapperOnCache mapperOnCache = null;
	private Gson gson = MyGson.getInstance();
	private StatesUserStatesMapper statesUserStatesMapper = null;
	
	@Autowired
	public StatesUserStatesRepository(RedisPool redisPool, MapperOnCache mapperOnCache, StatesUserStatesMapper statesUserStatesMapper) {
		super();
		this.redisPool = redisPool;
		this.mapperOnCache = mapperOnCache;
		this.statesUserStatesMapper = statesUserStatesMapper;
	}

	/**
	 * 发动态
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(StatesUserStates po)throws Exception{
		mapperOnCache.insertSelective(po, po.getId());
		ShardedJedis jedis = null;
		String uid = po.getUserid();
		try{
			jedis = redisPool.getConn();
			String key = HASH_USER_STATES+uid;
			String field = po.getId();
			String value = gson.toJson(po);
			logger.debug("缓存用户动态 key="+key+" ; value="+value);
			jedis.hset(key, field, value);
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	/**
	 * 发动态
	 * @param po
	 * @throws Exception
	 */
	public void updateSelective(StatesUserStates po)throws Exception{
		mapperOnCache.updateByPrimaryKey(po, po.getId());
		ShardedJedis jedis = null;
		String uid = po.getUserid();
		try{
			jedis = redisPool.getConn();
			String key = HASH_USER_STATES+uid;
			String field = po.getId();
			String value = gson.toJson(po);
			logger.debug("缓存用户动态 key="+key+" ; value="+value);
			jedis.hset(key, field, value);
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("updateSelective",e);
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	
	public void delete(String id) throws Exception{
		StatesUserStates po = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, id);
		mapperOnCache.deleteByPrimaryKey(StatesUserStates.class, id);
		ShardedJedis jedis = null;
		String uid = po.getUserid();
		try{
			jedis = redisPool.getConn();
			String key = HASH_USER_STATES+uid;
			String field = po.getId();
			logger.debug("删除缓存 key="+key);
			jedis.hdel(key, field);
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("delete",e);
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	/**
	 * 根据用户ID获取动态，适用于：我的动态、我某一个好友的动态
	 * @param uid
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<StatesUserStates> getStatesUserStatesListByUserid(String uid,int pageSize,int pageNo){
		String key = HASH_USER_STATES+uid;
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			boolean hasCache = jedis.exists(key)&&jedis.hlen(key)>0;
			if(!hasCache){
				//初始化用户动态缓存列表
				StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
				statesUserStatesCriteria.createCriteria().andUseridEqualTo(uid);
				statesUserStatesCriteria.setOrderByClause("ct desc");
				List<StatesUserStates> poList = statesUserStatesMapper.selectByExample(statesUserStatesCriteria);
				for(StatesUserStates po :poList){
					String v = gson.toJson(po);
					String f = po.getId();
					logger.debug("用户动态-缓存-初始化:"+v);
					jedis.hset(key, f, v);
				}
				logger.debug(key+" 初始化完成");
			}
			List<StatesUserStates> reslist = null;
			
			Set<String> keys = jedis.hkeys(key);
			if(keys!=null&&keys.size()>0){
				
				Map<String,String> valueMap = jedis.hgetAll(key);
				reslist = new ArrayList<StatesUserStates>(valueMap.size());
				
				for(Iterator<String> it = keys.iterator();it.hasNext();){
					String k = it.next();
					StatesUserStates po = gson.fromJson(valueMap.get(k),StatesUserStates.class);
					reslist.add(po);
				}
				
				Collections.sort(reslist,new Comparator<StatesUserStates>(){
					@Override
					public int compare(StatesUserStates o1, StatesUserStates o2) {
						return o2.getCt().compareTo(o1.getCt());
					}
				});
				logger.debug("****** 请检查排序结果");
				logger.debug("****** 请检查排序结果");
				int start = pageNo*pageSize;
				int end = start+pageSize;
				if(start>reslist.size())
					return null;
				if(end>reslist.size())
					end = reslist.size();
				reslist.subList(start,end);
			}
			return reslist;
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}finally{
			redisPool.closeConn(jedis);
		}
		return null;
	}
	
}
