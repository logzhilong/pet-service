package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.momoplan.pet.commons.NumberUtils;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumAssortRelMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.NoteSubMapper;
import com.momoplan.pet.commons.domain.bbs.po.Assort;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRel;
import com.momoplan.pet.commons.domain.bbs.po.ForumAssortRelCriteria;
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
	
	private ForumAssortRelMapper forumAssortRelMapper = null;
	
	@Autowired
	public NoteServiceImpl(NoteMapper noteMapper, NoteSubMapper noteSubMapper,
			NoteRepository noteRepository, NoteSubRepository noteSubRepository,
			MapperOnCache mapperOnCache, JmsTemplate apprequestTemplate,
			ForumAssortRelMapper forumAssortRelMapper) {
		super();
		this.noteMapper = noteMapper;
		this.noteSubMapper = noteSubMapper;
		this.noteRepository = noteRepository;
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
		this.apprequestTemplate = apprequestTemplate;
		this.forumAssortRelMapper = forumAssortRelMapper;
	}

	private static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
	/**
	 * 发帖子
	 * @param ClientRequest
	 * @return
	 */
	@Override
	public String sendNote(Note po) throws Exception {
		ForumAssortRelCriteria forumAssortRelCriteria = new ForumAssortRelCriteria();
		if(StringUtils.isNotEmpty(po.getForumId())){
			String forumId = po.getForumId();
			forumAssortRelCriteria.createCriteria().andForumIdEqualTo(forumId);
			List<ForumAssortRel> farl = forumAssortRelMapper.selectByExample(forumAssortRelCriteria);
			if(farl!=null && farl.size()>0){
				ForumAssortRel far = farl.get(0);
				po.setAssortId(far.getAssortId());
			}
		}else{
			String assortId = po.getAssortId();
			forumAssortRelCriteria.createCriteria().andAssortIdEqualTo(assortId);
			List<ForumAssortRel> farl = forumAssortRelMapper.selectByExample(forumAssortRelCriteria);
			if(farl!=null && farl.size()>0){
				ForumAssortRel far = farl.get(0);
				po.setForumId(far.getForumId());
			}
		}
		Date now = new Date();
		po.setId(IDCreater.uuid());
		po.setClientCount(1L);
		po.setCt(now);
		po.setEt(now);
		po.setRt(DateUtils.getDate("2013-01-01"));//default
		po.setIsDel(false);
		po.setIsEute(false);
		po.setIsTop(false);
		if(po.getContent().contains("<img")){
			logger.debug("包含图片的帖子");
			po.setType("img");
		}else{
			logger.debug("不包含图片的帖子");
			po.setType("text");
		}
		logger.debug("发帖子 ：" + po.toString());
		noteRepository.insertOrUpdateSelective(po,NoteState.AUDIT);
		noteRepository.updateClickCount(po.getId(), (long) NumberUtils.random(2000, 7000));
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
		if(NoteState.AUDIT.getCode().equals(po.getState())){
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
	}
	
	/**
	 * 更新帖子点击数
	 */
	@Override
	public void updateClickCount(String noteId) throws Exception {
		noteRepository.updateClickCount(noteId, (long) NumberUtils.random(2000, 8000));
	}
	/**
	 * 获取点击次数
	 */
	@Override
	public Long getClientCount(String noteId) throws Exception {
		return noteRepository.getClickCount(noteId, (long) NumberUtils.random(2000, 8000));
	}
	/**
	 * 获取帖子列表
	 */
	@Override
	public List<NoteVo> getNoteList(String userid,String assortId,String forumid,Action action,String condition,ConditionType conditionType,String conditionScope ,boolean withTop,int pageNo,int pageSize) throws Exception {
		NoteCriteria noteCriteria = new NoteCriteria();
		noteCriteria.setMysqlOffset(pageNo * pageSize);
		noteCriteria.setMysqlLength(pageSize);
		
		NoteCriteria.Criteria criteria = noteCriteria.createCriteria();
		if (StringUtils.isNotEmpty(forumid)&&!"0".equals(forumid)) {
			criteria.andForumIdEqualTo(forumid);
		}

		//assortId 只取某个分类的
		logger.debug("assortId="+assortId);
		if(StringUtils.isNotEmpty(assortId) && !"all".equalsIgnoreCase(assortId)){
			criteria.andAssortIdEqualTo(assortId);
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
				} else if(StringUtils.isEmpty(assortId)||"0".equals(assortId)){
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
		
		List<String> stateList = new ArrayList<String>();
//		stateList.add(NoteState.REJECT.getCode());//审核拒绝
//		stateList.add(NoteState.AUDIT.getCode());//审核中
//		stateList.add(NoteState.REPORT.getCode());//被举报
//		stateList.add(NoteState.DELETE.getCode());//被删除
		//以上状态不显示
		//criteria.andStateNotIn(stateList);
		
		stateList.add(NoteState.ACTIVE.getCode());//正常
		stateList.add(NoteState.PASS.getCode());//通过
		//只显示这几个状态
		criteria.andStateIn(stateList);
		
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
		logger.debug("// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树");
		List<NoteVo> noteVoList = new ArrayList<NoteVo>(notelist.size());
		buildNoteVoList(list,noteVoList);
		return noteVoList;
	}
	
	// add by liangc 131018 : 增加 发帖人昵称、发帖人头像、帖子回复树
	private void buildNoteVoList(List<Note> notelist,List<NoteVo> noteVoList) throws Exception {
		for (Note note : notelist) {
			NoteVo vo = createNoteVo(note);
			//logger.debug("//add by liangc 131206 : 校验返回的数据中，是不是包含图片呢，type=0的，都得判断一下，然后更新结果");
			updateNoteTypeForOldData(vo);
			noteVoList.add(vo);
		}
	}
	
	private void updateNoteTypeForOldData(NoteVo vo) throws Exception{
		String t = vo.getType();
		if(StringUtils.isEmpty(t)||"0".equals(t)){
			Note p = mapperOnCache.selectByPrimaryKey(Note.class, vo.getId());
			String c = p.getContent();
			if(c!=null&&c.contains("<img")){
				vo.setType("img");
			}else{
				vo.setType("text");
			}
			p.setType(vo.getType());
			mapperOnCache.updateByPrimaryKeySelective(p, vo.getId());
			logger.debug("NOTE_ID="+vo.getId()+" 需要更新 TYPE="+vo.getType());
		}
	}
	private List<String> getImagesInText(String content){
		List<String> images = new ArrayList<String>();
		Matcher matcher = Pattern.compile("(<img.*?src=\")(.+?)(\".*?[/>|>])").matcher(content);
		while(matcher.find()){
			String s2 = matcher.group(2);
			String[] tt = s2.split("/get/");
			if(tt!=null && tt.length>1){
				String imageId = tt[1];
				images.add(imageId);
			}
			if(images.size()>=3)
				return images;
		}
		return images;
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
		if(StringUtils.isNotEmpty(note.getAssortId())){
			//140226 : 如果有类型，就得把分类名称返回去
			Assort assort = mapperOnCache.selectByPrimaryKey(Assort.class, note.getAssortId());
			vo.setAssortName(assort.getName());
		}
		Long totalReply = noteSubRepository.totalReply(nid);
		vo.setTotalReply(totalReply);
		note.getForumId();
		Forum forum = mapperOnCache.selectByPrimaryKey(Forum.class, note.getForumId());
		logger.debug("131210:圈子名称:::"+forum.getName());
		vo.setForumName(forum.getName());
		Note n = mapperOnCache.selectByPrimaryKey(Note.class, note.getId());
		List<String> images = getImagesInText(n.getContent());
		logger.debug("140220:截取帖子图片:::> images.size="+images.size());
		vo.setImages(images);
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
