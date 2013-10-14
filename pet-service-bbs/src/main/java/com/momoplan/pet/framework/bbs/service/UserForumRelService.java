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
	
	/**
	 * 我关注的圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public Object getUserForumListbyUserid(BbSClientRequest bbsClientRequest);
	/**
	 * 根据用户id和圈子id查看是否关注该圈子
	 * @param bbsClientRequest
	 * @return
	 */
	public int isAttentionForum(String uid,String fid);
	
}
