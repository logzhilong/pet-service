package com.momoplan.pet.framework.fileserver.web.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

public class PwdInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(PwdInterceptor.class);
	private Md5PasswordEncoder encode = new Md5PasswordEncoder();

	private boolean isWrongPwd(String _pwd){
		String pwd = new SimpleDateFormat("yyyyMMdd").format(new Date());
		pwd = encode.encodePassword(pwd, null);
		logger.debug("real pwd is\t: "+pwd);
		logger.debug("input pwd is\t: "+_pwd);
		if(pwd.equalsIgnoreCase(_pwd)){
			return false;
		}
		return true;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle....."+request.getContextPath());
		String pwd = request.getParameter("pwd");
		if(isWrongPwd(pwd)){
			response.setCharacterEncoding("UTF-8");
			JsonObject json = new JsonObject();
			json.addProperty("returnCode", "ERROR");
			json.addProperty("returnValue", "Wrong password");
			json.addProperty("returnError", "Wrong password");
			response.getWriter().write(json.toString());
			return false;
		}else{
			logger.debug(pwd);
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
