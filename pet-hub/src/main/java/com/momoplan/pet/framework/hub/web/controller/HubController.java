package com.momoplan.pet.framework.hub.web.controller;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
/**
 * 转发服务
 * @author liangc
 * [INPUT]:body={"service":"service.uri.pet_sso","method":"login","channel":"1","params":{"username":"cc","password":"123"}}
 * [OUTPUT]:rtn={"success":true,"entity":{"authenticationToken":{"token":"xxx","createDate":"xxx","expire":-1,"userid":747},"chatserver":{"id":1,"address":"xxx","name":"xxx","version":0}}}
 */
@Controller
public class HubController {
	private static Logger logger = LoggerFactory.getLogger(HubController.class);
	private CommonConfig commonConfig = null;
	private JmsTemplate apprequestTemplate;
	
	@Autowired
	public HubController(CommonConfig commonConfig, JmsTemplate apprequestTemplate) {
		super();
		this.commonConfig = commonConfig;
		this.apprequestTemplate = apprequestTemplate;
	}

	/**
	 * 获取集合配置信息
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/request")
	public @ResponseBody String putFile(String body,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		logger.debug("[INPUT]:body="+body);
		String rtn = null;
		try{
			ClientRequest clientRequest = PetUtil.reviceClientRequest(body);
			String service = clientRequest.getService();
			String serviceUri = commonConfig.get(service,null);
			logger.debug("service="+service+" ; service_uri="+serviceUri);
			rtn = PostRequest.postText(serviceUri, "body",body);
		}catch(Exception e){
			logger.error("pet-hub error",e);
	        rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug("[OUTPUT]:rtn="+rtn);
			publishEvent(body,rtn);
			request.setAttribute("rtn", rtn);//向拦截器传递输出值
		}
		return rtn;
	}
	
	/**
	 * 向消息服务器发送一个TOPIC事件
	 * @param input
	 * @param output
	 */
	private void publishEvent(String input,String output){
		try {
			logger.debug("publishEvent...");
			TextMessage tm = new ActiveMQTextMessage();
			tm.setStringProperty("body", input);
			tm.setStringProperty("ret", output);
			apprequestTemplate.convertAndSend(tm);
		} catch (JMSException e) {
			logger.error("publishEvent error",e);		
		}
	}
	
}
