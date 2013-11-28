package com.momoplan.pet.framework.albums.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.albums.po.Photos;
import com.momoplan.pet.framework.albums.handler.AbstractHandler;
import com.momoplan.pet.framework.albums.service.PhotoService;
/**
 * 获取图片
 * @author liangc
 */
@Component("getPhotos")
public class GetPhotosHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(GetPhotosHandler.class);
	@Autowired
	private PhotoService photoService = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			logger.debug("getPhotos body="+gson.toJson(clientRequest));
			int pageNo=PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(clientRequest, "pageSize");
			Page<Photos> pages = new Page<Photos>();
			pages.setPageNo(pageNo);
			pages.setPageSize(pageSize);
			pages = photoService.getPublicPhotos(pages);
			rtn = new Success(sn,true,pages).toString();
		}catch(Exception e){
			logger.debug("getPhotos body="+gson.toJson(clientRequest));
			logger.error(e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}