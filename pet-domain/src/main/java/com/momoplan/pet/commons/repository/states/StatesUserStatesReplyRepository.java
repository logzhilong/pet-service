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

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReplyCriteria;
import com.momoplan.pet.commons.repository.CacheKeysConstance;
/**
 * 用户动态后台存储对象
 * @author liangc
 *
 */
public class StatesUserStatesReplyRepository implements CacheKeysConstance{
	
	private static Logger logger = LoggerFactory.getLogger(StatesUserStatesReplyRepository.class);
	private static Gson gson = MyGson.getInstance();
	
	private StorePool storePool= null;
	
	private MapperOnCache mapperOnCache = null;
	private StatesUserStatesReplyMapper statesUserStatesReplyMapper = null;
	
	@Autowired
	public StatesUserStatesReplyRepository(StorePool storePool,
			MapperOnCache mapperOnCache,
			StatesUserStatesReplyMapper statesUserStatesReplyMapper) {
		super();
		this.storePool = storePool;
		this.mapperOnCache = mapperOnCache;
		this.statesUserStatesReplyMapper = statesUserStatesReplyMapper;
	}

	/**
	 * 发动态
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(StatesUserStatesReply po)throws Exception{
		mapperOnCache.insertSelective(po, po.getId());
		Jedis jedis = null;
		String stateid = po.getStateid();
		try{
			jedis = storePool.getConn();
			String key = HASH_USER_STATES_REPLY+stateid;
			String field = po.getId();
			String value = gson.toJson(po);
			logger.debug("缓存动态回复 key="+key+" ; value="+value);
			jedis.hset(key, field, value);//缓存回复
		}catch(Exception e){
			logger.error("insertSelective",e);
		}finally{
			storePool.closeConn(jedis);
		}
	}
	
	public void delete(String id) throws Exception{
		StatesUserStatesReply po = mapperOnCache.selectByPrimaryKey(StatesUserStatesReply.class, id);
		mapperOnCache.deleteByPrimaryKey(StatesUserStatesReply.class, id);
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			String key = HASH_USER_STATES_REPLY+po.getStateid();
			String field = po.getId();
			logger.debug("删除动态回复缓存 key="+key);
			jedis.hdel(key, field);
		}catch(Exception e){
			logger.error("delete",e);
		}finally{
			storePool.closeConn(jedis);
		}
	}
	
	/**
	 * 根据用户ID获取动态，适用于：我的动态、我某一个好友的动态
	 * @param uid
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<StatesUserStatesReply> getStatesUserStatesReplyListByStatesId(String stateid,int pageSize,int pageNo){
		String key = HASH_USER_STATES_REPLY+stateid;
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			boolean hasCache = jedis.exists(key)&&jedis.hlen(key)>0;
			if(!hasCache){
				//初始化用户动态缓存列表
				StatesUserStatesReplyCriteria statesUserStatesReplyCriteria = new StatesUserStatesReplyCriteria();
				statesUserStatesReplyCriteria.createCriteria().andStateidEqualTo(stateid);
				statesUserStatesReplyCriteria.setOrderByClause("ct asc");
				List<StatesUserStatesReply> poList = statesUserStatesReplyMapper.selectByExample(statesUserStatesReplyCriteria);
				for(StatesUserStatesReply po :poList){
					String f = po.getId();
					String v = gson.toJson(po);
					logger.debug("动态回复-缓存-初始化:"+v);
					jedis.hset(key, f, v);
				}
				logger.debug(key+" 初始化完成");
			}
			List<StatesUserStatesReply> reslist = null;
			
			Set<String> keys = jedis.hkeys(key);
			if(keys!=null&&keys.size()>0){
				
				Map<String,String> valueMap = jedis.hgetAll(key);
				reslist = new ArrayList<StatesUserStatesReply>(valueMap.size());
				
				for(Iterator<String> it = keys.iterator();it.hasNext();){
					String k = it.next();
					StatesUserStatesReply po = gson.fromJson(valueMap.get(k),StatesUserStatesReply.class);
					reslist.add(po);
				}
				
				Collections.sort(reslist,new Comparator<StatesUserStatesReply>(){
					@Override
					public int compare(StatesUserStatesReply o1, StatesUserStatesReply o2) {
						return o1.getCt().compareTo(o2.getCt());
					}
				});
				logger.debug("****** 请检查排序结果");
				logger.debug("****** 请检查排序结果");
				int f = pageNo*pageSize;
				int t = (pageNo+1)*pageSize;
				if(f>reslist.size())
					return null;
				if(t>reslist.size())
					t = reslist.size();
				reslist.subList(f,t);
			}
			return reslist;
		}catch(Exception e){
			logger.error("getStatesUserStatesReplyListByStatesId",e);
		}finally{
			storePool.closeConn(jedis);
		}
		return null;
	}
	
}
