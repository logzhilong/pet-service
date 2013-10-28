package com.momoplan.pet.framework.servicestate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.pat.mapper.PatUserPatMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesCriteria;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReplyCriteria;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.pat.PatUserPatRepository;
import com.momoplan.pet.commons.repository.states.StatesUserStatesReplyRepository;
import com.momoplan.pet.commons.repository.states.StatesUserStatesRepository;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.servicestate.common.Constants;
import com.momoplan.pet.framework.servicestate.service.StateService;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesReplyVo;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesVo;

@Service
public class StateServiceImpl extends StateServiceSupport implements StateService {
	
	
	@Autowired
	public StateServiceImpl(JmsTemplate apprequestTemplate, CommonConfig commonConfig, StatesUserStatesMapper statesUserStatesMapper, StatesUserStatesReplyMapper statesUserStatesReplyMapper,
			StatesUserStatesAuditMapper statesUserStatesAuditMapper, PatUserPatMapper patUserPatMapper, StatesUserStatesRepository statesUserStatesRepository,
			StatesUserStatesReplyRepository statesUserStatesReplyRepository, MapperOnCache mapperOnCache, PatUserPatRepository patUserPatRepository) {
		super(apprequestTemplate, commonConfig, statesUserStatesMapper, statesUserStatesReplyMapper, statesUserStatesAuditMapper, patUserPatMapper, statesUserStatesRepository,
				statesUserStatesReplyRepository, mapperOnCache, patUserPatRepository);
	}

	private static Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);
	
	@Override
	public String addUserState(ClientRequest clientRequest, SsoAuthenticationToken authenticationToken) throws Exception {
		StatesUserStates userState = new StatesUserStates();
		String userid = authenticationToken.getUserid();
		userState.setId(IDCreater.uuid());
		userState.setUserid(userid);
		userState.setMsg(PetUtil.getParameter(clientRequest, "msg"));
		userState.setImgid(PetUtil.getParameter(clientRequest, "imgid"));
		userState.setCt(new Date());
		userState.setIfTransmitMsg(PetUtil.getParameterBoolean(clientRequest, "ifTransmitMsg"));
		userState.setTransmitUrl(PetUtil.getParameter(clientRequest, "transmitUrl"));
		userState.setTransmitMsg(PetUtil.getParameter(clientRequest, "transmitMsg"));
		userState.setLatitude(0.0);
		userState.setLongitude(0.0);
		userState.setState("3");
		userState.setReportTimes(0);
		// statesUserStatesMapper.insertSelective(userState);
		statesUserStatesRepository.insertSelective(userState);

		// stateResponse.setStateView(this.getStateView(userState,userState.getUserid(),
		// "myself"));
		sendJMS(userState, "user_states");
		return userState.getId();
	}

	@Override
	public void delUserState(String stateid) throws Exception {
		statesUserStatesRepository.delete(stateid);
	}

	@Override
	public void delReply(ClientRequest clientRequest) throws Exception {
		String replyid = PetUtil.getParameter(clientRequest, "replyid");
		statesUserStatesReplyRepository.delete(replyid);
		// return statesUserStatesReplyMapper.deleteByPrimaryKey(replyid);
	}




	private SsoUser getSsoUser(String userid) throws Exception {
		try {
			String path = Constants.SERVICE_URI_PET_USER;
			String method = Constants.MEDHOD_GET_USERINFO;
			Map<String, String> param = new HashMap<String, String>();
			param.put("userid", userid);
			String response = dopost(path, method, param).toString();
			JSONObject json = new JSONObject(response);
			JSONObject entity = json.getJSONObject("entity");
			SsoUser ssoUser = new SsoUser();
			ssoUser.setId(entity.get("id").toString());
			ssoUser.setNickname(entity.getString("nickname"));
			ssoUser.setUsername(entity.getString("username"));

			ssoUser.setImg(entity.getString("img"));

			return ssoUser;
		} catch (Exception e) {
			logger.debug("getSsoUser error :" + e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public int countReply(ClientRequest clientRequest, SsoAuthenticationToken authenticationToken) throws Exception {
		StatesUserStatesReplyCriteria statesUserStatesReplyCriteria = new StatesUserStatesReplyCriteria();
		StatesUserStatesReplyCriteria.Criteria criteria = statesUserStatesReplyCriteria.createCriteria();
		String stateid = PetUtil.getParameter(clientRequest, "stateid");
		String userid = authenticationToken.getUserid();
		List<SsoUser> users = getFriendsList(userid);
		List<String> userids = new ArrayList<String>();
		for (SsoUser user : users) {
			userids.add(user.getId());
		}
		userids.add(userid);
		criteria.andUseridIn(userids);

		criteria.andStateidEqualTo(stateid);
		int count = statesUserStatesReplyMapper.countByExample(statesUserStatesReplyCriteria);
		return count;
	}
	
	/**
	 * 获取好友列表
	 * @param myid
	 * @return
	 * @throws Exception
	 */
	private List<SsoUser> getFriendsList(String myid) throws Exception {
		List<SsoUser> users = new ArrayList<SsoUser>();
		String method = Constants.MEDHOD_GET_FRIENDLIST;
		String path = Constants.SERVICE_URI_PET_USER;
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", myid);
		String responseStr = dopost(path, method, params).toString();
		JSONObject json = new JSONObject(responseStr);
		JSONArray entities = json.getJSONArray("entity");
		for (int i = 0; i < entities.length(); i++) {
			SsoUser user = new SsoUser();
			JSONObject entity = new JSONObject(entities.get(i).toString());
			user.setId(entity.getString("id"));
			user.setUsername(entity.getString("username"));
			user.setNickname(entity.getString("nickname"));
			user.setPhoneNumber(entity.getString("phoneNumber"));
			users.add(user);
		}
		return users;
	}

	@Override
	public List<StatesUserStatesVo> getAllFriendStates(String userid,int pageSize,int pageNo) throws Exception {
		logger.debug("获取全部好友的动态 userid="+userid);
		logger.debug("1、获取好友列表");
		JSONArray jsonArray = getFriendList(userid);
		logger.debug("friend_list="+jsonArray.toString());
		logger.debug("2、根据好友列表获取动态");
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria c1 = statesUserStatesCriteria.createCriteria();
		c1.andStateEqualTo("0");//2013-10-26：经过讨论，这里只显示正常状态的
		Map<String,JSONObject> userMap = new HashMap<String,JSONObject>();//好友+我自己，都在这里
		userMap.put(userid, getUserinfo(userid));//我自己，加入映射表
		if(jsonArray!=null&&jsonArray.length()>0){
			//TODO or 其他人，用 or 代替 in 可以提高效率
			List<String> useridList = new ArrayList<String>();
			for(int i=0;i<jsonArray.length();i++){
				JSONObject jsonObj = jsonArray.getJSONObject(i);
				String uid = jsonObj.getString("id");
				userMap.put(uid, jsonObj);
				useridList.add(uid);
			}
			if(useridList!=null&&useridList.size()>0)
				c1.andUseridIn(useridList);
		}
		statesUserStatesCriteria.or(statesUserStatesCriteria.createCriteria().andUseridEqualTo(userid));//我自己
		//排序
		statesUserStatesCriteria.setOrderByClause("ct desc");
		//分页
		statesUserStatesCriteria.setMysqlOffset(pageNo*pageSize);
		statesUserStatesCriteria.setMysqlLength(pageSize);
		//中间结果
		List<StatesUserStates> list = statesUserStatesMapper.selectByExample(statesUserStatesCriteria);
		//最终结果
		logger.debug(userMap.toString());
		logger.debug("3、将动态中的用户信息补全,其中赞的相关信息为假值");
		List<StatesUserStatesVo> resList = new ArrayList<StatesUserStatesVo>();
		buildStatesUserStatesVoList(list,resList,userMap,userid);
		return resList;
	}

	@Override
	public StatesUserStatesVo findOneState(String userid,String stateid) throws Exception {
		StatesUserStates userStates = new StatesUserStates();
		if (stateid != "") {
			userStates = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, stateid);
		}
		JSONObject userJson = getUserinfo(userStates.getUserid());
		StatesUserStatesVo vo = new StatesUserStatesVo();
		buildStatesUserStatesVoByStates(userStates,vo,userJson,userid);
		logger.debug("单条动态信息："+vo.toString());
		return vo;
	}

	@Override
	public boolean reportContent(ClientRequest clientRequest, SsoAuthenticationToken authenticationToken) throws Exception {
		String stateid = PetUtil.getParameter(clientRequest, "stateid");
		// 举报类型（暂时不用）
		// int reporttype =
		// PetUtil.getParameterInteger(clientRequest,"reporttype");
		StatesUserStates userState = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, stateid);
		if (null == userState) {
			return false;
		}
		// 举报次数少于20的情况下
		int reportTimes = userState.getReportTimes();
		if (reportTimes < 20) {
			userState.setReportTimes(reportTimes + 1);
		} else {
			userState.setState("5");
		}
		// StatesUserStatesCriteria statesUserStatesCriteria = new
		// StatesUserStatesCriteria();
		// StatesUserStatesCriteria.Criteria criteria =
		// statesUserStatesCriteria.createCriteria();
		mapperOnCache.updateByPrimaryKeySelective(userState, stateid);
		// userState.merge();
		return true;
	}

	@Override
	public List<StatesUserStatesVo> getUserStates(String userid, int pageSize, int pageNo, boolean isSelf) throws Exception {
		List<StatesUserStatesVo> resList = new ArrayList<StatesUserStatesVo>();
		List<StatesUserStates> list = null;
		if(isSelf){
			logger.debug("取自己的动态，不区分状态");
			list = statesUserStatesRepository.getStatesUserStatesListByUserid(userid, pageSize, pageNo);
			logger.debug("结果集大小 list.size="+list.size());
		}else{
			logger.debug("取好友的动态，要区分状态");
			list = statesUserStatesRepository.getStatesUserStatesListByUserid(userid, Integer.MAX_VALUE, 0);
			logger.debug("结果集大小 list.size="+list.size());
			List<StatesUserStates> list2 = new ArrayList<StatesUserStates>();
			//TODO 目前都取出来，在本地分页吧，数据多时，需要创建缓冲区
			for(StatesUserStates states : list){
				String state = states.getState();
				if("0".equals(state)){//非自己的动态，要区分状态
					list2.add(states);
				}
			}
			if(list2!=null&&list2.size()>0){
				int start = pageNo*pageSize>list2.size()?list2.size():pageNo*pageSize;
				int end = pageSize*(pageNo+1)>list2.size()?list2.size():pageSize*(pageNo+1);
				list2.subList(start, end);
			}
			list.clear();
			list.addAll(list2);
		}
		JSONObject userJson = getUserinfo(userid);
		Map<String,JSONObject> userMap = new HashMap<String,JSONObject>();
		userMap.put(userid, userJson);
		buildStatesUserStatesVoList(list,resList,userMap,userid);
		return resList;
	}
	
	/**
	 * 添加一个回复
	 */
	public String addReply(StatesUserStatesReply reply) throws Exception {
		reply.setId(IDCreater.uuid());
		reply.setCt(new Date());
		statesUserStatesReplyRepository.insertSelective(reply);
		String puserid = reply.getPuserid();
		if(StringUtils.isEmpty(puserid)){
			StatesUserStates states = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, reply.getStateid());
			puserid = states.getUserid();
			logger.debug("回复动态：puserid="+puserid);
		}else{
			logger.debug("回复回复：puserid="+puserid);
		}
		try{
			JSONObject fromUserJson = getUserinfo(reply.getUserid());
			JSONObject toUserJson = getUserinfo(puserid);
			logger.debug("from_user="+fromUserJson.toString());
			logger.debug("to_user="+toUserJson.toString());
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("to", toUserJson.get("username"));
			jsonObj.put("from", fromUserJson.get("username"));
			jsonObj.put("domain", "@"+commonConfig.get(Constants.XMPP_DOMAIN));
			jsonObj.put("msgtype", "reply");
			jsonObj.put("msgtime", reply.getCt().getTime());
			jsonObj.put("fromNickname",fromUserJson.get("nickname"));
			jsonObj.put("fromHeadImg", fromUserJson.get("img"));
			jsonObj.put("body", reply.getMsg());
			TextMessage tm = new ActiveMQTextMessage();
			tm.setText(jsonObj.toString());
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName(Constants.PET_PUSH_TO_XMPP);
			apprequestTemplate.convertAndSend(queue, tm);
			logger.debug("queue_name="+Constants.PET_PUSH_TO_XMPP+" ; msg="+jsonObj.toString());
		}catch(Exception e){
			logger.error("send message",e);
		}
		return reply.getId();
	}
	
	private List<String> getUseridListByFriendList(JSONArray farr){
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
	
	/**
	 * 获取回复列表
	 */
	@Override
	public List<StatesUserStatesReplyVo> getReplyByStateid(String userid,String stateid, int pageSize, int pageNo) throws Exception {
		logger.debug("//1 找到共同的好友");
		StatesUserStates states = mapperOnCache.selectByPrimaryKey(StatesUserStates.class, stateid);
		String statesUserid = states.getUserid();//发动态的人
		JSONArray f1 = getFriendList(userid);
		JSONArray f2 = getFriendList(statesUserid);
		if(f1==null||f2==null)//如果俩人都没有好友，那么根本就不存在共同好友了
			return null;
		List<String> fl1 = getUseridListByFriendList(f1);
		List<String> fl2 = getUseridListByFriendList(f2);
		logger.debug("集合1:"+MyGson.getInstance().toJson(fl1));
		logger.debug("集合2:"+MyGson.getInstance().toJson(fl2));
		fl1.retainAll(fl2);
		logger.debug("交集:"+MyGson.getInstance().toJson(fl1));
		if(fl1==null||fl1.size()<1)//没有交集，就是没有共同好友，则直接返回
			return null;
		Map<String,String> retainMap = new HashMap<String,String>(); 
		for(String f : fl1){
			retainMap.put(f, "0");
		}
		
		List<StatesUserStatesReply> all = statesUserStatesReplyRepository.getStatesUserStatesReplyListByStatesId(stateid, Integer.MAX_VALUE, 0);
		logger.debug("//2 全部回复");

		logger.debug("//3 过滤出共同好友的回复");
		List<StatesUserStatesReplyVo> voList = new ArrayList<StatesUserStatesReplyVo>();
		if(all!=null&&all.size()>0){
			logger.debug("all size is : "+all.size());
			for(StatesUserStatesReply reply : all){
				String uid = reply.getUserid();
				logger.debug(uid+" ; "+retainMap.get(uid)); 
				if(retainMap.get(uid)!=null){
					StatesUserStatesReplyVo vo = new StatesUserStatesReplyVo();
					BeanUtils.copyProperties(reply, vo);
					JSONObject userJson = getUserinfo(uid);
					vo.setUsername(get(userJson,"username"));
					vo.setNickname(get(userJson,"nickname"));
					vo.setAlias(get(userJson,"alias"));
					vo.setUserImage(get(userJson,"img"));
					voList.add(vo);
				}
			}
		}
		
		if(voList!=null&&voList.size()>0){
			int start = pageNo*pageSize>voList.size()?voList.size():pageNo*pageSize;
			int end = pageSize*(pageNo+1)>voList.size()?voList.size():pageSize*(pageNo+1);
			logger.debug("//分页 start="+start+" ; end="+end);
			voList.subList(start, end);
			return voList;
		}
		
		return null;
	}

}
