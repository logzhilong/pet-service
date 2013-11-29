package com.momoplan.pet.framework.albums.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.albums.po.Photos;
import com.momoplan.pet.framework.albums.handler.AbstractHandler;
import com.momoplan.pet.framework.albums.service.PhotoService;
/**
 * 更新点击率
 * @author liangc
 */
@Component("clickPublicPhotos")
public class ClickPublicPhotosHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(ClickPublicPhotosHandler.class);
	@Autowired
	private PhotoService photoService = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			logger.debug("clickPublicPhotos body="+gson.toJson(clientRequest));
			String id = PetUtil.getParameter(clientRequest, "id");
			Photos p = mapperOnCache.selectByPrimaryKey(Photos.class, id);
			int totalClick = p.getTotalClick()+3;
			p.setTotalClick(totalClick);
			mapperOnCache.updateByPrimaryKeySelective(p, id);
			rtn = new Success(sn,true,"OK").toString();
		}catch(Exception e){
			logger.debug("getPhotos 失败 body=" + gson.toJson(clientRequest));
			logger.error(e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}