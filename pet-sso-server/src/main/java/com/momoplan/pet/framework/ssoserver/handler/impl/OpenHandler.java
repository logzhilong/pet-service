package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;
import com.momoplan.pet.framework.ssoserver.vo.LoginResponse;

/**
 * 每次打开时调用，返回版本信息和开机图片
 * @author liangc
 */
@Component("open")
public class OpenHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(OpenHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String phoneType = clientRequest.getImei();
			if(!"iphone".equalsIgnoreCase(phoneType)){
				phoneType = "android";
			}
			SsoVersion version = ssoService.getVersion(phoneType);
			String firstImage = ssoService.getFirstImage();
			LoginResponse loginResponse = new LoginResponse(version,firstImage);
			logger.debug("open ok: body="+gson.toJson(clientRequest));
			rtn = new Success(true,loginResponse).toString();
		}catch(Exception e){
			logger.debug("open error: body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
