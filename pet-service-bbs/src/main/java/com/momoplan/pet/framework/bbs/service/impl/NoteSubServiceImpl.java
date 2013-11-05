package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteSubService;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;

@Service
public class NoteSubServiceImpl implements NoteSubService {
	
	private static Logger logger = LoggerFactory.getLogger(NoteSubServiceImpl.class);

	private NoteSubMapper noteSubMapper = null;
	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;

	@Autowired
	public NoteSubServiceImpl(NoteSubMapper noteSubMapper, NoteSubRepository noteSubRepository, MapperOnCache mapperOnCache) {
		super();
		this.noteSubMapper = noteSubMapper;
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
	}

	/**
	 * 回复帖子
	 */
	@Override
	public String replyNote(NoteSub po) throws Exception {
		Date now = new Date();
		po.setId(IDCreater.uuid());
		po.setCt(now);
		noteSubRepository.insertSelective(po);
		String noteId = po.getNoteId();
		Note note = mapperOnCache.selectByPrimaryKey(Note.class, noteId);
		note.setEt(now);
		note.setRt(now);
		//TODO 这最好不要直接去 update ，如果并发量大会有问题，可以放队列，待修正
		mapperOnCache.updateByPrimaryKeySelective(note, note.getId());
		logger.debug("//TODO 这最好不要直接去 update ，如果并发量大会有问题，可以放队列，待修正");
		logger.debug(note.toString());
		return po.getId();
	}

	@Override
	public List<NoteSubVo> getReplyByNoteId(String noteId, int pageNo, int pageSize) throws Exception {
		List<NoteSub> list = noteSubRepository.getReplyListByNoteId(noteId, pageSize, pageNo);
		List<NoteSubVo> vos = new ArrayList<NoteSubVo>();
		for(NoteSub n : list){
			NoteSubVo vo = new NoteSubVo();
			BeanUtils.copyProperties(n, vo);
			String uid = n.getUserId();
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
			vo.setNickname(user.getNickname());
			vo.setUserIcon(user.getImg());
			vos.add(vo);
		}
		return vos;
	}

}
