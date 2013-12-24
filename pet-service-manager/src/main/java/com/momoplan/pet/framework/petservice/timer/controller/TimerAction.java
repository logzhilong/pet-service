package com.momoplan.pet.framework.petservice.timer.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTask;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.timer.service.TimerService;
import com.momoplan.pet.framework.petservice.timer.vo.TimerState;

@Controller
public class TimerAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(TimerAction.class);
	@Autowired
	private TimerService timerService = null;

	@RequestMapping("/petservice/timer/timerMain.html")
	public String main(MgrTimerTask myForm,Page<MgrTimerTask> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/timer/timerMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			Page<MgrTimerTask> page = timerService.getMgrTimerTaskList(pages,myForm);
			model.addAttribute("page", page);
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("timerMain error",e);
		}
		return "/petservice/timer/timerMain";
	}
	
	@RequestMapping("/petservice/timer/timerAddOrEdit.html")
	public String addOrEdit(String id,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		try{
			MgrTimerTask task = mapperOnCache.selectByPrimaryKey(MgrTimerTask.class, id);
			model.addAttribute("myForm", task);
		}catch(Exception e){
			logger.error("timerAddOrEdit error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/timer/timerAddOrEdit";
	}	
	
	@RequestMapping("/petservice/timer/timerSave.html")
	public void save(MgrTimerTask myForm,String at_str,String state,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("/petservice/timer/timerSave.html");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("navTabId","panel0701");//panel0501
		json.put("forwardUrl",request.getContextPath()+"/petservice/timer/timerMain.html");
		String res = null;
		try {
			MgrTimerTask t = mapperOnCache.selectByPrimaryKey(MgrTimerTask.class, myForm.getId());
			if(TimerState.FINISHED.getCode().equalsIgnoreCase(t.getState())){
				throw new Exception("此状态不能调整.");
			}
			WebUser user = SessionManager.getCurrentUser(request);
			myForm.setEb(user.getName());
			myForm.setEt(new Date());
			if("cancel".equalsIgnoreCase(state)){
				myForm.setState(TimerState.CANCEL.getCode());
				logger.info("撤销计划任务 id="+myForm.getId());
			}else if("active".equalsIgnoreCase(state)){
				myForm.setState(TimerState.NEW.getCode());
				logger.info("激活计划任务 id="+myForm.getId());
			}else{
				myForm.setState(TimerState.NEW.getCode());
				myForm.setAt(DateUtils.getDate(at_str, DateUtils.DEFAULT_DATETIME_FORMAT));
				json.put("callbackType","closeCurrent");
			}
			mapperOnCache.updateByPrimaryKeySelective(myForm,myForm.getId());
		} catch (Exception e) {
			logger.error("save timerMain error",e);
			json.put("message","失败:"+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
}
