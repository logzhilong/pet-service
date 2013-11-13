package com.momoplan.pet.framework.manager.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;


public interface NoteService {
	/**
	 * 根据id获取帖子
	 * @param id帖子id
	 * @return
	 * @throws Exception
	 */
	public Note getNotebyid(String id)throws Exception;
	/**
	 * 增加或者修改帖子
	 * @param note帖子实体
	 * @throws Exception
	 */
	public void NoteAddOrUpdate(Note note) throws Exception;
	/**
	 * 删除帖子
	 * @param note帖子实体
	 * @throws Exception
	 */
	public void NoteDel(Note note) throws Exception;
	/**
	 * 更新帖子点击数(每次查看帖子调用)
	 * @param noteid帖子id
	 * @throws Exception
	 */
	public void updateClickCount(String noteid)throws Exception;
	/**
	 * 获取所有托管用户
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public  List<MgrTrustUser> trustUserslist(HttpServletRequest request)throws Exception;
	/**
	 * 获取已经删除帖子列表
	 * @param note
	 * @return
	 * @throws Exception
	 */
	public List<Note> getDeledNotes(Forum forum)throws Exception;
}
