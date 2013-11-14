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

import com.momoplan.pet.commons.spring.CommonConfig;

public class UpImgVo {
	private Logger logger = LoggerFactory.getLogger(UpImgVo.class);
	private CommonConfig commonConfig = new CommonConfig();
	/**
	 * 上传图片调用此类
	 * @param req传入request获取File信息
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String upimg(HttpServletRequest req,String ys){
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
			String enty =null;
			if (multipartResolver.isMultipart(req)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 上传文件信息
					MultipartFile file = multiRequest.getFile(iter.next());
					//content-type
					String contentType = file.getContentType();
					if (file != null) {
						String name = file.getOriginalFilename();
						String path = "/tmp/" + name;
						// 将上传文件存储至本地
						File localFile = new File(path);
						if (!localFile.exists()) {
							localFile.mkdirs();
						}
						file.transferTo(localFile);
						DefaultHttpClient httpclient = new DefaultHttpClient();
						
						//设置消息头
						// 请求处理页面
						String url = commonConfig.get("service.uri.pet_file_server", null);
						HttpPost httppost = new HttpPost(url+"/put");
						httppost.setHeader("token", "pet-service-manager");
						// 创建待处理的文件
						FileBody file1 = new FileBody(new File(path),contentType);
						
						// 对请求的表单域进行填充
						MultipartEntity reqEntity = new MultipartEntity();
						reqEntity.addPart("file", file1);
						reqEntity.addPart("fileName", new StringBody(name));
						
						if(ys == "tpys"){
							//压缩图片参数
							reqEntity.addPart("compressImage", new StringBody("OK"));
						}if(ys == "ns"){
							//设置任务头像
							reqEntity.addPart("compressImage", new StringBody("OK"));
							reqEntity.addPart("addTopImage", new StringBody("no"));
							reqEntity.addPart("imageWidth", new StringBody("300"));
						}
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
								}else{
									enty = null;
									logger.debug("上传失败:"+enty);
								}
							}
							if (entity != null) {
								entity.consumeContent();
							}
						}
					}
				}
			}
			logger.debug("返回图片id:"+enty);
			return enty;
		} catch (Exception e) {
			logger.error("上传异常:"+e);
			return null;
		}
		
	}
	
}
