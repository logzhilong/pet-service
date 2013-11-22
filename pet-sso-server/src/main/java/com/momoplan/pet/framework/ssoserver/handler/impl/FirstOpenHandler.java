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
 * 安装后，第一次打开
 * @author liangc
 */
@Component("firstOpen")
public class FirstOpenHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(FirstOpenHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String phoneType = clientRequest.getImei();
			if(!"iphone".equalsIgnoreCase(phoneType)){
				phoneType = "android";
			}
			SsoVersion version = ssoService.getVersion(phoneType);
			String firstImage = ssoService.getFirstImage();
			LoginResponse loginResponse = new LoginResponse(version,firstImage);
			logger.debug("first open ok: body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,loginResponse).toString();
		}catch(Exception e){
			logger.debug("first open error: body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
