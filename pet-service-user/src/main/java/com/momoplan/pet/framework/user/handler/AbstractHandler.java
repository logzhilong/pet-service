package com.momoplan.pet.framework.user.handler;

import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.service.impl.UserServiceImpl;

public abstract class AbstractHandler extends PetUtil implements RequestHandler {
	
	protected UserService ssoService = Bootstrap.getBean(UserServiceImpl.class);
	protected Gson gson = MyGson.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
	
	protected CommonConfig commonConfig = Bootstrap.getBean(CommonConfig.class);

	protected UserService userService = Bootstrap.getBean(UserService.class);
	
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
	
	protected SsoUser reviceSsoUser(ClientRequest clientRequest){
		String nickname = PetUtil.getParameter(clientRequest, "nickname");
		String birthdate = PetUtil.getParameter(clientRequest, "birthdate");
		String gender = PetUtil.getParameter(clientRequest, "gender");
		String city = PetUtil.getParameter(clientRequest, "city");
		String signature = PetUtil.getParameter(clientRequest, "signature");
		String img = PetUtil.getParameter(clientRequest, "img");
		String hobby = PetUtil.getParameter(clientRequest, "hobby");
		SsoUser petUser = new SsoUser();
		petUser.setSignature(signature);
		petUser.setBirthdate(birthdate);
		petUser.setGender(gender);
		petUser.setCity(city);
		petUser.setNickname(nickname);
		petUser.setHobby(hobby);
		petUser.setImg(img);
		petUser.setCreateTime(new Date(System.currentTimeMillis()));
		logger.debug("reviceSsoUser : "+petUser.toString());
		return petUser;
	}
	
	protected PetInfo revicePetInfo(ClientRequest clientRequest){
		
		String nickname = PetUtil.getParameter(clientRequest, "nickname");
		String type = PetUtil.getParameter(clientRequest, "type");
		String img = PetUtil.getParameter(clientRequest, "img");
		String trait = PetUtil.getParameter(clientRequest, "trait");
		String gender = PetUtil.getParameter(clientRequest, "gender");
		String birthdate = PetUtil.getParameter(clientRequest, "birthdate");
		String id = PetUtil.getParameter(clientRequest, "id");
		
		PetInfo petInfo = new PetInfo();
		petInfo.setNickname(nickname);
		petInfo.setType(Long.parseLong(type));
		petInfo.setImg(img);
		petInfo.setTrait(trait);
		petInfo.setGender(gender);
		petInfo.setBirthdate(birthdate);
		petInfo.setId(id);
		
		return petInfo;
	}
}
