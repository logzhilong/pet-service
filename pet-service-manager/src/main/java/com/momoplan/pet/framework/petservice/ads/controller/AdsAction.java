package com.momoplan.pet.framework.petservice.ads.controller;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.notice.po.Ads;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.ads.service.AdsService;

@Controller
public class AdsAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(AdsAction.class);
	@Autowired
	private AdsService adsService = null;
	
	@RequestMapping("/petservice/ads/adsMain.html")
	public String main(Ads myForm,Page<Ads> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/ads/adsMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			pages.setPageSize(400);
			Page<Ads> page = adsService.getAdsList(pages,myForm);
			model.addAttribute("page", page);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("Ads error",e);
		}
		return "/petservice/ads/adsMain";
	}
	
	@RequestMapping("/petservice/ads/adsAddOrEdit.html")
	public String addOrEdit(Ads myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/ads/adsAddOrEdit.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(Ads.class, id);
				String imgUrl = uploadFile.getFileAsUrl(myForm.getImg());
				logger.debug("imgUrl="+imgUrl);
				model.addAttribute("imgUrl", imgUrl);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("add or edit error",e);
		}
		return "/petservice/ads/adsAddOrEdit";
	}

	@RequestMapping("/petservice/ads/adsSave.html")
	public void save(Ads myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/ads/adsSave.html");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0502");
		json.put("forwardUrl",ctx+"/petservice/ads/adsMain.html");
		String res = null;
		try {
			Ads vo = new Ads();
			BeanUtils.copyProperties(myForm, vo);
			WebUser user = SessionManager.getCurrentUser(request);
			vo.setEb(user.getName());
			adsService.save(vo);
		} catch (Exception e) {
			logger.error("save Ads error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	
	@RequestMapping("/petservice/ads/uploadFile.html")
	public void uploadFile(Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		JSONObject json = new JSONObject();
		try{
			for(Iterator<String> it = multipartRequest.getFileNames();it.hasNext();){
				String fileName = it.next();
				MultipartFile file = multipartRequest.getFile(fileName);
				logger.debug("-----------------------------------");
				logger.debug("file_key : "+fileName);
				logger.debug("name : "+file.getName());
				logger.debug("size : "+file.getSize());
				logger.debug("contentType : "+file.getContentType());
				logger.debug("originalFilename : "+file.getOriginalFilename());
				logger.debug("-----------------------------------");
				String type = null;
				try{
					String n = file.getOriginalFilename().toLowerCase();
					if(n.endsWith("png")){
						type = "image/png";
					}else if(n.endsWith("jpg")){
						type = "image/jpg";
					}else if(n.endsWith("bmp")){
						type = "image/bmp";
					}else if(n.endsWith("jpeg")){
						type = "image/jpeg";
					}else if(n.endsWith("gif")){
						type = "image/gif";
					}
				}catch(Exception e){
					logger.debug("TTTTTTT : "+e.getMessage());
					type = "image/jpg";
				}
				String uploadResponse = uploadFile.upload(file.getBytes(), fileName, type, "NO", "NO",null);
				JSONObject success = new JSONObject(uploadResponse);
				String entity = success.getString("entity");
				if(success.getBoolean("success")){
					json.put("img", entity);//结果在这里返回给副文本编辑器
					json.put("imgUrl", uploadFile.getFileAsUrl(entity));//结果在这里返回给副文本编辑器
				}else{
					throw new Exception(entity);
				}
			}
		}catch(Exception e){
			logger.error("",e);
			json.put("error", e.getMessage());
		}
		String res = json.toString();
		logger.debug(res);
		PetUtil.writeStringToResponse(res, response);
	}
	
}
