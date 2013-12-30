package com.momoplan.pet.framework.notice.handler.impl;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.notice.po.Ads;
import com.momoplan.pet.framework.notice.handler.AbstractHandler;
import com.momoplan.pet.framework.notice.service.AdsService;
/**
 * 根据广告列表
 * @author liangc
 */
@Component("getAds")
public class GetAdsHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetAdsHandler.class);
	@Autowired
	private AdsService adsService = null;
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			List<Ads> list = adsService.getAdsList();
			logger.debug("getAds 成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true,list).toString();
		}catch(Exception e){
			logger.debug("getAds 失败 body=" + gson.toJson(clientRequest));
			logger.error("getAds 失败",e);
			rtn = new Success(sn,false,"getAds 失败 err="+e.getMessage()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}