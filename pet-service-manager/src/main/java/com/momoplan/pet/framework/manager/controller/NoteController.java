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

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.framework.manager.service.NoteService;
import com.momoplan.pet.framework.manager.service.RoleManageService;
import com.momoplan.pet.framework.manager.service.RoleUserManageService;
import com.momoplan.pet.framework.manager.service.UserManageService;

@Controller
public class NoteController {
	
	Logger logger = LoggerFactory.getLogger(NoteController.class);
	
	@Autowired
	private RoleManageService roleManageService;
	@Autowired
	private UserManageService userManageService ;
	@Autowired
	private RoleUserManageService  roleUserManageService ;
	@Autowired
	private NoteService noteService=null;
	/**
	 * 获取帖子详情
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/notemanager/notedetail.html")
	public String Notedetail(Note note,Model model){
		logger.debug("wlcome to note notemanager Notedetail......"+note.toString());
		try {
			Note note2=noteService.getNotebyid(note.getId());
			noteService.updateClickCount(note2.getId());
			logger.debug("获取帖子详情"+note2);
			model.addAttribute("note2", note2);
			logger.debug("获取帖子详情controller"+note2.toString());
			return "/manager/notemanage/NoteDetail";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取详情异常"+e);
			return "/manager/notemanage/NoteDetail";
		}
	}
	/**
	 * To发送帖子Or修改帖子
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/notemanager/ToNoteAddOrUpdate.html")
	public String ToNoteAddOrUpdate(Note note,Model model,HttpServletRequest request){
		logger.debug("wlcome to note notemanager ToNoteAddOrUpdate......");
		try {
			if(note != null && !"".equals(note.getId()) && null != note.getId()){
				Note note2=noteService.getNotebyid(note.getId());
				logger.debug("获取帖子详情"+note2);
				model.addAttribute("note2", note2);
				return "/manager/notemanage/NoteUpdate";
			}else{
				model.addAttribute("forumId", note.getForumId());
				List<MgrTrustUser> trustUserlist= noteService.trustUserslist(request);
				model.addAttribute("trustUserlist",trustUserlist);
				return "/manager/notemanage/NoteAdd";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ToNoteAddOrUpdate"+ e);
			return "";
		}
	}
	/**
	 * 发送帖子Or修改帖子
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/notemanager/NoteAddOrUpdate.html")
	public void NoteAddOrUpdate(Note note,Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			logger.debug("wlcome to manager notemanager NoteAddOrUpdate......");
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "操作成功!");
			json.put("callbackType", "closeCurrent");
			json.put("forwardUrl", "");
			json.put("rel", "jbsxBox1");
			try {
				logger.debug("增加或者修改帖子congtroller"+note.toString());
				noteService.NoteAddOrUpdate(note);
			} catch (Exception e) {
				json.put("message", e.getMessage());
				e.printStackTrace();
			}
			String jsonStr = json.toString();
			logger.debug(jsonStr);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NoteAddOrUpdate"+e);
		}
	}
	/**
	 * 删除帖子
	 * @param model
	 * @return
	 */
	@RequestMapping("/manager/notemanager/NoteDel.html")
	public void NoteDel(Note note,Model model,HttpServletRequest request,HttpServletResponse response){
		try {
			logger.debug("wlcome to manager notemanager NoteAddOrUpdate......");
			JSONObject json = new JSONObject();
			json.put("statusCode", 200);
			json.put("message", "操作成功!");
			json.put("callbackType", "closeCurrent");
			json.put("forwardUrl", "");
			json.put("navTabId", "");
			try {
				logger.debug("进入删除帖子controller"+note.toString());
				noteService.NoteDel(note);
			} catch (Exception e) {
				logger.error("删除帖子失败"+e);
				json.put("message", e.getMessage());
				e.printStackTrace();
			}
			String jsonStr = json.toString();
			logger.debug(jsonStr);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("NoteAddOrUpdate"+e);
		}
	}
	
	
	@RequestMapping("/manager/notemamager/DelteedNotes.html")
	public String getDelteedNotes(Forum forum,Model model){
		try {
			logger.debug("welcome to getdeleteedNotes........"+forum.toString());
			List<Note> list=noteService.getDeledNotes(forum);
			logger.debug("已删除帖子"+list.toString());
			model.addAttribute("list", list);
			logger.debug("getdeleteedNotes+"+list);
			return "/manager/notemanage/Delednotes";
		} catch (Exception e) {
			logger.error("getDeletedNotes异常"+e);
			return "";
		}
	}
}
