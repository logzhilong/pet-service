package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;

@Component("delUserState")
public class DelUserStateHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(AddUserStateHandler.class);
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			int result = stateService.delUserState(clientRequest);
			if(result>0){
				rtn = new Success(true,true).toString();
			}else{
				rtn = new Success(false,"删除失败").toString();
			}
		}catch(Exception e){
			logger.debug("删除失败 body="+gson.toJson(clientRequest));
			logger.error("delUserState : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
