package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 * 根据宠物类型获取圈子
 */
@Component("getForum")
public class GetForumHandler extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(GetForumHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try {
			String petType = PetUtil.getParameter(clientRequest, "petType");
			logger.debug("根据宠物类型获取圈子 成功 body=" + gson.toJson(clientRequest));
			Forum po = forumService.getForum(petType);
			rtn = new Success(true,po).toString();
		} catch (Exception e) {
			logger.debug("根据宠物类型获取圈子 失败 body=" + gson.toJson(clientRequest));
			logger.error("getForumByPetType : ", e);
			rtn = new Success(false, e.toString()).toString();
		} finally {
			writeStringToResponse(rtn, response);
		}
	}
}
