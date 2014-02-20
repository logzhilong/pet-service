package com.momoplan.pet.framework.petservice.bbs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.po.Assort;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.bbs.service.AssortService;

/**
 * 帖子分类，分类跟圈子并列，是两种不同的归类方式
 * @author liangc
 */
@Controller
public class AssortAction extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(AssortAction.class);
	@Autowired
	private AssortService assortService = null;
	
	@RequestMapping("/petservice/bbs/assortMain.html")
	public String main(Assort myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("/petservice/bbs/assortMain");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			List<Assort> list = assortService.getAssortList(myForm);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("圈子列表异常",e);
		}
		return "/petservice/bbs/assortMain";
	}

	@RequestMapping("/petservice/bbs/assortAddOrEdit.html")
	public String addOrEdit(Assort myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/bbs/assortAddOrEdit");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				myForm = mapperOnCache.selectByPrimaryKey(Assort.class, id);
				model.addAttribute("forumChecked",assortService.getForumListChecked(id));
			}
			model.addAttribute("forumList",assortService.getForumListWithoutAssort(id));
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("add or edit error",e);
		}
		return "/petservice/bbs/assortAddOrEdit";
	}
	
	@RequestMapping("/petservice/bbs/assortSave.html")
	public void save(Assort myForm,String[] forumIds,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/bbs/assortSave");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");			
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0204");
		json.put("forwardUrl",ctx+"/petservice/bbs/assortMain.html");
		String res = null;
		try {
			WebUser user = SessionManager.getCurrentUser(request);
			myForm.setEb(user.getName());
			assortService.saveAssort(myForm,forumIds);
		} catch (Exception e) {
			logger.error("save assort error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res+" ; forumIds.size="+forumIds.length);
		PetUtil.writeStringToResponse(res, response);
	}
	
}
