package com.momoplan.pet.framework.manager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.framework.manager.service.ConfigManager;

@Controller
public class ConfigManagerController {
	private Logger logger = LoggerFactory.getLogger(CommonDataManagerController.class);
	@Autowired
	private ConfigManager configManager;
	
	/**
	 * 获取集合配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/configmanager/commanageList.html")
	public String ConfigGetCommonList(Model model){
		try {
			Map<String, String> configMap=configManager.getPublicPropertys();
		        List<Map<String,String>> configlist=new ArrayList<Map<String,String>>();
		        Iterator it = configMap.keySet().iterator();
		        while (it.hasNext()) {
		            String key = it.next().toString();
		            String value=configMap.get(key);
		        	Map< String, String> map=new HashMap<String, String>();
		        	map.put("key", key);
		        	map.put("value", value);
		        	configlist.add(map);
		        }
		        model.addAttribute("configlist", configlist);
			return "/manager/configmanager/comManageList";
		} catch (Exception e) {
		e.printStackTrace();
		return  "/manager/configmanager/comManageList";
		}
	}
	/**
	 * To增加或者修改配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/configmanager/TocommanageSaveOrUpdate.html")
	public String ToConfigconfigmanageraveOrUpdate(String key,Model model){
		try {
			if("".equals(key) || null==key)
			{
				return "/manager/configmanager/commanageAdd";
			}else{
				String valu=configManager.getProperty(key);
				model.addAttribute("valu", valu);
				model.addAttribute("key", key);
				
				return "/manager/configmanager/commanageUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/manager/configmanager/commanageUpdate";
		}
	}
	/**
	 * 增加或者修改配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/configmanager/commanageSaveOrUpdate.html")
	public void ConfigconfigmanageraveOrUpdate(String key,String pvalue,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage0001");
		try {
			if("" != key && null != key){
				String va=configManager.getProperty(key);
				if("" !=va && null!= va){
					configManager.deleteProperty(key);
					configManager.setProperty(key,pvalue);
				}else{
					if("" !=pvalue && null != pvalue ){
						configManager.setProperty(key, pvalue);
					}
				}
			}
		} catch (Exception e) {
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	/**
	 * 删除配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/configmanager/commanageDel.html")
	public void ConfigCommonDel(String key,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage0001");
		try {
			if("" != key && null != key){
				configManager.deleteProperty(key);
			}
		} catch (Exception e) {
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	
}
