package com.momoplan.pet.commons.repository.states;

import java.util.ArrayList;
import java.util.List;

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
			String listUserStatesKey = LIST_USER_STATES+uid;
			String value = gson.toJson(po);
			logger.debug("缓存用户动态 key="+listUserStatesKey+" ; value="+value);
			jedis.lpush(listUserStatesKey, gson.toJson(po));
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
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
		String listUserStatesKey = LIST_USER_STATES+uid;
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			boolean hasCache = jedis.exists(listUserStatesKey)&&jedis.llen(listUserStatesKey)>0;
			if(!hasCache){
				//初始化用户动态缓存列表
				StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
				statesUserStatesCriteria.createCriteria().andUseridEqualTo(uid);
				statesUserStatesCriteria.setOrderByClause("ct desc");
				List<StatesUserStates> poList = statesUserStatesMapper.selectByExample(statesUserStatesCriteria);
				for(StatesUserStates po :poList){
					String v = gson.toJson(po);
					logger.debug("用户动态-缓存-初始化:"+v);
					jedis.rpush(listUserStatesKey, v);
				}
				logger.debug(listUserStatesKey+" 初始化完成");
			}
			List<StatesUserStates> reslist = null;
			List<String> list = jedis.lrange(listUserStatesKey, pageNo*pageSize, (pageNo+1)*pageSize);
			if(list!=null&&list.size()>0){
				reslist = new ArrayList<StatesUserStates>(list.size());
				for(String json:list){
					StatesUserStates po = gson.fromJson(json, StatesUserStates.class);
					reslist.add(po);
				}
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
