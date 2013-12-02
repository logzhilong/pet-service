package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteCriteria;
import com.momoplan.pet.commons.domain.bbs.po.NoteSubCriteria;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.bbs.NoteRepository;
import com.momoplan.pet.commons.repository.bbs.NoteState;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.framework.bbs.service.BaseService;
import com.momoplan.pet.framework.bbs.service.NoteService;
import com.momoplan.pet.framework.bbs.vo.Action;
import com.momoplan.pet.framework.bbs.vo.ConditionType;
import com.momoplan.pet.framework.bbs.vo.NoteVo;

@Service
public class NoteServiceImpl extends BaseService implements NoteService {

	private NoteMapper noteMapper = null;
	private NoteSubMapper noteSubMapper = null;
	private NoteRepository noteRepository = null;
	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;
	private JmsTemplate apprequestTemplate = null;
	
	@Autowired
	public NoteServiceImpl(NoteMapper noteMapper, NoteSubMapper noteSubMapper,
			NoteRepository noteRepository, NoteSubRepository noteSubRepository,
			MapperOnCache mapperOnCache, JmsTemplate apprequestTemplate) {
		super();
		this.noteMapper = noteMapper;
		this.noteSubMapper = noteSubMapper;
		this.noteRepository = noteRepository;
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
		this.apprequestTemplate = apprequestTemplate;
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
		po.setRt(DateUtils.getDate("2013-01-01"));//default
		po.setIsDel(false);
		po.setIsEute(false);
		po.setIsTop(false);
		po.setState(NoteState.AUDIT.getCode());
		po.setType("0");
		logger.debug("发帖子 ：" + po.toString());
		noteRepository.insertOrUpdateSelective(po,NoteState.AUDIT);
		sendJMS(po);
		return po.getId();
	}
	
	/**
	 * 向校验内容的应用send消息
	 * 
	 * @param userState
	 * @param biz
	 */
	private void sendJMS(Note po) {
		TextMessage tm = new ActiveMQTextMessage();
		try {
			JSONObject json = new JSONObject();
			json.put("biz", "bbs_note");
			json.put("bid", po.getId());
			json.put("content", po.getContent());
			String msg = json.toString();
			logger.debug(msg);
			tm.setText(msg);
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName("pet_wordfilter");
			apprequestTemplate.convertAndSend(queue, tm);
		} catch (JMSException e) {
			logger.debug("sendJMS error :" + e);
			e.printStackTrace();
		} catch (JSONException e) {
			logger.debug("sendJMS error :" + e);
			e.printStackTrace();
		}
	}
	
	static long min = 100;
	/**
	 * 更新帖子点击数
	 */
	@Override
	public void updateClickCount(String noteId) throws Exception {
		noteRepository.updateClickCount(noteId, min);
	}
	/**
	 * 获取点击次数
	 */
	@Override
	public Long getClientCount(String noteId) throws Exception {
		return noteRepository.getClickCount(noteId, min);
	}
	/**
	 * 获取帖子列表
	 */
	@Override
	public List<NoteVo> getNoteList(String userid,String forumid,Action action,String condition,ConditionType conditionType,String conditionScope ,boolean withTop,int pageNo,int pageSize) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.setMysqlOffset(pageNo * pageSize);
		noteCriteria.setMysqlLength((pageNo+1)*pageSize);
		
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		if (StringUtils.isNotEmpty(forumid)&&!"0".equals(forumid)) {
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
			logger.debug("//全部的最新，只返回我关注的圈子的最新即可");
			if(StringUtils.isEmpty(forumid)||"0".equals(forumid)){
				logger.debug("131130:此处只取我关注的最新的帖子");
				Map<String,String> um = super.getUserForumMap(userid);
				if(um==null||um.size()==0){
					logger.debug("没有关注的圈子，返回空结果");
					return null;
				}
				Set<String> set = um.keySet();
				List<String> values = new ArrayList<String>();
				values.addAll(set);
				criteria.andForumIdIn(values);
				noteCriteria.setOrderByClause("et desc");
				logger.debug("我关注的-最新帖子...");
			}else{
				noteCriteria.setOrderByClause("rt desc");
				criteria.andRtGreaterThan(DateUtils.getDate("2013-01-01"));//default DateUtils.getDate("2013-01-01")
				logger.debug("最新回复...");
			}
		}else if(Action.NEW_CT.equals(action)){//最新发布
			noteCriteria.setOrderByClause("ct desc");
			logger.debug("最新发布...");
		}else if(Action.SEARCH.equals(action)){//查询
			/*
			2013-11-30 : 
        	增加搜索范围参数conditionScope，当forumId传空，或者 forumId=0 时此参数生效，
    		如果conditionScope传空，默认范围是我关注的圈子，conditionScope=EUTE时，表示范围在精华帖里。
			*/
			if(StringUtils.isEmpty(forumid)||"0".equals(forumid)){
				logger.debug("conditionScope = "+conditionScope);
				if("EUTE".equalsIgnoreCase(conditionScope)){
					logger.debug("131130 SEARCH : 此处只取精华帖范围");
					criteria.andIsEuteEqualTo(true);
				} else if("ALL".equalsIgnoreCase(conditionScope)){
					logger.debug("131130 SEARCH : 此处搜索全部");
				} else {
					logger.debug("131130 SEARCH : 此处只取我关注的最新的帖子");
					Map<String,String> um = super.getUserForumMap(userid);
					if(um==null||um.size()==0){
						logger.debug("没有关注的圈子，返回空结果");
						return null;
					}
					Set<String> set = um.keySet();
					List<String> values = new ArrayList<String>();
					values.addAll(set);
					criteria.andForumIdIn(values);
				}
			}
			
			noteCriteria.setOrderByClause("et desc");
			if(ConditionType.NOTE_NAME.equals(conditionType)){
				criteria.andNameLike("%"+condition+"%");
			}else if(ConditionType.I_CREATE.equals(conditionType)){
				criteria.andUserIdEqualTo(condition);
			}else if(ConditionType.I_REPLY.equals(conditionType)){
				List<String> noteIds = noteSubRepository.getNoteIdListOfMyReply(condition);
				if(noteIds==null||noteIds.size()<1)
					return null;
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
		stateList.add(NoteState.DELETE.getCode());//被删除
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
		SsoUser user = null;
		if(uid!=null){
			user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
		}
		if(user!=null){
			vo.setNickname(user.getNickname());
			vo.setUserIcon(user.getImg());
			vo.setClientCount(getClientCount(note.getId()));//from redis list len
		}else{
			logger.debug("不存在的用户 USER_ID="+uid);
		}
		Long totalReply = noteSubRepository.totalReply(nid);
		vo.setTotalReply(totalReply);
		return vo;
	}

	@Override
	public NoteVo getNoteById(String id) throws Exception {
		Note note = mapperOnCache.selectByPrimaryKey(Note.class, id);
		NoteVo vo = createNoteVo(note);
		//楼主回复数
		NoteSubCriteria noteSubCriteria = new NoteSubCriteria();
		NoteSubCriteria.Criteria criteria = noteSubCriteria.createCriteria();
		criteria.andNoteIdEqualTo(id);
		criteria.andUserIdEqualTo(vo.getUserId());
		int c = noteSubMapper.countByExample(noteSubCriteria);
		vo.setcTotalReply(Long.valueOf(c));
		logger.debug(vo.toString());
		return vo;
	}

}
