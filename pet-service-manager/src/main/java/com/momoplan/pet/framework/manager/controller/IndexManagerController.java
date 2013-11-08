package com.momoplan.pet.framework.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexManagerController {

	private Logger logger = LoggerFactory.getLogger(IndexManagerController.class);

	/**
	 * 索引管理
	 * 
	 * @param forum
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/indexmg/indexmamagerlist.html")
	public String indexmamagerlist() {
		logger.debug("wlcome to manager indexmg indexmamagerlist......");
		return "/manager/indexmanager/IndexManageList";
	}
}
