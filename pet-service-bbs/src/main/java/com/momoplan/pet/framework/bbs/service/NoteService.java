package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.bean.ClientRequest;

public interface NoteService {

	/**
	 * 发送帖子
	 * @param ClientRequest
	 * @return
	 */
	public Object sendNote(ClientRequest ClientRequest) throws Exception;

	/**
	 * 根据帖子name搜索
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object searchNote(ClientRequest ClientRequest) throws Exception;

	/**
	 * 查看帖子详情
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object detailNote(ClientRequest ClientRequest) throws Exception;

	/**
	 * 删除帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object delNote(ClientRequest ClientRequest) throws Exception ;


	/**
	 * 根据id举报帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object reportNote(ClientRequest ClientRequest) throws Exception;
	
	
	
	/**
	 * 更新帖子点击数
	 * @param ClientRequest
	 * @return
	 */
	public Object updateClickCount(ClientRequest ClientRequest) throws Exception;
	
	/**
	 * 我发表过的帖子列表
	 * 
	 */
	public Object getMyNotedListByuserid(ClientRequest ClientRequest) throws Exception;
	
	/**
	 *某圈子今日新增帖子列表
	 * 
	 */
	public Object getTodayNewNoteListByFid(ClientRequest ClientRequest)throws Exception;
	/**
	 * 获取最新帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object newNoteByFid(ClientRequest ClientRequest) throws Exception;
	/**
	 * 获取全站精华
	 * @param ClientRequest
	 * @return
	 */
	public Object getEuteNoteList(ClientRequest ClientRequest) throws Exception;
	
	/**
	 * 全局最新回复(根据回复时间将帖子显示{不显示置顶帖子})
	 */
	public Object getNewReplysByReplyct(ClientRequest ClientRequest) throws Exception;
	
}
