package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 * 获取被推荐的圈子列表
 * @author  liangc
 */
@Component("getUserForumCondition")
public class GetUserForumConditionHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetUserForumConditionHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			List<UserForumCondition> list = forumService.getUserForumCondition();
			logger.debug("获取被推荐的圈子列表 【成功】");
			rtn = new Success(true,list).toString();
		}catch(Exception e){
			logger.debug("获取被推荐的圈子列表 【失败】");
			logger.error("login : ",e);
			rtn = new Success(false,e.toString()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
