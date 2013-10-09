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
	@RequestMapping("/manager/commons/commanageList.html")
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
			return "/manager/commons/comManageList";
		} catch (Exception e) {
		e.printStackTrace();
		return  "/manager/commons/comManageList";
		}
	}
	/**
	 * To增加或者修改配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/TocommanageSaveOrUpdate.html")
	public String ToConfigCommonSaveOrUpdate(String key,Model model){
		try {
			if("".equals(key) || null==key)
			{
				return "/manager/commons/commanageAdd";
			}else{
				String valu=configManager.getProperty(key);
				model.addAttribute("valu", valu);
				model.addAttribute("key", key);
				
				return "/manager/commons/commanageUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "/manager/commons/commanageUpdate";
		}
	}
	/**
	 * 增加或者修改配置信息
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/commons/commanageSaveOrUpdate.html")
	public void ConfigCommonSaveOrUpdate(String key,String pvalue,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage");
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
	@RequestMapping("/manager/commons/commanageDel.html")
	public void ConfigCommonDel(String key,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage");
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
