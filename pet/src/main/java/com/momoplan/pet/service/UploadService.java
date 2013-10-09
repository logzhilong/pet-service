package com.momoplan.pet.service;

import com.momoplan.common.PetConstants;
import com.momoplan.pet.commons.spring.Bootstrap;

public class UploadService {
	
	private String uploadUrl;

	public String getUploadUrl() {
		
		return Bootstrap.configWatcher.getProperty(PetConstants.UPLOADPATH, "/home/appusr/static");
	}
	
}
