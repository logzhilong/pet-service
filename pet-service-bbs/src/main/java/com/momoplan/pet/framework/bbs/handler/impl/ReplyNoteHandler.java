package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 * 回帖
 * 
 */
@Component("replyNote")
public class ReplyNoteHandler extends AbstractHandler {

	private static Logger logger = LoggerFactory.getLogger(ReplyNoteHandler.class);

	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try {
			NoteSub noteSub = new NoteSub();
			noteSub.setUserId(getUseridFParamSToken(clientRequest));
			noteSub.setNoteId(PetUtil.getParameter(clientRequest, "noteId"));
			noteSub.setContent(PetUtil.getParameter(clientRequest, "content"));
			noteSub.setPid(PetUtil.getParameter(clientRequest, "pid"));
			String id = noteSubService.replyNote(noteSub);
			logger.debug("回帖成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(true, id).toString();
		} catch (Exception e) {
			logger.debug("回帖失败 body=" + gson.toJson(clientRequest));
			logger.error("replyNote : ", e);
			rtn = new Success(false, e.toString()).toString();
		} finally {
			logger.debug(rtn);
			writeStringToResponse(rtn, response);
		}
	}
}
