package com.momoplan.pet.framework.pat.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.pat.handler.AbstractHandler;
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
 * 获取赞
 * @author liangc
 */
@Component("getPat")
public class GetPatHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetPatHandler.class);
	private Gson gson = MyGson.getInstance();
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String srcid = PetUtil.getParameter(clientRequest, "srcid");
			List<SsoUser> list = patService.getPat(srcid);
			logger.debug("获取赞成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,list).toString();
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("获取赞失败 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
}
