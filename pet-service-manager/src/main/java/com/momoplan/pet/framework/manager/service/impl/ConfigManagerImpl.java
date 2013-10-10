package com.momoplan.pet.framework.manager.service.impl;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.framework.manager.service.ConfigManager;
@Service
public class ConfigManagerImpl implements ConfigManager{

	private String buildPath(String key){
		return Bootstrap.configWatcher.getBasePath()+"/"+key;
	}
	@Override
	public void deleteProperty(String key) throws Exception {
		Bootstrap.configWatcher.getStore().delete(buildPath(key),Bootstrap.configWatcher);
	}
	@Override
	public void setProperty(String key,String value) throws Exception {
		Bootstrap.configWatcher.getStore().write(buildPath(key), value);
	}
	@Override
	public String getProperty(String key) throws Exception {
		return Bootstrap.configWatcher.getStore().read(buildPath(key), Bootstrap.configWatcher);
	}
	@Override
	public Map<String, String> getPublicPropertys() throws Exception {
		Thread.sleep(2000);//等2秒,不然也许得不到最新结果
		return Bootstrap.configWatcher.publicConfig;
	}

}
