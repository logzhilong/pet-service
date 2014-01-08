package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.service.SpecialSubjectService;

/**
 * 获取专题 根据ID
 * @author liangc
 */
@Component("getSpecialSubjectById")
public class GetSpecialSubjectByIdHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetSpecialSubjectByIdHandler.class);
	
	@Autowired
	private SpecialSubjectService specialSubjectService = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String sn = clientRequest.getSn();
		String rtn = null;
		try{
			String id=PetUtil.getParameter(clientRequest, "id");
			SpecialSubject ss = mapperOnCache.selectByPrimaryKey(SpecialSubject.class, id);
			List<SpecialSubject> list = specialSubjectService.getSpecialSubjectListByGid(ss);
			logger.debug("获取专题 成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true,list).toString();
		}catch(Exception e){
			logger.debug("获取专题 失败 body="+gson.toJson(clientRequest));
			logger.error("getSpecialSubjectList : ",e);
			rtn = new Success(sn,false,e.toString()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}
