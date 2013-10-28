package com.momoplan.pet.framework.manager.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;

@Controller
public class BBSManagerController {

	private Logger logger = LoggerFactory.getLogger(BBSManagerController.class);

	@Autowired
	private BBSManagerService bBSManagerService;
	@Autowired
	private CommonDataManagerService commonDataManagerService;

	/**
	 * To增加OR修改圈子
	 * 
	 * @param forum
	 * @param model
	 * @return list1.html
	 */
	@RequestMapping("/manager/bbs/ToaddOrUpdateForum.html")
	public String ToaddOrEditAreaCode(Forum forum, Model model) {
		try {
			if ("".equals(forum.getId()) || null == forum.getId()) {
				List<CommonAreaCode> codes = commonDataManagerService
						.getConmonArealist();
				model.addAttribute("codes", codes);
				List<Forum> forums = bBSManagerService.getForumlist();
				model.addAttribute("forums", forums);
				logger.debug("wlcome to pet manager Forumadd......");
				return "/manager/bbs/forumAdd";
			} else {
				List<CommonAreaCode> codes = commonDataManagerService
						.getConmonArealist();
				model.addAttribute("codes", codes);
				Forum fos = bBSManagerService.getForumbyid(forum);
				model.addAttribute("fos", fos);
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
	 * 
	 * @param fatherid
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/bbs/addOrUpdateForum.html")
	public void addOrUpdateForum(String fatherid, String sunid,
			String grandsunid, Forum forum, Model model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.debug("wlcome to pet-service-bbs manager addorupdate ......");
		JSONObject json = new JSONObject();
		json.put("statusCode", 200);
		json.put("message", "操作成功!");
		json.put("callbackType", "closeCurrent");
		json.put("forwardUrl", "");
		json.put("navTabId", "panel0002");
		try {
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
	 * 
	 * @param forum
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/manager/bbs/DelForum.html")
	public void DelForum(Forum forum, Model model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
	public String main(Model model, HttpServletRequest request,
			HttpServletResponse response) {
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
		logger.debug("wlcome to pet-service-bbs manager forumList ......");
		try {
			// 测试时先写死，分页尚未实现 >>>>>>>>>>
			// pageBean.setPageNo(1);
			// pageBean.setPageSize(100);
			// 测试时先写死，分页尚未实现 <<<<<<<<<<
			pageBean = bBSManagerService.listForum(pageBean, myForm);
			model.addAttribute("pageBean", pageBean);
			// 读取所有国家(查询级联)
			List<CommonAreaCode> codes = commonDataManagerService
					.getConmonArealist();
			model.addAttribute("codes", codes);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "/manager/bbs/forumList";
	}

	/**
	 * 根据父圈子ID，找到子圈子，并进行管理； 此处为功能入口
	 * 
	 * @param myForm
	 * @param pageBean
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumManager.html")
	public String forumManager(String sss, Forum myForm,
			PageBean<Forum> pageBean, Model model) {
		logger.debug("wlcome to pet-service-bbs manager forumManager ......");
		try {
			// 测试时先写死，分页尚未实现 >>>>>>>>>>
			pageBean.setPageNo(1);
			pageBean.setPageSize(100);
			// 测试时先写死，分页尚未实现 <<<<<<<<<<
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
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
				return fmLeft(id, model);
			} else {
				return fmRight(id, model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private String fmLeft(String id, Model model) {
			return "/manager/bbs/forumManagerLeft";
	}

	public String fmRight(String id, Model model) {
		return "/manager/bbs/forumManagerRight";
	}

	/**
	 * 获取某圈子子集贴子
	 * 管理右侧的帖子集合
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/bbs/forumrightmanagelist.html")
	public String forumrightmanagelist(Forum forum, Model model) {
		try {
			List<Note> forums = bBSManagerService.getAllNotesByForumId(forum);
			logger.debug("forumrightmanagelist" + forums.toString());
			model.addAttribute("forums", forums);
			model.addAttribute("forumid", forum.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
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
			logger.debug("Toupimg" + e);
			return "/manager/notemanage/UploadImg";
		}
	}

	/**
	 * 上传图片 uploadimg文件上传
	 * 
	 * @return model
	 */
	@RequestMapping("/manager/notemanage/upimg.html")
	public void upImg(Model model, HttpServletRequest req,HttpServletResponse response) throws Exception {
		logger.debug("wlcome to upimg ......");
		try {
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
			// 用于显示在在线编辑器里，图片的路径
			String newFileName = "";

			if (multipartResolver.isMultipart(req)) {
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 上传文件信息
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						String name = file.getOriginalFilename();
						String type = file.getContentType();
						String path = "D:/" + name;

						// 访问url
						String urlStr ="http://123.178.27.74/pet-hub/request?body={\"service\":\"service.uri.pet_sso\",\"method\":\"login\",\"token\":\"\",\"channel\":\"\",\"mac\":\"\",\"imei\":\"\",\"params\":{\"username\":\"\",\"password\":\"\"}}";  
						String urlStr1 ="http://123.178.27.74/pet-file-server/put?{\"Content-Type\":\"64B7D0E143294CE7B76651F2109462FE\",\"name\":\"file\",\"fileName\":\"44\",\"mimeType\":\"image/jpeg\"}";  
						URL url = new URL(urlStr);  
						URLConnection URLconnection = url.openConnection();  
						HttpURLConnection httpConnection = (HttpURLConnection)URLconnection;  
						int responseCode = httpConnection.getResponseCode();  
						if (responseCode == HttpURLConnection.HTTP_OK) {  
							System.out.println("连接成功!");
						}else{ 
							System.out.println("连接失败!");
						}  
						InputStream urlStream = httpConnection.getInputStream();  
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));  
						String sCurrentLine = "";  
						String sTotalString = "";  
						while ((sCurrentLine = bufferedReader.readLine()) != null) {  
						    sTotalString += sCurrentLine;  
						}  
						//该URL返回值  sTotalString
						System.out.println(sTotalString);

						
						//返回页面数据赋值
						newFileName ="http://img.ivsky.com/img/tupian/slides/201310/04/chishui_dapubu.jpg";
						// 将上传文件存储至本地
						File localFile = new File(path);
						if (!localFile.exists()) {
							localFile.mkdirs();
						}
						file.transferTo(localFile);
						// 向前台返回文件名
						PrintWriter out = response.getWriter();
						out.println("{\"err\":\"" + "" + "\",\"msg\":\""
								+newFileName + "\"}");
						out.flush();
						out.close();
					}
				}
			}
			logger.debug("上传成功!");
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("{\"err\":\"" + "error" + "\",\"msg\":\"" + "上传错误!......" + "\"}");
			out.flush();
			out.close();
		}
	}

}
