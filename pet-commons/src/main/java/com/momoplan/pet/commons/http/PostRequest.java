package com.momoplan.pet.commons.http;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class PostRequest {
	
	public static String postText(String url,String ... args) throws Exception{
		HttpClient httpClient = new HttpClient();
		PostMethod post = new PostMethod(url);
		for(int i=0;i<args.length;i+=2){
			String k = args[i];
			String v = args[i+1];
			NameValuePair nvp = new NameValuePair();
			nvp.setName(k);
			nvp.setValue(v);
			post.addParameter(nvp);
		}
		httpClient.executeMethod(post);
		return post.getResponseBodyAsString();
	}
	
	public static void main(String[] args) throws Exception {
		String url = "http://192.168.2.115:8080/pet-service-bbs/forum/request.html";
		String body = "{\"method\":\"getReplyNoteSubByReplyNoteid\",\"token\":\"\",\"params\":{\"noteSubid\":\"640D576BC02245418B34949616C0059C\"}}";
		String res = PostRequest.postText(url, "body",body);
		System.out.println(res);
	}
	
}