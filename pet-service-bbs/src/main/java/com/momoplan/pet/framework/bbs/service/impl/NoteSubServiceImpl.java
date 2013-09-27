package com.momoplan.pet.framework.bbs.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.framework.bbs.controller.BbSClientRequest;
import com.momoplan.pet.framework.bbs.service.NoteSubService;

@Service
public class NoteSubServiceImpl implements NoteSubService {
	@Resource
	private NoteSubMapper noteSubMapper = null;
	@Resource
	private NoteMapper noteMapper = null;

	
	/**
	 * 回复帖子
	 * 
	 */
	@Override
	public Object replyNote(BbSClientRequest bbsClientRequest) {
		try {
			NoteSub bbsNoteSub = new NoteSub();
			bbsNoteSub.setId(IDCreater.uuid());
			bbsNoteSub.setUserId(PetUtil.getParameter(bbsClientRequest,"userId"));
			bbsNoteSub.setNoteId(PetUtil.getParameter(bbsClientRequest,"noteId"));
			bbsNoteSub.setContent(PetUtil.getParameter(bbsClientRequest,"content"));
			bbsNoteSub.setCt(new Date());
			bbsNoteSub.setArea(PetUtil.getParameter(bbsClientRequest, "area"));
			bbsNoteSub.setPid(PetUtil.getParameter(bbsClientRequest,"pid"));
			noteSubMapper.insertSelective(bbsNoteSub);
			return "replySuccss";
		} catch (Exception e) {
			e.printStackTrace();
			return "replyNoteFail";
		}
	}

	/**
	 * 
	 * 根据回帖id获取回帖
	 */
	@Override
	public Object getReplyNoteSubByReplyNoteid(BbSClientRequest bbsClientRequest) {
		try {
			String noteSubid=PetUtil.getParameter(bbsClientRequest, "noteSubid");
			return noteSubMapper.selectByPrimaryKey(noteSubid);
		} catch (Exception e) {
			e.printStackTrace();
			return "getReplyNoteSubByReplyNoteidFail";
		}
	}
	/**
	 * 获取当前帖子所有回复
	 */
	@Override
	public Object getAllReplyNoteByNoteid(BbSClientRequest bbsClientRequest){
		try {
			NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			noteSubCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteSubCriteria.setMysqlLength(pageSize);
			noteSubCriteria.setOrderByClause("ct desc");
			NoteSubCriteria.Criteria criteria=noteSubCriteria.createCriteria();
			criteria.andNoteIdEqualTo(PetUtil.getParameter(bbsClientRequest, "noteId"));
			List<NoteSub> noteSubs = noteSubMapper.selectByExample(noteSubCriteria);
			return noteSubs;
		} catch (Exception e) {
			e.printStackTrace();
			return "getAllReplyNoteByNoteidFail";
		}
	}
	/**
	 * 获取某圈子下所有回复数
	 * 
	 */
	public Object getNoteSubCountByForumid(BbSClientRequest bbsClientRequest){
		try {
			int count=0;
			NoteCriteria noteCriteria=new NoteCriteria();
			NoteCriteria.Criteria criteria=noteCriteria.createCriteria();
			criteria.andForumIdEqualTo(PetUtil.getParameter(bbsClientRequest, "forumId"));
			List<Note> notelist = noteMapper.selectByExample(noteCriteria);
			for(Note note:notelist){
				NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
				NoteSubCriteria.Criteria criteriaw=noteSubCriteria.createCriteria();
				criteriaw.andNoteIdEqualTo(note.getId());
				List<NoteSub> noteSubs=noteSubMapper.selectByExample(noteSubCriteria);
				count=noteSubs.size()+count;
			}
			return count;
		} catch (Exception e) {
			return "getNoteSubCountByForumidFail";
		}
	}
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(BbSClientRequest bbsClientRequest){
		try {
			NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
			NoteSubCriteria.Criteria criteria=noteSubCriteria.createCriteria();
			noteSubCriteria.setOrderByClause("ct desc");
			int pageNo=PetUtil.getParameterInteger(bbsClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(bbsClientRequest, "pageSize");
			noteSubCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteSubCriteria.setMysqlLength(pageSize);
			criteria.andUserIdEqualTo(PetUtil.getParameter(bbsClientRequest, "userId"));
			List<NoteSub> noteSubs=noteSubMapper.selectByExample(noteSubCriteria);
			return noteSubs;
		} catch (Exception e) {
			e.printStackTrace();
			return "getMyReplyNoteListByUseridFail";
		}
	}
}
