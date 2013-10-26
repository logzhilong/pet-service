package com.momoplan.pet.framework.pat.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.pat.handler.AbstractHandler;
/*

7、addOrRemoveFriend

  功能：添加或删除好友关系

  输入：{"SubscriptionType":"", "aId":"", "bId":""}

  输出：{"success":true,"entity":"OK" }
{"method":"addOrRemoveFriend","params":{"SubscriptionType":"unsubscribed", "aId":"cc", "bId":"cc"}}
*/
/**
 * 增加赞
 * @author liangc
 */
@Component("addPat")
public class AddPatHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(AddPatHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String srcid = getParameter(clientRequest, "srcid");
			String type = getParameter(clientRequest, "type");
			patService.addPat(userid, srcid, type);
			rtn = new Success(true,"OK").toString();
			logger.debug("添加赞 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("添加赞 失败 body="+gson.toJson(clientRequest));
			logger.debug(e.getMessage());
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
