package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;

/**
 * 根据帖子id获取所有回复
 */
@Component("getReplyList")
public class GetReplyListHandler extends AbstractHandler {

	private Logger logger = LoggerFactory.getLogger(GetReplyListHandler.class);

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try {
			int pageNo = PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize = PetUtil.getParameterInteger(clientRequest, "pageSize");
			String noteid = PetUtil.getParameter(clientRequest, "noteId");
			List<NoteSubVo> vo = noteSubService.getReplyByNoteId(noteid, pageNo, pageSize);
			logger.debug("根据帖子id获取所有回复成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(true, vo).toString();
		} catch (Exception e) {
			logger.error("根据帖子id获取所有回复失败 body=" + gson.toJson(clientRequest));
			logger.error("login : ", e);
			rtn = new Success(false, e.toString()).toString();
		} finally {
			logger.debug(rtn);
			writeStringToResponse(rtn, response);
		}
	}
}
