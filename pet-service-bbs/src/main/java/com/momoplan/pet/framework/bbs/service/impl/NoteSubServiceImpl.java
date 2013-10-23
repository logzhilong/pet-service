package com.momoplan.pet.framework.bbs.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteSubService;

@Service
public class NoteSubServiceImpl implements NoteSubService {
	@Resource
	private NoteSubMapper noteSubMapper = null;
	@Resource
	private NoteMapper noteMapper = null;
	@Resource
	private NoteSubRepository noteSubRepository = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	/**
	 * 回复帖子
	 * 
	 */
	@Override
	public Object replyNote(ClientRequest ClientRequest) {
		try {
			NoteSub bbsNoteSub = new NoteSub();
			bbsNoteSub.setId(IDCreater.uuid());
			bbsNoteSub.setUserId(PetUtil.getParameter(ClientRequest,"userId"));
			bbsNoteSub.setNoteId(PetUtil.getParameter(ClientRequest,"noteId"));
			bbsNoteSub.setContent(PetUtil.getParameter(ClientRequest,"content"));
			bbsNoteSub.setCt(new Date());
			bbsNoteSub.setArea(PetUtil.getParameter(ClientRequest, "area"));
			bbsNoteSub.setPid(PetUtil.getParameter(ClientRequest,"pid"));
			noteSubRepository.insertSelective(bbsNoteSub);
			String noteId = PetUtil.getParameter(ClientRequest,"noteId");
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, noteId);
			note.setEt(new Date());
			mapperOnCache.updateByPrimaryKeySelective(note, note.getId());
			return bbsNoteSub;
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
	public Object getReplyNoteSubByReplyNoteid(ClientRequest ClientRequest) {
		try {
			String noteSubid=PetUtil.getParameter(ClientRequest, "noteSubid");
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
	public Object getAllReplyNoteByNoteid(ClientRequest ClientRequest){
		try {
			NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			noteSubCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteSubCriteria.setMysqlLength(pageSize);
			noteSubCriteria.setOrderByClause("ct desc");
			NoteSubCriteria.Criteria criteria=noteSubCriteria.createCriteria();
			criteria.andNoteIdEqualTo(PetUtil.getParameter(ClientRequest, "noteId"));
			List<NoteSub> noteSubs = noteSubMapper.selectByExample(noteSubCriteria);
			return noteSubs;
		} catch (Exception e) {
			e.printStackTrace();
			return "getAllReplyNoteByNoteidFail";
		}
	}
	
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(ClientRequest ClientRequest){
		try {
			NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
			NoteSubCriteria.Criteria criteria=noteSubCriteria.createCriteria();
			noteSubCriteria.setOrderByClause("ct desc");
			int pageNo=PetUtil.getParameterInteger(ClientRequest, "pageNo");
			int pageSize=PetUtil.getParameterInteger(ClientRequest, "pageSize");
			noteSubCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteSubCriteria.setMysqlLength(pageSize);
			criteria.andUserIdEqualTo(PetUtil.getParameter(ClientRequest, "userId"));
			List<NoteSub> noteSubs=noteSubMapper.selectByExample(noteSubCriteria);
			return noteSubs;
		} catch (Exception e) {
			e.printStackTrace();
			return "getMyReplyNoteListByUseridFail";
		}
	}
}
