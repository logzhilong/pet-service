package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;

@Component("delReply")
public class DelReplyHandler extends AbstractHandler{
	private static Logger logger = LoggerFactory.getLogger(DelReplyHandler.class);
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String replyid = PetUtil.getParameter(clientRequest, "replyid");
			stateService.delReply(replyid);
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			logger.debug("删除失败  body="+gson.toJson(clientRequest));
			logger.error("delReply : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
