package com.momoplan.pet.framework.ency.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.framework.ency.handler.AbstractHandler;
/**
 * 获取宠物百科
 * @author liangc
 */
@Component("getEncyById")
public class GetEncyByIdHandler extends AbstractHandler {

	private static Logger logger = LoggerFactory.getLogger(GetEncyByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String id = PetUtil.getParameter(clientRequest, "id");
			Ency e = mapperOnCache.selectByPrimaryKey(Ency.class, id);
			rtn = new Success(sn,true,e).toString();
			logger.debug("getEncyById 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("getEncyById 失败 body=" + gson.toJson(clientRequest));
			logger.error(e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}