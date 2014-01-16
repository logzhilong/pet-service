package com.momoplan.pet.framework.petservice.petcard.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.user.po.PetCard;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.petservice.petcard.service.PetCardService;

@Controller
public class PetCardAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(PetCardAction.class);
	@Autowired
	private PetCardService petCardService = null;
	
	@RequestMapping("/petservice/petcard/petcardMain.html")
	public String main(PetCard myForm,Page<PetCard> pages,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/petcard/petcardMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			Page<PetCard> page = petCardService.getPetCardList(pages);
			model.addAttribute("page", page);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("PetCard error",e);
		}
		return "/petservice/petcard/petcardMain";
	}
	
	@RequestMapping("/petservice/petcard/petcardAddOrEdit.html")
	public String addOrEdit(PetCard myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		return "/petservice/petcard/petcardAddOrEdit";
	}

	@RequestMapping("/petservice/petcard/petcardSave.html")
	public void save(Integer number,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/petcard/petcardSave.html");
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","panel0801");
		json.put("forwardUrl",ctx+"/petservice/petcard/petcardMain.html");
		String res = null;
		try {
			WebUser user = SessionManager.getCurrentUser(request);
			petCardService.save(number,user.getUsername());
		} catch (Exception e) {
			logger.error("save PetCard error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
	
}
