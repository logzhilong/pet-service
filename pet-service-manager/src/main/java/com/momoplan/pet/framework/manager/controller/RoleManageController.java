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

@Controller
public class RoleManageController {
	
	Logger logger = LoggerFactory.getLogger(RoleManageController.class);
	
	@Autowired
	private RoleManageService roleManageService;
	@Autowired
	private RoleUserManageService roleUserManageService=null;
	@Autowired
	private UserManageService userManageService=null;
	/**
	 * 获取角色列表获取列表
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrrolemanage/rolemanageList.html")
	public String rolemanageList(Model model){
		logger.debug("wlcome to role manage getrolemanageList......");
		try {
			List<MgrRole> roles=roleManageService.getAllRole();
			model.addAttribute("roles", roles);
			return "/manager/rolemanage/roleManageList";
		} catch (Exception e) {
			e.printStackTrace();
			return "/manager/rolemanage/roleManageList";
		}
	}
	/**
	 * To增加或者修改角色
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrrolemanage/TorolemanageSaveOrUpdate.html")
	public String TorolemanageSaveOrUpdate(MgrRole role,Model model) throws Exception{
		try {
			if("".equals(role.getId()) || null==role.getId())
			{
				logger.debug("wlcome to pet rolemanager roleadd......");
				return "/manager/rolemanage/roleManageAdd";
			}else{
				MgrRole mgrRole=roleManageService.getRoleByid(role);
				model.addAttribute("mgrRole",mgrRole);
				logger.debug("wlcome to pet rolemanager roleupdate......");		
				return "/manager/rolemanage/roleManageUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 增加或者修改角色
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrrolemanage/rolemanageSaveOrUpdate.html")
	public void rolemanageSaveOrUpdate(MgrRole role,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("wlcome to role manage getrolemanageList......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage1101");
		try {
			roleManageService.SaveOrUpdateRole(role);
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
	 * 删除角色
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/mgrrolemanage/rolemanageDel.html")
	public void rolemanageDel(MgrRole role,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("wlcome to role manage getrolemanageList......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "commanage1101");
		try {
			roleManageService.delRoleByid(role);
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
	 * 选中角色时跳转至此
	 * 获取当前角色下用户
	 * @param role
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping("/manager/mgrrolemanage/Torolemanageuserlist.html")
	public String Torolemanageuserlist(MgrRole role,Model model) throws Exception{
		try {
			String id=role.getId();
			MgrUserRoleRel mgrUserRoleRel=new MgrUserRoleRel();
			mgrUserRoleRel.setRoleId(id);
			List<MgrUserRoleRel> rels=roleUserManageService.getRoleUserListbyRoleid(mgrUserRoleRel);
			List<MgrUser> mgrUsers=new ArrayList<MgrUser>();
			for(MgrUserRoleRel rel:rels){
				MgrUser mgrUser=new MgrUser();
				mgrUser.setId(rel.getUserId());
				MgrUser user=userManageService.getUserByid(mgrUser);
				mgrUsers.add(user);
			}
			model.addAttribute("mgrUsers", mgrUsers);
			return "/manager/rolemanage/roleManageUserList";
		} catch (Exception e) {
			e.printStackTrace();
			return "/manager/rolemanage/roleManageUserList";
		}
	}
	
	
	
	
}
