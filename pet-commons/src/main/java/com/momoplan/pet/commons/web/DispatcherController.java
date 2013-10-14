package com.momoplan.pet.commons.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
/**
 * 请求分发，分发到不同的 handler 
 * @author liangc
 */
@Controller
public class DispatcherController extends PetUtil {
	
	private Logger logger = LoggerFactory.getLogger(DispatcherController.class);

	@RequestMapping("/request")
	public void request(String body, HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("input : "+logger);
		try{
			ClientRequest clientRequest = reviceClientRequest(body);
			String method = clientRequest.getMethod();
			RequestHandler handrel = (RequestHandler)Bootstrap.getBean(method);
			handrel.process(clientRequest, response);
		}catch(Exception e){
			writeStringToResponse(new Success(false,e.getMessage()).toString(),response);
		}
	}
	
}
