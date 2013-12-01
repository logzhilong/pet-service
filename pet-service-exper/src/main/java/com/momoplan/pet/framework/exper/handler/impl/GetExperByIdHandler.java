package com.momoplan.pet.framework.exper.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.framework.exper.handler.AbstractHandler;
/**
 * 获取养宠经验
 * @author liangc
 */
@Component("getExperById")
public class GetExperByIdHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetExperByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String id = PetUtil.getParameter(clientRequest, "id");
			Exper exper = mapperOnCache.selectByPrimaryKey(Exper.class, id);
			logger.debug("getExperById 成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true,exper).toString();
		}catch(Exception e){
			logger.debug("getExperById 失败 body=" + gson.toJson(clientRequest));
			logger.error(e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}