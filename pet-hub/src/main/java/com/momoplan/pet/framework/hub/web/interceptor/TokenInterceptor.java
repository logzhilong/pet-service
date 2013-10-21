package com.momoplan.pet.framework.hub.web.interceptor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
/**
 * TOKEN 拦截器
 * <br>
 * passMethodMap 中配置的方法会跳过拦截
 * @author liangc
 */
public class TokenInterceptor implements HandlerInterceptor {
	
	private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
	
	private CommonConfig commonConfig = null;
	private MapFactoryBean passMethodMap = null;
	private Gson gson = MyGson.getInstance();

	@Autowired
	public TokenInterceptor(CommonConfig commonConfig, MapFactoryBean passMethodMap) {
		super();
		this.commonConfig = commonConfig;
		this.passMethodMap = passMethodMap;
	}
	/**
	 * 请求拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.debug("preHandle.....");
		String body = request.getParameter("body");
		ClientRequest clientRequest = PetUtil.reviceClientRequest(body);
		String service = clientRequest.getService();
		String method = clientRequest.getMethod();
		
		if(service!=null&&service.equals(passMethodMap.getObject().get(method))){
			logger.debug("跳过TOKEN校验 : service="+service+" ; method="+method);
			return true;
		}
		String token = clientRequest.getToken();
		if(!checkToken(token)){
			logger.debug("TOKEN 校验未通过");
			PetUtil.writeStringToResponse(new Success(false,"Faild Token.").toString(), response);
			return false;
		}
		logger.debug(token);
		return true;
	}
	/**
	 * 渲染视图前拦截
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String body = request.getParameter("body");
		String rtn = (String)request.getAttribute("rtn");
		try{
			sendMessage4VerificationCode(body,rtn);
		}catch(Exception e){
			logger.error("短信验证码异常",e);
		}
		logger.debug("postHandle...插入事件拦截");
	}
	/**
	 * 请求完成后拦截
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//logger.debug("afterCompletion...异常拦截器");
	}
	/**
	 * 发送手机短信
	 * @param clientRequest
	 * @throws Exception 
	 */
	private void sendMessage4VerificationCode(String body,String rtn) throws Exception{
		ClientRequest clientRequest = PetUtil.reviceClientRequest(body);
		String service = clientRequest.getService();
		String method = clientRequest.getMethod();
		if("service.uri.pet_sso".equals(service)&&"getVerificationCode".equals(method)){
			Success success = gson.fromJson(rtn, Success.class);
			if(success.isSuccess()){
				String phoneNumber = PetUtil.getParameter(clientRequest, "phoneNumber");
				Object xcode = success.getEntity();
				String userId = commonConfig.get("sms.username");
				String password = commonConfig.get("sms.password");
				String url = commonConfig.get("sms.path");
				String[] params = new String[]{
						"userId",userId,
						"password",password,
						"pszMobis",phoneNumber,
						"pszMsg","验证码:"+xcode.toString(),
						"iMobiCount","1",
						"pszSubPort","***********"
				};
				PostRequest.postText(url, params);
				logger.debug("发送短信 params="+gson.toJson(params));
			}
		}
	}
	/**
	 * TOKEN 是否有效
	 * @param token
	 * @return true 有效，false 无效
	 */
	private boolean checkToken(String token){
		String sso_server = commonConfig.get("service.uri.pet_sso");
		try {
			ClientRequest request = new ClientRequest();
			request.setMethod("token");
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("token", token);
			request.setParams(params);
			String json = PostRequest.postText(sso_server, "body",MyGson.getInstance().toJson(request));
			logger.debug("token : "+json);
			Success success = MyGson.getInstance().fromJson(json, Success.class);
			if(success.isSuccess())
				return true;
			return false;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
}