package com.momoplan.pet.framework.bbs.repository;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.framework.bbs.service.CacheKeysConstance;

@Repository
public class NoteRepository implements CacheKeysConstance{

	private static Logger logger = LoggerFactory.getLogger(NoteRepository.class);
	
	private RedisPool redisPool= null;
	private MapperOnCache mapperOnCache = null;
	private NoteMapper noteMapper = null;
	
	@Autowired
	public NoteRepository(RedisPool redisPool, MapperOnCache mapperOnCache, NoteMapper noteMapper) {
		super();
		this.redisPool = redisPool;
		this.mapperOnCache = mapperOnCache;
		this.noteMapper = noteMapper;
	}
	
	/**
	 * 发帖
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(Note po)throws Exception{
		//入库并加入缓存
		mapperOnCache.insertSelective(po, po);
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			//增加到 帖子总数列表
			jedis.lpush(LIST_NOTE_TOTALCOUNT+po.getForumId(), "n");
			//增加到 当天帖子总数
			jedis.lpush(LIST_NOTE_TOTALTODAY+DateUtils.getTodayStr(), "n");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	/**
	 * 指定圈子 总帖子数
	 * @return
	 */
	public Long totalCount(String forumId){
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			Long total = 0L;
			try{
				total = jedis.llen(LIST_NOTE_TOTALCOUNT+forumId);
			}catch(Exception e){}
			if(total>0){
				//在缓存里取总数，如果有值，则直接返回，否则得进行初始化。
				logger.debug("帖子总数 缓存取值 : "+total);
				return total;
			}
			NoteCriteria noteCriteria = new NoteCriteria();
			noteCriteria.createCriteria().andForumIdEqualTo(forumId);
			int count = noteMapper.countByExample(noteCriteria);
			logger.debug("初始化 帖子总数 数据库取值 : "+count);
			String[] array = new String[count];
			for(int i=0;i<count;i++){
				array[i] = "n";
			}
			if(array!=null&&array.length>0)
				jedis.lpush(LIST_NOTE_TOTALCOUNT+forumId, array);
			return Long.valueOf(count);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}finally{
			redisPool.closeConn(jedis);
		}
		return -1L;
	}
	
	/**
	 * 指定圈子 当天的帖子总数
	 * @return
	 */
	public Long totalToday(String forumId){
		ShardedJedis jedis = null;
		String today = DateUtils.getTodayStr();
		try{
			jedis = redisPool.getConn();
			Long total = 0L;
			try{
				total = jedis.llen(LIST_NOTE_TOTALTODAY+forumId+":"+today);
			}catch(Exception e){}
			if(total>0){
				//在缓存里取总数，如果有值，则直接返回，否则得进行初始化。
				logger.debug("今天 帖子总数 缓存取值 : "+total);
				return total;
			}
			NoteCriteria noteCriteria = new NoteCriteria();
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
			criteria.andCtGreaterThan(DateUtils.minusDays(new Date(),1));//大于昨天，就是今天
			criteria.andForumIdEqualTo(forumId);
			int count = noteMapper.countByExample(noteCriteria);
			logger.debug("初始化 今天 帖子总数 数据库取值 : "+count);
			String[] array = new String[count];
			for(int i=0;i<count;i++){
				array[i] = "n";
			}
			jedis.lpush(LIST_NOTE_TOTALTODAY+forumId+":"+today, array);
			return Long.valueOf(count);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}finally{
			redisPool.closeConn(jedis);
		}
		return -1L;
	}
	
}
