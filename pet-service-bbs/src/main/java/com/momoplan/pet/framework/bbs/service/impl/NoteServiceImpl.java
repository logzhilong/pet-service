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
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteState;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteService;
import com.momoplan.pet.framework.bbs.vo.Action;
import com.momoplan.pet.framework.bbs.vo.ConditionType;
import com.momoplan.pet.framework.bbs.vo.NoteVo;

@Service
public class NoteServiceImpl implements NoteService {
	private NoteMapper noteMapper = null;
	private NoteRepository noteRepository = null;
	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;

	@Autowired
	public NoteServiceImpl(NoteMapper noteMapper, NoteRepository noteRepository, NoteSubRepository noteSubRepository, MapperOnCache mapperOnCache) {
		super();
		this.noteMapper = noteMapper;
		this.noteRepository = noteRepository;
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
	}

	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	/**
	 * 发帖子
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public String sendNote(Note po) throws Exception {
		Date now = new Date();
		po.setId(IDCreater.uuid());
		po.setClientCount(1L);
		po.setCt(now);
		po.setEt(now);
		po.setRt(now);
		po.setIsDel(false);
		po.setIsEute(false);
		po.setIsTop(false);
		//TODO 发帖要走审核啊
		po.setState(NoteState.ACTIVE.getCode());
		po.setType("0");
		logger.debug("发帖子 ：" + po.toString());
		noteRepository.insertSelective(po);
		return po.getId();
	}

	/**
	 * 更新帖子点击数
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public void updateClickCount(String noteId) throws Exception {
		Note note = mapperOnCache.selectByPrimaryKey(Note.class, noteId);
		if(note!=null){
			note.setClientCount(note.getClientCount()+1);
			mapperOnCache.updateByPrimaryKeySelective(note, note.getId());
		}
	}

	/**
	 * 获取帖子列表
	 */
	@Override
	public List<NoteVo> getNoteList(String forumid,Action action,String condition,ConditionType conditionType,boolean withTop,int pageNo,int pageSize) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.setMysqlOffset(pageNo * pageSize);
		noteCriteria.setMysqlLength((pageNo+1)*pageSize);
		
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		if (!"0".equals(forumid)) {
			criteria.andForumIdEqualTo(forumid);
		}
		if(Action.ALL.equals(action)){//全部
			noteCriteria.setOrderByClause("et desc");
			logger.debug("全部...");
		}else if(Action.EUTE.equals(action)){//精华
			noteCriteria.setOrderByClause("et desc");
			criteria.andIsEuteEqualTo(true);
			logger.debug("精华...");
		}else if(Action.NEW_ET.equals(action)){//最新回复
			noteCriteria.setOrderByClause("rt desc");
			Note n = mapperOnCache.selectByPrimaryKey(Note.class, forumid);
			criteria.andRtGreaterThan(n.getCt());//回复时间一定大于创建时间
			logger.debug("最新回复...");
		}else if(Action.NEW_CT.equals(action)){//最新发布
			noteCriteria.setOrderByClause("ct desc");
			logger.debug("最新发布...");
		}else if(Action.SEARCH.equals(action)){//查询
			noteCriteria.setOrderByClause("et desc");
			if(ConditionType.NOTE_NAME.equals(conditionType)){
				criteria.andNameLike("%"+condition+"%");
			}else if(ConditionType.I_CREATE.equals(conditionType)){
				criteria.andUserIdEqualTo(condition);
			}else if(ConditionType.I_REPLY.equals(conditionType)){
				List<String> noteIds = noteSubRepository.getNoteIdListOfMyReply(condition);
				criteria.andIdIn(noteIds);
			}
			logger.debug("查询..."+conditionType.getName());
		}
		criteria.andIsTopEqualTo(false);
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		
		List<String> stateList = new ArrayList<String>();
		stateList.add(NoteState.REJECT.getCode());//审核拒绝
		stateList.add(NoteState.AUDIT.getCode());//审核中
		stateList.add(NoteState.REPORT.getCode());//被举报
		//以上状态不显示
		criteria.andStateNotIn(stateList);
		
		List<Note> notelist = noteMapper.selectByExample(noteCriteria);
		
		// 获取置顶帖子
		List<Note> list = new ArrayList<Note>();
		if(withTop&&pageNo==0){//是否带着置顶的帖子，如果带，则放在最前面,只在第一页显示
			list = noteRepository.getTopNoteByFid(forumid);
			if(list==null)
				list = new ArrayList<Note>();
		}
		if(notelist!=null)
			list.addAll(notelist);
		if(list==null||list.size()==0)
			return null;
		// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
		List<NoteVo> noteVoList = new ArrayList<NoteVo>(notelist.size());
		buildNoteVoList(list,noteVoList);
		return noteVoList;
	}
	
	// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
	private void buildNoteVoList(List<Note> notelist,List<NoteVo> noteVoList) throws Exception {
		for (Note note : notelist) {
			NoteVo vo = createNoteVo(note);
			noteVoList.add(vo);
		}
	}
	
	private NoteVo createNoteVo(Note note) throws Exception{
		NoteVo vo = new NoteVo();
		BeanUtils.copyProperties(note, vo);
		String uid = note.getUserId();
		String nid = note.getId();
		SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
		vo.setNickname(user.getNickname());
		vo.setUserIcon(user.getImg());
		Long totalReply = noteSubRepository.totalReply(nid);
		vo.setTotalReply(totalReply);
		return vo;
	}

	@Override
	public NoteVo getNoteById(String id) throws Exception {
		Note note = mapperOnCache.selectByPrimaryKey(Note.class, id);
		NoteVo vo = createNoteVo(note);
		logger.debug(vo.toString());
		return vo;
	}

}
