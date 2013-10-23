package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.bean.ClientRequest;

public interface NoteSubService {
	/**
	 * 回复帖子
	 * 
	 */
	public Object replyNote(ClientRequest ClientRequest) throws Exception;
	/**
	 * 
	 * 根据回帖id获取回帖
	 */
	public Object getReplyNoteSubByReplyNoteid(ClientRequest ClientRequest) throws Exception;
	/**
	 * 获取当前帖子所有回复
	 */
	public Object getAllReplyNoteByNoteid(ClientRequest ClientRequest) throws Exception;
	
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(ClientRequest ClientRequest) throws Exception;
}
