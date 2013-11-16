package com.momoplan.pet.framework.petservice.bbs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.vo.WebUser;
import com.momoplan.pet.framework.manager.vo.Xmlparser;
import com.momoplan.pet.framework.petservice.bbs.service.ForumService;
import com.momoplan.pet.framework.petservice.bbs.vo.ForumVo;

@Controller
public class ForumAction extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(ForumAction.class);
	@Autowired
	private ForumService forumService = null;
	
	@RequestMapping("/petservice/bbs/forumMain.html")
	public String main(ForumVo myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/bbs/forumMain");
		logger.debug("input:"+gson.toJson(myForm));
		String pid = myForm.getPid();
		try {
			if(StringUtils.isNotEmpty(pid)){
				Forum pf = mapperOnCache.selectByPrimaryKey(Forum.class, pid);
				model.addAttribute("pf",pf);//上级圈子对象
			}
			List<Forum> list = forumService.getForumList(myForm);
			model.addAttribute("list", list);
		} catch (Exception e) {
			logger.error("圈子列表异常",e);
		}
		return "/petservice/bbs/forumMain";
	}

	@RequestMapping("/petservice/bbs/forumAddOrEdit.html")
	public String addOrEdit(ForumVo myForm,Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("/petservice/bbs/forumAddOrEdit");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			String id = myForm.getId();
			String pid = myForm.getPid();
			if(StringUtils.isNotEmpty(id)){
				Forum c = mapperOnCache.selectByPrimaryKey(Forum.class, id);
				BeanUtils.copyProperties(c, myForm);
			}
			if(StringUtils.isNotEmpty(pid)){
				Forum p = mapperOnCache.selectByPrimaryKey(Forum.class, pid);
				myForm.setPname(p.getName());
				//获取xml圈子类型
				Xmlparser xmlparser=new Xmlparser();
				List<Xmlparser> xmllist=xmlparser.getFForums();
				logger.debug("增加子圈子时,解析xml获取子圈子集合"+xmllist.toString());
				model.addAttribute("xmllist", xmllist);
			}
			model.addAttribute("myForm", myForm);
		} catch (Exception e) {
			logger.error("add or edit error",e);
		}
		return "/petservice/bbs/forumAddOrEdit";
	}
	
	@RequestMapping("/petservice/bbs/forumSave.html")
	public void save(ForumVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/bbs/forumSave");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");			
		json.put("statusCode","200");			
		json.put("callbackType","closeCurrent");	
		json.put("navTabId","panel0201");
		json.put("forwardUrl",ctx+"/petservice/bbs/forumMain.html?pid="+myForm.getPid());	
		String res = null;
		try {
			Forum vo = new Forum();
			BeanUtils.copyProperties(myForm, vo);
			WebUser user = SessionManager.getCurrentUser(request);
			vo.setCb(user.getName());
			forumService.saveForum(vo);
			res = json.toString();
		} catch (Exception e) {
			logger.error("save forum error",e);
			json.put("message","操作失败："+e.getMessage());	
		}
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
}
