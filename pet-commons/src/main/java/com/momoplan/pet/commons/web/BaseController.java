package com.momoplan.pet.commons.web;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;

public class BaseController extends PetUtil {
	
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	protected CommonConfig commonConfig = null;
	
	protected Gson gson = MyGson.getInstance();
	
	public JSONObject getToken(String token) throws Exception{
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
				return authToken;
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw e;
		}
		return null;
	}
	
	public String getUseridFParamSToken(ClientRequest clientRequest) throws Exception{
		String userid = null;
		if(clientRequest.getParams()!=null&&StringUtils.isNotEmpty(getParameter(clientRequest, "userid"))){
			userid = getParameter(clientRequest, "userid");
			logger.debug("根据userid 获取用户信息 userid="+userid);
		}else{
			String token = clientRequest.getToken();
			userid = getToken(token).getString("userid");
			logger.debug("根据token 获取用户信息 token="+token);
		}
		return userid;
	}
	
}
