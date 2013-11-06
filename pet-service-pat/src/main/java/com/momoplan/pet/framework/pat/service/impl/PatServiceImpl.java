package com.momoplan.pet.framework.pat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.pat.PatUserPatRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.pat.service.PatService;
/**
 * @author liangc
 */
@Service
public class PatServiceImpl implements PatService {

	private static Logger logger = LoggerFactory.getLogger(PatServiceImpl.class);
	public static String XMPP_DOMAIN = "xmpp.domain";
	public static String PET_PUSH_TO_XMPP = "pet_push_to_xmpp";
	public static String SERVICE_URI_PET_USER = "service.uri.pet_user";
	public static String MEDHOD_GET_USERINFO = "getUserinfo";

	
	private PatUserPatRepository patUserPatRepository = null;
	private MapperOnCache mapperOnCache = null;
	private CommonConfig commonConfig = null;
	private JmsTemplate apprequestTemplate;
	
	@Autowired
	public PatServiceImpl(PatUserPatRepository patUserPatRepository,
			MapperOnCache mapperOnCache, CommonConfig commonConfig,
			JmsTemplate apprequestTemplate) {
		super();
		this.patUserPatRepository = patUserPatRepository;
		this.mapperOnCache = mapperOnCache;
		this.commonConfig = commonConfig;
		this.apprequestTemplate = apprequestTemplate;
	}

	@Override
	public void addPat(String userid, String srcid,String type) throws Exception {
		PatUserPat po = new PatUserPat();
		po.setId(IDCreater.uuid());
		po.setCt(new Date());
		po.setSrcId(srcid);
		po.setType(type);
		po.setUserid(userid);
		logger.debug("赞:po="+po.toString());
		patUserPatRepository.insertSelective(po);
		
//		赞：
//		msgType=zanDynamic
//		contentType=dynamic
//		content=内容前10个字
//		contentID=动态ID
//		picID=动态图片
//		body=null
		//赞动态要回推一个消息
		if(type!=null&&("赞动态".equals(type)||"dynamic".equalsIgnoreCase(type))){
			StatesUserStates states = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, srcid);//得到这个动态的信息
			try{
				JSONObject fromUserJson = getUserinfo(userid);
				JSONObject toUserJson = getUserinfo(states.getUserid());
				logger.debug("from_user="+fromUserJson.toString());
				logger.debug("to_user="+toUserJson.toString());
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("to", toUserJson.get("username"));
				jsonObj.put("from", fromUserJson.get("username"));
				jsonObj.put("domain", "@"+commonConfig.get(XMPP_DOMAIN));
				jsonObj.put("msgtype", "zanDynamic");
				jsonObj.put("msgtime", new Date().getTime());
				jsonObj.put("fromNickname",fromUserJson.get("nickname"));
				jsonObj.put("fromHeadImg", fromUserJson.get("img"));
				jsonObj.put("contentType", "dynamic");
				jsonObj.put("contentID", states.getId());
				if(states.getImgid()!=null&&!"".equals(states.getImgid())){
					jsonObj.put("picID", states.getImgid());
				}
				jsonObj.put("content",states.getMsg().length()>10?states.getMsg().substring(0, 10):states.getMsg());
				jsonObj.put("body", "");
				
				TextMessage tm = new ActiveMQTextMessage();
				logger.debug("send msg="+jsonObj.toString());
				tm.setText(jsonObj.toString());
				ActiveMQQueue queue = new ActiveMQQueue();
				queue.setPhysicalName(PET_PUSH_TO_XMPP);
				apprequestTemplate.convertAndSend(queue, tm);
				logger.debug("queue_name="+PET_PUSH_TO_XMPP+" ; msg="+jsonObj.toString());
			}catch(Exception e){
				logger.error("send message",e);
				throw e;
			}
		}
	
	}

	@Override
	public void delPat(String userid, String srcid) throws Exception {
		patUserPatRepository.delete(userid,srcid);
	}

	@Override
	public List<SsoUser> getPat(String srcid) throws Exception {
		List<String> list = patUserPatRepository.getPatUserListBySrcId(srcid);
		List<SsoUser> ulist = null;
		if(list!=null&&list.size()>0){
			ulist = new ArrayList<SsoUser>(list.size());
			for(String json : list){
				JSONObject jo = new JSONObject(json);
				String uid = jo.getString("userid");
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
				ulist.add(user);
			}
		}
		return ulist;
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
		String responseStr = PostRequest.postText(path, "body",body);
		JSONObject json = new JSONObject(responseStr);
		JSONObject entity = json.getJSONObject("entity");
		return entity;
	}
}