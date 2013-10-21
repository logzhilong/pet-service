package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import com.momoplan.pet.framework.bbs.vo.ForumNode;

public interface ForumService {

	/**
	 * 获取所有的圈子树，数组第一个元素是我关注的圈子
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ForumNode> getAllForumAsTree(String userId) throws Exception;

}