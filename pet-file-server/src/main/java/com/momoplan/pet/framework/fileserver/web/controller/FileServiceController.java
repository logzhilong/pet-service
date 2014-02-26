package com.momoplan.pet.framework.fileserver.web.controller;

import java.io.File;
import java.io.FileInputStream;
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
		String compressImage = request.getParameter("compressImage");
		try{
			String realPath = commonConfig.get("uploadPath","/home/appusr/static");
	        logger.debug("========================================"+realPath);
			logger.debug("文件长度: " + file.getSize());  
	        logger.debug("文件类型: " + file.getContentType());  
	        logger.debug("文件名称: " + file.getName());  
	        logger.debug("文件原名: " + file.getOriginalFilename());
	        logger.debug("compressImage: " + compressImage);
	        
	        logger.debug("form: " + form.toString());
	        logger.debug("========================================"+realPath);
	        String format = file.getContentType().toLowerCase();
	        form.setFileName(file.getName());
	        form.setFormat(format);
	        form.setFileStream(file.getInputStream());
	        String fileId = fileServer.put(form);
	        rtn = new Success(null,true,fileId).toString();
		}catch(Exception e){
			logger.error("上传异常",e);
	        rtn = new Success(null,false,e.getMessage()).toString();
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
			logger.debug("get file error : "+e.getMessage());
		}finally{
			try{
				is.close();
			}catch(Exception e){}
		}
	}
	/**
	 * 获取一个小图
	 * @param fileId
	 * @param width
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/get/{fileId}/{width}")
	public void getSmallImage(@PathVariable("fileId") String fileId,@PathVariable("width") String width,HttpServletResponse response) throws Exception{
		logger.debug("getSmallFile : fileId="+fileId+" ; width="+width);
		File tmpBaseDir = new File("/tmp/pet-file-server/small");
		if(!tmpBaseDir.exists()){
			tmpBaseDir.mkdirs();
			logger.info("初始化缓存图片目录");
		}
		getSmallImageInTmp(tmpBaseDir.getPath(),fileId,Integer.parseInt(width),response);
	}
	
	/**
	 * TODO 2014-02-26 : 要截正方形
	 * @param fileId
	 * @param width
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/get/{fileId}/{width}/square")
	public void getSmallImageSquare(@PathVariable("fileId") String fileId,@PathVariable("width") String width,HttpServletResponse response) throws Exception{
		logger.debug("getSmallFile : fileId="+fileId+" ; width="+width);
		File tmpBaseDir = new File("/tmp/pet-file-server/small");
		if(!tmpBaseDir.exists()){
			tmpBaseDir.mkdirs();
			logger.info("初始化缓存图片目录");
		}
		getSmallImageInTmp(tmpBaseDir.getPath(),fileId,Integer.parseInt(width),response);
	}

	
	
	/**
	 * 获取更新包
	 * @param fileId
	 * @param width
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/update/{fileId}/pet.apk")
	public void updateAPK(@PathVariable("fileId") String fileId,HttpServletResponse response) throws Exception{
		String fid = fileId.split("-")[0];
		logger.info("update_apk -- fileId="+fileId);
		logger.info("update_apk -- fid="+fid);
		getFile(fid,response);
	}
	
	public void getSmallImageInTmp(String tmpBasePath,String fileId,Integer width, HttpServletResponse response)throws Exception {
		OutputStream os = null;
		InputStream is = null;
		File imgDir = new File(tmpBasePath,fileId);
		if(!imgDir.exists()||!imgDir.isDirectory()){
			logger.debug("创建目录:"+imgDir.getPath());
			imgDir.mkdirs();
		}
		File img = new File(imgDir.getPath(),width+"");
		if(img.exists()){
			logger.debug("在缓存目录获取图片:"+img.getPath());
			is = new FileInputStream(img);
		}else{
			logger.debug("图片不在缓存中");
			is = fileServer.getFileAsStream(fileId,width,img);
		}
		try{
			os = response.getOutputStream();
			byte[] buff = new byte[1024];
			int t = 0;
			while((t=is.read(buff))!=-1){
				os.write(buff,0,t);
			}
			os.flush();
		}catch(Exception e){
			logger.error("get_small_img",e);
		}finally{
			if(is!=null)
				is.close();
			if(os!=null)
				os.close();
		}
	}
	
	@RequestMapping("/delete/{fileId}")
	public void deleteFile(@PathVariable("fileId") String fileId,HttpServletResponse response) throws IOException{
		logger.debug("getFile : fileId="+fileId);
		String rtn = null;
		try{
			fileServer.delete(fileId);
			rtn = new Success(null,true,fileId).toString();
		}catch(Exception e){
			logger.error("删除异常",e);
			rtn = new Success(null,false,fileId).toString();
		}
		logger.debug(rtn);
        response.getWriter().write(rtn);
	}
	
}
