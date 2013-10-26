package com.momoplan.pet.framework.servicestate.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.pat.mapper.PatUserPatMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.repository.pat.PatUserPatRepository;
import com.momoplan.pet.commons.repository.states.StatesUserStatesReplyRepository;
import com.momoplan.pet.commons.repository.states.StatesUserStatesRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.servicestate.common.Constants;

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

	protected JSONArray getFriendList(String userid) throws Exception {
		String method = Constants.MEDHOD_GET_FRIENDLIST;
		String path = Constants.SERVICE_URI_PET_USER;
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userid);
		String responseStr = dopost(path, method, params).toString();
		JSONObject json = new JSONObject(responseStr);
		JSONArray entities = json.getJSONArray("entity");
		return entities;
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
}
