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
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesReplyVo;

@Component("getReply")
public class GetReplyHandler extends AbstractHandler{
	
	private static Logger logger = LoggerFactory.getLogger(GetReplyHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String stateid = PetUtil.getParameter(clientRequest, "stateid");
			String pageSize = PetUtil.getParameter(clientRequest, "pageSize");
			String pageNo = PetUtil.getParameter(clientRequest, "pageNo");
			List<StatesUserStatesReplyVo> voList = stateService.getReplyByStateid(userid, stateid, Integer.parseInt(pageSize), Integer.parseInt(pageNo));
			rtn = new Success(sn,true,voList).toString();
			logger.debug("获取回复 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("获取回复 失败 body="+gson.toJson(clientRequest));
			logger.error("getReply : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}
