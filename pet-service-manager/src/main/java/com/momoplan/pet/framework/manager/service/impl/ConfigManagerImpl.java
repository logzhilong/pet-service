package com.momoplan.pet.framework.manager.service.impl;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.manager.service.ConfigManager;

@Service
public class ConfigManagerImpl implements ConfigManager{
	
	@Resource
	private CommonConfig commonConfig = null;
	
	@Override
	public void deleteProperty(String key) throws Exception {
		commonConfig.deleteProperty(key);
	}
	@Override
	public void setProperty(String key,String value) throws Exception {
		commonConfig.setProperty(key, value);
	}
	@Override
	public String getProperty(String key) throws Exception {
		return commonConfig.getProperty(key);
	}
	@Override
	public Map<String, String> getPublicPropertys() throws Exception {
		Thread.sleep(2000);//等2秒,不然也许得不到最新结果
		return commonConfig.getPublicPropertys();
	}

}
