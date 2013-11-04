package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;

public interface NoteSubService {
	
	/**
	 * 回复帖子
	 * @param noteSub
	 * userId用户id,noteId帖子id,content帖子内容,area区域,pid所回复帖子的id(上一级帖子id)
	 * @return
	 * @throws Exception
	 */
	public String replyNote(NoteSub noteSub) throws Exception;
	/**
	 * 
	 * 根据回帖id获取回帖
	 */
	public Object getReplyNoteSubByReplyNoteid(ClientRequest ClientRequest) throws Exception;
	/**
	 * 根据帖子id获取所有回复
	 * @param noteid帖子
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public Object getAllReplyNoteByNoteid(String noteid,int pageNo,int pageSize) throws Exception;
	
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(ClientRequest ClientRequest) throws Exception;
}
