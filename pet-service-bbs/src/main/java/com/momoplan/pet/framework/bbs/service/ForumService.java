package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;



public interface ForumService {
			/**
			 * 获取所有父级圈子
			 * @param bbsClientRequest
			 * @return
			 */
			public Object getForumList(BbSClientRequest bbsClientRequest);
			/**
			 * 根据圈子id获取圈子
			 * @param bbsClientRequest
			 * @return
			 */
			public Forum getForumByid(Forum forum);
			
			/**
			 * 根据父级圈子id获取子集圈子
			 * @param bbsClientRequest
			 * @return
			 */
			public Object getSunForumListByForumid(BbSClientRequest bbsClientRequest);
			
			
			
			
			
}
