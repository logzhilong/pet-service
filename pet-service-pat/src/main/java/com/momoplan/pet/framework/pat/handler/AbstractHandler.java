package com.momoplan.pet.framework.pat.handler;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.pat.service.PatService;

public abstract class AbstractHandler extends PetUtil implements RequestHandler {
	
	protected Gson gson = MyGson.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
	
	protected CommonConfig commonConfig = Bootstrap.getBean(CommonConfig.class);

	protected PatService patService = Bootstrap.getBean(PatService.class);
	
	protected SsoAuthenticationToken getToken(String token) throws Exception{
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
				JSONObject login = success.getJSONObject("entity");
				JSONObject authToken = login.getJSONObject("authenticationToken");
				return gson.fromJson(authToken.toString(), SsoAuthenticationToken.class);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
		return null;
	}
	
	protected String getUseridFParamSToken(ClientRequest clientRequest) throws Exception{
		String userid = null;
		if(clientRequest.getParams()!=null&&StringUtils.isNotEmpty(getParameter(clientRequest, "userid"))){
			userid = getParameter(clientRequest, "userid");
			logger.debug("根据userid 获取用户信息 userid="+userid);
		}else{
			String token = clientRequest.getToken();
			userid = getToken(token).getUserid();
			logger.debug("根据token 获取用户信息 token="+token);
		}
		return userid;
	}
	
}
