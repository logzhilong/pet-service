package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
import com.momoplan.pet.framework.user.vo.UserVo;
/*
获取用户信息

INPUT:
	body={
		"service":"service.uri.pet_user",
		"method":"getUserinfo",
		"channel":"1",
		"token":"xxx"
		"params":{}
	}

OUTPUT:
	失败: {"success":false,"entity":exception }
	成功: 
		{
			"success":true,
			"entity":{
				"longitude":0.0,
				"latitude":0.0,
				"id":747,
				"nickname":"cc",
				"username":"cc",
				"version":0,
				"email":"",
				"phoneNumber":"",
				"ifFraudulent":"0",
				"deviceToken":""
			}
		}
 */
/**
 * 获取用户信息
 * @author liangc
 */
@Component("getUserinfo")
public class GetUserInfoHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetUserInfoHandler.class);
	private Gson gson = MyGson.getInstance();
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String userid = null;
			SsoAuthenticationToken tokenObj = null;
			if(clientRequest.getParams()!=null){
				userid = getParameter(clientRequest, "userid");
				tokenObj = new SsoAuthenticationToken();
				tokenObj.setUserid(userid);
				logger.debug("根据userid 获取用户信息 userid="+userid);
			}else{
				String token = clientRequest.getToken();
				tokenObj = getToken(token);
				logger.debug("根据token 获取用户信息 token="+token);
			}
			UserVo vo = userService.getUser(tokenObj);
			logger.debug("获取用户信息成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,vo).toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("获取用户信息失败 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
}
