package com.momoplan.pet.framework.petservice.push.controller;

import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.push.service.IphonePushTask;
import com.momoplan.pet.framework.petservice.push.service.PushService;
import com.momoplan.pet.framework.petservice.push.vo.PushState;

@Controller
public class PushAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(PushAction.class);
	@Autowired
	private PushService pushService = null;
	@Autowired
	private JmsTemplate apprequestTemplate = null;

	@RequestMapping("/petservice/push/pushMain.html")
	public String main(MgrPush myForm,Page<MgrPush> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/push/pushMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			pages.setPageSize(400);
			Page<MgrPush> page = pushService.getMgrPushList(pages,myForm);
			model.addAttribute("page", page);
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
			logger.debug("//TODO 把消息推出去 根据 push = OK");
			if("OK".equalsIgnoreCase(push)){
				//其实，应该时 pending 状态，由异步线程负责更新状态
				myForm.setState(PushState.PUSHED.getCode());
				logger.debug("添加一个IOS推送任务");
				MgrPush task = mapperOnCache.selectByPrimaryKey(MgrPush.class, myForm.getId());
				push2mq(task);
				IphonePushTask.queue.put(task);
			}
			pushService.save(myForm);
		} catch (Exception e) {
			logger.error("save MgrPush error",e);
			json.put("message","失败:"+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	private void push2mq(MgrPush vo) {
		try{
			String dest = "pet_push_xmpp_pubsub";
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName(dest);
			TextMessage tm = new ActiveMQTextMessage();
			String msg = gson.toJson(vo);
			tm.setText(msg);
			apprequestTemplate.convertAndSend(queue, tm);
			logger.debug("dest=pet_push_xmpp_pubsub ; msg="+msg);
		}catch(Exception e){
			logger.debug("推送消息异常不能中断程序");
			logger.error("send message",e);
		}
	}
}
