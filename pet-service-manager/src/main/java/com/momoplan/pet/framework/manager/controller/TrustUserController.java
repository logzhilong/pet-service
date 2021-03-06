package com.momoplan.pet.framework.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.framework.manager.service.TrustUserService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.Petuser;


@Controller
public class TrustUserController {
	
	private Logger logger = LoggerFactory.getLogger(TrustUserController.class);

	@Autowired
	private TrustUserService trustUserService=null;
	
	/**
	 * 查看托管user列表
	 * @param pageBean
	 * @param myForm
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/trustuser/userList.html")
	public String userList(PageBean<MgrTrustUser> pageBean,Model model,HttpServletRequest request){
		logger.debug("wlcome to manager trustuser userList......"+pageBean.toString());
		try {
			PageBean<MgrTrustUser> bean= trustUserService.AllTrustUser(pageBean,request);
			logger.debug("获取托管用户集合"+bean.toString());
			model.addAttribute("pageBean", bean);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "/manager/trustusermanager/TrustUserList";
	}
	
	/**
	 * 增加或者修改托管用户
	 * @param trustUser
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/trustuser/TouserAddOrUpdate.html")
	public String TouserAddOrUpdate(MgrTrustUser trustUser,Model model){
		try {
			if("".equals(trustUser.getId()) || null==trustUser.getId())
			{
				logger.debug("wlcome to manager trustuser TouserAdd......");
				return "/manager/trustusermanager/TrustUserAdd";
			}else{
				logger.debug("wlcome to manager trustuser Update......");
				return "/manager/trustusermanager/TrustUserUpdate";
			}
		} catch (Exception e) {
			logger.error("TouserAddOrUpdate"+e);
			e.printStackTrace();
			return null;
		}
	}
	
	
	@RequestMapping("/manager/trustuser/userAddOrUpdate.html")
	public void userAddOrUpdate(Petuser petuser,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("wlcome to manager trustuser userAddOrUpdate......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0003");
		try {
			logger.debug("进入增加或者修改托管用户controller..........."+petuser.toString());
			int i=trustUserService.addOrUpdatetrust(petuser, request);
			if(i>0){
				json.put("message", "操作成功!");
				logger.debug("增加或者修改托管用户成功");
			}else{
				json.put("message", "操作失败!");
				logger.debug("增加或者修改托管用户失败");

			}
		} catch (Exception e) {
			logger.error(e.toString());
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	@RequestMapping("/manager/trustuser/userDel.html")
	public void userDel(Petuser petuser,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		logger.debug("wlcome to manager trustuser userAddOrUpdate......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0003");
		try {
			trustUserService.delPetUser(petuser);
		} catch (Exception e) {
			logger.error(e.toString());
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}

	
	
	@RequestMapping("/manager/trustuser/trustUserDetail.html")
	public String trustUserDetail(Petuser petuser,Model model){
		try {
			logger.debug("进入获取托管用户详情ocntroller.............."+petuser.toString());
			petuser=trustUserService.getPetUserByid(petuser);
			logger.debug("托管用户详情"+petuser.toString());
			model.addAttribute("petuser", petuser);
		} catch (Exception e) {
			logger.debug("trustUserDetailFail"+e);
			e.printStackTrace();
		}
		return "/manager/trustusermanager/TrustUseDetail";
	}
	@RequestMapping("/manager/trustuser/trustUserUpdate.html")
	public String trustUserUpdate(Petuser petuser,Model model,HttpServletRequest request){
		try {
			logger.debug("更新托管用户controller..............."+petuser.toString());
			trustUserService.addOrUpdatetrust(petuser,request);
			model.addAttribute("petuser", petuser);
		} catch (Exception e) {
			logger.error("trustUserDetailFail"+e);
			e.printStackTrace();
		}
		return "/manager/trustusermanager/TrustUseDetail";
	}
	
}
