package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;

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
	 * 根据帖子id获取所有回复
	 * @param noteid帖子
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<NoteSubVo> getReplyByNoteId(String noteid,int pageNo,int pageSize) throws Exception;
	
}
