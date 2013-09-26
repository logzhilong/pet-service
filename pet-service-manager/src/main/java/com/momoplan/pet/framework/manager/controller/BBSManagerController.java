package com.momoplan.pet.framework.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;

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
	
}
