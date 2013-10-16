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
	
	/**
	 * 我关注的圈子
	 * @param ClientRequest
	 * @return
	 */
	public Object getUserForumListbyUserid(ClientRequest ClientRequest);
	/**
	 * 根据用户id和圈子id查看是否关注该圈子
	 * @param ClientRequest
	 * @return
	 */
	public int isAttentionForum(String uid,String fid);
	
}
