package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.po.SsoChatServer;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;
import com.mysql.jdbc.StringUtils;

/**
 * 获取聊天服务器
 * @author liangc
 */
@Component("getChatServer")
public class GetChatServerHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetChatServerHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			SsoChatServer ss = ssoService.getSsoChatServer();
			String port = ss.getPort();
			if(StringUtils.isNullOrEmpty(port)){
				ss.setPort("5222");
			}
			rtn = new Success(sn,true,ss).toString();
		}catch(Exception e){
			logger.debug("token无效 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
