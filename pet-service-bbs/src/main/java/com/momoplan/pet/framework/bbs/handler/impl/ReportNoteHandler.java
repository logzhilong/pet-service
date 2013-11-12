package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.repository.bbs.NoteState;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.service.CacheKeysConstance;

/**
 * 举报帖子
 */
@Component("reportNote")
public class ReportNoteHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(ReportNoteHandler.class);
	@Autowired
	private RedisPool redisPool = null;

	/**
	 * 举报的阀值
	 */
	public static String REPORT_THRESHOLD = "report.threshold";

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String userid=getUseridFParamSToken(clientRequest);
			String noteId=PetUtil.getParameter(clientRequest, "noteId");
			String replyId=PetUtil.getParameter(clientRequest, "replyId");
			reportNote(userid,noteId);
			reportReply(userid,replyId);
			logger.debug("举报帖子成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			logger.debug("举报帖子失败 body="+gson.toJson(clientRequest));
			logger.error("reportNote : ",e);
			rtn = new Success(false,e.toString()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
	private void reportNote(String userid,String noteid)throws Exception{
		if(StringUtils.isEmpty(noteid))
			return;
		String threshold = commonConfig.get(REPORT_THRESHOLD, "20");
		logger.debug("//举报的阀值 "+threshold);
		int t = Integer.parseInt(threshold);
		ShardedJedis jedis = null;
		try{
			String key = CacheKeysConstance.HASH_NOTE_REPORT+noteid;
			jedis = redisPool.getConn();
			if(jedis.exists(key)&&StringUtils.isNotEmpty(jedis.hget(key, userid))){
				logger.debug("重复举报");
				throw new Exception("不能重复举报.");
			}
			logger.debug("//添加举报信息");
			jedis.hset(key, userid, noteid);
			Long count = jedis.hlen(key);
			logger.debug("//达到阀值以后，更新状态，并清空举信息");
			if(count>=t){
				Note note = mapperOnCache.selectByPrimaryKey(Note.class, noteid);
				note.setState(NoteState.REPORT.getCode());//被举报
				logger.debug("更改动态状态为 5 ; noteid="+noteid);
				mapperOnCache.updateByPrimaryKeySelective(note, noteid);
				jedis.del(key);
			}
		}catch(Exception e){
			logger.error("report",e);
			throw e;
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
	private void reportReply(String userid,String replyId)throws Exception{
		if(StringUtils.isEmpty(replyId))
			return;
		String threshold = commonConfig.get(REPORT_THRESHOLD, "20");
		logger.debug("//举报的阀值 "+threshold);
		int t = Integer.parseInt(threshold);
		ShardedJedis jedis = null;
		try{
			String key = CacheKeysConstance.HASH_REPLY_REPORT+replyId;
			jedis = redisPool.getConn();
			if(jedis.exists(key)&&StringUtils.isNotEmpty(jedis.hget(key, userid))){
				logger.debug("重复举报");
				throw new Exception("不能重复举报.");
			}
			logger.debug("//添加举报信息");
			jedis.hset(key, userid, replyId);
			Long count = jedis.hlen(key);
			logger.debug("//达到阀值以后，更新状态，并清空举信息");
			if(count>=t){
				NoteSub note = mapperOnCache.selectByPrimaryKey(NoteSub.class, replyId);
				note.setState(NoteState.REPORT.getCode());//被举报
				logger.debug("更改动态状态为 被举报 ; replyId="+replyId);
				mapperOnCache.updateByPrimaryKeySelective(note, replyId);
				jedis.del(key);
			}
		}catch(Exception e){
			logger.error("report",e);
			throw e;
		}finally{
			redisPool.closeConn(jedis);
		}
	}
	
}
