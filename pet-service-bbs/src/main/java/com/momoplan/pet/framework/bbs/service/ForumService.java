package com.momoplan.pet.framework.bbs.service;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.bbs.po.Forum;



public interface ForumService {
			/**
			 * 获取所有父级圈子
			 * @param ClientRequest
			 * @return
			 */
			public Object getForumList(ClientRequest ClientRequest);
			/**
			 * 根据圈子id获取圈子
			 * @param ClientRequest
			 * @return
			 */
			public Forum getForumByid(Forum forum);
			
			/**
			 * 根据父级圈子id获取子集圈子
			 * @param ClientRequest
			 * @return
			 */
			public Object getSunForumListByForumid(ClientRequest ClientRequest);
			
			
			
			
			
}
