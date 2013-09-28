package com.momoplan.pet.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.momoplan.common.PetUtil;
import com.momoplan.common.PushApn;
import com.momoplan.exception.DuplicatedUsernameException;
import com.momoplan.exception.PetException;
import com.momoplan.pet.domain.PetFile;
import com.momoplan.pet.domain.PetUser;
import com.momoplan.pet.domain.PetVersion;
import com.momoplan.pet.domain.UserFriendship;
import com.momoplan.pet.domain.UserStates;
import com.momoplan.pet.service.PetFileService;
import com.momoplan.pet.service.UploadService;
import com.momoplan.pet.service.UserFriendshipService;
import com.momoplan.pet.vo.ApnMsg;

@RequestMapping("/core")
@Controller
public class CoreController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	UserFriendshipService userFriendshipService;
	@Autowired
	PetFileService petFileService;
	@Autowired
	UploadService uploadService;
	
	@RequestMapping("request")
	public @ResponseBody Object request(@RequestParam("body")String body,HttpServletResponse response) throws Exception{
		System.out.println("----------------------------------------------------------debug");
		try{
			body = body.replaceAll("\\", "");
		}catch(Exception e){
			logger.debug("\ncoreRequest--------------------------------------------------------------------->"+body.toString());
			logger.debug(e.getMessage());
		}

		CoreRequest coreRequest=new ObjectMapper().reader(CoreRequest.class).readValue(body);
		if(coreRequest.getMethod().equals("open")){
			return null;
		}
		
		//apn推送
		if(coreRequest.getMethod().equals("pushMsgApn")){
			logger.debug("\ncoreRequest--------------------------------------------------------------------->"+coreRequest.toString());
			return handldPushMsgApn(coreRequest);
		}
		//处理好友关系
		if(coreRequest.getMethod().equals("handleFriendshipWithoutToken")){
			return handleFriendshipWithoutToken(coreRequest);
		}
		
		return body;
	}
	
	private Object handldPushMsgApn(CoreRequest coreRequest) {
		List<PetUser> petUsers = PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "fromname")).getResultList();
		if(petUsers.size()<0){
			return null;
		}
		long fromid = PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "toname")).getSingleResult().getId();
		long toid = petUsers.get(0).getId();
		String aliasName = userFriendshipService.getAliasName(toid,fromid);
		ApnMsg msg = new ApnMsg();
		if(StringUtils.isEmpty(aliasName)){
			msg.setMsg(petUsers.get(0).getNickname()+":"+PetUtil.getParameter(coreRequest, "msg"));	
		}else{
			msg.setMsg(aliasName+":"+PetUtil.getParameter(coreRequest, "msg"));
		}
		msg.setToken(PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "toname")).getSingleResult().getDeviceToken());
		logger.debug("\n msg--------------------------------------------------------------->"+msg.getMsg());
		logger.debug("\n msgtoken--------------------------------------------------------------->"+msg.getToken());
		
		PushApn.sendMsgApn(msg, 1);
		return null;
	}

	private Object handleFriendshipWithoutToken(CoreRequest coreRequest){
		try {
			if(PetUtil.getParameter(coreRequest, "SubscriptionType").compareTo("unsubscribed")==0){
				Long aId = PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "aId")).getSingleResult().getId();
				Long bId = PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "bId")).getSingleResult().getId();
				UserFriendship userFriendship = userFriendshipService.findUserFriendshipsByABId(aId,bId);
				if(null!=userFriendship){
					userFriendship.setVerified(2);
					userFriendship.merge();
				}
			}
			if(PetUtil.getParameter(coreRequest, "SubscriptionType").compareTo("subscribed")==0){
				Long aId = PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "aId")).getSingleResult().getId();
				Long bId = PetUser.findPetUsersByUsername(PetUtil.getParameter(coreRequest, "bId")).getSingleResult().getId();
				UserFriendship userFriendship = userFriendshipService.findUserFriendshipsByABId(aId,bId);
				if(null!=userFriendship){
					userFriendship.setVerified(0);
					userFriendship.merge();
				}else{
					UserFriendship uf = new UserFriendship();
					uf.setVerified(0);
					uf.setAId(aId);
					uf.setBId(bId);
					uf.persist();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "exception";
		}
		return "success";
	}
	
//	private Object handldDelFriendWithoutToken(CoreRequest coreRequest) {
//		if(PetUtil.getParameter(coreRequest, "SubscriptionType").compareTo("subscribed")!=0){
//			return null;
//		}
//		try {
//			Long aId = PetUtil.getParameterLong(coreRequest, "aId");
//			Long bId = PetUtil.getParameterLong(coreRequest, "bId");
//			UserFriendship userFriendship = userFriendshipService.findUserFriendshipsByABId(aId,bId);
//			if(null!=userFriendship){
//				userFriendship.setVerified(2);
//				userFriendship.merge();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "exception";
//		}
//		return "success";
//	}
//
//	private Object handldAddFriendWithoutToken(CoreRequest coreRequest) {
//		if(PetUtil.getParameter(coreRequest, "SubscriptionType").compareTo("subscribed")!=0){
//			return null;
//		}
//		try {
//			Long aId = PetUtil.getParameterLong(coreRequest, "aId");
//			Long bId = PetUtil.getParameterLong(coreRequest, "bId");
//			UserFriendship userFriendship = userFriendshipService.findUserFriendshipsByABId(aId,bId);
//			if(null!=userFriendship){
//				userFriendship.setVerified(0);
//				userFriendship.merge();
//			}else{
//				UserFriendship uf = new UserFriendship();
//				uf.setVerified(0);
//				uf.setAId(aId);
//				uf.setBId(bId);
//				uf.persist();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "exception";
//		}
//		return "success";
//		
//	}

//	private Object handldInsBlackListWithoutToken(CoreRequest coreRequest) {
//		if(PetUtil.getParameter(coreRequest, "SubscriptionType").compareTo("subscribed")!=0){
//			return null;
//		}
//		try {
//			Long aId = PetUtil.getParameterLong(coreRequest, "aId");
//			Long bId = PetUtil.getParameterLong(coreRequest, "bId");
//			UserFriendship userFriendship = userFriendshipService.findUserFriendshipsByABId(aId,bId);
//			if(null!=userFriendship){
//				userFriendship.setVerified(1);
//				userFriendship.merge();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "exception";
//		}
//		return "success";
//	}
	
//	private Object handleOpen(CoreRequest coreRequest) {
//		CoreResponse crp = new CoreResponse();
//		Device device = new Device();
//		try {
//			device.setCreateTime(coreRequest.getCreateTime());
//			device.setMac(coreRequest.getMac());
//			device.setImei(coreRequest.getImei());
//			device.setChannel(coreRequest.getChannel());
//			device.persist();
//		} catch (Exception e) {
//			crp.setRunningStr("初次开机异常");
//			throw new PetException("用户不存在");
//		}
//		return device;
//	}
	
	
	@RequestMapping("androidVersion")
	public @ResponseBody Object androidVersion(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			return "";
		}
		PetVersion petVersion = new PetVersion();
		petVersion.setPetVersion(file.getOriginalFilename());
		petVersion.setCreateDate(new Timestamp(System.currentTimeMillis()));
		petVersion.setPhoneType("android");
		petVersion.persist();
		FileOutputStream fileOS = new FileOutputStream(uploadService.getUploadUrl() + File.separator + petVersion.getId()+".petV");
		fileOS.write(file.getBytes());
		fileOS.close();
		return petVersion.getPetVersion();
	}
	
	@RequestMapping("iosversion")
	public @ResponseBody Object iosVersion(@RequestParam("iosversion") String iosversion) throws Exception {
		if (StringUtils.isEmpty(iosversion)) {
			return "";
		}
		PetVersion petVersion = new PetVersion();
		petVersion.setPetVersion(iosversion);
		petVersion.setCreateDate(new Timestamp(System.currentTimeMillis()));
		petVersion.setPhoneType("iphone");
		petVersion.persist();
		return petVersion.getPetVersion();
	}
	
	@RequestMapping("getPicId")
	public @ResponseBody Object getPicId() throws Exception {
		PetFile petFile = new PetFile();
		petFile.setType(2);
		petFile.setCreateDate(new Date(System.currentTimeMillis()));
		petFile.setName("openPic");
		petFile.persist();
		return petFile.getId();
	}
	
	@RequestMapping("uploadPic")
	public @ResponseBody Object uploadPic(@RequestParam("file") MultipartFile file,@RequestParam("picId") String picId) throws Exception {
		if (file.isEmpty()) {
			return "";
		}
		if(StringUtils.isEmpty(picId)){
			return "";
		}
		FileOutputStream fileOS = new FileOutputStream(uploadService.getUploadUrl() + File.separator + picId+"+"+file.getOriginalFilename());
		fileOS.write(file.getBytes());
		fileOS.close();
		return "success";
	}
	@RequestMapping("addstates")
	public @ResponseBody Object addstates(@RequestParam("fileM") MultipartFile fileM,
			@RequestParam("filemini") MultipartFile filemini,
			@RequestParam("id") String id,
			@RequestParam("longitude") String longitude,
			@RequestParam("latitude") String latitude,
			@RequestParam("msg") String msg,
			@RequestParam("type") String type
			) throws Exception {
		if (StringUtils.isEmpty(id)
				|| StringUtils.isEmpty(longitude)
				|| StringUtils.isEmpty(latitude) || StringUtils.isEmpty(msg)) {
			return "upload error";
		}
		if(StringUtils.isEmpty(type)){
			type = "0";
		}
		
		PetFile petFileM = new PetFile();
		PetFile petFilemini = new PetFile();
		UserStates userState = new UserStates();
		
		if(!fileM.isEmpty() && !filemini.isEmpty()){
			petFileM.setName(fileM.getOriginalFilename());
			petFileM.setCreateDate(new Date());
			petFileM.persist();
			FileOutputStream fileOSM = new FileOutputStream(
					uploadService.getUploadUrl() + File.separator
							+ petFileM.getId());
			fileOSM.write(fileM.getBytes());
			fileOSM.close();
			
			petFilemini.setName(filemini.getOriginalFilename());
			petFilemini.setCreateDate(new Date());
			petFilemini.persist();
			FileOutputStream fileOSmini = new FileOutputStream(
					uploadService.getUploadUrl() + File.separator
							+ petFilemini.getId());
			fileOSmini.write(fileM.getBytes());
			fileOSmini.close();	
		}
		
		userState.setPetUserid(Long.parseLong(id));
		userState.setMsg(msg);
		userState.setImgid("");
		if(!fileM.isEmpty() && !filemini.isEmpty()){
			userState.setImgid(petFilemini.getId() + "_" + petFileM.getId()+",");
		}
		userState.setIfTransmitMsg("0");
		userState.setTransmitMsg("");
		userState.setTransmitUrl("");
		userState.setStateType(type);
		userState.setSubmitTime(new Date(System.currentTimeMillis()));
		userState.setLatitude(Double.parseDouble(latitude));
		userState.setLongitude(Double.parseDouble(longitude));
		userState.persist();
		return "success";
	}
	
	@RequestMapping("uploadOpenPic")
	public @ResponseBody
	Object uploadOpenPic(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			return "";
		}
		PetFile petFile = new PetFile();
		petFile.setName(file.getOriginalFilename());
		petFile.setCreateDate(new Date());
		petFile.setType(2);
		petFile.persist();
		FileOutputStream fileOS = new FileOutputStream(
				uploadService.getUploadUrl() + File.separator + petFile.getId());
		fileOS.write(file.getBytes());
		fileOS.close();
		return petFile.getId();
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody ErrorResponse handleException(Exception ex,HttpServletRequest request,HttpServletResponse response) {
		logger.error("系统内部错误",ex);
		if(ex instanceof DuplicatedUsernameException){
			response.setStatus(500);
			return new ErrorResponse("10001", "用户名重复");
		}
		if(ex instanceof PetException){
			response.setStatus(500);
			return new ErrorResponse("10002", ex.getMessage());
		}
		response.setStatus(500);
		return new ErrorResponse("10000", "系统内部错误");
	}
}
