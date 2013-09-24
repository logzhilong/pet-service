package com.momoplan.test;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Test;


public class EjabberdHttpRequest {
	
	@Test
	public void testSendMessage() throws HttpException, IOException{
		HttpClient httpClient=new HttpClient();
		PostMethod method = new PostMethod("http://61.51.110.55:5280/rest");
		method.setRequestEntity(new StringRequestEntity("<message to=\"16399778855@test.com\" from=\"15001300855@test.com\" type=\"chat\" msgType = \"reply\" Dynamicid=\"121\" ><body></body></message>", "", "UTF-8"));//reply回复
		httpClient.executeMethod(method);
	}
}



