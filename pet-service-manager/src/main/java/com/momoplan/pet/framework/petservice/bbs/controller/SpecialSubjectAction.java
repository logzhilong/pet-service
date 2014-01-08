package com.momoplan.pet.framework.petservice.bbs.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.base.controller.BaseAction;
import com.momoplan.pet.framework.base.vo.MgrTrustUserVo;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.petservice.bbs.service.ForumService;
import com.momoplan.pet.framework.petservice.bbs.service.NoteService;
import com.momoplan.pet.framework.petservice.bbs.service.SpecialSubjectService;
import com.momoplan.pet.framework.petservice.bbs.vo.ForumVo;
import com.momoplan.pet.framework.petservice.bbs.vo.NoteVo;
import com.momoplan.pet.framework.petservice.bbs.vo.SpecialSubjectState;
import com.momoplan.pet.framework.petservice.bbs.vo.SpecialSubjectType;
import com.momoplan.pet.framework.petservice.bbs.vo.SpecialSubjectVo;

/**
 * 专题
 * @author liangc
 */
@Controller
public class SpecialSubjectAction extends BaseAction{

	private static Logger logger = LoggerFactory.getLogger(NoteAction.class);
	
	@Autowired
	private SpecialSubjectService specialSubjectService = null;
	@Autowired
	private NoteService noteService = null;
	@Autowired
	private ForumService forumService = null;

	@RequestMapping("/petservice/bbs/specialMain.html")
	public String main(SpecialSubject myForm,Page<SpecialSubject> page, Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("/petservice/bbs/specialMain.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			page = specialSubjectService.getSpecialSubjectList(page, myForm);
			model.addAttribute("myForm", myForm);
			model.addAttribute("page", page);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("圈子列表异常",e);
			throw e;
		}
		return "/petservice/bbs/specialMain";
	}
	
	@RequestMapping("/petservice/bbs/specialAddOrEdit_s.html")
	public String addOrEdit_s(SpecialSubjectVo myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		try{
			String id = myForm.getId();
			if(StringUtils.isNotEmpty(id)){
				SpecialSubject ss = mapperOnCache.selectByPrimaryKey(SpecialSubject.class, id);
				Note note = mapperOnCache.selectByPrimaryKey(Note.class, ss.getNoteId());
				Forum forum = mapperOnCache.selectByPrimaryKey(Forum.class, note.getForumId());
				SsoUser ssoUser = mapperOnCache.selectByPrimaryKey(SsoUser.class, note.getUserId());
				
				String imgUrl = uploadFile.getFileAsUrl(ss.getImg());
				logger.debug("imgUrl="+imgUrl);
				BeanUtils.copyProperties(ss, myForm);
				NoteVo noteVo = new NoteVo();
				BeanUtils.copyProperties(note, noteVo);
				noteVo.setForumName(forum.getName());
				noteVo.setNickname(ssoUser.getNickname());
				model.addAttribute("imgUrl", imgUrl);
				model.addAttribute("note", noteVo);
			}
			//新增时选择发帖帐号用
			List<MgrTrustUserVo> tuList = noteService.getMgrTrustUserList(SessionManager.getCurrentUser(request).getId());
			model.addAttribute("tuList", tuList);
			model.addAttribute("forumList", forumService.getForumList(new ForumVo()) );
			model.addAttribute("myForm", myForm);
		}catch(Exception e){
			logger.error("specialAddOrEdit_s error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/bbs/specialAddOrEdit_s";
	}
	
	@RequestMapping("/petservice/bbs/specialAddOrEdit_m/{gid}.html")
	public String addOrEdit_m( @PathVariable("gid") String gid,SpecialSubjectVo myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		myForm.setGid(gid);
		return addOrEdit_m(myForm,model,request,response);
	}
	@RequestMapping("/petservice/bbs/specialAddOrEdit_m.html")
	public String addOrEdit_m(SpecialSubjectVo myForm,Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		String gid = myForm.getGid();
		try{
			//新增时选择发帖帐号用
			List<MgrTrustUserVo> tuList = noteService.getMgrTrustUserList(SessionManager.getCurrentUser(request).getId());
			model.addAttribute("tuList", tuList);
			model.addAttribute("forumList", forumService.getForumList(new ForumVo()) );
			List<SpecialSubject> ssList = specialSubjectService.getSpecialSubjectListByGid(gid);
			model.addAttribute("list", ssList);
			model.addAttribute("myForm", myForm);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		}catch(Exception e){
			logger.error("specialAddOrEdit_s error",e);
			model.addAttribute("errorMsg", e.getMessage());
		}
		return "/petservice/bbs/specialAddOrEdit_m";
	}
	
	
	@RequestMapping("/petservice/bbs/specialGetNote.html")
	public String getNote(NoteVo myForm,Page<NoteVo> page, Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.debug("/petservice/bbs/specialGetNote.html");
		logger.debug("input:"+gson.toJson(myForm));
		try {
			List<Forum> forumList = forumService.getForumList(new ForumVo());
			
			if(StringUtils.isEmpty(myForm.getForumId()) ){
				ForumVo fv = new ForumVo();
				fv.setPid(forumList.get(0).getId());
				myForm.setForumId(forumService.getForumList(fv).get(0).getId());
			}
			
			Map<String,Forum> fm = forumService.getForumMap();
			
			page = noteService.getNoteList(page, myForm);
			for(NoteVo vo : page.getData()){
				vo.setForumName(fm.get(vo.getForumId()).getName());
			}
			
			model.addAttribute("forumList", forumList );
			model.addAttribute("myForm", myForm);
			model.addAttribute("page", page);
			model.addAttribute("pet_file_server", commonConfig.get("pet_file_server"));
		} catch (Exception e) {
			logger.error("specialGetNote",e);
			throw e;
		}
		return "/petservice/bbs/specialGetNote";
	}
	@RequestMapping("/petservice/bbs/getNoteById.html")
	public void getNoteById(String id ,HttpServletResponse response) throws Exception {
		logger.debug("/petservice/bbs/getNoteById.html");
		try {
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, id);
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, note.getUserId());
			NoteVo noteVo = new NoteVo();
			BeanUtils.copyProperties(note, noteVo);
			Forum f = mapperOnCache.selectByPrimaryKey(Forum.class, note.getForumId());
			noteVo.setForumName(f.getName());
			noteVo.setNickname(user.getNickname());
			PetUtil.writeStringToResponse(gson.toJson(noteVo), response);
		} catch (Exception e) {
			logger.error("specialGetNote",e);
			throw e;
		}
	}
	
	@RequestMapping("/petservice/bbs/specialGetForum.html")
	public String getForum(Model model) throws Exception {
		try {
			List<Forum> forumList = forumService.getForumList(new ForumVo());
			model.addAttribute("forumList", forumList );
		} catch (Exception e) {
			logger.error("specialGetNote",e);
			throw e;
		}
		return "/petservice/bbs/specialGetForum";
	}
	
	@RequestMapping("/petservice/bbs/specialGetGhost.html")
	public String getGhost(Model model,HttpServletRequest request) throws Exception {
		try {
			List<MgrTrustUserVo> tuList = noteService.getMgrTrustUserList(SessionManager.getCurrentUser(request).getId());
			model.addAttribute("list", tuList );
		} catch (Exception e) {
			logger.error("specialGetNote",e);
			throw e;
		}
		return "/petservice/bbs/specialGetGhost";
	}
	
	@RequestMapping("/petservice/bbs/specialDelete.html")
	public void delete(SpecialSubjectVo myForm,String m, Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("navTabId",myForm.getNavTabId());
		try{
			SpecialSubject ss = mapperOnCache.selectByPrimaryKey(SpecialSubject.class, myForm.getId());
			if(SpecialSubjectState.PUSHED.getCode().equalsIgnoreCase(ss.getState())){
				throw new Exception("Can't delete the pushed special subject.");
			}
			ss.setState(SpecialSubjectState.DELETE.getCode());
			mapperOnCache.updateByPrimaryKeySelective(ss, ss.getId());
			if(StringUtils.isNotEmpty(ss.getGid())){
				if(!"m".equals(m) && StringUtils.isNotEmpty(ss.getGid())){
					List<SpecialSubject> sslist = specialSubjectService.getSpecialSubjectListByGid(ss.getGid());
					//一个组的全部删除
					for(SpecialSubject s1 : sslist){
						s1.setState(SpecialSubjectState.DELETE.getCode());
						mapperOnCache.updateByPrimaryKeySelective(s1, s1.getId());
					}
				} else if("m".equals(m)){
					//组成员被删除，重新选举带头大哥
					specialSubjectService.groupLeader(ss.getGid());
				}
			}
		}catch(Exception e){
			logger.error("save_s",e);
			json.put("statusCode","201");
			json.put("message","操作失败:err="+e.getMessage());
		}
		String rtn = json.toString();
		logger.debug(rtn);
		PetUtil.writeStringToResponse(rtn, response);
	}
	
	/**
	 * 保存专题-单个帖子的
	 * @param myForm
	 * @param model
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/petservice/bbs/specialSave_s.html")
	public void save_s(SpecialSubjectVo myForm, Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id = myForm.getId();
		String gid = myForm.getGid();
		
		if(StringUtils.isNotEmpty(gid)){
			if(myForm.getSeq()==null||myForm.getSeq()<=0){
				myForm.setSeq(100);
			}
		}
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId",myForm.getNavTabId());
		try{
			Note note = myForm.getNote();
			String noteId = note.getId();
			if(StringUtils.isEmpty(noteId)){
				//给副文本中的img标签增加超级链接
				String content = note.getContent();
				note.setContent(addImgLink4Content(content));
				noteId = noteService.saveNote(note, null, SessionManager.getCurrentUser(request).getUsername());
			}
			Date now = DateUtils.now();
			SpecialSubject ss = new SpecialSubject();
			BeanUtils.copyProperties(myForm, ss);
			ss.setNoteId(noteId);
			ss.setEt(now);
			ss.setState(SpecialSubjectState.NEW.getCode());
			ss.setType(SpecialSubjectType.F.getCode());
			if(StringUtils.isEmpty(id)){
				ss.setCb(SessionManager.getCurrentUser(request).getUsername());
				ss.setCt(now);
				ss.setId(IDCreater.uuid());
				mapperOnCache.insertSelective(ss, ss.getId());
			}else{
				mapperOnCache.updateByPrimaryKeySelective(ss, ss.getId());
			}
			if(StringUtils.isNotEmpty(gid)){
				specialSubjectService.groupLeader(gid);
			}
		}catch(Exception e){
			logger.error("save_s",e);
			json.put("statusCode","201");
			json.put("message","操作失败:err="+e.getMessage());
		}
		String rtn = json.toString();
		logger.debug(rtn);
		PetUtil.writeStringToResponse(rtn, response);
	}
	@RequestMapping("/petservice/bbs/specialSave_m.html")
	public void save_m(SpecialSubjectVo myForm, Model model,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject json = new JSONObject();
		json.put("confirmMsg","");
		json.put("message","操作成功");
		json.put("statusCode","200");
		json.put("callbackType","closeCurrent");
		json.put("navTabId",myForm.getNavTabId());
		String rtn = json.toString();
		logger.debug(rtn);
		PetUtil.writeStringToResponse(rtn, response);
	}
	
}
