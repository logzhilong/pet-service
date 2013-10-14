package com.momoplan.pet.framework.bbs.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;
import com.momoplan.pet.framework.bbs.service.NoteService;
@Service
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteMapper noteMapper=null;

	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	
	/**
	 * 发送帖子
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object sendNote(BbSClientRequest bbsClientRequest) {
		try {
			Note bbsNote = new Note();
			bbsNote.setId(IDCreater.uuid());
			bbsNote.setUserId(PetUtil.getParameter(bbsClientRequest,"userId"));
			bbsNote.setClientCount(null);
			bbsNote.setCt(new Date());
			bbsNote.setEt(new Date());
			bbsNote.setForumId(PetUtil.getParameter(bbsClientRequest,"forumId"));
			bbsNote.setIsDel(false);
			bbsNote.setIsEute(false);
			bbsNote.setIsTop(false);
			bbsNote.setState("0");
			bbsNote.setType("0");
			bbsNote.setClientCount((long) 0);
			bbsNote.setName(PetUtil.getParameter(bbsClientRequest, "name"));
			bbsNote.setContent(PetUtil.getParameter(bbsClientRequest, "content"));
			logger.debug(""+bbsNote.toString());
			noteMapper.insertSelective(bbsNote);
			return "sendNoteSuccess";
				
		} catch (Exception e) {
			e.printStackTrace();
			return "sendNoteFail";
		}
	}

	/**
	 * 根据帖子name搜索
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object searchNote(BbSClientRequest bbsClientRequest) {
		try {
			NoteCriteria noteCriteria = new NoteCriteria();
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			String  forumid=PetUtil.getParameter(bbsClientRequest, "forumid");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
				if(forumid.equals("0")){
				}else{
					criteria.andForumIdEqualTo(forumid);
				}
			String name=PetUtil.getParameter(bbsClientRequest, "notename");
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			criteria.andNameLike("%"+name+"%");
			criteria.andStateEqualTo("0");
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "searchNoteFail";
		}
	}

	
	/**
	 * 根据noteid查看帖子详情
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object detailNote(BbSClientRequest bbsClientRequest) {
		try {
			String noteid=PetUtil.getParameter(bbsClientRequest, "noteid");
			NoteCriteria noteCriteria = new NoteCriteria();
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			criteria.andIdEqualTo(noteid);
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "detailNoteFail";
		}
	}

	
	/**
	 * 删除帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object delNote(BbSClientRequest bbsClientRequest) {
		try {
			String noteid=PetUtil.getParameter(bbsClientRequest, "noteid");
			Note note=noteMapper.selectByPrimaryKey(noteid);
			note.setIsDel(true);
			note.setEt(new Date());
			noteMapper.updateByPrimaryKey(note);
			return "delNoteSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "delNoteFail";
		}
	}

	

	
	
	/**
	 * 根据id举报帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object reportNote(BbSClientRequest bbsClientRequest) {
		try {
			String noteid=PetUtil.getParameter(bbsClientRequest, "noteid");
			Note note=noteMapper.selectByPrimaryKey(noteid);
			note.setState("1");
			note.setEt(new Date());
			noteMapper.updateByPrimaryKey(note);
			return "reportNoteSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "reportNoteFail";
		}
	}
	
	
	
	/**
	 * 更新帖子点击数
	 * @param bbsClientRequest
	 * @return
	 */
	public Object updateClickCount(BbSClientRequest bbsClientRequest){
		try {
			String noteid=PetUtil.getParameter(bbsClientRequest, "noteid");
			Note note=noteMapper.selectByPrimaryKey(noteid);
				if(note.equals(null)){
					return "updateClickCountFail";
				}else{
					note.setClientCount(note.getClientCount()+1);
					note.setEt(new Date());
					noteMapper.updateByPrimaryKey(note);
					return "updateClickCountSuccess";
				}
		} catch (Exception e) {
			e.printStackTrace();
			return "updateClickCountFail";
		}
	}
	/**
	 * 获取某圈子下所有帖子数
	 * 
	 */
	public Object getNoteCountByForumid(BbSClientRequest bbsClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			criteria.andForumIdEqualTo(PetUtil.getParameter(bbsClientRequest, "forumId"));
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist.size();
		} catch (Exception e) {
			e.printStackTrace();
			return "getNoteCountByForumidFail";
		}
	}
	/**
	 * 我发表过的帖子列表
	 * 
	 */
	public Object getMyNotedListByuserid(BbSClientRequest bbsClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			criteria.andUserIdEqualTo(PetUtil.getParameter(bbsClientRequest, "userid"));
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "getMyNotedCountByuseridFail";
		}
	}
	/**
	 *今日新增帖子列表
	 * 
	 */
	public Object getTodayNewNoteList(BbSClientRequest bbsClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			
			Calendar currentDate = new GregorianCalendar();   
			currentDate.set(Calendar.HOUR_OF_DAY, 0);  
			currentDate.set(Calendar.MINUTE, 0);  
			currentDate.set(Calendar.SECOND, 0);  
			criteria.andCtGreaterThanOrEqualTo(((Date)currentDate.getTime().clone()));
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "getTodayNewNoteListFail";
		}
	}
	
	/**
	 * 获取最新帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object newNote(BbSClientRequest bbsClientRequest) {
		try {
			NoteCriteria noteCriteria = new NoteCriteria();
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
			criteria.andIsTopEqualTo(false);
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			criteria.andIsEuteEqualTo(false);
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "newNoteFail";
		}
	}
	/**
	 *今日新增帖子列表
	 * 
	 */
	public Object getTodayNewNoteListByFid(BbSClientRequest bbsClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			String fid=PetUtil.getParameter(bbsClientRequest, "forumPid");
			criteria.andForumIdEqualTo(fid);
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			Calendar currentDate = new GregorianCalendar();   
			currentDate.set(Calendar.HOUR_OF_DAY, 0);  
			currentDate.set(Calendar.MINUTE, 0);  
			currentDate.set(Calendar.SECOND, 0);  
			criteria.andCtGreaterThanOrEqualTo(((Date)currentDate.getTime().clone()));
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "getTodayNewNoteListFail";
		}
	}
	
	/**
	 * 获取最新帖子
	 * 
	 * @param bbsClientRequest
	 * @return
	 */
	@Override
	public Object newNoteByFid(BbSClientRequest bbsClientRequest) {
		try {
			NoteCriteria noteCriteria = new NoteCriteria();
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			String fid=PetUtil.getParameter(bbsClientRequest, "forumPid");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
			criteria.andForumIdEqualTo(fid);
			criteria.andIsTopEqualTo(false);
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			criteria.andIsEuteEqualTo(false);
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "newNoteFail";
		}
	}
}
