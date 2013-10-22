package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
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
			"userid":"xxx",
			"longitude":000.000,
			"latitude":000.000
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
@Component("updateUserLocation")
public class UpdateUserLocationHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(UpdateUserLocationHandler.class);
	
	private Gson gson = MyGson.getInstance();

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String userid = PetUtil.getParameter(clientRequest, "userid");
			String longitude = PetUtil.getParameter(clientRequest, "longitude");
			String latitude = PetUtil.getParameter(clientRequest, "latitude");
			if(StringUtils.isEmpty(userid)){
				String token = clientRequest.getToken();
				SsoAuthenticationToken tokenObj = getToken(token);
				userid = tokenObj.getUserid()+"";
			}
			userService.updateUserLocation(userid, Double.valueOf(longitude), Double.valueOf(latitude));
			logger.debug("修改用户坐标成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("修改用户坐标失败 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
}
