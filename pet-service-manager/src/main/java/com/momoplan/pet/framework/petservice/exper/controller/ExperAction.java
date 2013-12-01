package com.momoplan.pet.framework.petservice.exper.controller;

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
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.exper.service.ExperService;

@Controller
public class ExperAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(ExperAction.class);
	@Autowired
	private ExperService experService = null;
	
	@RequestMapping("/petservice/exper/experMain.html")
	public String main(Exper myForm,Page<Exper> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/exper/experMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			pages.setPageSize(400);
			String pid = myForm.getPid();
			if(StringUtils.isNotEmpty(pid)){
				Exper pf = mapperOnCache.selectByPrimaryKey(Exper.class, pid);
				model.addAttribute("pf", pf);
			}
			Page<Exper> page = experService.getExperList(pages,myForm);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("百科 error",e);
		}
		return "/petservice/exper/experMain";
	}
	
	@RequestMapping("/petservice/exper/experAddOrEdit.html")
	public String addOrEdit(Exper myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/exper/experAddOrEdit.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(Exper.class, id);
			}
			String pid = myForm.getPid();
			if(StringUtils.isNotEmpty(pid)){
				myForm.setPid(pid);
				Exper pf = mapperOnCache.selectByPrimaryKey(Exper.class, pid);
				model.addAttribute("pf", pf);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("add or edit error",e);
		}
		return "/petservice/exper/experAddOrEdit";
	}

	@RequestMapping("/petservice/exper/experSave.html")
	public void save(Exper myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/exper/experSave.html");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0403");
		json.put("forwardUrl",ctx+"/petservice/exper/experMain.html?pid="+myForm.getPid());
		String res = null;
		try {
			Exper vo = new Exper();
			BeanUtils.copyProperties(myForm, vo);
			WebUser user = SessionManager.getCurrentUser(request);
			vo.setEb(user.getName());
			experService.save(vo);
		} catch (Exception e) {
			logger.error("save Exper error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	
}
