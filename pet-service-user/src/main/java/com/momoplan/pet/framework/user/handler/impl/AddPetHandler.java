package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/*
获取用户信息
	添加宠物 method:savePetinfo params: nickname type img trait gender birthdate id
	
INPUT:
	{
		"id":""
		"nickname":"" 
		"type":"" 
		"img":"" 
		"trait":"" 
		"gender":"" 
		"birthdate":"" 
	}
	
OUTPUT:
	成功: 
		{"success":true,"entity":"OK"}
*/
/**
 * 添加宠物
 * @author liangc
 */
@Component("savePetinfo")
public class AddPetHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(AddPetHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String token = clientRequest.getToken();
			String userid = getToken(token).getUserid();
			PetInfo petInfo = revicePetInfo(clientRequest);
			petInfo.setUserid(userid);
			logger.debug(petInfo.toString());
			String id = userService.savePetInfo(petInfo);
			logger.debug("添加宠物 成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,id).toString();
		}catch(Exception e){
			logger.debug("添加宠物 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
