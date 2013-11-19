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
import com.momoplan.pet.commons.domain.manager.po.MgrChannelDict;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.petservice.report.service.ChannelDictService;

@Controller
public class ChannelDictAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(ChannelDictAction.class);
	@Autowired
	private ChannelDictService channelDictService = null;
	/**
	 * 渠道字典管理
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/petservice/report/channelDictMain.html")
	public String main(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/report/channelDictMain.html");
		try {
			List<MgrChannelDict> list = channelDictService.getChannelDictList();
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("channelDictMain",e);
		}
		return "/petservice/report/channelDictMain";
	}

	@RequestMapping("/petservice/report/channelDictAddOrEdit.html")
	public String addOrEdit(MgrChannelDict myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/report/channelDictAddOrEdit.html");
		try {
			logger.debug("input="+gson.toJson(myForm));
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(MgrChannelDict.class, id);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("channelDictAddOrEdit",e);
		}
		return "/petservice/report/channelDictAddOrEdit";
	}
	
	@RequestMapping("/petservice/report/channelDictSave.html")
	public void save(MgrChannelDict myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/report/channelDictSave.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("callbackType","closeCurrent");
		json.put("navTabId","channelDictMainPanel");
		json.put("forwardUrl",ctx+"/petservice/report/channelDictMain.html");
		String res = null;
		try {
			myForm.setEb(SessionManager.getCurrentUser(request).getUsername());
			logger.debug("input:"+gson.toJson(myForm));
			MgrChannelDict po = new MgrChannelDict();
			BeanUtils.copyProperties(myForm, po);
			channelDictService.addOrEdit(po);
		} catch (Exception e) {
			logger.error("channelDictSave error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}

	
	@RequestMapping("/petservice/report/channelDictDel.html")
	public void del(MgrChannelDict myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/report/channelDictDel.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("navTabId","channelDictMainPanel");
		json.put("forwardUrl",ctx+"/petservice/report/channelDictMain.html");
		String res = null;
		try {
			logger.debug("input:"+gson.toJson(myForm));
			mapperOnCache.deleteByPrimaryKey(MgrChannelDict.class, myForm.getId());
		} catch (Exception e) {
			logger.error("channelDictDel error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("del_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}

}
