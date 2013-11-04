package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;


public interface UserForumRelService {
	/**
	 * 退出圈子
	 * @param userForumRel
	 *  userId用户id,forumid被取消关注圈子id
	 * @return
	 * @throws Exception
	 */
	public Object quitForum(UserForumRel userForumRel) throws Exception;
	
	/**
	 * 关注圈子
	 * @param userForumRel
	 * userId用户id,forumid被关注圈子id
	 * @return
	 * @throws Exception
	 */
	public void attentionForum(UserForumRel userForumRel) throws Exception;
	
}
