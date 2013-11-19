package com.momoplan.pet.framework.petservice.report.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.manager.po.MgrServiceDict;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.petservice.report.service.ServiceDictService;
import com.momoplan.pet.framework.petservice.report.vo.MgrServiceDictVo;

@Controller
public class ServiceDictAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(ServiceDictAction.class);
	@Autowired
	private ServiceDictService serviceDictService = null;
	/**
	 * 服务字典管理
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/petservice/report/serviceDictMain.html")
	public String main(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/report/serviceDictMain.html");
		try {
			List<MgrServiceDict> list = serviceDictService.getServiceDictList();
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("serviceDictMain",e);
		}
		return "/petservice/report/serviceDictMain";
	}

	@RequestMapping("/petservice/report/serviceDictAddOrEdit.html")
	public String addOrEdit(MgrServiceDict myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/report/serviceDictAddOrEdit.html");
		try {
			logger.debug("input="+gson.toJson(myForm));
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(MgrServiceDict.class, id);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("serviceDictAddOrEdit",e);
		}
		return "/petservice/report/serviceDictAddOrEdit";
	}
	
	@RequestMapping("/petservice/report/serviceDictSave.html")
	public void save(MgrServiceDictVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/report/serviceDictSave.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("callbackType","closeCurrent");
		json.put("navTabId","serviceDictMainPanel");
		json.put("forwardUrl",ctx+"/petservice/report/serviceDictMain.html");
		String res = null;
		try {
			myForm.setEb(SessionManager.getCurrentUser(request).getUsername());
			myForm.setMethod(myForm.getMethodValue());
			logger.debug("input:"+gson.toJson(myForm));
			MgrServiceDict po = new MgrServiceDict();
			BeanUtils.copyProperties(myForm, po);
			serviceDictService.addOrEditServiceDict(po);
		} catch (Exception e) {
			logger.error("save serviceDict error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}

	
	@RequestMapping("/petservice/report/serviceDictDel.html")
	public void del(MgrServiceDictVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/report/serviceDictDel.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("navTabId","serviceDictMainPanel");
		json.put("forwardUrl",ctx+"/petservice/report/serviceDictMain.html");
		String res = null;
		try {
			logger.debug("input:"+gson.toJson(myForm));
			mapperOnCache.deleteByPrimaryKey(MgrServiceDict.class, myForm.getId());
		} catch (Exception e) {
			logger.error("serviceDictDel error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("del_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}

}
