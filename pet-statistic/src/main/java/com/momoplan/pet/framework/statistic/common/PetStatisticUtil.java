package com.momoplan.pet.framework.statistic.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.statistic.vo.PetComRequest;

public class PetStatisticUtil {
	private static Logger logger = LoggerFactory.getLogger(PetStatisticUtil.class);
	@Autowired
	private static CommonConfig commonConfig = null;
	
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
	
	/**
	 * 将指定文件重命名（添加.done后缀）
	 * @param path
	 */
	public static void renameFile(String fileName){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		File files  = new File(fileName);//获取目录下所有文件
		String[] fileArray = files.list();
		for (int i = 0; i < fileArray.length; i++) {//遍历文件名
			if(fileArray[i].contains(Constants.ACCESS_FILE_NAME+"."+yesterday)){
				File file  = new File(fileName+fileArray[i]);
				file.renameTo(new File(file.getAbsolutePath()+".done"));
			}
		}
	}
}
