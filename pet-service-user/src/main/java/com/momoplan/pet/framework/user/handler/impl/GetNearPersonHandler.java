package com.momoplan.pet.framework.user.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
import com.momoplan.pet.framework.user.vo.UserVo;
/*
  getNearPerson

  功能：获取附近的人

  输入：{"gender":"", "petType":"", "longitude":"","latitude":""}

  输出：{"success":true,"entity":[{"id":"747","alias":"别名","nickname":"cc","username":"cc","phoneNumber":"","deviceToken":""}]}

{"method":"getNearPerson","params":{"gender":"", "petType":"", "longitude":"116.386294","latitude":"39.923879"}}
*/
/**
 * 获取附近的人
 * @author liangc
 */
@Component("getNearPerson")
public class GetNearPersonHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetNearPersonHandler.class);

	private String logTitle = "获取附近的人";

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			//现在 params 里找 userid ，如果没有则根据 token 获取
			String userid = getUseridFParamSToken(clientRequest);
			String gender = getParameter(clientRequest, "gender");
			String petType = getParameter(clientRequest, "petType");
			String longitude = getParameter(clientRequest, "longitude");
			String latitude = getParameter(clientRequest, "latitude");
			List<UserVo> userList = userService.getNearPerson(userid, gender, petType, Double.valueOf(longitude), Double.valueOf(latitude));
			rtn = new Success(true,userList).toString();
			logger.debug(logTitle+" 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug(logTitle+" 失败 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}