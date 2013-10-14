package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;

public interface NoteService {

	/**
	 * 发送帖子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object sendNote(BbSClientRequest bbsClientRequest);

	/**
	 * 根据帖子name搜索
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object searchNote(BbSClientRequest bbsClientRequest);

	/**
	 * 查看帖子详情
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object detailNote(BbSClientRequest bbsClientRequest);

	/**
	 * 删除帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object delNote(BbSClientRequest bbsClientRequest);


	/**
	 * 根据id举报帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object reportNote(BbSClientRequest bbsClientRequest);
	
	
	
	/**
	 * 更新帖子点击数
	 * @param bbsClientRequest
	 * @return
	 */
	public Object updateClickCount(BbSClientRequest bbsClientRequest);
	/**
	 * 获取某圈子下所有帖子数
	 * 
	 */
	public Object getNoteCountByForumid(BbSClientRequest bbsClientRequest);
	/**
	 * 我发表过的帖子列表
	 * 
	 */
	public Object getMyNotedListByuserid(BbSClientRequest bbsClientRequest);
	/**
	 *今日新增帖子列表
	 * 
	 */
	public Object getTodayNewNoteList(BbSClientRequest bbsClientRequest);
	
	
	/**
	 * 获取最新帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object newNote(BbSClientRequest bbsClientRequest);
	/**
	 *某圈子今日新增帖子列表
	 * 
	 */
	public Object getTodayNewNoteListByFid(BbSClientRequest bbsClientRequest);
	/**
	 * 某圈子获取最新帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	public Object newNoteByFid(BbSClientRequest bbsClientRequest);
}
