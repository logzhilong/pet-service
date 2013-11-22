package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesVo;
/**
 * 获取用户动态，根据动态ID
 * @author liangc
 */
@Component("getUserStateById")
public class GetUserStateByIdHandler extends AbstractHandler{
	
	private static Logger logger = LoggerFactory.getLogger(GetUserStateByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String stateid = PetUtil.getParameter(clientRequest, "stateid");
			StatesUserStatesVo vo = stateService.findOneState(userid,stateid);
			rtn = new Success(sn,true,vo).toString();
			logger.debug("获取一条动态 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("获取一条动态 失败 body="+gson.toJson(clientRequest));
			logger.error("getUserStateById : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
