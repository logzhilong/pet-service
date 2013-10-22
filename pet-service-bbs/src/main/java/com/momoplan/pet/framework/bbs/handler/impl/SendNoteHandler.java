package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 * 发帖
 * @author  qiyongc
 */
@Component("sendNote")
public class SendNoteHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(SendNoteHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			Object object=noteService.sendNote(clientRequest);
			logger.debug("发帖成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,object).toString();
		}catch(Exception e){
			logger.debug("发帖失败 body="+gson.toJson(clientRequest));
			logger.error("sendNote : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
