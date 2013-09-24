package com.momoplan.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;


public class PetUtil {
	
	public static String getParameter(PetRequest request, String key) {
		Object value = request.getParams().get(key);
		return value==null?"": value.toString();
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
	
	public static double getDistance(double wd1, double jd1, double wd2,
			double jd2) {
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
	
}
