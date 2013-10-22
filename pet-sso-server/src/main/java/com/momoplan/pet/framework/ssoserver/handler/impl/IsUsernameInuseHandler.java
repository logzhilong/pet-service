package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;

/**
 * 用户名是否存在
 * @author liangc
 */
@Component("isUsernameInuse")
public class IsUsernameInuseHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(IsUsernameInuseHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String username = PetUtil.getParameter(clientRequest, "username");
			SsoUser user = ssoService.getSsoUserByName(username);
			logger.debug("用户名重复判断 成功 body="+gson.toJson(clientRequest)+" ; user="+user);
			Boolean hasUser = true;
			if(user==null)
				hasUser=false;
			rtn = new Success(true,hasUser).toString();
		}catch(Exception e){
			logger.debug("用户名重复判断 失败 body="+gson.toJson(clientRequest));
			logger.error("login : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
