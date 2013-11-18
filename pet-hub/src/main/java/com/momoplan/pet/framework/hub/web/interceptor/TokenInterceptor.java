package com.momoplan.pet.framework.hub.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
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
	private Map<String,Boolean> memCache = new HashMap<String,Boolean>(512);
	
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
		String body = request.getParameter("body");
		logger.debug("preHandle....."+body);
		ClientRequest clientRequest = PetUtil.reviceClientRequest(body);
		String service = clientRequest.getService();
		String method = clientRequest.getMethod();
		
		if(service!=null&&service.equals(passMethodMap.getObject().get(method))){
			logger.debug("跳过TOKEN校验 : service="+service+" ; method="+method);
			return true;
		}
		String c = clientRequest.getChannel();//如果 channel == 9 则表示为 XMPP 服务器回调的请求，不需要校验TOKEN
		if("9".equals(c)){
			logger.debug("跳过TOKEN校验 : XMPP 回调请求");
			return true;
		}
		String token = clientRequest.getToken();
		if(!checkToken(token)){
			logger.debug("TOKEN 校验未通过");
			PetUtil.writeStringToResponse(new Success(false,"Faild Token.").toString(), response);
			return false;
		}
		logger.debug(token+" TOKEN 校验通过");
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
		boolean sendXcode = "service.uri.pet_sso".equals(service)&&"getVerificationCode".equals(method);
		logger.debug("service="+service+" ; method="+method+ " ; 验证码判断="+sendXcode+" ; rtn="+rtn);
		if(sendXcode){
			Success success = gson.fromJson(rtn, Success.class);
			if(success.isSuccess()){
				String phoneNumber = PetUtil.getParameter(clientRequest, "phoneNum");
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
				String res = PostRequest.postText(url, params);
				logger.debug("发送短信 input="+gson.toJson(params));
				logger.debug("发送短信 output="+res);
			}
		}
	}
	/**
	 * TOKEN 是否有效
	 * @param token
	 * @return true 有效，false 无效
	 */
	private boolean checkToken(String token){
		if(memCache.get(token)!=null){
			if(memCache.size()>2000000){
				memCache.clear();
				logger.info("Clear memCache , size > 2000000");
			}
			logger.debug("memCache find token="+token);
			return true;
		}
		String sso_server = commonConfig.get("service.uri.pet_sso");
		try {
			ClientRequest request = new ClientRequest();
			request.setMethod("token");
			request.setToken(token);
			String json = PostRequest.postText(sso_server, "body",MyGson.getInstance().toJson(request));
			logger.debug("token : "+json);
			JSONObject success = new JSONObject(json);
			boolean isSuccess = success.getBoolean("success");
			if(isSuccess){
				memCache.put(token, isSuccess);
				return true;
			}
			return false;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return false;
	}
}