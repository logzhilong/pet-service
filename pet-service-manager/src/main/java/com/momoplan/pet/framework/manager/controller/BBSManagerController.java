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

import com.google.gson.Gson;
import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.TreeBean;

@Controller
public class BBSManagerController {

	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	private BBSManagerService bBSManagerService;
	@Autowired
	private CommonDataManagerService commonDataManagerService;
	/**
	 * To增加OR修改圈子
	 * @param forum
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/ToaddOrUpdateForum.html")
	public String ToaddOrEditAreaCode(Forum forum,Model model){
		try {
			if("".equals(forum.getId()) || null==forum.getId())
			{
				List<CommonAreaCode> codes=commonDataManagerService.getConmonArealist();
				model.addAttribute("codes", codes);
				List<Forum> forums=	bBSManagerService.getForumlist();
				model.addAttribute("forums",forums);
				logger.debug("wlcome to pet manager Forumadd......");
				return "/manager/bbs/forumAdd";
			}else{
				List<CommonAreaCode> codes=commonDataManagerService.getConmonArealist();
				model.addAttribute("codes", codes);
				Forum  fos=bBSManagerService.getForumbyid(forum);
				model.addAttribute("fos",fos);
				logger.debug("wlcome to pet manager updateforum......");		
				return "/manager/bbs/forumUpdate";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 增加或者修改圈子
	 * @param fatherid
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/bbs/addOrUpdateForum.html")
	public void addOrUpdateForum(String fatherid,String sunid,String grandsunid,Forum forum,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("wlcome to pet-service-bbs manager addorupdate ......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0002");
		try {
			if(!"all".equals(fatherid) && !"all".equals(sunid) && !"all".equals(grandsunid) && !"".equals(fatherid)){
				
				if(!"".equals(grandsunid) && !"all".equals(grandsunid)){
					forum.setAreaCode(fatherid+"-"+sunid+"-"+grandsunid);
				}else if(!"".equals(sunid) && !"all".equals(sunid) ){
					forum.setAreaCode(fatherid+"-"+sunid);
				}else if(!"".equals(fatherid) && !"all".equals(fatherid)){
					forum.setAreaCode(fatherid);
				}
			}
			bBSManagerService.addOrUpdateForum(forum);
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
	 * 根据id删除指定圈子
	 * @param forum
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/bbs/DelForum.html")
	public void DelForum(Forum forum,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		logger.debug("wlcome to pet-service-bbs manager del ......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0002");
		try {
			bBSManagerService.DelForum(forum);
		} catch (Exception e) {
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		String jsonStr = json.toString();
		logger.debug(jsonStr);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonStr);
	}
	@RequestMapping("/manager/bbs/main.html")
	public String main(Model model,HttpServletRequest request,HttpServletResponse response){
		logger.debug("wlcome to pet-service-bbs manager main ......");
		return "/manager/bbs/main";
	}
	
	/**
	 * 列出最顶级的父圈子
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumList.html")
	public String forumList(Forum myForm,PageBean<Forum> pageBean,Model model){
		logger.debug("wlcome to pet-service-bbs manager forumList ......");
		try {
			//测试时先写死，分页尚未实现 >>>>>>>>>>
//			pageBean.setPageNo(1);
//			pageBean.setPageSize(100);
			//测试时先写死，分页尚未实现 <<<<<<<<<<
			pageBean = bBSManagerService.listForum(pageBean, myForm);
			model.addAttribute("pageBean",pageBean);
			//读取所有国家(查询级联)
			List<CommonAreaCode> codes=commonDataManagerService.getConmonArealist();
			model.addAttribute("codes", codes);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/bbs/forumList";
	}
	
	/**
	 * 根据父圈子ID，找到子圈子，并进行管理；
	 * 此处为功能入口
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumManager.html")
	public String forumManager(Forum myForm,PageBean<Forum> pageBean,Model model){
		logger.debug("wlcome to pet-service-bbs manager forumManager ......");
		try {
			//测试时先写死，分页尚未实现 >>>>>>>>>>
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
			//测试时先写死，分页尚未实现 <<<<<<<<<<
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/bbs/forumManager";
	}
	
	@RequestMapping("/manager/bbs/forumManagerMain.html")
	public String forumManagerMain(String target,String id,Model model){
		logger.debug("wlcome to pet-service-bbs manager forumManagerMain ......");
		if("left".equals(target)){
			return fmLeft(id,model);
		}else{//right
			return fmRight(id,model);
		}
	}
	private String fmLeft(String id,Model model){
		List<TreeBean> tree =  new ArrayList<TreeBean> ();//platform 的节点集合
		TreeBean root = new TreeBean();
		root.setName("功能列表");
		root.setLeaf(false);
		root.setNode("0");
		tree.add(root);
		
		TreeBean bbs = new TreeBean("00","0","圈子");
		TreeBean bbsMain = new TreeBean("0001","00","圈子统计信息","/manager/bbs/main.html");
		TreeBean bbsList = new TreeBean("0002","00","圈子管理","/manager/bbs/forumList.html");
		
		TreeBean common = new TreeBean("01","0","公共数据");
		TreeBean commonAreaCode = new TreeBean("0101","01","地域信息","/manager/commons/areaCodeList.html");
		
		tree.add(bbs);
		tree.add(bbsMain);
		tree.add(bbsList);
		tree.add(common);
		tree.add(commonAreaCode);
		
		Gson g = new Gson();
		String json = g.toJson(tree);
		logger.debug(json);
		model.addAttribute("tree", json);
		return "/manager/bbs/forumManagerLeft";
	}
	private String fmRight(String id,Model model){
		
		return "/manager/bbs/forumManagerRight";
	}
	
}
