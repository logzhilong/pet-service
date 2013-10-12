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

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.framework.manager.service.RoleManageService;

@Controller
public class RoleManageController {
	
	Logger logger = LoggerFactory.getLogger(RoleManageController.class);
	
	@Autowired
	private RoleManageService roleManageService;
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
	
	
}
