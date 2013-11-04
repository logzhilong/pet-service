package com.momoplan.pet.commons;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.momoplan.pet.commons.bean.ClientRequest;


public class PetUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PetUtil.class);
	
	public static String getParameter(PetRequest request, String key) {
		Object value = request.getParams().get(key);
		return value==null?null:value.toString();
	}
	
	public static long getParameterLong(PetRequest request, String key) {
		
		try {
			if(null==getParameter(request, key)||""==getParameter(request, key)){
				return Long.parseLong("-1");
			}
			return Long.parseLong(getParameter(request, key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static boolean getParameterBoolean(PetRequest request, String key) {
		
		try {
			if(null==getParameter(request, key)||""==getParameter(request, key)){
				return false;
			}
			return Boolean.parseBoolean(getParameter(request, key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Double getParameterDouble(PetRequest request, String key) {
		
		try {
			if(null==getParameter(request, key)||""==getParameter(request, key)){
				return Double.parseDouble("-1");
			}
			return Double.parseDouble(getParameter(request, key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	public static Integer getParameterInteger(PetRequest request, String key) {
		
		try {
			if(null==getParameter(request, key)||""==getParameter(request, key)){
				return Integer.parseInt("-1");
			}
			return Integer.parseInt(getParameter(request, key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static Date getParameterDate(PetRequest request, String key)
			throws ParseException {
		request.getParams().get(key);
		Object value = request.getParams().get(key);
		if (null == value) {
			return null;
		} else {
			return new Date(getLong(value));
		}
	}
	
	public static Timestamp getParameterTimestamp(PetRequest request, String key)
			throws ParseException {
		request.getParams().get(key);
		Object value = request.getParams().get(key);
		if (null == value) {
			return null;
		} else {
			return new Timestamp(getLong(value));
		}
	}
	
	private static Long getLong(Object obj){
        if (obj==null || false == NumberUtils.isNumber(obj+"")) return 0L;
        return Long.valueOf(obj+"");
	}
	
	public static double getDistance(double wd1, double jd1, double wd2,double jd2) {
		double x, y, out;
		double PI = 3.14159265;
		double R = 6.371229 * 1e6;

		x = (jd2 - jd1) * PI * R * Math.cos(((wd1 + wd2) / 2) * PI / 180) / 180;
		y = (wd2 - wd1) * PI * R / 180;
		out = Math.hypot(x, y);
		return out;
	}
	
	public static String delOneImg(String img,String delImg){
		String[] imgs = img.split(",");
		StringBuffer newImg = new StringBuffer();
		for (String eImg : imgs) {
			if(eImg.equals(delImg)){
				continue;
			}
			newImg.append(eImg+",");
		}
		return newImg.toString().substring(0,newImg.length()-1);
	}
	
	//add by liangc >>>>>>>>>>>>>>
	public static void writeStringToResponse(String str,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		try {
			OutputStream os = response.getOutputStream();
			IOUtils.write(str, os);
		} catch (IOException e) {
			logger.error("写入 response 失败 : ",e);
			throw new Exception("写入 response 失败 : ",e);
		}
	}
	
	public static void writeStringToResponse(Object obj,HttpServletResponse response) throws Exception{
		writeStringToResponse(obj.toString(),response);
	}
	
	public static ClientRequest reviceClientRequest(String body) throws Exception{
		try{
			ClientRequest clientRequest = new ObjectMapper().reader(ClientRequest.class).readValue(body);
			return clientRequest;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("body = "+body);
			logger.error("ClientRequest 参数反序列化异常",e);
			throw new Exception("ClientRequest 参数反序列化异常",e);
		}
	}
	
	//add by liangc <<<<<<<<<<<<<<
	
}
