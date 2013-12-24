package com.momoplan.pet.framework.petservice.push.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.push.service.PushService;

@Controller
public class PushAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(PushAction.class);
	@Autowired
	private PushService pushService = null;

	@RequestMapping("/petservice/push/pushMain.html")
	public String main(MgrPush myForm,Page<MgrPush> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/push/pushMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			Page<MgrPush> page = pushService.getMgrPushList(pages,myForm);
			model.addAttribute("page", page);
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("MgrPush error",e);
		}
		return "/petservice/push/pushMain";
	}
	
	@RequestMapping("/petservice/push/pushSave.html")
	public void save(MgrPush myForm,String navTabId,String push,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//String ctx = request.getContextPath();
		logger.debug("/petservice/push/pushSave.html");
		logger.debug("input:"+gson.toJson(myForm));
		logger.debug("input: navTabId="+navTabId);

		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("navTabId",navTabId);//panel0501
		//json.put("callbackType","closeCurrent");
		//json.put("forwardUrl",ctx+"/petservice/push/pushMain.html");
		String res = null;
		try {
			WebUser user = SessionManager.getCurrentUser(request);
			myForm.setEb(user.getName());
			pushService.save(myForm,push);
		} catch (Exception e) {
			logger.error("save MgrPush error",e);
			json.put("message","失败:"+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	
	@RequestMapping("/petservice/push/timerAdd.html")
	public String timerAdd(String id,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		try{
			MgrPush p = mapperOnCache.selectByPrimaryKey(MgrPush.class, id);
			model.addAttribute("myForm", p);
		}catch(Exception e){
			logger.error("timerAddOrEdit error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/push/timerAdd";
	}
	

	@RequestMapping("/petservice/push/timerSave.html")
	public void timerSave(MgrPush myForm,String at_str,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("navTabId","panel0601");
		json.put("callbackType","closeCurrent");
		json.put("forwardUrl",ctx+"/petservice/push/pushMain.html");
		String res = null;
		try {
			WebUser user = SessionManager.getCurrentUser(request);
			pushService.saveTimer(myForm, at_str, user.getUsername());
		} catch (Exception e) {
			logger.error("timerSave error",e);
			json.put("message","失败:"+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
}
