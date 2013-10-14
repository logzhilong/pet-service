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
import com.momoplan.pet.framework.ssoserver.vo.LoginResponse;

/**
 * 注册
 * @author liangc
 */
@Component("login")
public class LoginHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(LoginHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String username = PetUtil.getParameter(clientRequest, "username");
			String password = PetUtil.getParameter(clientRequest, "password");
			String deviceToken = PetUtil.getParameter(clientRequest, "deviceToken");
			SsoUser user = new SsoUser();
			user.setUsername(username);
			user.setPassword(passwordEncoder.encodePassword(password, salt));
			user.setDeviceToken(deviceToken);
			LoginResponse loginResponse = ssoService.login(user);
			logger.debug("登录成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,loginResponse).toString();
		}catch(Exception e){
			logger.debug("登录失败 body="+gson.toJson(clientRequest));
			logger.error("login : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
