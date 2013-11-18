package com.momoplan.pet.framework.base.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.petservice.bbs.utils.UploadFile;

public class BaseAction {
	
	protected static Gson gson = MyGson.getInstance();
	@Autowired
	protected MapperOnCache mapperOnCache = null;
	@Autowired
	protected CommonConfig commonConfig = null;
	@Autowired
	protected UploadFile uploadFile = null;
	
}
