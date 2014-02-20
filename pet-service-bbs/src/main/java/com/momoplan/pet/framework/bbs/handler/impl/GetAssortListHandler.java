package com.momoplan.pet.framework.bbs.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.bbs.mapper.AssortMapper;
import com.momoplan.pet.commons.domain.bbs.po.Assort;
import com.momoplan.pet.commons.domain.bbs.po.AssortCriteria;
import com.momoplan.pet.framework.bbs.handler.AbstractHandler;

/**
 * 获取分类列表
 * @author  liangc
 */
@Component("getAssortList")
public class GetAssortListHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(GetAssortListHandler.class);
	@Autowired
	private AssortMapper assortMapper = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			logger.debug("获取全部分类 成功 body="+gson.toJson(clientRequest));
			List<Assort> list =  assortMapper.selectByExample(new AssortCriteria());
			rtn = new Success(sn,true,list).toString();
		}catch(Exception e){
			logger.debug("获取全部分类 失败 body="+gson.toJson(clientRequest));
			logger.error("getAssortList : ",e);
			rtn = new Success(sn,false,e.toString()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}
