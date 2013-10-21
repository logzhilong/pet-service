package com.momoplan.pet.framework.manager.service;

import com.momoplan.pet.commons.domain.bbs.po.Note;


public interface NoteService {
	/**
	 * 获取帖子
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Note getNotebyid(String id)throws Exception;
	/**
	 * 增加或者修改帖子
	 * @param note
	 * @throws Exception
	 */
	public void NoteAddOrUpdate(Note note) throws Exception;
	/**
	 * 删除帖子
	 * @param note
	 * @throws Exception
	 */
	public void NoteDel(Note note) throws Exception;
	/**
	 * 更新帖子点击数
	 */
	public void updateClickCount(String noteid)throws Exception;
}
