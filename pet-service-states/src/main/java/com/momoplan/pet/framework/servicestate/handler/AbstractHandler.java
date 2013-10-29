package com.momoplan.pet.framework.servicestate.handler;

import java.io.IOException;

import javax.annotation.Resource;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.servicestate.common.Constants;
import com.momoplan.pet.framework.servicestate.service.StateService;
import com.momoplan.pet.framework.servicestate.service.impl.StateServiceImpl;

public abstract class AbstractHandler extends PetUtil implements RequestHandler {
	
	protected StateService stateService = Bootstrap.getBean(StateServiceImpl.class);
	protected Gson gson = MyGson.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
	@Resource
	protected CommonConfig commonConfig = null;
	Gson gs = MyGson.getInstance();
	public SsoAuthenticationToken verifyToken(ClientRequest clientRequest){
		String token = clientRequest.getToken();
		JSONObject bodyJson = new JSONObject();
		try {
			bodyJson.accumulate("method", "token");
			bodyJson.accumulate("token", token);
			logger.debug("\nbodyJsonStr:"+bodyJson.toString());
			String str = ssoProxyRequest(bodyJson.toString()).toString();
			if(str.contains("false")){
				return null;
			}
			logger.debug(str);
			SsoAuthenticationToken authenticationToken = gs.fromJson(str, SsoAuthenticationToken.class);
			//= new ObjectMapper().reader(SsoAuthenticationToken.class).readValue(str);
			return authenticationToken;
		} catch (Exception e) {
			logger.debug("token verify error...");
			e.printStackTrace();
			return null;
		}
	}
	
	private Object ssoProxyRequest(String body){
		logger.debug("\nbody:"+body);
		String responseStr;
		try {
			responseStr = PostRequest.postText(commonConfig.get(Constants.SERVICE_URI_PET_SSO, null), "body",body);
			if(responseStr.contains("false")){
				return "false";
			}
			JSONObject json = new JSONObject(responseStr);
			logger.debug("\nresponseStr:"+responseStr);
			Success success;
			success = new ObjectMapper().reader(Success.class).readValue(responseStr);
			if(success.isSuccess()){
				String str = json.getJSONObject("entity").getString("authenticationToken").toString();
				return str;
			}else{
				return "false";
			}
		} catch (JsonProcessingException e) {
			logger.debug("json processing error...");
			e.printStackTrace();
			return "false";
		} catch (IOException e) {
			logger.debug("sso request errro...");
			e.printStackTrace();
			return "false";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "false";
		}
	}
}
