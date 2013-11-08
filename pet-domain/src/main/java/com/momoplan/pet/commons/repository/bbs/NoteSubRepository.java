package com.momoplan.pet.commons.repository.bbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.commons.repository.CacheKeysConstance;

public class NoteSubRepository implements CacheKeysConstance{

	private static Logger logger = LoggerFactory.getLogger(NoteSubRepository.class);
	private static Gson gson = MyGson.getInstance();

	private StorePool storePool = null;
	private MapperOnCache mapperOnCache = null;
	private NoteMapper noteMapper = null;
	private NoteSubMapper noteSubMapper = null;
	
	@Autowired
	public NoteSubRepository(StorePool storePool, MapperOnCache mapperOnCache,
			NoteMapper noteMapper, NoteSubMapper noteSubMapper) {
		super();
		this.storePool = storePool;
		this.mapperOnCache = mapperOnCache;
		this.noteMapper = noteMapper;
		this.noteSubMapper = noteSubMapper;
	}
	/**
	 * 获取我回复过的帖子的ID集合
	 * @param userId
	 * @return
	 */
	public List<String> getNoteIdListOfMyReply(String userId){
		String key = USER_REPLY_NOTE+userId;
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			if(!jedis.exists(key)){
				NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
				noteSubCriteria.createCriteria().andUserIdEqualTo(userId);
				List<NoteSub> l = noteSubMapper.selectByExample(noteSubCriteria);
				if(l!=null){
					for(NoteSub s : l){
						jedis.hset(key, s.getNoteId(), "1");
						logger.debug("初始化我参与过的帖子缓存 userid="+s.getUserId()+" ; noteid="+s.getNoteId());
					}
				}
			}
			Set<String> set = jedis.hkeys(key);
			if(set!=null&&set.size()>0){
				List<String> list = new ArrayList<String>();
				list.addAll(set);
				return list;
			}
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}finally{
			storePool.closeConn(jedis);
		}
		return null;
	}
	/**
	 * 回帖
	 * @param po
	 * @throws Exception
	 */
	public void insertSelective(NoteSub po)throws Exception{
		logger.debug("入库并加入缓存");
		Long seq = totalReply(po.getNoteId());
		if(seq>0)
			seq+=1;
		else
			seq = 1L;
		po.setSeq((int)(seq+0));
		mapperOnCache.insertSelective(po, po.getId());
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			logger.debug("增加到 回帖总数列表");
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, po.getNoteId());
			String forumId = note.getForumId();
			logger.debug("forumId 圈子的 回帖总数");
			lpushNoteSubTotalCount(jedis,LIST_NOTE_SUB_TOTALCOUNT+forumId,forumId);
			logger.debug("需求是：最新的回帖排在最下面,【队列】结构");
			rpushListNoteSub(jedis,po);
			logger.debug("用户回过的帖子，记录在一个 hash 结构中 "+USER_REPLY_NOTE+po.getUserId());
			jedis.hset(USER_REPLY_NOTE+po.getUserId(), po.getNoteId(), "0");
		}catch(Exception e){
			//TODO 这种异常很严重啊，要发邮件通知啊
			logger.error("insertSelective",e);
		}finally{
			storePool.closeConn(jedis);
		}
	}
	
	/**
	 * 针对 forumId 圈子的回帖总数
	 * @param jedis
	 * @param key
	 * @param forumId
	 */
	private void lpushNoteSubTotalCount(Jedis jedis,String key,String forumId){
		if(!jedis.exists(key))
			initTotalCount(jedis,key,forumId);
		else//初始化时的总数，已经包括了当前的这次请求
			jedis.lpush(key, "n");
	}
	
	/**
	 * 获取noteId的回帖数
	 * @param noteId
	 * @return
	 */
	public Long totalReply(String noteId){
		String key = LIST_NOTE_SUB+noteId;
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			if(!jedis.exists(key)||jedis.llen(key)==0){
				initListNoteSub(jedis,noteId,key);
			}
			Long totalReply = jedis.llen(key);
			logger.debug("noteId="+noteId+" ; totalReply="+totalReply);
			return totalReply;
		}catch(Exception e){
			logger.debug("获取回复总数 ERROR="+e.getMessage());
		}finally{
			storePool.closeConn(jedis);
		}
		return 0L;
	}

	/**
	 * 获取noteId的回帖数
	 * @param noteId
	 * @return
	 */
	public List<NoteSub> getReplyListByNoteId(String noteId,int pageSize,int pageNo){
		pageSize-=1;//因为下脚标在0开始，所以取20条记录时 0～19，size 是要取的条数
		String key = LIST_NOTE_SUB+noteId;
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			if(!jedis.exists(key)||jedis.llen(key)==0){
				//初始化将加载所有的数据到缓存
				initListNoteSub(jedis,noteId,key);
			}
			List<String> list = jedis.lrange(key, pageNo*pageSize, (pageNo+1)*pageSize);
			if(list!=null){
				List<NoteSub> nsl = new ArrayList<NoteSub>(list.size());
				for(String j : list){
					NoteSub ns = gson.fromJson(j, NoteSub.class);
					nsl.add(ns);
				}
				return nsl;
			}
		}catch(Exception e){
			logger.error("totalReply",e);
		}finally{
			storePool.closeConn(jedis);
		}
		return null;
	}
	
	
	/**
	 * 需求是：最新的回帖排在最下面,【队列】结构
	 * @param po
	 */
	private void rpushListNoteSub(Jedis jedis,NoteSub po) throws Exception {
		String key = LIST_NOTE_SUB+po.getNoteId();
		try {
			//init key 对应的队列
			if(!jedis.exists(key)||jedis.llen(key)==0){
				//初始化将加载所有的数据到缓存
				initListNoteSub(jedis,po.getNoteId(),key);
			}else{
				//如果不初始化，则将最新的放入缓存即可
				NoteSub noteSub = mapperOnCache.selectByPrimaryKey(po.getClass(), po.getId());
				String noteSubJson = gson.toJson(noteSub);
				jedis.rpush(key,noteSubJson);
				logger.debug(key+" rpush "+noteSubJson);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	/**
	 * 初始化缓存堆，如果手动清除了缓存，此处可以重新构建
	 * @param jedis
	 * @param po
	 * @param key
	 * @throws Exception 
	 */
	private void initListNoteSub(Jedis jedis,String noteId,String key) throws Exception{
		NoteSubCriteria noteCriteria = new NoteSubCriteria();
		NoteSubCriteria.Criteria criteria = noteCriteria.createCriteria();
		criteria.andNoteIdEqualTo(noteId);
		noteCriteria.setOrderByClause("ct asc");
		List<NoteSub> noteList = noteSubMapper.selectByExample(noteCriteria);
		logger.info("初始化 回帖 缓存队列 key="+key);
		logger.info("初始化 回帖 缓存队列 noteSubList="+noteList);
		String[] arr = new String[noteList.size()];
		int size = 0;
		if(noteList!=null&&noteList.size()>0){
			int i=0;
			for(NoteSub note : noteList){
				note = mapperOnCache.selectByPrimaryKey(NoteSub.class, note.getId());//这样会带上内容
				String json = gson.toJson(note);
				arr[i++] = json;
				logger.debug(key+" rpush "+json);
			}
			jedis.rpush(key, arr);
			size = noteList.size();
		}
		logger.info("初始化 回帖 缓存队列 完成. size="+size);
	}
	
	/**
	 * 初始化 forumId 圈子的回帖总数
	 * @param jedis
	 * @param key
	 * @param forumId
	 * @return
	 */
	private Long initTotalCount(Jedis jedis,String key,String forumId){
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.createCriteria().andForumIdEqualTo(forumId);
		List<Note> noteList = noteMapper.selectByExample(noteCriteria);
		List<String> noteIds = new ArrayList<String>();
		if(noteList!=null&&noteList.size()>0)
		for(Note note : noteList){
			noteIds.add(note.getId());
		}
		int count = 0;
		if(noteIds.size()>0){
			NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
			noteSubCriteria.createCriteria().andNoteIdIn(noteIds);
			count = noteSubMapper.countByExample(noteSubCriteria);
			logger.debug("初始化 回帖总数 数据库取值 " + key + " : "+count);
			String[] array = new String[count];
			for(int i=0;i<count;i++){
				array[i] = "n";
			}
			if(array!=null&&array.length>0)
				jedis.lpush(key, array);
		}
		return Long.valueOf(count);
	}
	
	/**
	 * 指定帖子 总回帖数
	 * @return
	 */
	public Long totalCount(String forumId){
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			Long total = 0L;
			String key = LIST_NOTE_SUB_TOTALCOUNT+forumId;
			try{
				total = jedis.llen(key);
			}catch(Exception e){}
			if(jedis.exists(key) && total>0){
				//在缓存里取总数，如果有值，则直接返回，否则得进行初始化。
				logger.debug("帖子总数 缓存取值 : "+total);
				return total;
			}
			return initTotalCount(jedis,key,forumId);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}finally{
			storePool.closeConn(jedis);
		}
		return 0L;
	}
	
}
