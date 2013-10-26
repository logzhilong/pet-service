package com.momoplan.pet.framework.pat.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.pat.handler.AbstractHandler;
/*
删除宠物信息

INPUT:{"id":"宠物ID"}
	
OUTPUT:
	成功: {"success":true,"entity":"OK" }
*/
/**
 * 取消赞
 * @author liangc
 */
@Component("delPat")
public class DelPatHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(DelPatHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String srcid = PetUtil.getParameter(clientRequest, "srcid");
			patService.delPat(userid, srcid);
			logger.debug("删除赞 成功 body="+gson.toJson(clientRequest));
			rtn = new Success(true,"OK").toString();
		}catch(Exception e){
			logger.debug("删除赞 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
