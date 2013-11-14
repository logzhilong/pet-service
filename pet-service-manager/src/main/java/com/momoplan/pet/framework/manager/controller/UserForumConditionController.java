package com.momoplan.pet.framework.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.domain.bbs.mapper.UserForumConditionMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.service.UserForumService;

@Controller
public class UserForumConditionController {

	Logger logger = LoggerFactory.getLogger(UserForumConditionController.class);
	@Autowired
	UserForumConditionMapper conditionMapper=null;
	@Autowired
	UserForumService userforumService;
	@Autowired
	BBSManagerService bbsManagerService;
	/**
	 * 默认关注圈子列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/userforumcondition/userforumlist.html")
	public String userforumlist(Model model) {
		logger.debug("wlcome to manager userforumcondition userforumlist......");
		try {
			List<UserForumCondition> userforumlist=userforumService.GetUserForumList();
			for(UserForumCondition condition:userforumlist) {
				Forum forum=new Forum();
				try {
					forum.setId(condition.getForumId());
				} catch (Exception e) {
					forum.setId("null");
				}
					condition.setForumId(bbsManagerService.getForumbyid(forum).getName());
			}
			model.addAttribute("userforumlist", userforumlist);
			logger.debug("获取默认关注圈子表list"+userforumlist.toString());
			return "/manager/userforummanager/UserForumList";
		} catch (Exception e) {
			logger.error("userforumlist"+e);
			e.printStackTrace();
			return "/manager/userforummanager/UserForumList";
		}
	
	}
	
	
	@RequestMapping("/manager/userforumcondition/ToAddOrUpdateuserforum.html")
	public String ToAddOrUpdateuserforum(UserForumCondition condition,Model model){
		try {
			logger.debug("传进默认关注圈子:"+condition);
			if("" != condition.getId() && null != condition.getId()){
				try {
					UserForumCondition userForum=userforumService.getuserforumByid(condition);
					//设置默认选项的value
					String va=userForum.getForumId();
					model.addAttribute("fid", va);
					if(null != userForum.getForumId() && ""!= userForum.getForumId()){
						Forum forum=new Forum();
						forum.setId(userForum.getForumId());
						userForum.setForumId(bbsManagerService.getForumbyid(forum).getName());
						logger.debug("根据默认关注圈子id:"+userForum.getForumId() +"获取圈子名称为:"+bbsManagerService.getForumbyid(forum).getName());
						model.addAttribute("userForum", userForum);
					}
				} catch (Exception e) {
					logger.error("获取默认关注圈子异常:"+e);
				}
				//圈子集合
				List<Forum> forums=userforumService.getForumlist();
				model.addAttribute("forums", forums);
				logger.debug("获取圈子集合"+forums.toString());
				return  "/manager/userforummanager/UserForumUpdate";
			}
			else{
				List<Forum> forums=userforumService.getForumlist();
				model.addAttribute("forums", forums);
				return "/manager/userforummanager/UserForumAdd";
			}
			
		} catch (Exception e) {
			logger.debug("ToAddOrUpdateuserforumError"+e);
			return null;
		}
	}
	
	@RequestMapping("/manager/userforumcondition/AddOrUpdateuserforum.html")
	public void AddOrUpdateuserforum(UserForumCondition condition,Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("wlcome to manager userforumcondition AddOrUpdateuserforum......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0004");
		try {
			userforumService.addOrUpdateUserForum(condition);
		} catch (Exception e) {
			logger.error("usermanageSaveOrUpdate"+e);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
    }
	
	@RequestMapping("/manager/userforumcondition/deleteuserforum.html")
	public void deleteuserforum(UserForumCondition condition,Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("wlcome to manager userforumcondition AddOrUpdateuserforum......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0004");
		try {
			userforumService.deleteUserForoum(condition);
		} catch (Exception e) {
			logger.error("usermanageSaveOrUpdate"+e);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	
	
	
	
}
