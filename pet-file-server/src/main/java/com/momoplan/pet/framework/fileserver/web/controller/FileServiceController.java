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

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.Success;
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
	 * @throws Exception 
	 */
	@RequestMapping("/put")
	public void putFile(FileBean form, MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws Exception{
		String rtn = null;
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
	        rtn = new Success(true,fileId).toString();
		}catch(Exception e){
			logger.error("上传异常",e);
	        rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			PetUtil.writeStringToResponse(rtn, response);
		}
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
		String rtn = null;
		try{
			fileServer.delete(fileId);
			rtn = new Success(true,fileId).toString();
		}catch(Exception e){
			logger.error("删除异常",e);
			rtn = new Success(false,fileId).toString();
		}
		logger.debug(rtn);
        response.getWriter().write(rtn);
	}
	
}
