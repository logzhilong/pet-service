package com.momoplan.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpRequestProxy {

	private static int connectTimeOut = 5000;
	private static int readTimeOut = 10000;
	private static String requestEncoding = "utf-8";


	public static String doPost(String reqUrl, Map parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry element = (Entry) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),HttpRequestProxy.requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,recvEncoding));
			String tempLine = rd.readLine();
			StringBuffer tempStr = new StringBuffer();
			String crlf = System.getProperty("line.separator");
			while (tempLine != null) {
				tempStr.append(tempLine);
				tempStr.append(crlf);
				tempLine = rd.readLine();
			}
			responseContent = tempStr.toString();
			rd.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;
	}

	public static int getConnectTimeOut() {
		return connectTimeOut;
	}

	public static void setConnectTimeOut(int connectTimeOut) {
		HttpRequestProxy.connectTimeOut = connectTimeOut;
	}

	public static int getReadTimeOut() {
		return readTimeOut;
	}

	public static void setReadTimeOut(int readTimeOut) {
		HttpRequestProxy.readTimeOut = readTimeOut;
	}

	public static String getRequestEncoding() {
		return requestEncoding;
	}

	public static void setRequestEncoding(String requestEncoding) {
		HttpRequestProxy.requestEncoding = requestEncoding;
	}
	public static String doPostHttpClient(String url, String body){
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(url);
		try {
//			PostRequest.postText(url, args)
//			stringRequestEntity = new StringRequestEntity("body="+body, "application/json", requestEncoding);
//			postMethod.setRequestEntity(stringRequestEntity);
			postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
			postMethod.setParameter("body", body);
//			postMethod.addRequestHeader("Accept-Language", "zh-CN");
//			postMethod.addRequestHeader("Content-Type", "charset=utf-8");
//			postMethod.addRequestHeader("charset", "utf-8");
			httpClient.executeMethod(postMethod);
			String respronse = postMethod.getResponseBodyAsString();
			postMethod.releaseConnection();
			return respronse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	public static String doPostHttpClientByObj(String url, Object request){
//		HttpClient httpClient = new HttpClient();
//		PostMethod postMethod = new PostMethod(url);
//		try {
////			PostRequest.postText(url, args)
////			stringRequestEntity = new StringRequestEntity("body="+body, "application/json", requestEncoding);
////			postMethod.setRequestEntity(stringRequestEntity);
//			postMethod.set
//			postMethod.setParameter("body", request);
////			postMethod.addRequestHeader("Accept-Language", "zh-CN");
////			postMethod.addRequestHeader("Content-Type", "application/json;charset=utf-8");
//			httpClient.executeMethod(postMethod);
//			String respronse = postMethod.getResponseBodyAsString();
//			postMethod.releaseConnection();
//			return respronse;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
//	public static void main(String[] args) {
//		Map map = new HashMap<String, String>();
//		map.put("userId","J00348");
//		map.put("password", "142753");
//		map.put("pszMobis", "18801108730");
//		map.put("pszMobis", "18801108730");
//		map.put("pszMsg", "test");
//		map.put("iMobiCount", "1");
//		map.put("pszSubPort", "***********");
//		System.out.println(doPost("http://61.145.229.29:9002/MWGate/wmgw.asmx/MongateCsSpSendSmsNew",map, "utf-8"));
//		
//	}
}