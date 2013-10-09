package com.momoplan.pet.framework.manager.service;

import java.util.Map;
/**
 * 配置管理
 * v.0.0.1 当前版本只提供界面配置公共属性，做横向或者纵向分布式部署时的私有属性，目前要通过zoo后台配置，计划下个版本增加配置此功能
 * @author liangc
 */
public interface ConfigManager {
	/**
	 * 删除一个属性
	 * @param key
	 */
	public void deleteProperty(String key) throws Exception;
	/**
	 * 设置一个属性
	 * @param key
	 */
	public void setProperty(String key,String value) throws Exception;
	/**
	 * 获取一个属性
	 * @param key
	 */
	public String getProperty(String key) throws Exception;
	/**
	 * 获取全部公共属性
	 * @return
	 */
	public Map<String,String> getPublicPropertys() throws Exception;
}
