package com.momoplan.pet.framework.manager.controller;

import java.io.PrintWriter;
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
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.UpImgVo;
import com.momoplan.pet.framework.manager.vo.Xmlparser;

@Controller
public class BBSManagerController {

	private Logger logger = LoggerFactory.getLogger(BBSManagerController.class);

	@Autowired
	private BBSManagerService bBSManagerService;
	@Autowired
	private CommonDataManagerService commonDataManagerService;
	private CommonConfig commonConfig = new CommonConfig();
	/**
	 * To增加OR修改圈子
	 * 
	 * @param forum
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/ToaddOrUpdateForum.html")
	public String ToaddOrEditAreaCode(Forum forum, Model model) {
		try {
			if ("".equals(forum.getId()) || null == forum.getId()) {
				logger.debug("进入To增加圈子controller.......");
				List<CommonAreaCode> codes = commonDataManagerService.getConmonArealist();
				model.addAttribute("codes", codes);
				List<Forum> forums = bBSManagerService.getForumlist();
				logger.debug("增加圈子时,获取父圈子集合........."+forums.toString());
				//获取xml圈子类型
				Xmlparser xmlparser=new Xmlparser();
				List<Xmlparser> xmllist=xmlparser.getFForums();
				logger.debug("增加子圈子时,解析xml获取子圈子集合"+xmllist.toString());
				model.addAttribute("xmllist", xmllist);
				model.addAttribute("forums", forums);
				logger.debug("wlcome to pet manager Forumadd......"+forums);
				return "/manager/bbs/forumAdd";
			} else {
				List<CommonAreaCode> codes = commonDataManagerService.getConmonArealist();
				model.addAttribute("codes", codes);
				Forum fos = bBSManagerService.getForumbyid(forum);
				model.addAttribute("fos", fos);
				logger.debug("进入To修改圈子controller.......");
				logger.debug("圈子id"+forum.getId());
				logger.debug("修改圈子前获取圈子信息"+fos.toString());
				return "/manager/bbs/forumUpdate";
			}
		} catch (Exception e) {
			logger.error("ToaddOrEditAreaCode" + e);
			e.printStackTrace();
			return null;
		}
	}
	@RequestMapping("/manager/bbs/Toaddforum.html")
	public void Toaddforum(Xmlparser xmlparser,Model model,HttpServletResponse response) {
		try {
			Xmlparser xmlparser2=new Xmlparser();
			List<Xmlparser> list=xmlparser2.getsForum(xmlparser.getFid());
			//拼接字符串作为显示子圈子类型级联的效果
			StringBuffer sb = new StringBuffer("[");
			if(list.size()>0){
				int i=0;
				sb.append("[\"").append("").append("\",\"").append("--请选择--").append("\"]");
				sb.append(",");
				for(Xmlparser xml :list ){
					if(i++>0){
						sb.append(",");
					}
					sb.append("[\"").append(xml.getId()).append("\",\"").append(xml.getName()).append("\"]");
				}
				sb.append("]");
			}
			else{
					sb.append("[\"").append("").append("\",\"").append("--请选择--").append("\"]");
					sb.append("]");
			}
			logger.debug("增加圈子前获取级联子圈子类型:"+sb.toString());
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 增加或者修改圈子
	 * 
	 * @param fatherid
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/bbs/addOrUpdateForum.html")
	public void addOrUpdateForum(String fatherid, String sunid,String grandsunid, Forum forum, Model model,HttpServletRequest request, HttpServletResponse response)throws Exception {
		logger.debug("wlcome to pet-service-bbs manager addorupdate ......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0002");
		try {
			// 看地区是否有选择,若有,则作为条件
			if (!"all".equals(fatherid) && !"all".equals(sunid)
					&& !"all".equals(grandsunid) && !"".equals(fatherid)) {
				if (!"".equals(grandsunid) && !"all".equals(grandsunid)) {
					forum.setAreaCode(fatherid + "-" + sunid + "-" + grandsunid);
				} else if (!"".equals(sunid) && !"all".equals(sunid)) {
					forum.setAreaCode(fatherid + "-" + sunid);
				} else if (!"".equals(fatherid) && !"all".equals(fatherid)) {
					forum.setAreaCode(fatherid);
				}
			}
			logger.debug("进入或者修改增加圈子controller......."+forum.toString());
			bBSManagerService.addOrUpdateForum(forum, request);
		} catch (Exception e) {
			logger.error("addOrUpdateForum" + e);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		logger.debug(json.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

	/**
	 * 根据id删除指定圈子
	 * 
	 * @param forum
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/bbs/DelForum.html")
	public void DelForum(Forum forum, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("wlcome to pet-service-bbs manager del ......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0002");
		try {
			logger.debug("进入删除圈子controller..............删除圈子输入"+forum.toString());
			bBSManagerService.DelForum(forum);
		} catch (Exception e) {
			logger.error("DelForum" + e);
			json.put("message", e.getMessage());
			e.printStackTrace();
		}
		logger.debug(json.toString());
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

	@RequestMapping("/manager/bbs/main.html")
	public String main(Model model, HttpServletRequest request,HttpServletResponse response) {
		logger.debug("wlcome to pet-service-bbs manager main ......");
		return "/manager/bbs/main";
	}

	/**
	 * 列出最顶级的父圈子
	 * 
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumList.html")
	public String forumList(Forum myForm, PageBean<Forum> pageBean, Model model) {
		logger.debug("wlcome to 获取父级圈子controller ......"+myForm.toString());
		try {
			pageBean = bBSManagerService.listForum(pageBean, myForm);
			logger.debug("获取父级圈子controller 输出"+pageBean.toString());
			model.addAttribute("pageBean", pageBean);
			// 读取所有国家(查询级联)
			List<CommonAreaCode> codes = commonDataManagerService.getConmonArealist();
			model.addAttribute("codes", codes);
		} catch (Exception e) {
			logger.error("forumList" + e);
			logger.error(e.getMessage());
		}
		return "/manager/bbs/forumList";
	}

	/**
	 * 根据父圈子ID，找到子圈子集合，并进行管理；
	 * 此处为功能入口
	 * 
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumManager.html")
	public String forumManager() {
		return "/manager/bbs/forumManager";
	}

	/**
	 * 显示圈子左边和右边的dialog
	 * 
	 * @param target
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumManagerMain.html")
	public String forumManagerMain(String target, String id, Model model) {
		logger.debug("wlcome to pet-service-bbs manager forumManagerMain ......");
		try {
			Forum forum = new Forum();
			forum.setId(id);
			List<Forum> forumss;
			forumss = bBSManagerService.getSunForumListbyPid(forum);
			model.addAttribute("forumss", forumss);
			if ("left".equals(target)) {
				return "/manager/bbs/forumManagerLeft";
			} else {
				return "/manager/bbs/forumManagerRight";
			}
		} catch (Exception e) {
			logger.error("forumManagerMain" + e);
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取某圈子子集贴子 管理右侧的帖子集合
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumrightmanagelist.html")
	public String forumrightmanagelist(String tname, Forum forum, Model model) {
		try {
			forum.setDescript(tname);
			List<Note> forums = bBSManagerService.getAllNotesByForumId(forum);
			logger.debug("forumrightmanagelist" + forums);
			model.addAttribute("forums", forums);
			model.addAttribute("forumid", forum.getId());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("forumrightmanagelistError" + e);
			return null;
		}
		return "/manager/bbs/forumManagerRight";
	}

	/**
	 * To上传图片
	 * 
	 * @return model
	 */
	@RequestMapping("/manager/notemanage/Toupimg.html")
	public String ToupImg(Model model) {
		try {
			logger.debug("welcome   Toupimg");
			return "/manager/notemanage/UploadImging";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Toupimg" + e);
			return "/manager/notemanage/UploadImg";
		}
	}

	/**
	 * 帖子上传图片 uploadimg文件上传
	 * 
	 * @return model
	 */
	@RequestMapping("/manager/notemanage/upimg.html")
	public void upImg(Model model, HttpServletRequest req,HttpServletResponse response) throws Exception {
		logger.debug("wlcome to 进入上传帖子图片 controller......");
		PrintWriter out = response.getWriter();
		try {
			// 用于显示在在线编辑器里，图片的路径
			String newFileName = null;
			UpImgVo imgVo=new UpImgVo();
			String enty=imgVo.upimg(req,"tpys");
			logger.debug("上传帖子图片返回值:......"+enty);
			if(enty != null && "" != enty){
				String url = commonConfig.get("pet_file_server", null);
				newFileName = url+"/get/" + enty;
				logger.debug("上传完成讲返回值放入帖子增加图片富文本地址栏"+newFileName);
			out.println("{\"err\":\"" + "" + "\",\"msg\":\"" + newFileName+ "\"}");
			}else {
				out.println("{\"err\":\"" + "" + "\",\"msg\":\"" + "上传失败!"+ "\"}");
				logger.debug("上传图片失败............"+enty);
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("{\"err\":\"" + "error" + "\",\"msg\":\""+ "上传错误!......" + "\"}");
		}
		finally{
			out.flush();
			out.close();
		}
	}
	@RequestMapping("/manager/forummamage/Toupdateforum.html")
	public String Toupdateforum(Forum forum, Model model) {
		try {
			Xmlparser xmlparser=new Xmlparser();
			List<Xmlparser> xmllist=xmlparser.getFForums();
			logger.debug("进入修改圈子controller.............解析子圈子类型xml"+xmllist.toString());
			model.addAttribute("xmllist", xmllist);
			forum = bBSManagerService.getForumbyid(forum);
			logger.debug("更新圈子前获取圈子信息:"+forum.toString());
			model.addAttribute("fos", forum);
			return "/manager/bbs/forumUpdate";
		} catch (Exception e) {
			logger.error("Toupdateforum" + e);
			return null;
		}
	}
	/**
	 *人物富文本上传图片
	 * @param model
	 * @param req
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/forummamage/upimgforforum.html")
	public void upimgforforum(Model model, HttpServletRequest req,HttpServletResponse response)throws Exception{
		logger.debug("wlcome to 上传托管用户头像controller ......");
		PrintWriter out = response.getWriter();
		try {
			// 用于显示在在线编辑器里，图片的路径
			String newFileName = null;
			UpImgVo imgVo=new UpImgVo();
			String enty=imgVo.upimg(req,"ns");
			logger.debug("上传托管用户头像返回值"+enty);
			if(enty != null && "" != enty){
				String url = commonConfig.get("pet_file_server", null);
				newFileName = url+"/get/" + enty;
				logger.debug("上传托管用户头像后将返回值拼接显示在地址栏"+newFileName);
			out.println("{\"err\":\"" + "" + "\",\"msg\":\"" + newFileName+ "\"}");
			}
		} catch (Exception e) {
			logger.debug("上传人物头像失败"+e);
			e.printStackTrace();
			out.println("{\"err\":\"" + "error" + "\",\"msg\":\""+ "上传错误!......" + "\"}");
		}
		finally{
			out.flush();
			out.close();
		}
	}
}
