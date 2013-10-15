package com.momoplan.pet.framework.fileserver.web.interceptor;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;

public class PwdInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(PwdInterceptor.class);
	
	@Resource
	private CommonConfig commonConfig = null;
	
	private boolean isWrongPwd(String token){
		String sso_server = commonConfig.get("service.uri.pet_sso");
		try {
			ClientRequest request = new ClientRequest();
			request.setMethod("token");
			request.setChannel("1");
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("token", token);
			request.setParams(params);
			String json = PostRequest.postText(sso_server, "body",new Gson().toJson(request));
			logger.debug("token : "+json);
			Success success = new Gson().fromJson(json, Success.class);
			if(success.isSuccess())
				return true;
			return false;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle....."+request.getContextPath());
		String token = request.getParameter("token");
		if(isWrongPwd(token)){
			response.setCharacterEncoding("UTF-8");
			JsonObject json = new JsonObject();
			json.addProperty("returnCode", "ERROR");
			json.addProperty("returnValue", "Wrong password");
			json.addProperty("returnError", "Wrong password");
			response.getWriter().write(json.toString());
			return false;
		}else{
			logger.debug(token);
			return true;
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
