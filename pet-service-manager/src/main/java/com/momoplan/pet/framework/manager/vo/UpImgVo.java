package com.momoplan.pet.framework.manager.vo;

import java.io.File;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class UpImgVo {
	private Logger logger = LoggerFactory.getLogger(UpImgVo.class);
	/**
	 * 上传图片调用此类
	 * @param req传入request获取File信息
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String upimg(HttpServletRequest req){
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
			String enty =null;
			if (multipartResolver.isMultipart(req)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 上传文件信息
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String name = file.getOriginalFilename();
						String path = "D:\\uploadimg\\" + name;
						// 将上传文件存储至本地
						File localFile = new File(path);
						if (!localFile.exists()) {
							localFile.mkdirs();
						}
						file.transferTo(localFile);
						DefaultHttpClient httpclient = new DefaultHttpClient();
						// 请求处理页面
						HttpPost httppost = new HttpPost("http://123.178.27.74/pet-file-server/put");
						// 创建待处理的文件
						FileBody file1 = new FileBody(new File(path));
						// 对请求的表单域进行填充
						MultipartEntity reqEntity = new MultipartEntity();
						reqEntity.addPart("file", file1);
						reqEntity.addPart("fileName", new StringBody(name));
						//TODO暂时先放一个能通过token
//						reqEntity.addPart("token", new StringBody("694359BE12E04E0088B78F297CDD3F61"));
						reqEntity.addPart("mimeType", new StringBody("image/jpeg"));
						//压缩图片参数
						reqEntity.addPart("compressImage", new StringBody("OK"));
						// 设置请求
						httppost.setEntity(reqEntity);
						// 执行
						HttpResponse response1 = httpclient.execute(httppost);
						String output=null;
						if (HttpStatus.SC_OK == response1.getStatusLine().getStatusCode()) {
							logger.debug("连接通过!");
							HttpEntity entity = response1.getEntity();
							// 显示内容
							if (entity != null) {
								output=EntityUtils.toString(entity);
								JSONObject jsonObj = new JSONObject(output);  
								String success = jsonObj.getString("success");  
								if(success == "true"){
									enty = jsonObj.getString("entity"); 
								}
							}
							if (entity != null) {
								entity.consumeContent();
							}
						}
					}
				}
			}
			logger.debug("上传成功!");
			logger.debug("返回图片id:"+enty);
			return enty;
		} catch (Exception e) {
			logger.error("上传异常:"+e);
			return null;
		}
		
	}
	
}
