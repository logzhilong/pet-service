package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;
import com.momoplan.pet.framework.bbs.vo.PageBean;

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
	 * 取回复信息，如果不传 userId 则取所有回复，否则只取指定回复
	 * @param noteId
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public PageBean<NoteSubVo> getReplyByNoteId(String noteId,String userId,int pageNo,int pageSize) throws Exception;
	
}
