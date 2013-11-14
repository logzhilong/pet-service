package com.momoplan.pet.framework.fileserver.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;

public class PwdInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(PwdInterceptor.class);
	
	@Resource
	private CommonConfig commonConfig = null;
	private Map<String,Boolean> memCache = new HashMap<String,Boolean>(512);
	
	private boolean isWrongPwd(String token){
		if(memCache.get(token)!=null){
			if(memCache.size()>2000000){
				memCache.clear();
				logger.info("Clear memCache , size > 2000000");
			}
			logger.debug("memCache find token="+token);
			return false;
		}
		String sso_server = commonConfig.get("service.uri.pet_sso");
		try {
			ClientRequest request = new ClientRequest();
			request.setMethod("token");
			request.setChannel("1");
			request.setToken(token);
			String json = PostRequest.postText(sso_server, "body",MyGson.getInstance().toJson(request));
			logger.debug("token : "+json);
			JSONObject success = new JSONObject(json);
			if(success!=null&&success.getBoolean("success")){
				memCache.put(token, true);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return true;
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle....."+request.getContextPath());
		String token = request.getParameter("token");
		if(isWrongPwd(token)){
			PetUtil.writeStringToResponse(new Success(false,"Faild Token.").toString(), response);
		}else{
			logger.debug(token);
			return true;
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}
