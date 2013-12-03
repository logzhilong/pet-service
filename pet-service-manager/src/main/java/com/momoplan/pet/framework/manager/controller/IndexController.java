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
import com.momoplan.pet.commons.MyGson;
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
		
		TreeBean bbs = new TreeBean("00","0","客户管理");
//		TreeBean bbsMain = new TreeBean("0001","00","圈子统计信息","/manager/bbs/main.html");
//		TreeBean bbsList = new TreeBean("0002","00","圈子管理","/manager/bbs/forumList.html");
//		TreeBean allCustomer = new TreeBean("0004","00","全部账户","/manager/trustuser/userList.html");
//		TreeBean trustList = new TreeBean("0003","00","托管账户","/manager/trustuser/userList.html");
		
		TreeBean ghostCustomer = new TreeBean("0001","00","幽灵客户","/petservice/customer/ghostMain.html");
		tree.add(ghostCustomer);

//		TreeBean realyCustomer = new TreeBean("0002","00","真实客户","/petservice/customer/realyMain.html");
//		tree.add(realyCustomer);
		
//		TreeBean common = new TreeBean("01","0","公共数据");
//		TreeBean commonAreaCode = new TreeBean("0101","01","地域信息","/manager/commons/areaCodeList.html");
		
		TreeBean bbsRoot = new TreeBean("02","0","圈子管理");
		TreeBean forum = new TreeBean("0201","02","圈子管理","/petservice/bbs/forumMain.html");
		TreeBean condotion = new TreeBean("0202","02","默认关注","/manager/userforumcondition/userforumlist.html");
		tree.add(bbsRoot);
		tree.add(forum);
		tree.add(condotion);
		
		tree.add(bbs);
//		tree.add(bbsMain);
//		tree.add(bbsList);
//		tree.add(common);
//		tree.add(commonAreaCode);
//		tree.add(trustList);

		TreeBean albums = new TreeBean("04","0","发现-管理");
		TreeBean publicAlbums = new TreeBean("0401","04","宠物美图","/petservice/albums/publicAlbumsMain.html");
		TreeBean ency = new TreeBean("0402","04","宠物百科","/petservice/ency/encyMain.html");
		TreeBean exper = new TreeBean("0403","04","养宠经验","/petservice/exper/experMain.html");
		tree.add(albums);
		tree.add(publicAlbums);
		tree.add(ency);
		tree.add(exper);

		TreeBean notice = new TreeBean("05","0","通知-管理");
		TreeBean sysNotice = new TreeBean("0501","05","系统通知","/petservice/notice/noticeMain.html");
		tree.add(notice);
		tree.add(sysNotice);

		TreeBean push = new TreeBean("06","0","推送-管理");
		TreeBean pushMsg = new TreeBean("0601","06","推荐","/petservice/push/pushMain.html");
		tree.add(push);
		tree.add(pushMsg);

		//数据统计页面
		TreeBean statistic = new TreeBean("03","0","统计报表");
		TreeBean statisticUser = new TreeBean("0301","03","渠道统计","/petservice/report/channelCounter0.html");
		TreeBean statisticMethod = new TreeBean("0302","03","服务统计","/petservice/report/serviceCounter0.html");
		tree.add(statistic);
		tree.add(statisticUser);
		tree.add(statisticMethod);
		
		Gson g = MyGson.getInstance();
		String json = g.toJson(tree);
		logger.debug(json);
		model.addAttribute("funTree", json);
		return "funTree";
	}
	
}
