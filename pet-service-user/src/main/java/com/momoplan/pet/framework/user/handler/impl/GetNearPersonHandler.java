package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/*
  getNearPerson

  功能：获取附近的人

  输入：{"pageIndex":"","gender":"", "petType":"", "longitude":"","latitude":""}

  输出：{"success":true,"entity":[{"id":"747","alias":"别名","nickname":"cc","username":"cc","phoneNumber":"","deviceToken":""}]}

{"method":"getNearPerson","params":{"pageIndex":"1","gender":"", "petType":"", "longitude":"116.386294","latitude":"39.923879"}}
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
			long start = System.currentTimeMillis();
			//现在 params 里找 userid ，如果没有则根据 token 获取
			String userid = getUseridFParamSToken(clientRequest);
			String gender = getParameter(clientRequest, "gender");
			String petType = getParameter(clientRequest, "petType");
			String longitude = getParameter(clientRequest, "longitude");
			String latitude = getParameter(clientRequest, "latitude");
			String pageIndex = getParameter(clientRequest, "pageIndex");
			if(pageIndex==null||"".equals(pageIndex))
				pageIndex = "0";
			JSONArray jsonArray = userService.getNearPerson(pageIndex,userid, gender, petType, Double.valueOf(longitude), Double.valueOf(latitude));
			JSONObject success = new JSONObject();
			success.put("success", true);
			success.put("entity", jsonArray);
			rtn = success.toString();
			long end = System.currentTimeMillis();
			logger.debug(logTitle+" 成功 body="+gson.toJson(clientRequest));
			logger.info("获取附近的人-耗时："+(end-start)+" 毫秒。("+latitude+","+longitude+")");	
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