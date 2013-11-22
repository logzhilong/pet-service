package com.momoplan.pet.framework.petservice.version.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.petservice.version.service.VersionService;

/**
 * 软件版本管理
 * @author liangc
 */
@Controller
public class VersionAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(VersionAction.class);
	@Autowired
	private VersionService versionService = null;
	
	/**
	 * 软件版本列表
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/petservice/version/main.html")
	public String main(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/version/main.html");
		try {
			List<SsoVersion> list = versionService.getSsoVersionList();
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("/petservice/version/main.html",e);
		}
		return "/petservice/version/main";
	}

	@RequestMapping("/petservice/version/edit.html")
	public String edit(SsoVersion myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/version/edit.html");
		try {
			logger.debug("input="+gson.toJson(myForm));
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = versionService.get(id);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("/petservice/version/edit.html",e);
		}
		return "/petservice/version/edit";
	}
	
	@RequestMapping("/petservice/version/save.html")
	public void save(SsoVersion myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/version/save.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("callbackType","closeCurrent");
		json.put("navTabId","versionMain");
		json.put("forwardUrl",ctx+"/petservice/version/main.html");
		String res = null;
		try {
			logger.debug("input:"+gson.toJson(myForm));
			versionService.update(myForm);
		} catch (Exception e) {
			logger.error("/petservice/version/save.html",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}

}
