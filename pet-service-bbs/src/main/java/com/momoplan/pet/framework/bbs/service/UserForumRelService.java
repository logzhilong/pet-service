package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;

public interface UserForumRelService {
	/**
	 * 
	 * 退出圈子
	 * @return
	 */
	public Object quitForum(BbSClientRequest bbsClientRequest);
	
	/**
	 * 关注圈子
	 * @return
	 */
	public Object attentionForum(BbSClientRequest bbsClientRequest);
	
}
