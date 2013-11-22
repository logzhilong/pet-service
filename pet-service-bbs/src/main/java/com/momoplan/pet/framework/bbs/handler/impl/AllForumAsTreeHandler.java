package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.vo.ForumNode;

/**
 * 获取圈子(父圈套子圈)集合
 * 
 * @author qiyongc
 */
@Component("getAllForumAsTree")
public class AllForumAsTreeHandler extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(AllForumAsTreeHandler.class);

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try {
			String userid = getUseridFParamSToken(clientRequest);
			List<ForumNode> list = forumService.getAllForumAsTree(userid);
			logger.debug("获取圈子(父圈套子圈)集合 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true, list).toString();
		} catch (Exception e) {
			logger.debug("获取圈子(父圈套子圈)集合 body=" + gson.toJson(clientRequest));
			logger.error("getAllForumAsTree : ", e);
			rtn = new Success(sn,false, e.getMessage()).toString();
		} finally {
			writeStringToResponse(rtn, response);
		}
	}
}
