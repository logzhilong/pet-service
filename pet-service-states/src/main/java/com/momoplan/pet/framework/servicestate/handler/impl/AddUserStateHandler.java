package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;

@Component("addUserState")
public class AddUserStateHandler extends AbstractHandler{
	private static Logger logger = LoggerFactory.getLogger(AddUserStateHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String stateid = stateService.addUserState(clientRequest,userid);
			rtn = new Success(sn,true,stateid).toString();
			logger.debug("发布状态成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("发布状态失败 body="+gson.toJson(clientRequest));
			logger.error("addUserState : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
