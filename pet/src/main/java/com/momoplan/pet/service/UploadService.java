package com.momoplan.pet.service;

import javax.annotation.Resource;

import com.momoplan.common.PetConstants;
import com.momoplan.pet.commons.spring.CommonConfig;

public class UploadService {
	
	private String uploadUrl;
	@Resource
	CommonConfig commonConfig = null;
	
	public String getUploadUrl() {
		
		return commonConfig.get(PetConstants.UPLOADPATH, "/home/appusr/static");
	}
	
}
