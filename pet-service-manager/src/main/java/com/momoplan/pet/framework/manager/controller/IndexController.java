package com.momoplan.pet.framework.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.momoplan.pet.framework.manager.vo.TreeBean;

@Controller
public class IndexController {
	
	Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@RequestMapping("/index.html")
	public String index(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("wlcome to pet manager index......");
		return "index";
	}
	
	@RequestMapping("/funTree.html")
	public String funTree(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("wlcome to pet manager funTree......");
		List<TreeBean> tree =  new ArrayList<TreeBean> ();//platform 的节点集合

		TreeBean root = new TreeBean();
		root.setName("功能列表");
		root.setLeaf(false);
		root.setNode("0");
		tree.add(root);
		
		TreeBean bbs = new TreeBean("00","0","圈子");
		TreeBean bbsMain = new TreeBean("0001","00","圈子统计信息","/manager/bbs/main.html");
		TreeBean bbsList = new TreeBean("0002","00","圈子管理","/manager/bbs/forumList.html");
		
		TreeBean common = new TreeBean("01","0","公共数据");
		TreeBean commonAreaCode = new TreeBean("0101","01","地域信息","/manager/commons/areaCodeList.html");
		TreeBean configManager = new TreeBean("0102","01","配置管理-public","/manager/configmanager/list.html");
		
		tree.add(bbs);
		tree.add(bbsMain);
		tree.add(bbsList);
		tree.add(common);
		tree.add(commonAreaCode);
		tree.add(configManager);
		
		Gson g = new Gson();
		String json = g.toJson(tree);
		logger.debug(json);
		model.addAttribute("funTree", json);
		return "funTree";
	}
	
}
