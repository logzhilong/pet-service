package com.momoplan.pet.framework.petservice.ency.controller;

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
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.ency.service.EncyService;

@Controller
public class EncyAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(EncyAction.class);
	@Autowired
	private EncyService encyService = null;
	
	@RequestMapping("/petservice/ency/encyMain.html")
	public String main(Ency myForm,Page<Ency> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/ency/encyMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			pages.setPageSize(400);
			String pid = myForm.getPid();
			if(StringUtils.isNotEmpty(pid)){
				Ency pf = mapperOnCache.selectByPrimaryKey(Ency.class, pid);
				model.addAttribute("pf", pf);
			}
			Page<Ency> page = encyService.getEncyList(pages,myForm);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("百科 error",e);
		}
		return "/petservice/ency/encyMain";
	}
	
	@RequestMapping("/petservice/ency/encyAddOrEdit.html")
	public String addOrEdit(Ency myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/ency/encyAddOrEdit.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(Ency.class, id);
			}
			String pid = myForm.getPid();
			if(StringUtils.isNotEmpty(pid)){
				myForm.setPid(pid);
				Ency pf = mapperOnCache.selectByPrimaryKey(Ency.class, pid);
				model.addAttribute("pf", pf);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("add or edit error",e);
		}
		return "/petservice/ency/encyAddOrEdit";
	}

	@RequestMapping("/petservice/ency/encySave.html")
	public void save(Ency myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/ency/encySave.html");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0402");
		json.put("forwardUrl",ctx+"/petservice/ency/encyMain.html?pid="+myForm.getPid());
		String res = null;
		try {
			Ency vo = new Ency();
			BeanUtils.copyProperties(myForm, vo);
			WebUser user = SessionManager.getCurrentUser(request);
			vo.setEb(user.getName());
			encyService.save(vo);
		} catch (Exception e) {
			logger.error("save ency error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	
}
