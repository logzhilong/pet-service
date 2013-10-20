package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.bbs.vo.ForumNode;



public interface ForumService {
			/**
			 * 获取所有父级圈子
			 * @param ClientRequest
			 * @return
			 */
			public Object getForumList(ClientRequest ClientRequest) throws Exception;
			/**
			 * 根据圈子id获取圈子
			 * @param ClientRequest
			 * @return
			 */
			public Forum getForumByid(Forum forum) throws Exception;
			
			/**
			 * 根据父级圈子id获取子集圈子
			 * @param ClientRequest
			 * @return
			 */
			public Object getSunForumListByForumid(ClientRequest ClientRequest)throws Exception;
			
			
			
			
			/**
			 * 获取所有圈子(父级和子集)
			 * @param ClientRequest
			 * @return
			 */
			public Object getAllForum(ClientRequest ClientRequest);
			
			
			public List<ForumNode> getAllForumAsTree(String userId)throws Exception;
			
			
			
			
			
}
