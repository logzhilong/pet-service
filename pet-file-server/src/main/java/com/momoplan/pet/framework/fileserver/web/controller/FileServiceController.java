package com.momoplan.pet.framework.fileserver.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.fileserver.service.FileServer;
import com.momoplan.pet.framework.fileserver.vo.FileBean;

@Controller
public class FileServiceController {
	
	private Logger logger = LoggerFactory.getLogger(FileServiceController.class);
	private CommonConfig commonConfig = null;
	private FileServer fileServer = null;
	@Autowired
	public FileServiceController(CommonConfig commonConfig, FileServer fileServer) {
		super();
		this.commonConfig = commonConfig;
		this.fileServer = fileServer;
	}
	/**
	 * 获取集合配置信息
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/put")
	public void putFile(FileBean form, MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		JsonObject json = new JsonObject();
		try{
			String realPath = commonConfig.get("uploadPath","/home/appusr/static");
	        logger.debug("========================================"+realPath);
			logger.debug("文件长度: " + file.getSize());  
	        logger.debug("文件类型: " + file.getContentType());  
	        logger.debug("文件名称: " + file.getName());  
	        logger.debug("文件原名: " + file.getOriginalFilename());  
	        logger.debug("========================================"+realPath);
	        form.setFileStream(file.getInputStream());
	        String fileId = fileServer.put(form);
	        json.addProperty("returnCode", "OK");
	        json.addProperty("returnValue", fileId);
		}catch(Exception e){
			logger.error("上传异常",e);
			json.addProperty("returnCode", "ERROR");
			json.addProperty("returnValue", "上传失败");
			json.addProperty("returnError", e.getMessage());
		}
		logger.debug(json.toString());
        response.getWriter().write(json.toString());
	}
	@RequestMapping("/get/{fileId}")
	public void getFile(@PathVariable("fileId") String fileId,HttpServletResponse response){
		logger.debug("getFile : fileId="+fileId);
		InputStream is = null;
		try {
			is = fileServer.getFileAsStream(fileId);
			OutputStream os = response.getOutputStream();
			byte[] buff = new byte[1024];
			int t = 0;
			while((t=is.read(buff))!=-1){
				os.write(buff,0,t);
			}
			os.flush();
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}finally{
			try{
				is.close();
			}catch(Exception e){}
		}
	}
	@RequestMapping("/delete/{fileId}")
	public void deleteFile(@PathVariable("fileId") String fileId,HttpServletResponse response) throws IOException{
		logger.debug("getFile : fileId="+fileId);
		response.setCharacterEncoding("UTF-8");
		JsonObject json = new JsonObject();
		try{
			fileServer.delete(fileId);
			json.addProperty("returnCode", "OK");
			json.addProperty("returnValue", "删除成功");
		}catch(Exception e){
			logger.error("删除异常",e);
			json.addProperty("returnCode", "ERROR");
			json.addProperty("returnValue", "删除失败");
			json.addProperty("returnError", e.getMessage());
		}
		logger.debug(json.toString());
        response.getWriter().write(json.toString());
	}
	
}
