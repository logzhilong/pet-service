package com.momoplan.pet.framework.servicestate.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesVo;

/**
 * 获取所有好友的动态
 * 
 * @author SKS
 * 
 */
@Component("getAllFriendStates")
public class GetAllFriendStatesHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetAllFriendStatesHandler.class);

	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try {
			SsoAuthenticationToken authenticationToken = verifyToken(clientRequest);
			String userid = authenticationToken.getUserid();
			String pageSize = PetUtil.getParameter(clientRequest, "pageSize");
			String pageNo = PetUtil.getParameter(clientRequest, "pageNo");
			List<StatesUserStatesVo> list = stateService.getAllFriendStates(userid,Integer.parseInt(pageSize),Integer.parseInt(pageNo));
			rtn = new Success(sn,true, list).toString();
			logger.debug("获取所有好友动态 成功 body=" + gson.toJson(clientRequest));
		} catch (Exception e) {
			logger.debug("获取所有好友动态 失败 body=" + gson.toJson(clientRequest));
			logger.error("getAllFriendStates : ", e);
			rtn = new Success(sn,false, e.getMessage()).toString();
		} finally {
			logger.debug(rtn);
			writeStringToResponse(rtn, response);
		}
	}
}
