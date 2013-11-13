package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 * 退出圈子
 */
@Component("quitForum")
public class QuitForumHandler extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(QuitForumHandler.class);

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try {
			UserForumRel userForumRel = new UserForumRel();
			userForumRel.setForumId(PetUtil.getParameter(clientRequest, "forumId"));
			userForumRel.setUserId(getUseridFParamSToken(clientRequest));
			userForumRelService.quitForum(userForumRel);
			logger.debug("退出圈子成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(true, "OK").toString();
		} catch (Exception e) {
			logger.debug("退出圈子失败 body=" + gson.toJson(clientRequest));
			logger.error("quitForum : ", e);
			rtn = new Success(false, e.toString()).toString();
		} finally {
			logger.debug(rtn);
			writeStringToResponse(rtn, response);
		}
	}
}
