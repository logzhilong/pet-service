package com.momoplan.pet.framework.user.handler;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.po.PetInfo;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.web.BaseController;
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.service.impl.UserServiceImpl;

public abstract class AbstractHandler extends BaseController implements RequestHandler {
	
	protected UserService ssoService = Bootstrap.getBean(UserServiceImpl.class);
	
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
	
	protected UserService userService = Bootstrap.getBean(UserService.class);
	
	protected SsoUser reviceSsoUser(ClientRequest clientRequest){
		String nickname = PetUtil.getParameter(clientRequest, "nickname");
		String birthdate = PetUtil.getParameter(clientRequest, "birthdate");
		String gender = PetUtil.getParameter(clientRequest, "gender");
		String city = PetUtil.getParameter(clientRequest, "city");
		String signature = PetUtil.getParameter(clientRequest, "signature");
		String img = PetUtil.getParameter(clientRequest, "img");
		String hobby = PetUtil.getParameter(clientRequest, "hobby");
		String backgroundImg = PetUtil.getParameter(clientRequest, "backgroundImg");
		SsoUser petUser = new SsoUser();
		petUser.setSignature(signature);
		petUser.setBackgroundImg(backgroundImg);
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
