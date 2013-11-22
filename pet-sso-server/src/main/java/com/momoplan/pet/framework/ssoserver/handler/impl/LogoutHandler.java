package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;

/**
 * 注册
 * @author liangc
 */
@Component("logout")
public class LogoutHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(LogoutHandler.class);
	
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userId = getUseridFParamSToken(clientRequest);
			logger.debug("退出登录 : userId="+userId);
			SsoUser u = mapperOnCache.selectByPrimaryKey(SsoUser.class, userId);
			if(u!=null){
				logger.debug(u.toString());
				u.setDeviceToken(null);
				mapperOnCache.updateByPrimaryKey(u, userId);
				logger.debug("清楚 deviceToken ...");
			}
			String token = clientRequest.getToken();
			ssoService.logout(token);
			logger.debug("logout成功 body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,"OK").toString();
		}catch(Exception e){
			logger.debug("logout失败 body="+gson.toJson(clientRequest));
			logger.error("logout : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
