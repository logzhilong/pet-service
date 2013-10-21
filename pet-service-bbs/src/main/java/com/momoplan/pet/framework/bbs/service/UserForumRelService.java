package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.bean.ClientRequest;


public interface UserForumRelService {
	/**
	 * 
	 * 退出圈子
	 * @return
	 */
	public Object quitForum(ClientRequest ClientRequest);
	
	/**
	 * 关注圈子
	 * @return
	 */
	public Object attentionForum(ClientRequest ClientRequest);
	
}
