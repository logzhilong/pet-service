package com.momoplan.pet.framework.manager.controller;

import java.util.ArrayList;
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
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;
import com.momoplan.pet.framework.manager.service.RoleManageService;
import com.momoplan.pet.framework.manager.service.RoleUserManageService;
import com.momoplan.pet.framework.manager.service.UserManageService;
import com.momoplan.pet.framework.manager.vo.RoleUserUpdate;

@Controller
public class UserManageController {

	Logger logger = LoggerFactory.getLogger(UserManageController.class);

	@Autowired
	private RoleManageService roleManageService;
	@Autowired
	private UserManageService userManageService;
	@Autowired
	private RoleUserManageService roleUserManageService;
	/**
	 * 获取用户列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/usermanageList.html")
	public String usermanageList(Model model) {
		logger.debug("wlcome to role manage usermanageList......");
		try {
			List<MgrUser> musers = userManageService.getAllUser();
			model.addAttribute("musers", musers);
			return "/manager/usermanage/UserManageList";
		} catch (Exception e) {
			logger.error("usermanageList"+e);
			e.printStackTrace();
			return "/manager/usermanage/UserManageList";
		}
	}

	/**
	 * To增加或者修改用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/TousermanageSaveOrUpdate.html")
	public String TousermanageSaveOrUpdate(MgrUser mgrUser, Model model)
			throws Exception {
		try {
			if ("".equals(mgrUser.getId()) || null == mgrUser.getId()) {
				logger.debug("wlcome to pet usermanage useradd......");
				List<MgrRole> roles = roleManageService.getAllRole();
				model.addAttribute("roles", roles);
				return "/manager/usermanage/UserManageAdd";
			} else {
				// 获取当前用户信息
				MgrUser mgrUser2 = userManageService.getUserByid(mgrUser);
				model.addAttribute("mgrUser2", mgrUser2);
				logger.debug("wlcome to pet usermanage userupdate......");
				// 获取所有的角色
				List<MgrRole> roles = roleManageService.getAllRole();
				model.addAttribute("roles", roles);
				// 获取当前用户角色
				MgrUserRoleRel userRoleRel = new MgrUserRoleRel();
				userRoleRel.setUserId(mgrUser.getId());
				List<MgrUserRoleRel> roles1 = roleUserManageService.getRoleUserListbyUserid(userRoleRel);
				List<RoleUserUpdate> list = new ArrayList<RoleUserUpdate>();
				for (MgrRole role : roles) {
					RoleUserUpdate roleUserUpdate = new RoleUserUpdate();
					for (MgrUserRoleRel roleRel : roles1) {
						if (role.getId().equals(roleRel.getRoleId())) {
							roleUserUpdate.setRcheck(true);
						}
					}
					roleUserUpdate.setRid(role.getId());
					roleUserUpdate.setRname(role.getName());
					list.add(roleUserUpdate);
				}
				model.addAttribute("roles1", roles1);
				model.addAttribute("list", list);
				return "/manager/usermanage/UserManageUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 增加或者修改用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/usermanageSaveOrUpdate.html")
	public void usermanageSaveOrUpdate(String[] roletype, MgrUser mgrUser,
			Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.debug("wlcome to role manage usermanagerSaveOrUpdate......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage2201");
		try {
			userManageService.SaveOrUpdateUser(roletype, mgrUser);
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

	/**
	 * 删除用户
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrusermanager/usermanageDel.html")
	public void usermanageDel(MgrUser mgrUser, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
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
			logger.error("usermanageSaveOrUpdate"+e);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}

	@RequestMapping("/manager/pwdmanager/Topwdmanageedit.html")
	public String Topwdmanageredit(HttpServletRequest request) {
		logger.debug("welcome to pwdmanager to editpwd....");
		try {
			return "/manager/pwdmanage/pwdManageUpdate";
		} catch (Exception e) {
			logger.error("ToUpdatepwdExctption" + e);
			return null;
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/manager/pwdmanager/pwdmanageedit.html")
	public void pwdmanageredit(String opassword, String npassword,String rnpassword, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("welcome to pwdmanager to editpwd....");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "");
		try {
			userManageService.upUserPwd(opassword, npassword, rnpassword, request);
		} catch (Exception e) {
			logger.error("uppwdExctption" + e);
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
}
