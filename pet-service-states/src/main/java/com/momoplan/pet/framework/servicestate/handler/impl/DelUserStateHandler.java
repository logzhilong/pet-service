package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;

@Component("delUserState")
public class DelUserStateHandler extends AbstractHandler{
	private static Logger logger = LoggerFactory.getLogger(DelUserStateHandler.class);
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String stateid = PetUtil.getParameter(clientRequest, "stateid");
			stateService.delUserState(stateid);
			rtn = new Success(true,"OK").toString();
			logger.debug("删除动态 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("删除动态 失败 body="+gson.toJson(clientRequest));
			logger.error("delUserState : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
