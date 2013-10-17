package com.momoplan.pet.framework.bbs.repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.framework.bbs.service.CacheKeysConstance;

@Repository
public class NoteSubRepository implements CacheKeysConstance{

	private static Logger logger = LoggerFactory.getLogger(NoteSubRepository.class);
	
	private RedisPool redisPool= null;
	private MapperOnCache mapperOnCache = null;
	private NoteMapper noteMapper = null;
	private NoteSubMapper noteSubMapper = null;
	
	@Autowired
	public NoteSubRepository(RedisPool redisPool, MapperOnCache mapperOnCache, NoteMapper noteMapper, NoteSubMapper noteSubMapper) {
		super();
		this.redisPool = redisPool;
		this.mapperOnCache = mapperOnCache;
		this.noteMapper = noteMapper;
		this.noteSubMapper = noteSubMapper;
	}

	/**
	 * 回帖
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(NoteSub po)throws Exception{
		//入库并加入缓存
		mapperOnCache.insertSelective(po, po);
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			//增加到 回帖总数列表
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, po.getNoteId());
			String forumId = note.getForumId();
			//forumId 对应栏目的 回帖总数
			jedis.lpush(LIST_NOTE_SUB_TOTALCOUNT+forumId, "n");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	/**
	 * 指定帖子 总回帖数
	 * @return
	 */
	public Long totalCount(String forumId){
		ShardedJedis jedis = null;
		try{
			jedis = redisPool.getConn();
			Long total = 0L;
			try{
				total = jedis.llen(LIST_NOTE_SUB_TOTALCOUNT+forumId);
			}catch(Exception e){}
			if(total>0){
				//在缓存里取总数，如果有值，则直接返回，否则得进行初始化。
				logger.debug("帖子总数 缓存取值 : "+total);
				return total;
			}
			NoteCriteria noteCriteria = new NoteCriteria();
			noteCriteria.createCriteria().andForumIdEqualTo(forumId);
			List<Note> noteList = noteMapper.selectByExample(noteCriteria);
			List<String> noteIds = new ArrayList<String>();
			for(Note note : noteList){
				noteIds.add(note.getId());
			}
			NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
			noteSubCriteria.createCriteria().andNoteIdIn(noteIds);
			int count = noteSubMapper.countByExample(noteSubCriteria);
			logger.debug("初始化 回帖总数 数据库取值 : "+count);
			String[] array = new String[count];
			for(int i=0;i<count;i++){
				array[i] = "n";
			}
			if(array!=null&&array.length>0)
				jedis.lpush(LIST_NOTE_SUB_TOTALCOUNT+forumId, array);
			return Long.valueOf(count);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}finally{
			redisPool.closeConn(jedis);
		}
		return -1L;
	}
	
}
