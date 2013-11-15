package com.momoplan.pet.commons;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.PartSource;
import org.apache.commons.io.IOUtils;

public class UploadFileTest {

	
	
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
	
		upload(bos, src.getName(), addTopImage, compressImage);
		is.close();
	}
	
	private static String getUploadUrl(String addTopImage,String compressImage){
		String base_url = "http://localhost:8081/pet-file-server";
		String service = "/put?compressImage=#compressImage#&addTopImage=#addTopImage#";
		service = service.replaceAll("#compressImage#", compressImage);
		service = service.replaceAll("#addTopImage#", addTopImage);
		String url = base_url+service;
		System.out.println(url);
		return url;
	}
	
	public static String upload(ByteArrayOutputStream bos,String fileName,String addTopImage,String compressImage) throws Exception{
		PostMethod post = new PostMethod(getUploadUrl(addTopImage,compressImage));
		try {
			PartSource part = new ByteArrayPartSource(fileName,bos.toByteArray());
			FilePart filePart = new FilePart("file",part);
			filePart.setContentType("image/png");
			post.addRequestHeader("token", "pet-service-manager");
			post.setRequestEntity( new MultipartRequestEntity( new Part[]{filePart} , post.getParams() ) );
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(post);
			String res = post.getResponseBodyAsString();
			System.out.println(res);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
			} else {
				System.out.println("上传失败");
			}
			return res;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			post.releaseConnection();
		}
	}
}
