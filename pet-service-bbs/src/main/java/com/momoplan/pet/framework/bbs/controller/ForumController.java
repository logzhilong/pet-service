package com.momoplan.pet.framework.bbs.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.service.NoteService;
import com.momoplan.pet.framework.bbs.service.NoteSubService;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;

@Controller
public class ForumController {
	
	private static Logger logger = LoggerFactory.getLogger(ForumController.class);
	
	@Autowired
	private ForumService forumService;
	@Autowired
	private NoteService noteService;
	@Autowired
	private NoteSubService noteSubService;
	@Autowired
	private UserForumRelService userForumRelService;
	

	/**
	 * 接收bbs用户请求
	 * 
	 * @param body
	 * @param response
	 * @throws Exception
	 */
	
	@RequestMapping("/forum/request.html")
	public @ResponseBody Object request(@RequestParam("body") String body) throws Exception {
		logger.info("bodyinput="+body);
		Object result = null;
		BbSClientRequest bbsClientRequest = new ObjectMapper().reader(BbSClientRequest.class).readValue(body);
		if (bbsClientRequest.getMethod().equals("sendNote")) {
			result = sendNote(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("replyNote")) {
			result = replyNote(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("searchNote")) {
			result = searchNote(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("detailNote")) {
			result = detailNote(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("delNote")) {
			result = delNote(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("attentionForum")) {
			result = attentionForum(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("quitForum")) {
			result = quitForum(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("newNote")) {
			result = newNote(bbsClientRequest);
		}
		if (bbsClientRequest.getMethod().equals("reportNote")) {
			result = reportNote(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getAllReplyNoteByNoteid")){
			result = getAllReplyNoteByNoteid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getReplyNoteSubByReplyNoteid")){
			result = getReplyNoteSubByReplyNoteid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getNoteCountByForumid")){
			result = getNoteCountByForumid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getNoteSubCountByForumid")){
			result = getNoteSubCountByForumid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getMyNotedListByuserid")){
			result = getMyNotedListByuserid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getMyReplyNoteListByUserid")){
			result = getMyReplyNoteListByUserid(bbsClientRequest);
		}
		
		if(bbsClientRequest.getMethod().equals("getTodayNewNoteList")){
			result = getTodayNewNoteList(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getForumList")){
			result = getForumList(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getUserForumListbyUserid")){
			result = getUserForumListbyUserid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getSunForumListByForumid")){
			result = getSunForumListByForumid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("getTodayNewNoteListByFid")){
			result = getTodayNewNoteListByFid(bbsClientRequest);
		}
		if(bbsClientRequest.getMethod().equals("newNoteByFid")){
			result = newNoteByFid(bbsClientRequest);
		}
		
		logger.info("bodyoutput="+result.toString());
		return result;
	}
	
	
	/** 某圈子最新帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object newNoteByFid(BbSClientRequest bbsClientRequest){
		return noteService.newNoteByFid(bbsClientRequest);
	}
	/** 某圈子今日最新帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getTodayNewNoteListByFid(BbSClientRequest bbsClientRequest){
		return noteService.getTodayNewNoteListByFid(bbsClientRequest);
	}
	/** 根据父级圈子id获取子圈子集合
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getSunForumListByForumid(BbSClientRequest bbsClientRequest){
		return forumService.getSunForumListByForumid(bbsClientRequest);
	}
	/** 我关注的圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getUserForumListbyUserid(BbSClientRequest bbsClientRequest){
		return userForumRelService.getUserForumListbyUserid(bbsClientRequest);
	}
	
	
	/**
	 * 获取所有父级圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getForumList(BbSClientRequest bbsClientRequest){
		return forumService.getForumList(bbsClientRequest);
	}
	
	/**
	 * 根据id举报帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object reportNote(BbSClientRequest bbsClientRequest){
		return noteService.reportNote(bbsClientRequest);
	}
	
	/**
	 * 获取最新帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object newNote(BbSClientRequest bbsClientRequest){
		return noteService.newNote(bbsClientRequest);
	}
	
	/**
	 * 
	 * 退出圈子
	 * @return
	 */
	public Object quitForum(BbSClientRequest bbsClientRequest){
		return userForumRelService.quitForum(bbsClientRequest);
	}
	
	/**
	 * 关注圈子
	 * @return
	 */
	public Object attentionForum(BbSClientRequest bbsClientRequest){
		return userForumRelService.attentionForum(bbsClientRequest);
	}
	
	/**
	 * 删除帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object delNote(BbSClientRequest bbsClientRequest){
			return noteService.delNote(bbsClientRequest);
	}
	
	
		
	/**
	 * 根据id查看帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object detailNote(BbSClientRequest bbsClientRequest){
		//TODO 客户端每请求一次(查看帖子详情),则另Note(CilentCount属性)更新+1
		noteService.updateClickCount(bbsClientRequest);
			
			
		return noteService.detailNote(bbsClientRequest);
	}
	
	/**
	 * 根据name搜索帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object searchNote(BbSClientRequest bbsClientRequest){
		return noteService.searchNote(bbsClientRequest);
	}
	/**
	 * 发送帖子
	 * 
	 */
	private Object sendNote(BbSClientRequest bbsClientRequest) {
		return noteService.sendNote(bbsClientRequest);
	}
	

	/**
	 * 回复帖子
	 * 
	 */
	public Object replyNote(BbSClientRequest bbsClientRequest) {
		return noteSubService.replyNote(bbsClientRequest);
	}
	
	
	/**
	 * 获取当前帖子所有回复
	 */
	public Object getAllReplyNoteByNoteid(BbSClientRequest bbsClientRequest) {
		return noteSubService.getAllReplyNoteByNoteid(bbsClientRequest);
	}
	
	/**
	 * 根据回帖id获取回帖
	 * 
	 */
	public Object getReplyNoteSubByReplyNoteid(BbSClientRequest bbsClientRequest) {
		return noteSubService.getReplyNoteSubByReplyNoteid(bbsClientRequest);
	}
	
	/**
	 * 获取某圈子下所有帖子数
	 * 
	 */
	public Object getNoteCountByForumid(BbSClientRequest bbsClientRequest){
		return noteService.getNoteCountByForumid(bbsClientRequest);
	}
	/**
	 * 获取某圈子下所有回复数
	 * 
	 */
	public Object getNoteSubCountByForumid(BbSClientRequest bbsClientRequest){
		return noteSubService.getNoteSubCountByForumid(bbsClientRequest);
	}
	/**
	 * 我发表过的帖子列表
	 * 
	 */
	public Object getMyNotedListByuserid(BbSClientRequest bbsClientRequest){
		return noteService.getMyNotedListByuserid(bbsClientRequest);
	}
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(BbSClientRequest bbsClientRequest){
		return noteSubService.getMyReplyNoteListByUserid(bbsClientRequest);
	}
	/**
	 *今日新增帖子列表
	 * 
	 */
	public Object getTodayNewNoteList(BbSClientRequest bbsClientRequest){
		return noteService.getTodayNewNoteList(bbsClientRequest);
	}
	
}
