package com.momoplan.pet.framework.servicestate.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.jms.core.JmsTemplate;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.pat.mapper.PatUserPatMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.pat.PatUserPatRepository;
import com.momoplan.pet.commons.repository.states.StatesUserStatesReplyRepository;
import com.momoplan.pet.commons.repository.states.StatesUserStatesRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.servicestate.common.Constants;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesVo;

public class StateServiceSupport {
	
	private static Logger logger = LoggerFactory.getLogger(StateServiceSupport.class);

	protected JmsTemplate apprequestTemplate;
	protected CommonConfig commonConfig = null;
	protected StatesUserStatesMapper statesUserStatesMapper = null;
	protected StatesUserStatesReplyMapper statesUserStatesReplyMapper = null;
	protected StatesUserStatesAuditMapper statesUserStatesAuditMapper = null;
	protected PatUserPatMapper patUserPatMapper = null;
	protected StatesUserStatesRepository statesUserStatesRepository = null;
	protected StatesUserStatesReplyRepository statesUserStatesReplyRepository = null;
	protected MapperOnCache mapperOnCache = null;
	protected PatUserPatRepository patUserPatRepository = null;
	
	public StateServiceSupport(JmsTemplate apprequestTemplate, CommonConfig commonConfig, StatesUserStatesMapper statesUserStatesMapper, StatesUserStatesReplyMapper statesUserStatesReplyMapper,
			StatesUserStatesAuditMapper statesUserStatesAuditMapper, PatUserPatMapper patUserPatMapper, StatesUserStatesRepository statesUserStatesRepository,
			StatesUserStatesReplyRepository statesUserStatesReplyRepository, MapperOnCache mapperOnCache, PatUserPatRepository patUserPatRepository) {
		super();
		this.apprequestTemplate = apprequestTemplate;
		this.commonConfig = commonConfig;
		this.statesUserStatesMapper = statesUserStatesMapper;
		this.statesUserStatesReplyMapper = statesUserStatesReplyMapper;
		this.statesUserStatesAuditMapper = statesUserStatesAuditMapper;
		this.patUserPatMapper = patUserPatMapper;
		this.statesUserStatesRepository = statesUserStatesRepository;
		this.statesUserStatesReplyRepository = statesUserStatesReplyRepository;
		this.mapperOnCache = mapperOnCache;
		this.patUserPatRepository = patUserPatRepository;
	}

	/**
	 * 抽取出好友集合中的用户ID集合
	 * @param farr
	 * @return
	 */
	protected List<String> getUseridListByFriendList(JSONArray farr){
		try{
			List<String> uidList = new ArrayList<String>();
			for(int i=0;i<farr.length();i++){
				JSONObject obj = farr.getJSONObject(i);
				uidList.add(obj.getString("id"));
			}
			return uidList;
		}catch(Exception e){
			logger.error("getUseridListByFriendList",e);
		}
		return null;
	}
	
	protected JSONArray getFriendList(String userid) throws Exception {
		try{
			String method = Constants.MEDHOD_GET_FRIENDLIST;
			String path = Constants.SERVICE_URI_PET_USER;
			Map<String, String> params = new HashMap<String, String>();
			params.put("userid", userid);
			String responseStr = dopost(path, method, params).toString();
			JSONObject json = new JSONObject(responseStr);
			JSONArray entities = json.getJSONArray("entity");
			return entities;
		}catch(Exception e){
			logger.error("getFriendList",e);
		}
		return null;
	}
	
	protected String get(JSONObject json,String key){
		try{
			return json.getString(key);
		}catch(Exception e){
			logger.debug(e.getMessage());
		}
		return null;
	}
	
	protected JSONObject getUserinfo(String userid) throws Exception {
		String path = Constants.SERVICE_URI_PET_USER;
		String method = Constants.MEDHOD_GET_USERINFO;
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userid);
		String responseStr = dopost(path, method, params).toString();
		JSONObject json = new JSONObject(responseStr);
		JSONObject entity = json.getJSONObject("entity");
		return entity;
	}
	
	
	/**
	 * 根据method发送不同的post请求获取数据,只处理字符串类型的参数
	 * 
	 * @param method
	 * @param param
	 * @throws Exception
	 */
	protected Object dopost(String path, String method, Map<String, String> params) throws Exception {
		StringBuffer req = new StringBuffer();
		// {"method":"","params":{key:value}}
		Iterator<String> iter = params.keySet().iterator();
		req.append("{\"method\":\"");
		req.append(method + "\",\"params\":{");
		while (iter.hasNext()) {
			String mKey = iter.next();
			req.append("\"" + mKey + "\":");
			req.append("\"" + params.get(mKey) + "\",");
		}
		req.deleteCharAt(req.length() - 1);
		req.append("}}");
		String service_uri = commonConfig.get(path, null);
		logger.debug("requestBody : " + req.toString());
		logger.debug("service="+path+" ; service_uri=" + service_uri);
		return PostRequest.postText(service_uri, "body", req.toString());
	}
	
	/**
	 * 构建动态信息
	 * @param userStates
	 * @param vo
	 * @param userJson
	 * @param myUserid
	 * @throws Exception
	 */
	protected void buildStatesUserStatesVoByStates(StatesUserStates userStates,StatesUserStatesVo vo,JSONObject userJson,String myUserid) throws Exception{
		BeanUtils.copyProperties(userStates, vo);
		vo.setUsername(get(userJson,"username"));
		vo.setNickname(get(userJson,"nickname"));
		vo.setAlias(get(userJson,"alias"));
		vo.setUserImage(get(userJson,"img"));
		//赞信息 >>>>>>>>>>
		String srcid = userStates.getId();
		int totalPat = patUserPatRepository.getTotalPatBySrcId(srcid);
		boolean didIpat = patUserPatRepository.didIpat(myUserid, srcid);
		vo.setTotalPat(totalPat+"");
		vo.setDidIpat(didIpat);
	}
	/**
	 * 构建用户动态列表
	 * @param list po 集合
	 * @param resList 最终结果，包含用户信息以及 赞 信息
	 * @param userMap 用户信息映射
	 * @param userid 用户ID
	 * @throws Exception
	 */
	protected void buildStatesUserStatesVoList(List<StatesUserStates> list,List<StatesUserStatesVo> resList,Map<String,JSONObject> userMap,String userid) throws Exception{
		for(StatesUserStates states : list){
			StatesUserStatesVo vo = new StatesUserStatesVo();
			JSONObject userJson = userMap.get(states.getUserid());
			buildStatesUserStatesVoByStates(states,vo,userJson,userid);
			logger.debug("动态:"+vo.toString());
			resList.add(vo);
		}
	}
	
	/**
	 * 向校验内容的应用send消息
	 * 
	 * @param userState
	 * @param biz
	 */
	protected void sendJMS(StatesUserStates userState, String biz) {
		TextMessage tm = new ActiveMQTextMessage();
		logger.debug("\nbid:" + userState.getId().toString());
		logger.debug("\nmsg:" + userState.getMsg());
		try {
			tm.setStringProperty("biz", biz);
			tm.setStringProperty("bid", userState.getId().toString());
			tm.setStringProperty("content", userState.getMsg());
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName("queue/pet_wordfilter");
			apprequestTemplate.convertAndSend(queue, tm);
		} catch (JMSException e) {
			logger.debug("sendJMS error :" + e);
			e.printStackTrace();
		}
	}
}
