package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
@Component("reportContent")
public class ReportContentHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(AddUserStateHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			SsoAuthenticationToken authenticationToken = verifyToken(clientRequest);
			boolean ifReport = stateService.reportContent(clientRequest,authenticationToken);
			
			rtn = new Success(true,ifReport).toString();
		}catch(Exception e){
			logger.debug("举报失败 body="+gson.toJson(clientRequest));
			logger.error("addReply : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
