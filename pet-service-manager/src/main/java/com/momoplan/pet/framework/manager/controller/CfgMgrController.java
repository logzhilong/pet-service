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
public class CfgMgrController {

	Logger logger = LoggerFactory.getLogger(CfgMgrController.class);

	@Autowired
	private ConfigManager configManager;

	@RequestMapping("/manager/configmanager/add.html")
	public String add(Model model){
		return "/manager/configmanager/add";
	}
	
	@RequestMapping("/manager/configmanager/save.html")
	public void save(String key,String value,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功。");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0102");
		try{
			logger.debug("key="+key+" ; value="+value); 
			configManager.setProperty(key, value);
		}catch(Exception e){
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	
	@RequestMapping("/manager/configmanager/list.html")
	public String list(Model mode){
		try {
			Map<String, String> configMap=configManager.getPublicPropertys();
	        List<Map<String,String>> configlist=new ArrayList<Map<String,String>>();
	        Iterator<String> it = configMap.keySet().iterator();
	        while (it.hasNext()) {
	            String key = it.next();
	            String value=configMap.get(key);
	        	Map< String, String> map=new HashMap<String, String>();
	        	map.put("key", key);
	        	map.put("value", value);
	        	configlist.add(map);
	        }
	        mode.addAttribute("list", configlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/manager/configmanager/list";
	}
	
}
