package com.momoplan.pet.framework.user.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
/*
  pushMsgApn

  功能：给IOS推送离线消息

  输入：{"fromname":"", "toname":"", "msg":""}

  输出：{"success":true,"entity":"OK" }
  
  {"method":"pushMsgApn","params":{"fromname":"cc", "toname":"cc", "msg":"fuck"}}
  
*/
/**
 * 给IOS推送离线消息
 * @author liangc
 */
@Component("pushMsgApn")
public class PushMsgApnHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(PushMsgApnHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String f = getParameter(clientRequest, "fromname");
			String t = getParameter(clientRequest, "toname");
			String m = getParameter(clientRequest, "msg");
			/*
			 img:发的消息是，用户名：发来一张图片
			 audio:用户名：发来一段语音 
			 text:或没有（因为早期版本没有fileType字段）就是以前的格式，用户名：消息内容
			 */
			String type = getParameter(clientRequest, "type");
			
			userService.pushMsgApn(f,t,m,type);
			rtn = new Success(sn,true,"OK").toString();
			logger.debug("推送IOS消息 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("推送IOS消息 失败 body="+gson.toJson(clientRequest));
			logger.error("pushMsgApn",e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}