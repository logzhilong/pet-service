package com.momoplan.pet.framework.user.handler.impl;

import java.util.List;

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

INPUT:
	body={
		"service":"service.uri.pet_user",
		"method":"getPetinfo",
		"channel":"1",
		"token":"xxx"
		"params":{"userid":731}
	}

OUTPUT:
	失败: {"success":false,"entity":exception }
	成功: 
		{
			"success":true,
			"entity":[
				{"id":556,"gender":"male","img":"1133_1134,1137_1138,","nickname":"萨","type":1004,"userid":731,"version":8,"trait":"xxx","birthdate":"2"},
				{"id":627,"gender":"female","img":"1663_1664,","nickname":"金毛","type":1005,"userid":731,"version":8,"trait":"可爱","birthdate":"3"},
				{"id":628,"gender":"male","img":"1665_1667,1666_1668,","nickname":"铛铛","type":2001,"userid":731,"version":0,"trait":"xxx","birthdate":"3"},
				{"id":629,"gender":"","img":"1669_1670,","nickname":"等等等等等等","type":1001,"userid":731,"version":1,"trait":"xxx","birthdate":"1"},
				{"id":630,"gender":"male","img":"1671_1673,1672_1674,","nickname":"刚刚刚刚过","type":1001,"userid":731,"version":0,"trait":"xxx","birthdate":"4"}
			]
		}
*/
/**
 * 获取宠物信息
 * @author liangc
 */
@Component("getPetinfo")
public class GetPetHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetPetHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String userid = null;
			if(clientRequest.getParams()!=null){
				userid = getParameter(clientRequest, "userid");
				logger.debug("根据userid 获取用户信息 userid="+userid);
			}else{
				String token = clientRequest.getToken();
				userid = getToken(token).getUserid();
				logger.debug("根据token 获取用户信息 token="+token);
			}
			List<PetInfo> list = userService.getPetInfo(userid);
			rtn = new Success(true,list).toString();
			logger.debug("获取宠物信息 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("获取宠物信息 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
