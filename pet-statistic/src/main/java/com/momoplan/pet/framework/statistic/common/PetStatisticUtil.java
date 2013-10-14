package com.momoplan.pet.framework.statistic.common;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;

import org.apache.commons.lang3.math.NumberUtils;

import com.momoplan.pet.framework.statistic.vo.PetComRequest;

public class PetStatisticUtil {
	public static String getParameter(PetComRequest request, String key) {
		Object value = request.getParams().get(key);
		return value==null?"": value.toString();
	}
	
	public static long getParameterLong(PetComRequest request, String key) {
		
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
	
	public static Integer getParameterInteger(PetComRequest request, String key) {
		
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
	
	public static Date getParameterDate(PetComRequest request, String key)
			throws ParseException {
		request.getParams().get(key);
		Object value = request.getParams().get(key);
		if (null == value) {
			return null;
		} else {
			return new Date(getLong(value));
		}
	}
	
	public static Timestamp getParameterTimestamp(PetComRequest request, String key)
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
}
