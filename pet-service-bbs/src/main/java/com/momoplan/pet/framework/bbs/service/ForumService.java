package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.framework.bbs.vo.ForumNode;

public interface ForumService {

	/**
	 * 获取所有的圈子树，数组第一个元素是我关注的圈子
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ForumNode> getAllForumAsTree(String userId) throws Exception;
	
	/**
	 * 获取被推荐的圈子列表
	 * @return
	 * @throws Exception
	 */
	public List<UserForumCondition> getUserForumCondition() throws Exception;
	
	/**
	 * 获取圈子
	 * @param petType
	 * @return
	 * @throws Exception
	 */
	public Forum getForum(String petType) throws Exception;
	
}