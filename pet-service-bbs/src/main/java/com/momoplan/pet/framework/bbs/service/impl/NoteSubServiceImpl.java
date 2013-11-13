package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.NoteSub;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.bbs.NoteSubRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.bbs.service.NoteSubService;
import com.momoplan.pet.framework.bbs.vo.NoteSubVo;
import com.momoplan.pet.framework.bbs.vo.PageBean;

@Service
public class NoteSubServiceImpl implements NoteSubService {
	
	private static Logger logger = LoggerFactory.getLogger(NoteSubServiceImpl.class);

	private NoteSubRepository noteSubRepository = null;
	private MapperOnCache mapperOnCache = null;
	private CommonConfig commonConfig = null;
	private JmsTemplate apprequestTemplate = null;
	public static void main(String[] args) {
		Date d = new Date(1384333352347L);
		System.out.println(d);
	}
	@Autowired
	public NoteSubServiceImpl(NoteSubRepository noteSubRepository,MapperOnCache mapperOnCache, CommonConfig commonConfig, JmsTemplate apprequestTemplate) {
		super();
		this.noteSubRepository = noteSubRepository;
		this.mapperOnCache = mapperOnCache;
		this.commonConfig = commonConfig;
		this.apprequestTemplate = apprequestTemplate;
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
		pushMsg(po,note);
		
		return po.getId();
	}

	private void pushMsg(NoteSub reply,Note note) {
//		回复：
//		msgType=reply
//		contentType=topic/dynamic
//		content=
//			tipic:帖子标题
//			dynamic:动态前10个字
//		contentID=帖子/动态 ID
//		picID=动态图片，帖子无图片
//		body=楼层
		try{
			JSONObject fromUserJson = getUserinfo(reply.getUserId());
			JSONObject toUserJson = getUserinfo(note.getUserId());
			logger.debug("from_user="+fromUserJson.toString());
			logger.debug("to_user="+toUserJson.toString());
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("to", toUserJson.get("username"));
			jsonObj.put("from", fromUserJson.get("username"));
			jsonObj.put("domain", "@"+commonConfig.get(XMPP_DOMAIN));
			jsonObj.put("msgtype", "reply");
			jsonObj.put("msgtime", new Date().getTime());
			jsonObj.put("fromNickname",fromUserJson.get("nickname"));
			jsonObj.put("fromHeadImg", fromUserJson.get("img"));
			jsonObj.put("contentType", "topic");
			jsonObj.put("contentID", note.getId());
			jsonObj.put("content",note.getName());
			jsonObj.put("body", reply.getSeq());
			
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName(PET_PUSH_TO_XMPP);
			
			TextMessage tm = new ActiveMQTextMessage();
			tm.setText(jsonObj.toString());
			apprequestTemplate.convertAndSend(queue, tm);
			logger.debug("send msg="+jsonObj.toString());
			logger.debug("queue_name="+PET_PUSH_TO_XMPP+" ; msg="+jsonObj.toString());
			String pid = reply.getPid();
			if(StringUtils.isNotEmpty(pid)){
				logger.debug("+++++++继续+++++++推送+++++++");
				NoteSub po = mapperOnCache.selectByPrimaryKey(NoteSub.class, pid);
				toUserJson = getUserinfo(po.getUserId());
				jsonObj.put("to", toUserJson.get("username"));
				tm = new ActiveMQTextMessage();
				tm.setText(jsonObj.toString());
				apprequestTemplate.convertAndSend(queue, tm);
				logger.debug("send msg="+jsonObj.toString());
			}
		}catch(Exception e){
			logger.debug("推送消息异常不能中断程序");
			logger.error("send message",e);
		}
	}
	
	protected JSONObject getUserinfo(String userid) throws Exception {
		String path = SERVICE_URI_PET_USER;
		String method = MEDHOD_GET_USERINFO;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		ClientRequest clientRequest = new ClientRequest();
		clientRequest.setMethod(method);
		clientRequest.setParams(params);
		String body = MyGson.getInstance().toJson(clientRequest);
		path = commonConfig.get(path);
		logger.debug("========");
		logger.debug(path);
		logger.debug(body);
		logger.debug("========");
		String responseStr = PostRequest.postText(path, "body",body);
		JSONObject json = new JSONObject(responseStr);
		JSONObject entity = json.getJSONObject("entity");
		return entity;
	}
	
	private PageBean<NoteSub> getNoteSubList(String noteId,String userId,int pageNo, int pageSize){
		List<NoteSub> list = null;
		long totalCount = 0;
		list = noteSubRepository.getReplyListByNoteId(noteId, pageSize, pageNo);
		if(list!=null)
			totalCount = noteSubRepository.totalReply(noteId);
		if(list!=null){
			logger.debug(noteId+" 回复列表大小 list.size="+list.size());
			for(NoteSub ns : list){
				logger.debug("seq="+ns.getSeq());
			}
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
			if(StringUtils.isNotEmpty(uid)){
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);// 在缓存中获取用户
				logger.debug("---------------");
				logger.debug("userId="+uid);
				logger.debug("user="+user);
				logger.debug("noteSub="+noteSub);
				logger.debug("vo="+vo);
				logger.debug("---------------");
				vo.setNickname(user.getNickname());
				vo.setUserIcon(user.getImg());
			}else{
				logger.debug("ERR : 用户ID为空 : note_sub_id="+noteSub.getId());
			}
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
