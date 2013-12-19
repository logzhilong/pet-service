package com.momoplan.pet.framework.manager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.web.BaseController;

@Controller
public class ShareController extends BaseController{

	private static Logger logger = LoggerFactory.getLogger(ShareController.class);
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	@RequestMapping("/pet_bbs/{id}.html")
	public String login(@PathVariable("id") String id,Model model) throws Exception{
		logger.info("pet_bbs_id="+id);
		setHeadAndBottom(model);
		Note n = mapperOnCache.selectByPrimaryKey(Note.class, id);
		String content = n.getContent();
		model.addAttribute("content", content);
		return "share";
	}
	private void setHeadAndBottom(Model model){
		String head = commonConfig.get("share.head", "宠物圈");
		String head_link = commonConfig.get("share.head.link", "http://www.52pet.net");
		String bottom = commonConfig.get("share.bottom", "北京爱宠联盟-宠物圈分享");
		String bottom_link = commonConfig.get("share.bottom.link", "http://www.52pet.net");
		model.addAttribute("head", head);
		model.addAttribute("head_link", head_link);
		model.addAttribute("bottom", bottom);
		model.addAttribute("bottom_link", bottom_link);
	}
}
