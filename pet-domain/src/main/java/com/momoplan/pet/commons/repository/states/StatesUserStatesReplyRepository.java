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
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReplyCriteria;
/**
 * 用户动态后台存储对象
 * @author liangc
 *
 */
public class StatesUserStatesReplyRepository implements CacheKeysConstance{
	
	private static Logger logger = LoggerFactory.getLogger(StatesUserStatesReplyRepository.class);
	
	private RedisPool redisPool= null;
	
	private MapperOnCache mapperOnCache = null;
	private Gson gson = MyGson.getInstance();
	private StatesUserStatesReplyMapper statesUserStatesReplyMapper = null;
	
	@Autowired
	public StatesUserStatesReplyRepository(RedisPool redisPool, MapperOnCache mapperOnCache, StatesUserStatesReplyMapper statesUserStatesReplyMapper) {
		super();
		this.redisPool = redisPool;
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
		ShardedJedis jedis = null;
		String stateid = po.getStateid();
		try{
			jedis = redisPool.getConn();
			String listUserStatesReplyKey = LIST_USER_STATES_REPLY+stateid;
			String value = gson.toJson(po);
			logger.debug("缓存动态回复 key="+listUserStatesReplyKey+" ; value="+value);
			//放到队尾
			jedis.rpush(listUserStatesReplyKey, gson.toJson(po));
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
	public List<StatesUserStatesReply> getStatesUserStatesReplyListByStatesId(String stateid,int pageSize,int pageNo){
		String listUserStatesReplyKey = LIST_USER_STATES_REPLY+stateid;
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			boolean hasCache = jedis.exists(listUserStatesReplyKey)&&jedis.llen(listUserStatesReplyKey)>0;
			if(!hasCache){
				//初始化用户动态缓存列表
				StatesUserStatesReplyCriteria statesUserStatesReplyCriteria = new StatesUserStatesReplyCriteria();
				statesUserStatesReplyCriteria.createCriteria().andStateidEqualTo(stateid);
				statesUserStatesReplyCriteria.setOrderByClause("ct asc");
				List<StatesUserStatesReply> poList = statesUserStatesReplyMapper.selectByExample(statesUserStatesReplyCriteria);
				for(StatesUserStatesReply po :poList){
					String v = gson.toJson(po);
					logger.debug("动态回复-缓存-初始化:"+v);
					jedis.rpush(listUserStatesReplyKey, v);
				}
				logger.debug(listUserStatesReplyKey+" 初始化完成");
			}
			List<StatesUserStatesReply> reslist = null;
			List<String> list = jedis.lrange(listUserStatesReplyKey, pageNo*pageSize, (pageNo+1)*pageSize);
			if(list!=null&&list.size()>0){
				reslist = new ArrayList<StatesUserStatesReply>(list.size());
				for(String json:list){
					StatesUserStatesReply po = gson.fromJson(json, StatesUserStatesReply.class);
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
