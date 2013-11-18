package com.momoplan.pet.framework.petservice.customer.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.MgrTrustUserVo;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.customer.service.GhostService;
import com.momoplan.pet.framework.petservice.customer.vo.SsoUserVo;

@Controller
public class GhostAction extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(GhostAction.class);
	@Autowired
	private GhostService ghostService = null;
	
	@RequestMapping("/petservice/customer/ghostMain.html")
	public String main(MgrTrustUserVo myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/customer/ghostMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			WebUser cuser = SessionManager.getCurrentUser(request);
			List<MgrTrustUserVo> list = ghostService.getGhostList(cuser.getId());
			model.addAttribute("list", list);
			model.addAttribute("cuser", cuser);
		} catch (Exception e) {
			logger.error("ghostMain error",e);
		}
		return "/petservice/customer/ghostMain";
	}
	

	/*	    
	{
        "nickname":""
        "phonenumber":""
        "birthdate":""
        "gender":""
        "city":""
        "signature":""
        "hobby":""
    }
	*/
	@RequestMapping("/petservice/customer/ghostAddOrEdit.html")
	public String addOrEdit(SsoUserVo myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id = myForm.getId();
		try{
			if(StringUtils.isNotEmpty(id)){
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, id);
				BeanUtils.copyProperties(user, myForm);
				String iconUrl = uploadFile.getFileAsUrl(user.getImg().split("_")[0]);
				logger.debug("iconUrl="+iconUrl);
				myForm.setImgUrl(iconUrl);
			}
			model.addAttribute("myForm", myForm);
		}catch(Exception e){
			logger.error("note save error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/customer/ghostAddOrEdit";
	}	
	
	@RequestMapping("/petservice/customer/ghostSave.html")
	public void save(SsoUserVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/bbs/forumSave");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");	
		json.put("statusCode","200");			
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0001");
		json.put("forwardUrl",ctx+"/petservice/customer/ghostMain.html");	
		String res = null;
		try {
			SsoUser vo = new SsoUser();
			BeanUtils.copyProperties(myForm, vo);
			WebUser user = SessionManager.getCurrentUser(request);
			ghostService.saveGhost(user.getId(), vo);
		} catch (Exception e) {
			logger.error("save ghost error",e);
			json.put("statusCode","201");
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	@RequestMapping("/petservice/customer/uploadFile.html")
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
				String uploadResponse = uploadFile.upload(file.getBytes(), fileName, type, "NO", "OK");
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
