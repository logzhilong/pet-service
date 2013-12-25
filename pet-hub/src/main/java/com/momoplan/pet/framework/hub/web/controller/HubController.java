package com.momoplan.pet.framework.hub.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.hub.web.EventQueue;
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
	private EventQueue eventQueue = null;
	
	@Autowired
	public HubController(CommonConfig commonConfig, EventQueue eventQueue) {
		super();
		this.commonConfig = commonConfig;
		this.eventQueue = eventQueue;
	}

	/**
	 * 获取集合配置信息
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/request")
	public void request(String body,HttpServletRequest request,HttpServletResponse response,Model model) throws Exception{
		logger.debug("[INPUT]:body="+body);
		String rtn = null;
		long start = System.currentTimeMillis();
		String service = null;
		String method = null;
		String sn = null;
		try{
			ClientRequest clientRequest = PetUtil.reviceClientRequest(body);
			sn = clientRequest.getSn();
			service = clientRequest.getService();
			method = clientRequest.getMethod();
			String serviceUri = commonConfig.get(service,null);
			logger.debug("service="+service+" ; service_uri="+serviceUri);
			rtn = PostRequest.postText(serviceUri, "body",body);
		}catch(Exception e){
			logger.error("pet-hub error",e);
	        rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug("[OUTPUT]:rtn="+rtn);
			publishEvent(body,rtn);
			request.setAttribute("rtn", rtn);//向拦截器传递输出值
			long end = System.currentTimeMillis();
			logger.info("["+service+"."+method+"]-"+(end-start)+"ms.");			
			PetUtil.writeStringToResponse(rtn, response);
		}
	}
	
	/**
	 * 向消息服务器发送一个TOPIC事件
	 * @param input
	 * @param output
	 */
	private void publishEvent(String input,String output){
		try {
			logger.debug("publishEvent...");
			JSONObject inputJson = new JSONObject(input);
			JSONObject outputJson = new JSONObject(output);
			if(outputJson.getBoolean("success")){//请求成功，则不用记录结果集
				outputJson.remove("entity");
			}
			output = outputJson.toString();
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("input", inputJson);
			jsonObj.put("output", outputJson);
			String event = jsonObj.toString();
			if(!eventQueue.push(event)){
				logger.error("消息服务器异常了么？？？");
			}
		} catch (JSONException e) {
			logger.error("JSONException error",e);		
		}
	}
	
}
