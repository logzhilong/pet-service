package com.momoplan.pet.framework.petservice.bbs.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.MgrTrustUserVo;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.petservice.bbs.service.NoteService;
import com.momoplan.pet.framework.petservice.bbs.service.ReplyService;
import com.momoplan.pet.framework.petservice.bbs.vo.NoteSubVo;
import com.momoplan.pet.framework.petservice.bbs.vo.NoteVo;

@Controller
public class NoteAction extends BaseAction {
	
	private static Logger logger = LoggerFactory.getLogger(NoteAction.class);
	@Autowired
	private NoteService noteService = null;
	@Autowired
	private ReplyService replyService = null;

	@RequestMapping("/petservice/bbs/noteMain.html")
	public String main(NoteVo myForm,Page<NoteVo> page, Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("/petservice/bbs/noteMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			page.setPageSize(400);
			page = noteService.getNoteList(page, myForm);
			Forum f = mapperOnCache.selectByPrimaryKey(Forum.class, myForm.getForumId());
			model.addAttribute("forum", f);
			model.addAttribute("myForm", myForm);
			model.addAttribute("page", page);
		} catch (Exception e) {
			logger.error("圈子列表异常",e);
			throw e;
		}
		return "/petservice/bbs/noteMain";
	}
	
	@RequestMapping("/petservice/bbs/noteAddOrEdit.html")
	public String addOrEdit(NoteVo myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		String id = myForm.getId();
		try{
			if(StringUtils.isNotEmpty(id)){
				//修改时发帖人不允许修改，只能修改 状态、标题、内容
				Note n = mapperOnCache.selectByPrimaryKey(Note.class, id);
				BeanUtils.copyProperties(n, myForm);
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, n.getUserId());
				myForm.setUserName(user.getUsername());
				myForm.setNickname(user.getNickname());
			}else{
				//新增时选择发帖帐号用
				List<MgrTrustUserVo> tuList = noteService.getMgrTrustUserList(SessionManager.getCurrentUser(request).getId());
				model.addAttribute("tuList", tuList);
			}
			model.addAttribute("myForm", myForm);
		}catch(Exception e){
			logger.error("note save error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/bbs/noteAddOrEdit";
	}
	
	@RequestMapping("/petservice/bbs/noteSave.html")
	public void save(NoteVo myForm,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/bbs/noteSave");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","noteMainTab");
		json.put("forwardUrl",ctx+"/petservice/bbs/noteMain.html?forumId="+myForm.getForumId());
		String res = null;
		try {
			Note vo = new Note();
			//给副文本中的img标签增加超级链接
			String content = myForm.getContent();
			Matcher matcher = Pattern.compile("(<img.*?src=\")(.+?)(\".*?/>)").matcher(content);
			Map<String,String> map = new HashMap<String,String>();
			while(matcher.find()){
				String s1 = matcher.group(1);
				String s2 = matcher.group(2);
				String s3 = matcher.group(3);
				String s4 = "<a href=\""+s2+"\">";
				String s5 = "</a>";
				String str = s1+s2+s3;
				String target = s4+s1+s2+s3+s5;
				map.put(str, target);
			}
			for(String k :map.keySet()){
				String target = map.get(k);
				content = content.replaceAll(k, target);
				logger.debug("替换 "+k+" --> "+target);
			}
			myForm.setContent(content);
			logger.debug("content:\r\n"+content);
			BeanUtils.copyProperties(myForm, vo);
			noteService.saveNote(vo);
		} catch (Exception e) {
			logger.error("save note error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}	
	
	@RequestMapping("/petservice/bbs/uploadFile.html")
	public void uploadFile(Model model,HttpServletRequest request,HttpServletResponse response)throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		JSONObject json = new JSONObject();
		json.put("err", "");
		try{
			for(Iterator<String> it = multipartRequest.getFileNames();it.hasNext();){
				String fileName = it.next();
				MultipartFile file = multipartRequest.getFile(fileName);
				logger.debug("-----------------------------------");
				logger.debug("file_key : "+fileName);
				logger.debug("name : "+file.getName());
				logger.debug("size : "+file.getSize());
				logger.debug("contentType : "+file.getContentType());
				logger.debug("originalFilename : "+file.getOriginalFilename());
				logger.debug("-----------------------------------");
				String uploadResponse = uploadFile.upload(file.getBytes(), fileName, file.getContentType(), "OK", "OK");
				JSONObject success = new JSONObject(uploadResponse);
				String entity = success.getString("entity");
				if(success.getBoolean("success")){
					json.put("msg", uploadFile.getFileAsUrl(entity));//结果在这里返回给副文本编辑器
					String res = json.toString();
					logger.debug(res);
					PetUtil.writeStringToResponse(res, response);
				}else{
					throw new Exception(entity);
				}
			}
		}catch(Exception e){
			logger.error("",e);
			throw e;
		}
	}
	
	
	
	@RequestMapping("/petservice/bbs/noteView.html")
	public String view(NoteVo myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		String id = myForm.getId();
		try{
			Note n = mapperOnCache.selectByPrimaryKey(Note.class, id);
			BeanUtils.copyProperties(n, myForm);
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, n.getUserId());
			String img = user.getImg();
			if(img!=null){
				String imgId = img.split("_")[0];
				String fs = commonConfig.get("pet_file_server");
				String icon = fs+"/get/"+imgId;
				myForm.setIcon(icon);
			}
			List<NoteSubVo> reply = replyService.getReplyList(id);
			myForm.setUserName(user.getUsername());
			myForm.setNickname(user.getNickname());
			model.addAttribute("myForm", myForm);
			model.addAttribute("reply", reply);
		}catch(Exception e){
			logger.error("note save error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/bbs/noteView";
	}
	
	@RequestMapping("/petservice/bbs/reply.html")
	public String reply(NoteSub myForm , Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		List<MgrTrustUserVo> tuList = noteService.getMgrTrustUserList(SessionManager.getCurrentUser(request).getId());
		logger.debug("input = "+gson.toJson(myForm));
		model.addAttribute("tuList", tuList);
		model.addAttribute("myForm", myForm);
		return "/petservice/bbs/reply";
	}
	@RequestMapping("/petservice/bbs/replySave.html")
	public void replySave(NoteSub myForm , Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		String ctx = request.getContextPath();
		logger.debug("/petservice/bbs/noteSave");
		logger.debug("input:"+gson.toJson(myForm));
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId","noteViewTab");
		json.put("forwardUrl",ctx+"/petservice/bbs/noteView.html?id="+myForm.getNoteId());
		String res = null;
		try {
			NoteSub vo = new NoteSub();
			//给副文本中的img标签增加超级链接
			String content = myForm.getContent();
			Matcher matcher = Pattern.compile("(<img.*?src=\")(.+?)(\".*?/>)").matcher(content);
			Map<String,String> map = new HashMap<String,String>();
			while(matcher.find()){
				String s1 = matcher.group(1);
				String s2 = matcher.group(2);
				String s3 = matcher.group(3);
				String s4 = "<a href=\""+s2+"\">";
				String s5 = "</a>";
				String str = s1+s2+s3;
				String target = s4+s1+s2+s3+s5;
				map.put(str, target);
			}
			for(String k :map.keySet()){
				String target = map.get(k);
				content = content.replaceAll(k, target);
				logger.debug("替换 "+k+" --> "+target);
			}
			myForm.setContent(content);
			logger.debug("content:\r\n"+content);
			BeanUtils.copyProperties(myForm, vo);
			replyService.saveReply(vo);
		} catch (Exception e) {
			logger.error("save note error",e);
			json.put("message","操作失败："+e.getMessage());
		}
		res = json.toString();
		logger.debug("save_output : "+res);
		PetUtil.writeStringToResponse(res, response);
	}
	
}
