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

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.framework.manager.service.RoleManageService;
import com.momoplan.pet.framework.manager.service.RoleUserManageService;
import com.momoplan.pet.framework.manager.service.UserManageService;

@Controller
public class UserManageController {
	
	Logger logger = LoggerFactory.getLogger(UserManageController.class);
	
	@Autowired
	private RoleManageService roleManageService;
	@Autowired
	private UserManageService userManageService ;
	@Autowired
	private RoleUserManageService  roleUserManageService ;
	/**
	 * 获取用户列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/usermanageList.html")
	public String usermanageList(Model model){
		logger.debug("wlcome to role manage usermanageList......");
		try {
			List<MgrUser> musers=userManageService.getAllUser();
			model.addAttribute("musers", musers);
			return "/manager/usermanage/UserManageList";
		} catch (Exception e) {
			e.printStackTrace();
			return "/manager/usermanage/UserManageList";
		}
	}
	/**
	 * To增加或者修改用户
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/TousermanageSaveOrUpdate.html")
	public String TousermanageSaveOrUpdate(MgrUser mgrUser,Model model) throws Exception{
		try {
			if("".equals(mgrUser.getId()) || null==mgrUser.getId())
			{
				logger.debug("wlcome to pet usermanage useradd......");
				List<MgrRole> roles=roleManageService.getAllRole();
				model.addAttribute("roles", roles);
				return "/manager/usermanage/UserManageAdd";
			}else{
				MgrUser  mgrUser2=userManageService.getUserByid(mgrUser);
				model.addAttribute("mgrUser2",mgrUser2);
				logger.debug("wlcome to pet usermanage userupdate......");		
				List<MgrRole> roles=roleManageService.getAllRole();
				model.addAttribute("roles", roles);
				return "/manager/usermanage/UserManageUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * 增加或者修改用户
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/usermanageSaveOrUpdate.html")
	public void usermanageSaveOrUpdate(String[] roletype,MgrUser mgrUser,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("wlcome to role manage usermanagerSaveOrUpdate......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage2201");
		try {
			userManageService.SaveOrUpdateUser(roletype,mgrUser);
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
	 * 删除用户
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/usermanageDel.html")
	public void usermanageDel(MgrUser mgrUser,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("wlcome to role manage usermanageDel......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage2201");
		try {
			userManageService.delUserByid(mgrUser);
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
