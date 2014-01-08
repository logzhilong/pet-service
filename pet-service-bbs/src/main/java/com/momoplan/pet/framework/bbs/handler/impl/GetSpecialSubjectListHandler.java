package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;
import com.momoplan.pet.framework.bbs.service.SpecialSubjectService;

/**
 * 获取专题列表
 * @author liangc
 */
@Component("getSpecialSubjectList")
public class GetSpecialSubjectListHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetSpecialSubjectListHandler.class);
	
	@Autowired
	private SpecialSubjectService specialSubjectService = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String sn = clientRequest.getSn();
		String rtn = null;
		try{
			int pageNo=PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(clientRequest, "pageSize");
			Page<List<SpecialSubject>> data = specialSubjectService.getSpecialSubjectList(pageSize,pageNo);
			logger.debug("获取专题列表成功 body=" + gson.toJson(clientRequest));
			rtn = new Success(sn,true,data.getData()).toString();
		}catch(Exception e){
			logger.debug("获取专题列表失败 body="+gson.toJson(clientRequest));
			logger.error("getSpecialSubjectList : ",e);
			rtn = new Success(sn,false,e.toString()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}
