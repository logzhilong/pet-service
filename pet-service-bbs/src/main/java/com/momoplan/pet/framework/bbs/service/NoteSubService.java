package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;

public interface NoteSubService {
	/**
	 * 回复帖子
	 * 
	 */
	public Object replyNote(BbSClientRequest bbsClientRequest);
	/**
	 * 
	 * 根据回帖id获取回帖
	 */
	public Object getReplyNoteSubByReplyNoteid(BbSClientRequest bbsClientRequest);
	/**
	 * 获取当前帖子所有回复
	 */
	public Object getAllReplyNoteByNoteid(BbSClientRequest bbsClientRequest);
	/**
	 * 获取某圈子下所有回复数
	 * 
	 */
	public Object getNoteSubCountByForumid(BbSClientRequest bbsClientRequest);
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(BbSClientRequest bbsClientRequest);
}
