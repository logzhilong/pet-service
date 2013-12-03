package com.momoplan.pet.framework.petservice.notice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.notice.service.NoticeService;

@Controller
public class NoticeAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(NoticeAction.class);
	@Autowired
	private NoticeService noticeService = null;
	
	@RequestMapping("/petservice/notice/noticeMain.html")
	public String main(Notice myForm,Page<Notice> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/notice/noticeMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			pages.setPageSize(400);
			Page<Notice> page = noticeService.getNoticeList(pages,myForm);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("notice error",e);
		}
		return "/petservice/notice/noticeMain";
	}
	
	@RequestMapping("/petservice/notice/noticeAddOrEdit.html")
	public String addOrEdit(Notice myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/notice/noticeAddOrEdit.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(Notice.class, id);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("add or edit error",e);
		}
		return "/petservice/notice/noticeAddOrEdit";
	}

	@RequestMapping("/petservice/notice/noticeSave.html")
	public void save(Notice myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/notice/noticeSave.html");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0501");
		json.put("forwardUrl",ctx+"/petservice/notice/noticeMain.html");
		String res = null;
		try {
			Notice vo = new Notice();
			BeanUtils.copyProperties(myForm, vo);
			WebUser user = SessionManager.getCurrentUser(request);
			vo.setEb(user.getName());
			noticeService.save(vo);
		} catch (Exception e) {
			logger.error("save Notice error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	
}
