package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;

/**
 * 注册
 * @author liangc
 */
@Component("resetPassword")
public class ResetPasswordHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(ResetPasswordHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String password = PetUtil.getParameter(clientRequest, "password");
			String phoneNumber = PetUtil.getParameter(clientRequest, "phonenumber");
			SsoUser user = new SsoUser();
			user.setUsername(phoneNumber);
			user.setPassword(passwordEncoder.encodePassword(password, salt));
			ssoService.updatePassword(user);
			logger.debug("修改成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			logger.debug("修改失败 body="+gson.toJson(clientRequest));
			logger.error("resetPassword : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
