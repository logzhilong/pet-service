package com.momoplan.pet.framework.bbs.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.sun.xml.internal.bind.v2.TODO;

/**
 * 根据帖子id查看帖子详情
 * @author  qiyongc
 */
@Component("detailNote")
public class DetailNoteHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(DetailNoteHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			String id=PetUtil.getParameter(clientRequest, "noteid");
			//TODO更新帖子点击数   暂时这样写,
			noteService.updateClickCount(clientRequest);
			Object object=noteService.detailNote(id);
			logger.debug("根据id获取帖子详情 body="+gson.toJson(clientRequest));
			rtn = new Success(true,object).toString();
		}catch(Exception e){
			logger.error("根据id获取帖子详情 body="+gson.toJson(clientRequest));
			logger.error("login : ",e);
			rtn = new Success(false,e.toString()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
