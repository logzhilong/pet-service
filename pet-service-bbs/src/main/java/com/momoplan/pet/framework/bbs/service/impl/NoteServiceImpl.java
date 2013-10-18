package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;
import com.momoplan.pet.framework.bbs.repository.NoteRepository;
import com.momoplan.pet.framework.bbs.repository.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteService;
import com.momoplan.pet.framework.bbs.vo.NoteVo;
@Service
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteMapper noteMapper=null;
	@Resource
	private NoteSubMapper noteSubMapper=null;
	@Resource
	private NoteRepository noteRepository = null;
	@Resource
	private NoteSubRepository noteSubRepository = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	
	/**
	 * 发送帖子
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object sendNote(ClientRequest ClientRequest) {
		try {
			Note bbsNote = new Note();
			bbsNote.setId(IDCreater.uuid());
			bbsNote.setUserId(PetUtil.getParameter(ClientRequest,"userId"));
			bbsNote.setClientCount(null);
			bbsNote.setCt(new Date());
			bbsNote.setEt(new Date());
			bbsNote.setForumId(PetUtil.getParameter(ClientRequest,"forumId"));
			bbsNote.setIsDel(false);
			bbsNote.setIsEute(false);
			bbsNote.setIsTop(false);
			bbsNote.setState("0");
			bbsNote.setType("0");
			bbsNote.setClientCount((long) 0);
			bbsNote.setName(PetUtil.getParameter(ClientRequest, "name"));
			bbsNote.setContent(PetUtil.getParameter(ClientRequest, "content"));
			logger.debug(""+bbsNote.toString());
			noteRepository.insertSelective(bbsNote);
			return "sendNoteSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "sendNoteFail";
		}
	}

	/**
	 * 根据帖子name搜索
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object searchNote(ClientRequest ClientRequest) {
		try {
			NoteCriteria noteCriteria = new NoteCriteria();
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			String  forumid=PetUtil.getParameter(ClientRequest, "forumid");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
				if(forumid.equals("0")){
				}else{
					criteria.andForumIdEqualTo(forumid);
				}
			String name=PetUtil.getParameter(ClientRequest, "notename");
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
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object detailNote(ClientRequest ClientRequest) {
		try {
			String noteid=PetUtil.getParameter(ClientRequest, "noteid");
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
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object delNote(ClientRequest ClientRequest) {
		try {
			String noteid=PetUtil.getParameter(ClientRequest, "noteid");
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
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object reportNote(ClientRequest ClientRequest) {
		try {
			String noteid=PetUtil.getParameter(ClientRequest, "noteid");
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
	 * @param ClientRequest
	 * @return
	 */
	public Object updateClickCount(ClientRequest ClientRequest){
		try {
			String noteid=PetUtil.getParameter(ClientRequest, "noteid");
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
	public Object getNoteCountByForumid(ClientRequest ClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			criteria.andForumIdEqualTo(PetUtil.getParameter(ClientRequest, "forumId"));
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
	public Object getMyNotedListByuserid(ClientRequest ClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			criteria.andUserIdEqualTo(PetUtil.getParameter(ClientRequest, "userid"));
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
	public Object getTodayNewNoteListByFid(ClientRequest ClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			String fid=PetUtil.getParameter(ClientRequest, "forumPid");
			criteria.andIsTopEqualTo(false);
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			if(fid.equals("0")){
				criteria.andForumIdEqualTo(fid);
			}
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			Calendar currentDate = new GregorianCalendar();   
			currentDate.set(Calendar.HOUR_OF_DAY, 0);  
			currentDate.set(Calendar.MINUTE, 0);  
			currentDate.set(Calendar.SECOND, 0);  
			criteria.andCtGreaterThanOrEqualTo(((Date)currentDate.getTime().clone()));
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			
			
			
			
			NoteCriteria noteCriteria1=new NoteCriteria();
			NoteCriteria.Criteria criteria1=noteCriteria1.createCriteria();
			criteria1.andIsTopEqualTo(true);
			criteria1.andIsDelEqualTo(false);
			criteria1.andTypeEqualTo("0");
			String fid1=PetUtil.getParameter(ClientRequest, "forumPid");
			if(fid1.equals("0")){
				criteria1.andForumIdEqualTo(fid1);
			}
			noteCriteria1.setMysqlLength(5);
			noteCriteria1.setOrderByClause("ct desc");
			Calendar currentDate1 = new GregorianCalendar();   
			currentDate1.set(Calendar.HOUR_OF_DAY, 0);  
			currentDate1.set(Calendar.MINUTE, 0);  
			currentDate1.set(Calendar.SECOND, 0);  
			criteria.andCtGreaterThanOrEqualTo(((Date)currentDate1.getTime().clone()));
			List<Note> notelist1 = noteMapper.selectByExample(noteCriteria1);
			
			
			List<Note> notes=new ArrayList<Note>();
			for(Note note2:notelist1){
				notes.add(note2);
			}
			for(Note note:notelist){
					notes.add(note);
			}
			
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
			return "getTodayNewNoteListFail";
		}
	}
	
	/**
	 * 某圈子获取最新帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object newNoteByFid(ClientRequest ClientRequest) {
		try {
			NoteCriteria noteCriteria = new NoteCriteria();
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
			String fid=PetUtil.getParameter(ClientRequest, "forumPid");
			if(fid.equals("0")){
			}else {
				criteria.andForumIdEqualTo(fid);
			}
			criteria.andIsTopEqualTo(false);
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			
			//获取置顶帖子
			NoteCriteria noteCriteria1 = new NoteCriteria();
			noteCriteria1.setMysqlOffset((1-1)*5);
			noteCriteria1.setMysqlLength(5);
			noteCriteria1.setOrderByClause("ct desc");
			NoteCriteria.Criteria criteria1 = noteCriteria1.createCriteria();
			String fid1=PetUtil.getParameter(ClientRequest, "forumPid");
			if(fid1.equals("0")){
			}else {
				criteria1.andForumIdEqualTo(fid);
			}
			criteria1.andIsTopEqualTo(true);
			criteria1.andIsDelEqualTo(false);
			criteria1.andTypeEqualTo("0");
			List<Note> notelist1 = noteMapper.selectByExample(noteCriteria1);
			
			//获取置顶帖子+普通帖子(按时间排序)
			List<Note> notes=new ArrayList<Note>();
			for(Note  note2:notelist1){
				notes.add(note2);
			}
			for(Note note:notelist){
				notes.add(note);
			}
			
			
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
			return "newNotesByidFail";
		}
	}
	
	
	/**
	 * 获取全站精华
	 * @param ClientRequest
	 * @return
	 */
	public Object getEuteNoteList(ClientRequest ClientRequest){
		try {
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			String fid=PetUtil.getParameter(ClientRequest, "forumPid");
			if(fid.equals("0")){
			}else {
				criteria.andForumIdEqualTo(fid);
			}
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			noteCriteria.setOrderByClause("ct desc");
			criteria.andIsEuteEqualTo(true);
			criteria.andIsTopEqualTo(false);
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			//add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
			List<NoteVo> noteVoList = new ArrayList<NoteVo>(notelist.size());
			for(Note note : notelist){
				NoteVo vo = new NoteVo();
				BeanUtils.copyProperties(note, vo);
				String uid = note.getUserId();
				String nid = note.getId();
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);//在缓存中获取用户
				vo.setNickname(user.getNickname());
				vo.setUserIcon(user.getImg());
				Long totalReply = noteSubRepository.totalReply(nid);
				vo.setTotalReply(totalReply);
				noteVoList.add(vo);
			}
			return notelist;
		} catch (Exception e) {
			e.printStackTrace();
			return "getEuteNoteListFail";
		}
	}
	
	
	
	/**
	 * 全局最新回复(根据回复时间将帖子显示{不显示置顶帖子})
	 */
	public Object getNewReplysByReplyct(ClientRequest ClientRequest){
		try {
		    NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			criteria.andIsTopEqualTo(false);
			criteria.andIsDelEqualTo(false);
			criteria.andTypeEqualTo("0");
			noteCriteria.setOrderByClause("et desc");
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			noteCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteCriteria.setMysqlLength(pageSize);
			return noteMapper.selectByExample(noteCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			return "getNewReplysByReplyct";
		}
	}
}
