package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteSubService;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;
import com.momoplan.pet.framework.bbs.vo.PageBean;

@Service
public class NoteSubServiceImpl implements NoteSubService {
	
	private static Logger logger = LoggerFactory.getLogger(NoteSubServiceImpl.class);

	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;
	private NoteSubMapper noteSubMapper = null;
	
	@Autowired
	public NoteSubServiceImpl(NoteSubRepository noteSubRepository,
			MapperOnCache mapperOnCache, NoteSubMapper noteSubMapper) {
		super();
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
		this.noteSubMapper = noteSubMapper;
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
		note.setRt(now);//default new Date(0)
		//TODO 这最好不要直接去 update ，如果并发量大会有问题，可以放队列，待修正
		mapperOnCache.updateByPrimaryKeySelective(note, note.getId());
		logger.debug("//TODO 这最好不要直接去 update ，如果并发量大会有问题，可以放队列，待修正");
		logger.debug(note.toString());
		return po.getId();
	}

	private PageBean<NoteSub> getNoteSubList(String noteId,String userId,int pageNo, int pageSize){
		List<NoteSub> list = null;
		long totalCount = 0;
		if(StringUtils.isEmpty(userId)){
			list = noteSubRepository.getReplyListByNoteId(noteId, pageSize, pageNo);
			if(list!=null)
				totalCount = noteSubRepository.totalReply(noteId);
		}else{
			NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
			NoteSubCriteria.Criteria criteria = noteSubCriteria.createCriteria();
			criteria.andNoteIdEqualTo(noteId);
			criteria.andUserIdEqualTo(userId);
			totalCount = noteSubMapper.countByExample(noteSubCriteria);
			noteSubCriteria.setMysqlOffset(pageNo * pageSize);
			noteSubCriteria.setMysqlLength((pageNo+1)*pageSize);
			list = noteSubMapper.selectByExample(noteSubCriteria);
		}
		PageBean<NoteSub> p = new PageBean<NoteSub>();
		p.setData(list);
		p.setTotalCount(totalCount);
		return p;
	}
	
	@Override
	public PageBean<NoteSubVo> getReplyByNoteId(String noteId,String userId, int pageNo, int pageSize) throws Exception {
		PageBean<NoteSub> list = getNoteSubList(noteId, userId, pageNo, pageSize);
		List<NoteSubVo> vos = new ArrayList<NoteSubVo>();
		for(NoteSub ns : list.getData()){
			String nsid = ns.getId();
			NoteSub noteSub = mapperOnCache.selectByPrimaryKey(NoteSub.class, nsid);//这么做是为了取最新的状态,都是缓存取值
			NoteSubVo vo = new NoteSubVo();
			BeanUtils.copyProperties(noteSub, vo);
			String uid = noteSub.getUserId();
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
			logger.debug("---------------");
			logger.debug("userId="+uid);
			logger.debug("user="+user);
			logger.debug("noteSub="+noteSub);
			logger.debug("vo="+vo);
			logger.debug("---------------");
			vo.setNickname(user.getNickname());
			vo.setUserIcon(user.getImg());
			vos.add(vo);
		}
		PageBean<NoteSubVo> p = new PageBean<NoteSubVo>();
		
		p.setData(vos);
		p.setPageNo(pageNo);
		p.setPageSize(pageSize);
		p.setTotalCount(list.getTotalCount());
		return p;
	}

}
