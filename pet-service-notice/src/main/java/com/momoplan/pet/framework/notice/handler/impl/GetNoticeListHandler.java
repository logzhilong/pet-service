package com.momoplan.pet.framework.notice.handler.impl;
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
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.framework.notice.handler.AbstractHandler;
import com.momoplan.pet.framework.notice.service.NoticeService;
/**
 * 获取系统通知
 * @author liangc
 */
@Component("getNoticeList")
public class GetNoticeListHandler extends AbstractHandler {
	private static Logger logger = LoggerFactory.getLogger(GetNoticeListHandler.class);
	@Autowired
	private NoticeService noticeService = null;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			logger.debug("getNoticeList body="+gson.toJson(clientRequest));
			int pageNo=PetUtil.getParameterInteger(clientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(clientRequest, "pageSize");
			String pid = PetUtil.getParameter(clientRequest, "pid");
			
			Page<Notice> pages = new Page<Notice>();
			pages.setPageNo(pageNo);
			pages.setPageSize(pageSize);
			pages = noticeService.getNoticeList(pages,pid);
			
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
			logger.debug("getNoticeList 成功 body=" + gson.toJson(clientRequest));
			rtn = success.toString();
		}catch(Exception e){
			logger.debug("getNoticeList 失败 body=" + gson.toJson(clientRequest));
			logger.error("getNoticeList 失败",e);
			rtn = new Success(sn,false,"getNoticeList 失败 err="+e.getMessage()).toString();
		}finally{
			writeStringToResponse(rtn,response);
		}
	}
}