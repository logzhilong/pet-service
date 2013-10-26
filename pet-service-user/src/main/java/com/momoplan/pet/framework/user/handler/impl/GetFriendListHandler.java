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

getFriendList
获取好友列表
{"method":"getFriendList","params":{"userid":"770"}}
{"success":true,"entity":[{"id":"747","alias":"别名","nickname":"cc","username":"cc","phoneNumber":"","deviceToken":""}]}

*/
/**
 * 获取好友列表
 * @author liangc
 */
@Component("getFriendList")
public class GetFriendListHandler extends AbstractHandler {

	private String logTitle = "获取好友列表";

	private static Logger logger = LoggerFactory.getLogger(GetFriendListHandler.class);

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			//现在 params 里找 userid ，如果没有则根据 token 获取
			String userid = getUseridFParamSToken(clientRequest);
			List<UserVo> userList = userService.getFirendList(userid);
			rtn = new Success(true,userList).toString();
			logger.debug(logTitle+" 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug(logTitle+" 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
