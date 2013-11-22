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
	修改宠物 method:updatePetinfo params: nickname type img trait gender birthdate id
	添加宠物 method:savePetinfo params: nickname type img trait gender birthdate
	
INPUT:
	body={
	}

OUTPUT:
	成功: 
		{
			"success":true,
			"entity":{}
		}
*/
/**
 * 更新宠物信息
 * @author liangc
 */
@Component("updatePetinfo")
public class UpdatePetHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(UpdatePetHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			PetInfo petInfo = revicePetInfo(clientRequest);
			userService.updatePetInfo(petInfo);
			rtn = new Success(sn,true,"OK").toString();
			logger.debug("修改宠物 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("修改宠物 失败 body="+gson.toJson(clientRequest));
			logger.error("updatePetinfo",e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
