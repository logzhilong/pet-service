package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;


public interface UserForumService {
		/**
		 * 获取默认关注圈子列表
		 * @return
		 * @throws Exception
		 */
		public List<UserForumCondition> GetUserForumList()throws Exception;
		/**
		 * 增加或者修改默认关注圈子
		 * @throws Exception
		 */
		public void addOrUpdateUserForum(UserForumCondition condition)throws Exception;
		/**
		 * 删除默认圈子
		 * @param condition
		 * @throws Exception
		 */
		public void deleteUserForoum(UserForumCondition condition)throws Exception;
		/**
		 *通过id获取默认关注圈子
		 * @param condition
		 * @return
		 * @throws Exception
		 */
		public UserForumCondition getuserforumByid(UserForumCondition condition)throws Exception;
		/**
		 * 获取圈子集合做级联
		 * @return
		 * @throws Exception
		 */
		public List<Forum> getForumlist() throws Exception ;
			
}

