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

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;

@Controller
public class CommonDataManagerController {
	
	private Logger logger = LoggerFactory.getLogger(CommonDataManagerController.class);
	
	@Autowired
	private CommonDataManagerService commonDataManagerService;
	
	@RequestMapping("/manager/commons/areaCodeList.html")
	public String areaCodeList(PageBean<CommonAreaCode> pageBean, CommonAreaCode myForm,Model model){
		logger.debug("wlcome to pet manager areaCodeManager......");
		try {
			//测试时先写死，分页尚未实现 >>>>>>>>>>
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
			//测试时先写死，分页尚未实现 <<<<<<<<<<
			pageBean = commonDataManagerService.listAreaCode(pageBean, myForm);
			model.addAttribute("pageBean",pageBean);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/commons/areaCodeList";
	}

	@RequestMapping("/manager/commons/areaCodeAdd.html")
	public String addOrEditAreaCode(CommonAreaCode myForm,Model model){
		logger.debug("wlcome to pet manager addOrEditAreaCode......");
		model.addAttribute("myForm",myForm);
		return "/manager/commons/areaCodeAdd";
	}

	@RequestMapping("/alerts/areaCodeSaveOrUpdate.html")
	public void saveOrUpdateAreaCode(CommonAreaCode myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		long now = System.currentTimeMillis();
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", 200);
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0101");
/*		
		{
			“statusCode”:”200″,
			”message”:”添加成功”,
			“callbackType”:”closeCurrent”,
			”forwardUrl”:”",
			“navTabId”:”main”
		}
*/		
		try{
			commonDataManagerService.insertOrUpdateAreaCode(myForm);
		}catch(Exception e){
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	
	
}
