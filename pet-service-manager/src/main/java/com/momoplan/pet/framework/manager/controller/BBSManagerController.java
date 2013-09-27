package com.momoplan.pet.framework.manager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.TreeBean;

@Controller
public class BBSManagerController {

	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private BBSManagerService bBSManagerService = null;
	
	@RequestMapping("/manager/bbs/main.html")
	public String main(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("wlcome to pet-service-bbs manager main ......");
		return "/manager/bbs/main";
	}
	
	/**
	 * 列出最顶级的父圈子
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumList.html")
	public String forumList(Forum myForm,PageBean<Forum> pageBean,Model model){
		logger.debug("wlcome to pet-service-bbs manager forumList ......");
		try {
			//测试时先写死，分页尚未实现 >>>>>>>>>>
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
			//测试时先写死，分页尚未实现 <<<<<<<<<<
			pageBean = bBSManagerService.listForum(pageBean, myForm);
			model.addAttribute("pageBean",pageBean);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/bbs/forumList";
	}
	
	/**
	 * 根据父圈子ID，找到子圈子，并进行管理；
	 * 此处为功能入口
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumManager.html")
	public String forumManager(Forum myForm,PageBean<Forum> pageBean,Model model){
		logger.debug("wlcome to pet-service-bbs manager forumManager ......");
		try {
			//测试时先写死，分页尚未实现 >>>>>>>>>>
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
			//测试时先写死，分页尚未实现 <<<<<<<<<<
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/bbs/forumManager";
	}
	
	@RequestMapping("/manager/bbs/forumManagerMain.html")
	public String forumManagerMain(String target,String id,Model model){
		logger.debug("wlcome to pet-service-bbs manager forumManagerMain ......");
		if("left".equals(target)){
			return fmLeft(id,model);
		}else{//right
			return fmRight(id,model);
		}
	}
	private String fmLeft(String id,Model model){
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
		
		tree.add(bbs);
		tree.add(bbsMain);
		tree.add(bbsList);
		tree.add(common);
		tree.add(commonAreaCode);
		
		Gson g = new Gson();
		String json = g.toJson(tree);
		logger.debug(json);
		model.addAttribute("tree", json);
		return "/manager/bbs/forumManagerLeft";
	}
	private String fmRight(String id,Model model){
		
		return "/manager/bbs/forumManagerRight";
	}
	
}
