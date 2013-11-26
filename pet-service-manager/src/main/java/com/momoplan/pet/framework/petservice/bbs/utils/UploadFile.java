package com.momoplan.pet.framework.petservice.bbs.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.spring.CommonConfig;

@Component
public class UploadFile {

	private static Logger logger = LoggerFactory.getLogger(UploadFile.class);
	
	@Autowired
	private CommonConfig commonConfig = null;
	
	public static void main(String args[]) throws Exception {
		File src = new File("/app/1.png");
		FileInputStream is = new FileInputStream(src);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int t = 256;
		byte[] buffer = new byte[t];
		while((t = is.read(buffer,0,t))!=-1){
			IOUtils.write(buffer, bos);
		}
		String addTopImage = "OK";
		String compressImage = "OK";
	
		new UploadFile().upload(bos.toByteArray(), src.getName(),"image/png", addTopImage, compressImage,null);
		is.close();
	}
	
	/**
	 * 获取上传地址
	 * @param addTopImage
	 * @param compressImage
	 * @return
	 */
	private String getUploadUrl(String addTopImage,String compressImage,String imageWidth){
		String base_url = commonConfig.get("service.uri.pet_file_server");
		String service = "/put?compressImage=#compressImage#&addTopImage=#addTopImage#";
		service = service.replaceAll("#compressImage#", compressImage);
		service = service.replaceAll("#addTopImage#", addTopImage);
		if(StringUtils.isNotEmpty(imageWidth)){
			service = service+"&imageWidth="+imageWidth;
		}
		String url = base_url+service;
		logger.debug("upload_url = "+url);
		return url;
	}
	/**
	 * 根据图片ID获取图片URL
	 * @param id
	 * @return
	 */
	public String getFileAsUrl(String id) throws Exception {
		if(StringUtils.isNotEmpty(id)){
			String base_url = commonConfig.get("pet_file_server");
			String fileUrl = base_url+"/get/"+id;
			logger.debug("file_url="+fileUrl);
			return fileUrl;
		}else{
			throw new NullPointerException("根据图片ID获取图片URL,图片ID不能为空");
		}
	}
	/**
	 * 上传到图片服务器，返回图片ID
	 * @param bos 字节数组输出流
	 * @param fileName 文件名
	 * @param contentType 请求类型
	 * @param addTopImage OK 加水印，NO 不加水印
	 * @param compressImage OK 压缩，NO 不压缩
	 * @return
	 * @throws Exception
	 */
	public String upload(byte[] obj,String fileName,String contentType,String addTopImage,String compressImage,String imageWidth) throws Exception{
		logger.debug("upload_input : fileName="+fileName+";contentType="+contentType+";addTopImage="+addTopImage+";compressImage="+compressImage+";imageWidth="+imageWidth);
		PostMethod post = new PostMethod(getUploadUrl(addTopImage,compressImage,imageWidth));
		try {
			PartSource part = new ByteArrayPartSource(fileName,obj);
			FilePart filePart = new FilePart("file",part);
			filePart.setContentType(contentType);
			logger.debug("TODO 上传时，用了固定的 token=pet-service-manager");
			post.addRequestHeader("token", "pet-service-manager");
			post.setRequestEntity( new MultipartRequestEntity( new Part[]{filePart} , post.getParams() ) );
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
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