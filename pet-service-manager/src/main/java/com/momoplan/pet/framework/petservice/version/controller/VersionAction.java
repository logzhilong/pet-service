package com.momoplan.pet.framework.petservice.version.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.fileserver.po.FileIndex;
import com.momoplan.pet.commons.domain.manager.po.MgrPublishedApk;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.petservice.version.service.VersionService;

/**
 * 软件版本管理
 * @author liangc
 */
@Controller
public class VersionAction extends BaseAction {

	private static Logger logger = LoggerFactory.getLogger(VersionAction.class);
	@Autowired
	private VersionService versionService = null;
	
	/**
	 * 软件版本列表
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/petservice/version/main.html")
	public String main(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/version/main.html");
		try {
			List<SsoVersion> list = versionService.getSsoVersionList();
			model.addAttribute("list", list);
			
			List<MgrPublishedApk> list2 = versionService.getMgrPublishedApkList();
			model.addAttribute("list2", list2);
			
		} catch (Exception e) {
			logger.error("/petservice/version/main.html",e);
		}
		return "/petservice/version/main";
	}

	@RequestMapping("/petservice/version/edit.html")
	public String edit(SsoVersion myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/version/edit.html");
		try {
			logger.debug("input="+gson.toJson(myForm));
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = versionService.get(id);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("/petservice/version/edit.html",e);
		}
		return "/petservice/version/edit";
	}
	
	@RequestMapping("/petservice/version/save.html")
	public void save(SsoVersion myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/version/save.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("callbackType","closeCurrent");
		json.put("navTabId","versionMain");
		json.put("forwardUrl",ctx+"/petservice/version/main.html");
		String res = null;
		try {
			logger.debug("input:"+gson.toJson(myForm));
			versionService.update(myForm);
		} catch (Exception e) {
			logger.error("/petservice/version/save.html",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	@RequestMapping("/petservice/version/publishApk.html")
	public String publishApk(SsoVersion myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/version/publishApk.html");
		return "/petservice/version/publishApk";
	}
	public static void main(String[] args) {
		String v = "petapk-1.4.5-test";
		System.out.println(v.replaceAll("\\.", "_"));
	}
	@RequestMapping("/petservice/version/publishApkSave.html")
	public void publishApkSave(MgrPublishedApk myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/version/publishApkSave.html");
		JSONObject json = new JSONObject();
		json.put("message","操作成功");
		json.put("statusCode",200);
		json.put("callbackType","closeCurrent");
		json.put("navTabId","versionMain");
		json.put("forwardUrl",ctx+"/petservice/version/main.html");
		String res = null;
		try {
			String fid = myForm.getVersion();//+"-"+myForm.getChannel();
			fid = fid.replaceAll("\\.", "_");
			logger.debug("input : "+gson.toJson(myForm));
			logger.debug("fid : "+fid);
			FileIndex index = mapperOnCache.selectByPrimaryKey(FileIndex.class, myForm.getFileId());
			index.setId(fid);
			mapperOnCache.insertSelective(index, fid);
			logger.debug("修正 APK 索引");
			String cb = SessionManager.getCurrentUser(request).getName();
			myForm.setCb(cb);
			myForm.setCt(DateUtils.now());
			myForm.setFileId(fid);
			myForm.setId(IDCreater.uuid());
			logger.debug("发布内容 "+gson.toJson(myForm));
			mapperOnCache.insertSelective(myForm, myForm.getId());
		} catch (Exception e) {
			logger.error("/petservice/version/publishApkSave.html",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("publishApkSave output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	@RequestMapping("/petservice/version/uploadFile.html")
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
				logger.debug("-----------------------------------");
				String uploadResponse = upload(file.getBytes(), fileName, file.getContentType());
				JSONObject success = new JSONObject(uploadResponse);
				String entity = success.getString("entity");
				if(success.getBoolean("success")){
					json.put("fileId", entity);//结果在这里返回给副文本编辑器
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

	private String getUploadUrl(){
		String base_url = commonConfig.get("service.uri.pet_file_server");
		String service = "/put?fileType=APK";
		String url = base_url+service;
		logger.debug("upload_url = "+url);
		return url;
	}
	private String upload(byte[] obj,String fileName,String contentType) throws Exception{
		logger.debug("upload_input : fileName="+fileName+";contentType="+contentType);
		PostMethod post = new PostMethod(getUploadUrl());
		try {
			PartSource part = new ByteArrayPartSource(fileName,obj);
			FilePart filePart = new FilePart("file",part);
			filePart.setContentType(contentType);
			logger.debug("TODO 上传时，用了固定的 token=pet-service-manager");
			post.addRequestHeader("token", "pet-service-manager");
			post.setRequestEntity( new MultipartRequestEntity( new Part[]{filePart} , post.getParams() ) );
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
			int status = client.executeMethod(post);
			String res = post.getResponseBodyAsString();
			logger.debug("upload_output : "+res);
			if (status == HttpStatus.SC_OK) {
				logger.debug("上传成功");
			} else {
				logger.debug("上传失败");
			}
			return res;
		} catch (Exception e) {
			logger.error("上传异常",e);
			throw e;
		} finally {
			post.releaseConnection();
		}
	}
}
