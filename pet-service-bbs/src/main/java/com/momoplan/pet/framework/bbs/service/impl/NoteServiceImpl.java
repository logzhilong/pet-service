package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.NoteService;
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
	 * 发送帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object sendNote(Note note) throws Exception {

		Note bbsNote = new Note();
		bbsNote.setId(IDCreater.uuid());
		bbsNote.setUserId(note.getUserId());
		bbsNote.setClientCount(null);
		bbsNote.setCt(new Date());
		bbsNote.setEt(new Date());
		bbsNote.setForumId(note.getForumId());
		bbsNote.setIsDel(false);
		bbsNote.setIsEute(false);
		bbsNote.setIsTop(false);
		bbsNote.setState("0");
		bbsNote.setType("0");
		bbsNote.setClientCount((long) 0);
		bbsNote.setName(note.getName());
		bbsNote.setContent(note.getContent());
		logger.debug("" + bbsNote.toString());
		noteRepository.insertSelective(bbsNote);
		return bbsNote;
	}

	/**
	 * 根据帖子name搜索
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object searchNote(Note note, int pageNo, int pageSize) throws Exception {

		NoteCriteria noteCriteria = new NoteCriteria();
		String forumid = note.getForumId();
		noteCriteria.setMysqlOffset((pageNo - 1) * pageSize);
		noteCriteria.setMysqlLength(pageSize);
		noteCriteria.setOrderByClause("ct desc");
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		if (forumid.equals("0")) {
		} else {
			criteria.andForumIdEqualTo(forumid);
		}
		String name = note.getName();
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		criteria.andNameLike("%" + name + "%");
		criteria.andStateEqualTo("0");

		List<Note> notelist = noteMapper.selectByExample(noteCriteria);
		List<Note> list = new ArrayList<Note>();
		for (Note note1 : notelist) {
			note1.setContent(noteMapper.selectByPrimaryKey(note1.getId()).getContent());
			list.add(note1);
		}
		return list;
	}

	/**
	 * 根据id查看帖子详情
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object detailNote(String id) throws Exception {
		String noteid = id;
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		criteria.andIdEqualTo(noteid);
		List<Note> notelist = noteMapper.selectByExample(noteCriteria);
		Note note = noteMapper.selectByPrimaryKey(noteid);
		for (Note note2 : notelist) {
			note2.setContent(note.getContent());
		}
		return notelist;
	}

	/**
	 * 删除帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object delNote(ClientRequest ClientRequest) throws Exception {
		String noteid = PetUtil.getParameter(ClientRequest, "noteid");
		Note note = noteMapper.selectByPrimaryKey(noteid);
		note.setIsDel(true);
		note.setEt(new Date());
		noteMapper.updateByPrimaryKey(note);
		return "delNoteSuccess";
	}

	/**
	 * 根据id举报帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object reportNote(String noteid) throws Exception {
		Note note = noteMapper.selectByPrimaryKey(noteid);
		note.setState("1");
		note.setEt(new Date());
		noteMapper.updateByPrimaryKey(note);
		return "reportNoteSuccess";
	}

	/**
	 * 更新帖子点击数
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object updateClickCount(ClientRequest ClientRequest) throws Exception {
		String noteid = PetUtil.getParameter(ClientRequest, "noteid");
		Note note = noteMapper.selectByPrimaryKey(noteid);
		if (note.equals(null)) {
			return "updateClickCountFail";
		} else {
			note.setClientCount(note.getClientCount() + 1);
			note.setEt(new Date());
			noteMapper.updateByPrimaryKey(note);
			return "updateClickCountSuccess";
		}
	}

	/**
	 * 我发表过的帖子列表
	 * 
	 */
	public Object getMyNotedListByuserid(ClientRequest ClientRequest) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		int pageNo = PetUtil.getParameterInteger(ClientRequest, "pageNo");
		int pageSize = PetUtil.getParameterInteger(ClientRequest, "pageSize");
		noteCriteria.setMysqlOffset((pageNo - 1) * pageSize);
		noteCriteria.setMysqlLength(pageSize);
		noteCriteria.setOrderByClause("ct desc");
		criteria.andUserIdEqualTo(PetUtil.getParameter(ClientRequest, "userid"));
		List<Note> notelist = noteMapper.selectByExample(noteCriteria);
		List<Note> list = new ArrayList<Note>();
		for (Note note : notelist) {
			note.setContent(noteMapper.selectByPrimaryKey(note.getId()).getContent());
			list.add(note);
		}
		return list;
	}

	/**
	 * 今日新增帖子列表
	 */
	public Object getTodayNewNoteListByFid(String fid, int pageNo, int pageSize) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		criteria.andIsTopEqualTo(false);
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		if (fid.equals("0")) {
		} else {
			criteria.andForumIdEqualTo(fid);
		}
		noteCriteria.setMysqlOffset((pageNo - 1) * pageSize);
		noteCriteria.setMysqlLength(pageSize);
		noteCriteria.setOrderByClause("ct desc");
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		criteria.andCtGreaterThanOrEqualTo(((Date) currentDate.getTime().clone()));
		List<Note> notelist = noteMapper.selectByExample(noteCriteria);

		NoteCriteria noteCriteria1 = new NoteCriteria();
		NoteCriteria.Criteria criteria1 = noteCriteria1.createCriteria();
		criteria1.andIsTopEqualTo(true);
		criteria1.andIsDelEqualTo(false);
		criteria1.andTypeEqualTo("0");
		if (fid.equals("0")) {
		} else {
			criteria.andForumIdEqualTo(fid);
		}
		noteCriteria1.setMysqlLength(5);
		noteCriteria1.setOrderByClause("ct desc");
		criteria1.andCtGreaterThanOrEqualTo(((Date) currentDate.getTime().clone()));
		List<Note> notelist1 = noteMapper.selectByExample(noteCriteria1);

		List<Note> notes = new ArrayList<Note>();
		for (Note note2 : notelist1) {
			notes.add(note2);
		}
		for (Note note : notelist) {
			notes.add(note);
		}
		List<Note> list = new ArrayList<Note>();
		for (Note note : notes) {
			note.setContent(noteMapper.selectByPrimaryKey(note.getId()).getContent());
			list.add(note);
		}
		return list;
	}

	/**
	 * 某圈子最新帖子
	 * 
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public Object newNoteByFid(String fid, int pageNo, int pageSize) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.setMysqlOffset( pageNo * pageSize );
		noteCriteria.setMysqlLength(pageSize);
		noteCriteria.setOrderByClause("ct desc");
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		if (!"0".equals(fid)) {
			criteria.andForumIdEqualTo(fid);
		}
		criteria.andIsTopEqualTo(false);
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		List<Note> notelist = noteMapper.selectByExample(noteCriteria);
		
		// 获取置顶帖子
		List<Note> list = noteRepository.getTopNoteByFid(fid);
		if(list==null)
			list = new ArrayList<Note>();
		list.addAll(notelist);
		// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
		List<NoteVo> noteVoList = new ArrayList<NoteVo>(notelist.size());
		buildNoteVoList(list,noteVoList);
		return noteVoList;
	}

	/**
	 * 获取全站精华
	 * 
	 * @param ClientRequest
	 * @return
	 */
	public Object getEuteNoteList(String fid, int pageNo, int pageSize) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		if (fid.equals("0")) {
		} else {
			criteria.andForumIdEqualTo(fid);
		}
		noteCriteria.setMysqlOffset((pageNo - 1) * pageSize);
		noteCriteria.setMysqlLength(pageSize);
		noteCriteria.setOrderByClause("ct desc");
		criteria.andIsEuteEqualTo(true);
		criteria.andIsTopEqualTo(false);
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		List<Note> notelist1 = noteMapper.selectByExample(noteCriteria);
		List<Note> notelist = new ArrayList<Note>();
		for (Note note : notelist1) {
			note.setContent(noteMapper.selectByPrimaryKey(note.getId()).getContent());
			notelist.add(note);
		}
		// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
		List<NoteVo> noteVoList = new ArrayList<NoteVo>(notelist.size());
		buildNoteVoList(notelist,noteVoList);
		return noteVoList;
	}

	// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
	private void buildNoteVoList(List<Note> notelist,List<NoteVo> noteVoList) throws Exception {
		for (Note note : notelist) {
			NoteVo vo = new NoteVo();
			BeanUtils.copyProperties(note, vo);
			String uid = note.getUserId();
			String nid = note.getId();
			SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
			vo.setNickname(user.getNickname());
			vo.setUserIcon(user.getImg());
			Long totalReply = noteSubRepository.totalReply(nid);
			vo.setTotalReply(totalReply);
			noteVoList.add(vo);
		}
	}
	
	/**
	 * 全局最新回复(根据回复时间将帖子显示{不显示置顶帖子})(forumPid是否为0判断是否全站或者某圈子内)
	 */
	public Object getNewReplysByReplyct(String fid, int pageNo, int pageSize) throws Exception {

		NoteCriteria noteCriteria = new NoteCriteria();
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		criteria.andIsTopEqualTo(false);
		criteria.andIsDelEqualTo(false);
		criteria.andTypeEqualTo("0");
		if (fid.equals("0")) {
		} else {
			criteria.andForumIdEqualTo(fid);
		}
		noteCriteria.setOrderByClause("et desc");
		noteCriteria.setMysqlOffset((pageNo - 1) * pageSize);
		noteCriteria.setMysqlLength(pageSize);
		List<Note> list1 = noteMapper.selectByExample(noteCriteria);
		List<Note> list = new ArrayList<Note>();
		for (Note note : list1) {
			note.setContent(noteMapper.selectByPrimaryKey(note.getId()).getContent());
			list.add(note);
		}
		return list;
	}
}
