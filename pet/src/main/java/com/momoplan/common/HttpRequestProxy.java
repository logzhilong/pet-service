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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * <pre>
 * HTTP请求[qing qiu]代理类
 * </pre>
 * 
 * @author benl
 * @version 1.0, 2007-7-3
 */
public class HttpRequestProxy {

	/**
	 * 连接[lian jie]超时[chao shi]
	 */
	private static int connectTimeOut = 5000;

	/**
	 * 读取[du qu]数据[shu ju]超时[chao shi]
	 */
	private static int readTimeOut = 10000;

	/**
	 * 请求[qing qiu]编码[bian ma]
	 */
	private static String requestEncoding = "utf-8";



	/**
	 * <pre>
	 * 发送带参数[can shu]的POST的HTTP请求[qing qiu]
	 * </pre>
	 * 
	 * @param reqUrl
	 *            HTTP请求[qing qiu]URL
	 * @param parameters
	 *            参数[can shu]映射[ying she]表
	 * @return HTTP响应[xiang ying]的字符[zi fu]串[zi fu chuan]
	 */
	public static String doPost(String reqUrl, Map parameters,
			String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry element = (Entry) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(),
						HttpRequestProxy.requestEncoding));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("POST");
			url_con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			System.setProperty("sun.net.client.defaultConnectTimeout",String.valueOf(HttpRequestProxy.connectTimeOut));
			// （单位[dan wei]：毫秒）jdk1.4换成这个,连接[lian jie]超时[chao shi]
//			System.setProperty("sun.net.client.defaultReadTimeout",String.valueOf(HttpRequestProxy.readTimeOut));
			// （单位[dan wei]：毫秒）jdk1.4换成这个,读操作超时[chao shi] url_con.setConnectTimeout(5000);
			
			//（单位[dan wei]：毫秒）jdk 1.5换成这个,连接[lian jie]超时[chao shi] url_con.setReadTimeout(5000);
			//（单位[dan wei]：毫秒）jdk 1.5换成这个,读操作超时[chao shi]
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

	/**
	 * @return 连接[lian jie]超时[chao shi](毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static int getConnectTimeOut() {
		return HttpRequestProxy.connectTimeOut;
	}

	/**
	 * @return 读取[du qu]数据[shu ju]超时[chao shi](毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static int getReadTimeOut() {
		return HttpRequestProxy.readTimeOut;
	}

	/**
	 * @return 请求[qing qiu]编码[bian ma]
	 * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static String getRequestEncoding() {
		return requestEncoding;
	}

	/**
	 * @param connectTimeOut
	 *            连接[lian jie]超时[chao shi](毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#connectTimeOut
	 */
	public static void setConnectTimeOut(int connectTimeOut) {
		HttpRequestProxy.connectTimeOut = connectTimeOut;
	}

	/**
	 * @param readTimeOut
	 *            读取[du qu]数据[shu ju]超时[chao shi](毫秒)
	 * @see com.hengpeng.common.web.HttpRequestProxy#readTimeOut
	 */
	public static void setReadTimeOut(int readTimeOut) {
		HttpRequestProxy.readTimeOut = readTimeOut;
	}

	/**
	 * @param requestEncoding
	 *            请求[qing qiu]编码[bian ma]
	 * @see com.hengpeng.common.web.HttpRequestProxy#requestEncoding
	 */
	public static void setRequestEncoding(String requestEncoding) {
		HttpRequestProxy.requestEncoding = requestEncoding;
	}
}