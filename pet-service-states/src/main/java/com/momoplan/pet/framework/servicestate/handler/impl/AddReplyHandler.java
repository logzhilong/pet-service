package com.momoplan.pet.framework.servicestate.handler.impl;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;

@Component("addReply")
public class AddReplyHandler extends AbstractHandler{
	private static Logger logger = LoggerFactory.getLogger(AddReplyHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			SsoAuthenticationToken authenticationToken = verifyToken(clientRequest);
			StatesUserStatesReply reply = new StatesUserStatesReply();
			
			String userid = authenticationToken.getUserid();
			reply.setId(IDCreater.uuid());
			reply.setCt(new Date());
			reply.setMsg(PetUtil.getParameter(clientRequest, "msg"));
			reply.setPid(PetUtil.getParameter(clientRequest, "pid"));// 可以为空
			reply.setPuserid(PetUtil.getParameter(clientRequest, "puserid"));// 可以为空
			reply.setUserid(userid);
			reply.setStateid(PetUtil.getParameter(clientRequest, "stateid"));
			
			String replyid = stateService.addReply(reply);
			rtn = new Success(sn,true,replyid).toString();
		}catch(Exception e){
			logger.debug("回复失败 body="+gson.toJson(clientRequest));
			logger.error("addReply : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
