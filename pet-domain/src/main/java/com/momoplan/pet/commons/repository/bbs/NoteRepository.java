package com.momoplan.pet.commons.repository.bbs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.cache.pool.StorePool;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.repository.CacheKeysConstance;

public class NoteRepository implements CacheKeysConstance{

	private static Logger logger = LoggerFactory.getLogger(NoteRepository.class);
	private static Gson gson = MyGson.getInstance();
	
	private StorePool storePool= null;
	private MapperOnCache mapperOnCache = null;
	private NoteMapper noteMapper = null;
	
	@Autowired
	public NoteRepository(StorePool storePool, MapperOnCache mapperOnCache,
			NoteMapper noteMapper) {
		super();
		this.storePool = storePool;
		this.mapperOnCache = mapperOnCache;
		this.noteMapper = noteMapper;
	}
	/**
	 * 发帖
	 * @param po
	 * @throws Exception
	 */
	public void insertOrUpdateSelective(Note po,NoteState noteState)throws Exception{
		if(NoteState.AUDIT.equals(noteState)){
			logger.debug("审核中的帖子【入库】:"+po.toString());
			mapperOnCache.insertSelective(po,po.getId());
		}else{
			logger.debug("审核后的帖子【更新】:"+po.toString());
			mapperOnCache.updateByPrimaryKeySelective(po, po.getId());
		}
		String forumId = po.getForumId() ; 
		//帖子总数列表
		String totalCountKey = LIST_NOTE_TOTALCOUNT+forumId;
		//当天帖子总数
		String totalTodayKey = LIST_NOTE_TOTALTODAY+forumId+":"+DateUtils.getTodayStr();
		Jedis jedis = null;
		try{
			if(NoteState.PASS.equals(noteState)){//如果审核通过了，就加入缓存中
				logger.debug("审核通过的帖子加入缓存.");
				jedis = storePool.getConn();
				lpushTotalCount(jedis,totalCountKey,forumId);
				lpushTotalToday(jedis,totalTodayKey,forumId);
			}
			if(NoteState.REJECT.equals(noteState)||NoteState.DELETE.equals(noteState)||NoteState.REPORT.equals(noteState)){//审核拒绝、删除、被举报，都要清理缓存的计数器
				logger.debug(noteState.getName()+" 清理计数器 -1");
				jedis = storePool.getConn();
				lpopTotalCount(jedis,totalCountKey);
				lpopTotalToday(jedis,totalTodayKey);
			}
		}catch(Exception e){
			logger.error("insertSelective",e);
		}finally{
			storePool.closeConn(jedis);
		}
	}
	private void lpushTotalCount(Jedis jedis,String key,String forumId){
		if(!jedis.exists(key)){
			initTotalCount(jedis, key, forumId);
		}else{//初始化时的总数，已经包括了当前的这次请求
			jedis.lpush(key, "n");
		}
	}
	private void lpushTotalToday(Jedis jedis,String key,String forumId){
		if(!jedis.exists(key)){
			initTotalToday(jedis, key, forumId);
		}else{//初始化时的总数，已经包括了当前的这次请求
			jedis.lpush(key, "n");
		}
	}
	
	private void lpopTotalCount(Jedis jedis,String key){
		if(jedis.exists(key)){
			jedis.lpop(key);
		}
	}
	private void lpopTotalToday(Jedis jedis,String key){
		if(jedis.exists(key)){
			jedis.lpop(key);
		}
	}

	/**
	 * 初始化 指定圈子 总帖子数
	 * @param jedis
	 * @param key
	 * @param forumId
	 * @return
	 */
	private Long initTotalCount(Jedis jedis,String key,String forumId){
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		List<String> stateList = new ArrayList<String>();
		stateList.add(NoteState.REJECT.getCode());//审核拒绝
		stateList.add(NoteState.AUDIT.getCode());//审核中
		stateList.add(NoteState.REPORT.getCode());//被举报
		stateList.add(NoteState.DELETE.getCode());//被删除
		//以上状态不显示
		criteria.andStateNotIn(stateList);
		criteria.andForumIdEqualTo(forumId);
		int count = noteMapper.countByExample(noteCriteria);
		logger.debug("key="+key+" forumId="+forumId+ " 初始化 帖子总数 数据库取值 : "+count);
		String[] array = new String[count];
		for(int i=0;i<count;i++){
			array[i] = "n";
		}
		if(array!=null&&array.length>0)
			jedis.lpush(key, array);
		return Long.valueOf(count);
	}
	
	/**
	 * 指定圈子 总帖子数
	 * @return
	 */
	public Long totalCount(String forumId){
		Jedis jedis = null;
		String key = LIST_NOTE_TOTALCOUNT+forumId;
		try{
			jedis = storePool.getConn();
			Long total = 0L;
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
	
	private Long initTotalToday(Jedis jedis,String key,String forumId){
		
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		List<String> stateList = new ArrayList<String>();
		stateList.add(NoteState.REJECT.getCode());//审核拒绝
		stateList.add(NoteState.AUDIT.getCode());//审核中
		stateList.add(NoteState.REPORT.getCode());//被举报
		stateList.add(NoteState.DELETE.getCode());//被删除
		//以上状态不显示
		criteria.andStateNotIn(stateList);
		criteria.andForumIdEqualTo(forumId);
		Date d = DateUtils.minusDays(new Date(),1);
		String dd = DateUtils.formatDate(d)+" 23:59:59";
		d = DateUtils.getDate(dd,DateUtils.DEFAULT_DATETIME_FORMAT);
		criteria.andCtGreaterThan(d);//大于昨天，就是今天
		
		int count = noteMapper.countByExample(noteCriteria);
		logger.debug("初始化 今天 帖子总数 数据库取值 : "+count);
		String[] array = new String[count];
		for(int i=0;i<count;i++){
			array[i] = "n";
		}
		jedis.lpush(key, array);
		return Long.valueOf(count);
	}
	/**
	 * 指定圈子 当天的帖子总数
	 * @return
	 */
	public Long totalToday(String forumId){
		Jedis jedis = null;
		String today = DateUtils.getTodayStr();
		String key = LIST_NOTE_TOTALTODAY+forumId+":"+today;
		try{
			jedis = storePool.getConn();
			Long total = 0L;
			try{
				total = jedis.llen(key);
			}catch(Exception e){}
			if(jedis.exists(key) && total>0){
				//在缓存里取总数，如果有值，则直接返回，否则得进行初始化。
				logger.debug("今天 帖子总数 缓存取值 : "+total);
				return total;
			}
			return initTotalToday(jedis, key, forumId);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}finally{
			storePool.closeConn(jedis);
		}
		return 0L;
	}
	public void flushTopNoteByFid(String fid)throws Exception{
		Jedis jedis = null;
		String key = LIST_NOTE_TOP+fid;
		logger.debug(key+" 刷新置顶缓存...");
		try{
			jedis = storePool.getConn();
			jedis.del(key);
			logger.debug(key+" 刷新置顶缓存...OK.");
		}catch(Exception e){
			logger.debug(key+" 刷新置顶缓存...ERROR.");
			logger.error(e.getMessage(),e);
		}finally{
			storePool.closeConn(jedis);
		}
	}
	/**
	 * 获取某个圈子置顶帖子的列表，缓存这个列表到 key-value ，当编辑这个圈子的置顶时，删除这个列表缓存即可
	 * @param fid
	 * @return
	 * @throws Exception
	 */
	public List<Note> getTopNoteByFid(String fid)throws Exception{
		Jedis jedis = null;
		try{
			jedis = storePool.getConn();
			String key = LIST_NOTE_TOP+fid;
			if(!jedis.exists(key)||jedis.llen(key)<1){
				jedis.del(key);
				// 获取置顶帖子
				NoteCriteria noteCriteria1 = new NoteCriteria();
				noteCriteria1.setMysqlOffset(0);
				noteCriteria1.setMysqlLength(5);
				noteCriteria1.setOrderByClause("ct desc");
				NoteCriteria.Criteria criteria1 = noteCriteria1.createCriteria();
				if (!"0".equals(fid)) {
					criteria1.andForumIdEqualTo(fid);
				}
				criteria1.andIsTopEqualTo(true);
				criteria1.andIsDelEqualTo(false);
				criteria1.andTypeEqualTo("0");//0 帖子
				List<Note> notelist = noteMapper.selectByExample(noteCriteria1);
				for(int i=(notelist.size()-1);i>-1;i--){
					Note n = notelist.get(i);
					String json = gson.toJson(n);
					jedis.lpush(key, json);
					logger.debug("缓存精华=="+json);
				}
			}
			List<String> list = jedis.lrange(key, 0, -1);
			List<Note> listNote = new ArrayList<Note>();
			for(String json : list){
				Note n = gson.fromJson(json, Note.class);
				listNote.add(n);
			}
			return listNote;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			storePool.closeConn(jedis);
		}
		return null;
	}
	
	public Long getClickCount(String noteId,Long min){
		Jedis jedis = null;
		String key = LIST_NOTE_CLICK_COUNT+noteId;
		try{
			jedis = storePool.getConn();
			if(!jedis.exists(key)||jedis.llen(key)<1){
				updateClickCount(noteId,min);
			}
			return jedis.llen(key);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			storePool.closeConn(jedis);
		}
		return min+1;
	}
	
	/**
	 * 更新帖子的点击次数，从指定值开始
	 * @param noteId
	 * @param start
	 */
	public void updateClickCount(String noteId,Long min){
		Jedis jedis = null;
		String key = LIST_NOTE_CLICK_COUNT+noteId;
		try{
			jedis = storePool.getConn();
			Long end = 1L;
			if(!jedis.exists(key)||jedis.llen(key)<1){
				end = min;
			}
			for(long s=0;s<end;s++){
				jedis.lpush(key, "1");
			}
			logger.debug("更新点击次数： key="+key+" end="+end);
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}finally{
			storePool.closeConn(jedis);
		}
	}
	
}
