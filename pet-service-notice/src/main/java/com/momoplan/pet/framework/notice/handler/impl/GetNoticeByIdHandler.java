package com.momoplan.pet.framework.notice.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.framework.notice.handler.AbstractHandler;
/**
 * 获取系统通知
 * @author liangc
 */
@Component("getNoticeById")
public class GetNoticeByIdHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(GetNoticeByIdHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String id = PetUtil.getParameter(clientRequest, "id");
			Notice notice = mapperOnCache.selectByPrimaryKey(Notice.class, id);
			logger.debug("getNoticeById 成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true,notice).toString();
		}catch(Exception e){
			logger.debug("getNoticeById 失败 body=" + gson.toJson(clientRequest));
			logger.error(e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}