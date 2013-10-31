package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteSubService;

@Service
public class NoteSubServiceImpl implements NoteSubService {
	private NoteSubMapper noteSubMapper = null;
	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;
	@Autowired
	public NoteSubServiceImpl(NoteSubMapper noteSubMapper,
			 NoteSubRepository noteSubRepository,
			MapperOnCache mapperOnCache) {
		super();
		this.noteSubMapper = noteSubMapper;
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
	}

	/**
	 * 回复帖子
	 * 
	 */
	@Override
	public Object replyNote(NoteSub noteSub) throws Exception{
			NoteSub bbsNoteSub = new NoteSub();
			bbsNoteSub.setId(IDCreater.uuid());
			bbsNoteSub.setUserId(noteSub.getUserId());
			bbsNoteSub.setNoteId(noteSub.getNoteId());
			bbsNoteSub.setContent(noteSub.getContent());
			bbsNoteSub.setCt(new Date());
			bbsNoteSub.setArea(noteSub.getArea());
			bbsNoteSub.setPid(noteSub.getPid());
			noteSubRepository.insertSelective(bbsNoteSub);
			String noteId =noteSub.getNoteId();
			Note note = mapperOnCache.selectByPrimaryKey(Note.class, noteId);
			note.setEt(new Date());
			mapperOnCache.updateByPrimaryKeySelective(note, note.getId());
			return bbsNoteSub;
	}

	/**
	 * 
	 * 根据回帖id获取回帖
	 */
	@Override
	public Object getReplyNoteSubByReplyNoteid(ClientRequest ClientRequest) throws Exception{
			String noteSubid=PetUtil.getParameter(ClientRequest, "noteSubid");
			return noteSubMapper.selectByPrimaryKey(noteSubid);
	}
	/**
	 * 根据帖子id获取所有回复
	 */
	@Override
	public Object getAllReplyNoteByNoteid(String noteid,int pageNo,int pageSize) throws Exception{
			NoteSubCriteria noteSubCriteria=new NoteSubCriteria();
			noteSubCriteria.setMysqlOffset((pageNo-1)*pageSize);
			noteSubCriteria.setMysqlLength(pageSize);
			noteSubCriteria.setOrderByClause("ct desc");
			NoteSubCriteria.Criteria criteria=noteSubCriteria.createCriteria();
			criteria.andNoteIdEqualTo(noteid);
			List<NoteSub> noteSubs = noteSubMapper.selectByExample(noteSubCriteria);
			List<NoteSub> list=new ArrayList<NoteSub>();
			for(NoteSub sub :noteSubs){
				sub.setContent(noteSubMapper.selectByPrimaryKey(sub.getId()).getContent());
				list.add(sub);
			}
			return list;
	}
	
	/**
	 *我回复过的帖子列表
	 * 
	 */
	public Object getMyReplyNoteListByUserid(ClientRequest ClientRequest) throws Exception{
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
	}
}
