package com.momoplan.pet.framework.ssoserver.handler.impl;

import java.util.Map;

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
 * 注册
 * @author liangc
 */
@Component("token")
public class TokenHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(TokenHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String _token = clientRequest.getToken();
			Map<String,Object> params = clientRequest.getParams();
			LoginResponse loginResponse = ssoService.getToken(_token);
			if(params!=null&&"open".equalsIgnoreCase(params.get("action").toString())){
				logger.debug("action=open 需要获取开机图片和版本信息");
				String phoneType = clientRequest.getImei();
				SsoVersion version = ssoService.getVersion(phoneType);
				String firstImage = ssoService.getFirstImage();
				loginResponse.setVersion(version);
				loginResponse.setFirstImage(firstImage);
			}
			logger.debug("token有效 body="+_token);
			rtn = new Success(true,loginResponse).toString();
		}catch(Exception e){
			logger.debug("token无效 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
