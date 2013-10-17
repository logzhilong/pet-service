package com.momoplan.pet.commons.spring;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.zookeeper.KeeperException;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.zoo.config.Config;
import com.momoplan.pet.commons.zoo.config.ConfigWatcher;

/**
 * 配置管理器，从 zoo 中获取资源
 * @author liangc
 */
@Component
public class CommonConfig {
	/**
	 * 初始化
	 * @throws Exception
	 */
	@PostConstruct
	private void initZoo() throws Exception {
		String zooServer = (String) Bootstrap.getBean("zooServer");
		try {
			System.out.println("初始化 port=" + Bootstrap.http_port + " zooServer=" + zooServer);
			Bootstrap.configWatcher = new ConfigWatcher(zooServer);
			System.err.println("public : " + Config.publicConfig);
			System.err.println("private : " + Config.privateConfig);
			System.err.println("privateReg : " + Config.privateConfigReg);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}
	
	public String get(String key,String defaultValue){
    	return Bootstrap.configWatcher.getProperty(key, defaultValue);
    }
	public String get(String key){
    	return Bootstrap.configWatcher.getProperty(key, null);
    }
	private String buildPath(String key){
		return Bootstrap.configWatcher.getBasePath()+"/"+key;
	}
	public void deleteProperty(String key) throws Exception {
		Bootstrap.configWatcher.getStore().delete(buildPath(key),Bootstrap.configWatcher);
	}
	public void setProperty(String key,String value) throws Exception {
		Bootstrap.configWatcher.getStore().write(buildPath(key), value);
	}
	public String getProperty(String key) throws Exception {
		return Bootstrap.configWatcher.getStore().read(buildPath(key), Bootstrap.configWatcher);
	}
	public Map<String, String> getPublicPropertys() throws Exception {
		return Bootstrap.configWatcher.publicConfig;
	}
	
}
