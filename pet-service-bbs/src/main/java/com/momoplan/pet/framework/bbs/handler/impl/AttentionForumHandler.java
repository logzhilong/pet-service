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
 * 关注圈子
 * @author  qiyongc
 */
@Component("attentionForum")
public class AttentionForumHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(AttentionForumHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			UserForumRel userForumRel=new UserForumRel();
			userForumRel.setUserId(getUseridFParamSToken(clientRequest));
			userForumRel.setForumId(PetUtil.getParameter(clientRequest, "forumId"));
			userForumRelService.attentionForum(userForumRel);
			logger.debug("关注圈子成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			logger.debug("关注圈子失败 body="+gson.toJson(clientRequest));
			logger.error("attentionForum : ",e);
			rtn = new Success(false,e.toString()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
