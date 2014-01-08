package com.momoplan.pet.framework.base.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.petservice.bbs.utils.UploadFile;

public class BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(BaseAction.class);
	
	protected static Gson gson = MyGson.getInstance();
	@Autowired
	protected MapperOnCache mapperOnCache = null;
	@Autowired
	protected CommonConfig commonConfig = null;
	@Autowired
	protected UploadFile uploadFile = null;
	
	protected String addImgLink4Content(String content){
		Matcher matcher = Pattern.compile("(<img.*?src=\")(.+?)(\".*?/>)").matcher(content);
		Map<String,String> map = new HashMap<String,String>();
		while(matcher.find()){
			String s1 = matcher.group(1);
			String s2 = matcher.group(2);
			String s3 = matcher.group(3);
			String s4 = "<a href=\""+s2+"\">";
			String s5 = "</a>";
			String str = s1+s2+s3;
			String target = s4+s1+s2+s3+s5;
			map.put(str, target);
		}
		for(String k :map.keySet()){
			String target = map.get(k);
			content = content.replaceAll(k, target);
			logger.debug("替换 "+k+" --> "+target);
		}
		return content;
	}
	
}
