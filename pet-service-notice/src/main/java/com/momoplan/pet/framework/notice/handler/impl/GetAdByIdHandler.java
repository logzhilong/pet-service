package com.momoplan.pet.framework.notice.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.notice.po.Ads;
import com.momoplan.pet.framework.notice.handler.AbstractHandler;
/**
 * 根据广告ID获取广告
 * @author liangc
 */
@Component("getAdById")
public class GetAdByIdHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetAdByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String id = PetUtil.getParameter(clientRequest, "id");
			Ads ads = mapperOnCache.selectByPrimaryKey(Ads.class, id);
			logger.debug("getAdById 成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true,ads).toString();
		}catch(Exception e){
			logger.debug("getAdById 失败 body=" + gson.toJson(clientRequest));
			logger.error("getAdById 失败",e);
			rtn = new Success(sn,false,"getAdById 失败 err="+e.getMessage()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}