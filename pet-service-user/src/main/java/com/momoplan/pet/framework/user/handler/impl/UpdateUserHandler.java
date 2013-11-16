package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/*
更新用户坐标

INPUT:
	body={
		"service":"service.uri.pet_user",
		"method":"updateUserLocation",
		"channel":"1",
		"token":"xxx"
		"params":{
			"nickname":"cc",
			"username":"cc",
			"email":"",
			"phoneNumber":"",
			"ifFraudulent":"0",
			"deviceToken":""
			...
		}
	}

OUTPUT:
	成功: {"success":true,"entity":"OK" }
	失败: {"success":false,"entity":exception }
*/
/**
 * 获取用户信息
 * @author liangc
 */
@Component("updateUser")
public class UpdateUserHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(UpdateUserHandler.class);
	
	private Gson gson = MyGson.getInstance();

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			SsoUser user = reviceSsoUser(clientRequest);
			user.setId(getUseridFParamSToken(clientRequest));
			userService.updateUser(user);
			logger.debug("修改用户信息成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("修改用户信息失败 body="+gson.toJson(clientRequest));
			logger.error("",e);
			rtn = new Success(false,"修改用户信息失败:"+e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
}
