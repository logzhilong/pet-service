package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/*

7、addOrRemoveFriend

  功能：添加或删除好友关系

  输入：{ "SubscriptionType":"", "aId":"", "bId":""}

  输出：{"success":true,"entity":"OK" }

*/
/**
 * 更新宠物信息
 * @author liangc
 */
@Component("addOrRemoveFriend")
public class AddOrRemoveFriendHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(AddOrRemoveFriendHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String st = getParameter(clientRequest, "SubscriptionType");
			String aid = getParameter(clientRequest, "aId");//a username
			String bid = getParameter(clientRequest, "bId");//b username
			userService.addOrRemoveFriend(st, aid, bid);
			rtn = new Success(true,"OK").toString();
			logger.debug("添加/删除 好友 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("添加/删除 好友 失败 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
