package com.momoplan.pet.framework.manager.service.impl;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.zoo.config.ConfigWatcher;
import com.momoplan.pet.framework.manager.service.ConfigManager;
@Service
public class ConfigManagerImpl implements ConfigManager{

	private ConfigWatcher configWatcher = Bootstrap.configWatcher;
	
	private String buildPath(String key){
		return configWatcher.getBasePath()+"/"+key;
	}
	@Override
	public void deleteProperty(String key) throws Exception {
		configWatcher.getStore().delete(buildPath(key),configWatcher);
	}
	@Override
	public void setProperty(String key,String value) throws Exception {
		configWatcher.getStore().write(buildPath(key), value);
	}
	@Override
	public String getProperty(String key) throws Exception {
		return configWatcher.getStore().read(buildPath(key), configWatcher);
	}
	@Override
	public Map<String, String> getPublicPropertys() throws Exception {
		return configWatcher.publicConfig;
	}

}
