package com.momoplan.pet.framework.servicestate.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesVo;

/**
 * 获取用户动态信息
 * @author liangc
 */
@Component("getUserState")
public class GetUserStateListHandler extends AbstractHandler {

	private static Logger logger = LoggerFactory.getLogger(GetUserStateListHandler.class);

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try {
			String currentUser = getToken(clientRequest.getToken()).getString("userid");;// 当前登录人
			String targetUser = PetUtil.getParameter(clientRequest, "userid");// 目标获取人
			String pageSize = PetUtil.getParameter(clientRequest, "pageSize");
			String pageNo = PetUtil.getParameter(clientRequest, "pageNo");
			boolean isSelf = currentUser.equals(targetUser) ? true : false;// 是否取我自己的
			List<StatesUserStatesVo> list = stateService.getUserStates(targetUser, Integer.parseInt(pageSize), Integer.parseInt(pageNo), isSelf);
			rtn = new Success(sn,true, list).toString();
			logger.debug("获取[好友/个人]动态 成功 body=" + gson.toJson(clientRequest));
		} catch (Exception e) {
			logger.debug("获取[好友/个人]动态 失败 body=" + gson.toJson(clientRequest));
			logger.error("获取动态列表 : ", e);
			rtn = new Success(sn,false, e.getMessage()).toString();
		} finally {
			writeStringToResponse(rtn, response);
		}
	}

}
