package com.momoplan.pet.framework.manager.security;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.momoplan.pet.framework.manager.vo.WebUser;

public class SessionManager {
	
	private static Logger logger = LoggerFactory.getLogger(SessionManager.class);
	
	public final static String SESSION_KEY = "asdfghjklzxcvbnm1234567890";
	
	public static WebUser getCurrentUser(HttpServletRequest request){
		try{
			return (WebUser)request.getSession().getAttribute(SESSION_KEY);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static void initSession(HttpServletRequest request,WebUser user,boolean override) throws Exception {
		logger.debug("initSession : "+user.toString());
		WebUser cu = getCurrentUser(request);
		if(cu==null || override)
			request.getSession().setAttribute(SESSION_KEY,user);
		else
			throw new Exception("session not null and override=="+override);
	}
	
	public static void clean(HttpServletRequest request){
		request.getSession().removeAttribute(SESSION_KEY);
	}
	
}
