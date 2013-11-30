package com.momoplan.pet.framework.ency.handler.impl;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.framework.ency.handler.AbstractHandler;
import com.momoplan.pet.framework.ency.service.EncyService;
/**
 * 获取宠物百科
 * @author liangc
 */
@Component("getEncyList")
public class GetEncyListHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(GetEncyListHandler.class);
	@Autowired
	private EncyService encyService = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			logger.debug("getPhotos body="+gson.toJson(clientRequest));
			int pageNo=PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(clientRequest, "pageSize");
			String pid = PetUtil.getParameter(clientRequest, "pid");
			
			Page<Ency> pages = new Page<Ency>();
			pages.setPageNo(pageNo);
			pages.setPageSize(pageSize);
			pages = encyService.getEncyList(pages,pid);
			
			String jsonArr = null;
			if(pages.getData()!=null&&pages.getData().size()>0)
				jsonArr = gson.toJson(pages.getData());
			JSONObject success = new JSONObject();
			success.put("success", true);
			JSONObject entity = new JSONObject();
			entity.put("pageSize", pageSize);
			entity.put("pageNo", pageNo);
			entity.put("totalCount", pages.getTotalCount());
			if(jsonArr!=null){
				entity.put("data", new JSONArray(jsonArr));
			}
			success.put("entity", entity);
			success.put("sn", sn);
			logger.debug("getEncyList 成功 body=" + gson.toJson(clientRequest));
			rtn = success.toString();
		}catch(Exception e){
			logger.debug("getEncyList 失败 body=" + gson.toJson(clientRequest));
			logger.error(e.getMessage());
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}