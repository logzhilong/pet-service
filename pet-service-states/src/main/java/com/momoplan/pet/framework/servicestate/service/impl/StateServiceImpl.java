package com.momoplan.pet.framework.servicestate.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.pat.mapper.PatUserPatMapper;
import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
import com.momoplan.pet.commons.domain.pat.po.PatUserPatCriteria;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesCriteria;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReplyCriteria;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.http.PostRequest;
import com.momoplan.pet.commons.spring.CommonConfig;
import com.momoplan.pet.framework.servicestate.common.Constants;
import com.momoplan.pet.framework.servicestate.common.XMPPRequest;
import com.momoplan.pet.framework.servicestate.service.StateService;
import com.momoplan.pet.framework.servicestate.vo.PetUserView;
import com.momoplan.pet.framework.servicestate.vo.ReplyView;
import com.momoplan.pet.framework.servicestate.vo.StateResponse;
import com.momoplan.pet.framework.servicestate.vo.StateView;

@Service
public class StateServiceImpl implements StateService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private JmsTemplate apprequestTemplate;
	@Resource
	CommonConfig commonConfig = null;
	
	@Autowired
	StatesUserStatesMapper statesUserStatesMapper =null;
	@Autowired
	StatesUserStatesReplyMapper statesUserStatesReplyMapper = null;
	@Autowired
	StatesUserStatesAuditMapper statesUserStatesAuditMapper = null;
	@Autowired
	PatUserPatMapper patUserPatMapper = null;
	
	public StateResponse addReply(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception {
		StateResponse stateResponse = new StateResponse();
		StatesUserStatesReply reply = new StatesUserStatesReply();
		String userid = authenticationToken.getUserid();
		reply.setId(IDCreater.uuid());
		reply.setCt(new Date());
		reply.setMsg(PetUtil.getParameter(clientRequest, "msg"));
		reply.setPid(PetUtil.getParameter(clientRequest, "pid"));//可以为空
		reply.setPuserid(PetUtil.getParameter(clientRequest, "puserid"));//可以为空
		reply.setUserid(userid);
		reply.setStateid(PetUtil.getParameter(clientRequest, "stateid"));
		statesUserStatesReplyMapper.insertSelective(reply);
		stateResponse.setReplyView(getReplyView(reply,reply.getUserid()));
		try {
			XMPPRequest xr = new XMPPRequest();
			SsoUser sendUser = getSsoUser(reply.getUserid());
			String stateUserid = PetUtil.getParameter(clientRequest, "stateUserid");//获取接受用户的id
			SsoUser toUser = getSsoUser(stateUserid);
			xr.setSendUser(sendUser.getUsername());
			xr.setReceiveUser(toUser.getUsername());
			xr.setType("reply");
			xr.setPrams(reply.getStateid());
			xr.setWords(reply.getMsg());
			xr.setMsgTime(reply.getCt());
			xr.setRegion("@"+commonConfig.get(Constants.XMPP_DOMAIN, "hadoop7.ruyicai.com"));
			xr.setFromHeadImg(sendUser.getImg());
			xr.setFromNickname(sendUser.getNickname());
			xr.setXmpppath(commonConfig.get(Constants.XMPP_SERVER, "http://192.168.99.53:5280/rest"));
			logger.debug(new Gson().toJson(xr));
			xr.SendMessage();
		} catch (Exception e) {
			logger.debug("xmpp send error...");
		}finally{
			return stateResponse;
		}
	}
	
	@Override
	public StateResponse addUserState(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception {
		StateResponse stateResponse = new StateResponse();
		StatesUserStates userState = new StatesUserStates();
		String userid = authenticationToken.getUserid();
		userState.setId(IDCreater.uuid());
		userState.setUserid(userid);
		userState.setMsg(PetUtil.getParameter(clientRequest, "msg"));
		userState.setImgid(PetUtil.getParameter(clientRequest, "imgid"));
		userState.setCt(new Date());
		userState.setIfTransmitMsg(PetUtil.getParameterBoolean(clientRequest,"ifTransmitMsg"));
		userState.setTransmitUrl(PetUtil.getParameter(clientRequest,"transmitUrl"));
		userState.setTransmitMsg(PetUtil.getParameter(clientRequest,"transmitMsg"));
		userState.setLatitude(0.0);
		userState.setLongitude(0.0);
		userState.setState("3");
		userState.setReportTimes(0);
		statesUserStatesMapper.insertSelective(userState);
		
		stateResponse.setStateView(this.getStateView(userState,userState.getUserid(), "myself"));
		sendJMS(userState,"user_states");
		return stateResponse;
	}
	
	@Override
	public int delUserState(ClientRequest clientRequest) throws Exception {
		String stateid = PetUtil.getParameter(clientRequest, "stateid");
		return statesUserStatesMapper.deleteByPrimaryKey(stateid);
	}
	
	@Override
	public int delReply(ClientRequest clientRequest) throws Exception {
		String replyid = PetUtil.getParameter(clientRequest, "replyid");
		return statesUserStatesReplyMapper.deleteByPrimaryKey(replyid);
	}
	
	/**
	 * 向校验内容的应用send消息
	 * @param userState
	 * @param biz
	 */
	private void sendJMS(StatesUserStates userState,String biz) {
		TextMessage tm = new ActiveMQTextMessage();
		System.out.println("\nbid:"+userState.getId().toString());
		System.out.println("\nmsg:"+userState.getMsg());
		try {
			tm.setStringProperty("biz", biz);
			tm.setStringProperty("bid", userState.getId().toString());
			tm.setStringProperty("content", userState.getMsg());
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName("queue/pet_wordfilter");
			apprequestTemplate.convertAndSend(queue, tm);
		} catch (JMSException e) {
			logger.debug("sendJMS error :"+e);
			e.printStackTrace();
		} finally{
			return;
		}
	}
	
	/**
	 * 重载此方法，根据用户状态表获取用户状态视图（时间排序）
	 * 
	 * @param userStates
	 * @param userid
	 * @return
	 * @throws Exception 
	 */
	private StateView getStateView(StatesUserStates userState, String userid, String whos) throws Exception {
		if (null == userState) {
			return null;
		}
		// 封装到状态视图中
		StateView stateView = new StateView();
		stateView.setId(userState.getId());
		stateView.setCt(userState.getCt());
		stateView.setMsg(userState.getMsg());
		stateView.setIfTransmitMsg(userState.getIfTransmitMsg());
		stateView.setTransmitMsg(userState.getTransmitMsg());
		stateView.setTransmitUrl(userState.getTransmitUrl());
		stateView.setImgid(userState.getImgid());
		stateView.setState(userState.getState());
		stateView.setPatUserPat(getPatUserPat(userState,null));
		stateView.setCountZan(countZan(userState));
		stateView.setIfIZaned(ifIZaned(userState,userid));
		stateView.setPetUserView(getPetUserView(userState,userid));
		return stateView;
	}

	private ReplyView getReplyView(StatesUserStatesReply reply, String userid) throws Exception {
		ReplyView replyView = new ReplyView();
		replyView.setCt(reply.getCt());
		replyView.setId(reply.getId());
		replyView.setPetUserView(getPetUserView(reply,userid));
		replyView.setMsg(reply.getMsg());
		replyView.setPid(reply.getPid());
		replyView.setPuserid(reply.getPuserid());
		replyView.setUserid(reply.getUserid());
		replyView.setStateid(reply.getStateid());
		return replyView;
	}
	
	/**
	 * 回复用
	 * @param reply
	 * @param userid
	 * @return
	 * @throws Exception 
	 */
	private PetUserView getPetUserView(StatesUserStatesReply reply,String userid) throws Exception {
//		SsoUser user = new SsoUser();
		SsoUser user = getSsoUser(reply.getUserid());//根据动态的userid获取发送这条动态的用户信息
		PetUserView petUserView = new PetUserView();
		petUserView.setImg(user.getImg());
		petUserView.setNickname(user.getNickname());
		petUserView.setAliasname(getAliasname(userid,reply.getUserid()));
		petUserView.setUserid(user.getId());
		return petUserView;
	}

	/**
	 * 动态用
	 * @param userState
	 * @param userid
	 * @return
	 * @throws Exception 
	 */
	private PetUserView getPetUserView(StatesUserStates userState, String userid) throws Exception {
//		SsoUser user = new SsoUser();
		SsoUser user = getSsoUser(userState.getUserid());//根据动态的userid获取发送这条动态的用户信息
		PetUserView petUserView = new PetUserView();
		petUserView.setImg(user.getImg());
		petUserView.setNickname(user.getNickname());
		petUserView.setAliasname(getAliasname(userid,userState.getUserid()));
		petUserView.setUserid(user.getId());
		return petUserView;
	}

	private SsoUser getSsoUser(String userid) throws Exception {
		try {
			String path = Constants.SERVICE_URI_PET_USER;
			String method = Constants.MEDHOD_GET_USERINFO;
			Map<String,String> param = new HashMap<String,String>();
			param.put("userid", userid);
			String response = dopost(path,method,param).toString();
			JSONObject json = new JSONObject(response);
			JSONObject entity = json.getJSONObject("entity");
			SsoUser ssoUser = new SsoUser();
			ssoUser.setId(entity.get("id").toString());
			ssoUser.setNickname(entity.getString("nickname"));
			ssoUser.setUsername(entity.getString("username"));
			
			ssoUser.setImg(entity.getString("img"));
			
			return ssoUser;
		} catch (Exception e) {
			logger.debug("getSsoUser error :"+e);
			e.printStackTrace();
			throw e;
		}
	}



	private String getAliasname(String myid, String friendid) throws Exception {
		if(myid.compareTo(friendid)==0){
			return "";
		}
		List<PetUserView> userViews = new ArrayList<PetUserView>();
		String method = Constants.MEDHOD_GET_FRIENDLIST;
		String path = Constants.SERVICE_URI_PET_USER;
		Map<String,String> params = new HashMap<String,String>();
		params.put("userid", myid);
		String responseStr = dopost(path,method,params).toString();
		JSONObject json = new JSONObject(responseStr);
		JSONArray entities = json.getJSONArray("entity");
		for (int i = 0; i < entities.length(); i++) {
			JSONObject entity = new JSONObject(entities.get(i).toString());
			if(entity.getString("id").compareTo(friendid)==0){
				PetUserView userView = new PetUserView();
				try {
					userView.setUserid(entity.getString("username"));
					userView.setNickname(entity.getString("nickname"));
					userView.setImg(entity.getString("img"));
					userView.setAliasname(entity.getString("alias"));
				} catch (Exception e) {
					logger.debug("one of the element doesnot found ...");
					logger.debug(entity.toString());
				}
				userViews.add(userView);
			}
		}
		if(userViews.size()>0){
			return userViews.get(0).getAliasname();
		}
		return "";
	}

//	private String getDistance(StatesUserStates userState, String userid) {
//		return null;
//	}

	/**
	 * 判断是否赞过这条动态
	 * @param userState
	 * @param userid
	 * @return
	 */
	private Boolean ifIZaned(StatesUserStates userState, String userid) {
		boolean ifIZaned = false;
		List<PatUserPat> patUserPats = getPatUserPat(userState,userid);
		for (PatUserPat patUserPat : patUserPats) {
			if(patUserPat.getType().contains("states")&&patUserPat.getSrcId().compareTo(userState.getId())==0){
				ifIZaned = true;
			}
		}
		return ifIZaned;
	}

	/**
	 * 获取一个用户所有的动态赞
	 * @param userState
	 * @param userid
	 * @return
	 */
	private List<PatUserPat> getPatUserPat(StatesUserStates userState, String userid) {
		PatUserPatCriteria patUserPatCriteria = new PatUserPatCriteria();
		PatUserPatCriteria.Criteria criteria = patUserPatCriteria.createCriteria();
		criteria.andTypeEqualTo("state");
		criteria.andSrcIdEqualTo(userState.getId());
		if(StringUtils.isNotEmpty(userid)){
			criteria.andUseridEqualTo(userid);
		}
		return patUserPatMapper.selectByExample(patUserPatCriteria);
//		return null;
	}

	private int countZan(StatesUserStates userState) {
		return this.getPatUserPat(userState, null).size(); 
	}
	
//	public static void main(String[] args) {
//		StringBuffer req = new StringBuffer();
//		String method = "getUserinfo";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("p1", "value");
//		params.put("p2", 2);
//		params.put("p3", true);
//		Iterator<String> iter = params.keySet().iterator();
//		req.append("{\"method\":\"");
//		req.append(method+"\",\"params\":{");
//		while(iter.hasNext()){
//			String mKey = iter.next();
//			req.append("\""+mKey+"\":");
//			req.append("\""+params.get(mKey)+"\",");
//		}
//		req.deleteCharAt(req.length()-1);
//		req.append("}");
//		System.out.println(req.toString());
//	}
	
	
	/**
	 * 根据method发送不同的post请求获取数据,只处理字符串类型的参数
	 * @param method
	 * @param param
	 * @throws Exception 
	 */
	private Object dopost(String path,String method, Map<String, String> params) throws Exception {
		StringBuffer req = new StringBuffer();
		//{"method":"","params":{key:value}}
		Iterator<String> iter = params.keySet().iterator();
		req.append("{\"method\":\"");
		req.append(method+"\",\"params\":{");
		while(iter.hasNext()){
			String mKey = iter.next();
			req.append("\""+mKey+"\":");
			req.append("\""+params.get(mKey)+"\",");
		}
		req.deleteCharAt(req.length()-1);
		req.append("}}");
		logger.debug("requestBody : "+req.toString());
		return PostRequest.postText(commonConfig.get(path,null), "body",req.toString());
	}

	@Override
	public int countReply(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception {
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
	
	public List<SsoUser> getFriendsList(String myid) throws Exception{
		List<SsoUser> users = new ArrayList<SsoUser>();
		String method = Constants.MEDHOD_GET_FRIENDLIST;
		String path = Constants.SERVICE_URI_PET_USER;
		Map<String,String> params = new HashMap<String,String>();
		params.put("userid", myid);
		String responseStr = dopost(path,method,params).toString();
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
	
//	public static void main(String[] args) {
//		String str = "{\"success\":true,\"entity\":[{\"id\":\"747\",\"alias\":\"别名\",\"nickname\":\"cc\",\"username\":\"cc\",\"phoneNumber\":\"\",\"deviceToken\":\"\"},{\"id\":\"747\",\"alias\":\"别名\",\"nickname\":\"cc\",\"username\":\"cc\",\"phoneNumber\":\"\",\"deviceToken\":\"\"}]}";
//		try {
//			Gson gson = MyGson.getInstance();
//			Success success = gson.fromJson(str, Success.class);
//			String entity = success.toString();
//			System.out.println(entity);
//			JSONObject json = new JSONObject(str);
//			
//			JSONArray entity = json.getJSONArray("entity");
//			System.out.println(entity.get(0));
//			
//			
//			for (String string : arr) {
//				System.out.println(string);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
	
	@Override
	public StateResponse findMyStates(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception {
		StateResponse stateResponse = new StateResponse();
		String userid = authenticationToken.getUserid();
		String lastStateid = PetUtil.getParameter(clientRequest,"lastStateid");
		StatesUserStates lastStates = new StatesUserStates();
		if(lastStateid!=""){
			lastStates = statesUserStatesMapper.selectByPrimaryKey(lastStateid);
		}
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
		criteria.andUseridEqualTo(userid);
		if(lastStateid!=""){
			criteria.andCtGreaterThan(lastStates.getCt());
		}
		statesUserStatesCriteria.setMysqlLength(20);
		statesUserStatesCriteria.setMysqlOffset(0);
		List<StateView> stateViewList = new ArrayList<StateView>();// 用户状态视图
		List<StatesUserStates> userStates = statesUserStatesMapper.selectByExample(statesUserStatesCriteria);
		for (StatesUserStates statesUserStates : userStates) {
			StateView stateView = getStateView(statesUserStates, userid, "myself");
			stateViewList.add(stateView);
		}
		stateResponse.setStateViews(stateViewList);
		return stateResponse;
	}

	@Override
	public StateResponse findFriendStates(ClientRequest clientRequest,
			SsoAuthenticationToken authenticationToken) throws Exception {
		StateResponse stateResponse = new StateResponse();
		String userid = PetUtil.getParameter(clientRequest,"userid");
		String lastStateid = PetUtil.getParameter(clientRequest,"lastStateid");
		StatesUserStates lastStates = new StatesUserStates();
		if(lastStateid!=""){
			lastStates = statesUserStatesMapper.selectByPrimaryKey(lastStateid);
		}
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
		criteria.andUseridEqualTo(userid);
		if(lastStateid!=""){
			criteria.andCtGreaterThan(lastStates.getCt());
		}
		statesUserStatesCriteria.setMysqlLength(20);
		statesUserStatesCriteria.setMysqlOffset(0);
		statesUserStatesCriteria.setOrderByClause("ct desc");
		List<StateView> stateViewList = new ArrayList<StateView>();// 用户状态视图
		List<StatesUserStates> userStates = statesUserStatesMapper.selectByExample(statesUserStatesCriteria);
		for (StatesUserStates statesUserStates : userStates) {
			StateView stateView = getStateView(statesUserStates, userid, "myself");
			stateViewList.add(stateView);
		}
		stateResponse.setStateViews(stateViewList);
		return stateResponse;
	}

	@Override
	public StateResponse getAllFriendStates(ClientRequest clientRequest,
			SsoAuthenticationToken authenticationToken) throws Exception {
		StateResponse stateResponse = new StateResponse();
		String userid = authenticationToken.getUserid();
		String lastStateid = PetUtil.getParameter(clientRequest,"lastStateid");
		StatesUserStates lastStates = new StatesUserStates();
		if(lastStateid!=""){
			lastStates = statesUserStatesMapper.selectByPrimaryKey(lastStateid);
		}
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
		List<SsoUser> users = getFriendsList(userid);
		List<String> userids = new ArrayList<String>();
		for (SsoUser user : users) {
			userids.add(user.getId());
		}
		criteria.andUseridIn(userids);
		if(lastStateid!=""){
			criteria.andCtGreaterThan(lastStates.getCt());
		}
		statesUserStatesCriteria.setMysqlLength(20);
		statesUserStatesCriteria.setMysqlOffset(0);
		statesUserStatesCriteria.setOrderByClause("ct desc");
		List<StateView> stateViewList = new ArrayList<StateView>();// 用户状态视图
		List<StatesUserStates> userStates = statesUserStatesMapper.selectByExample(statesUserStatesCriteria);
		for (StatesUserStates statesUserStates : userStates) {
			StateView stateView = getStateView(statesUserStates, userid, "myself");
			stateViewList.add(stateView);
		}
		stateResponse.setStateViews(stateViewList);
		return stateResponse;
	}

	@Override
	public StateResponse findOneState(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken)
			throws Exception {
		StateResponse stateResponse = new StateResponse();
		String userid = authenticationToken.getUserid();
		String stateid = PetUtil.getParameter(clientRequest,"stateid");
		StatesUserStates userStates = new StatesUserStates();
		if(stateid!=""){
			userStates = statesUserStatesMapper.selectByPrimaryKey(stateid);
		}
		stateResponse.setStateView(getStateView(userStates, userid, "others"));
		return stateResponse;
	}

	@Override
	public StateResponse getRepliesByTimeIndex(ClientRequest clientRequest,
			SsoAuthenticationToken authenticationToken) throws Exception {
		StateResponse stateResponse = new StateResponse();
		String stateid = PetUtil.getParameter(clientRequest,"stateid");
		String stateuserid = PetUtil.getParameter(clientRequest,"stateuserid");
		String userid = authenticationToken.getUserid();
		String lastReplyid = PetUtil.getParameter(clientRequest,"lastReplyid");
		StatesUserStatesReply lastReply = new StatesUserStatesReply();
		if(lastReplyid!=""){
			lastReply = statesUserStatesReplyMapper.selectByPrimaryKey(lastReplyid);
		}
		StatesUserStatesReplyCriteria statesUserStatesReplyCriteria = new StatesUserStatesReplyCriteria();
		StatesUserStatesReplyCriteria.Criteria criteria = statesUserStatesReplyCriteria.createCriteria();
		criteria.andStateidEqualTo(stateid);
		if(lastReplyid!=""){
			criteria.andCtLessThan(lastReply.getCt());
		}
		if(stateuserid.compareTo(userid)!=0){
			List<SsoUser> users = getFriendsList(userid);
			List<String> userids = new ArrayList<String>();
			for (SsoUser user : users) {
				userids.add(user.getId());
			}
			criteria.andUseridIn(userids);
		}
		statesUserStatesReplyCriteria.setMysqlLength(20);
		statesUserStatesReplyCriteria.setMysqlOffset(0);
		statesUserStatesReplyCriteria.setOrderByClause("ct asc");
		List<ReplyView> replyViewList = new ArrayList<ReplyView>();// 用户状态视图
		List<StatesUserStatesReply> statesReplies = statesUserStatesReplyMapper.selectByExample(statesUserStatesReplyCriteria);
		for (StatesUserStatesReply statesUserStatesReply : statesReplies) {
			ReplyView replyView = getReplyView(statesUserStatesReply, userid);
			replyViewList.add(replyView);
		}
		stateResponse.setReplyViews(replyViewList);
		return stateResponse;
	}

	@Override
	public boolean reportContent(ClientRequest clientRequest,
			SsoAuthenticationToken authenticationToken) throws Exception {
		String stateid = PetUtil.getParameter(clientRequest,"stateid");
		//举报类型（暂时不用）
//		int reporttype = PetUtil.getParameterInteger(clientRequest,"reporttype");
		StatesUserStates userState = statesUserStatesMapper.selectByPrimaryKey(stateid);
		if(null == userState){
			return false;
		}
		//举报次数少于20的情况下
		int reportTimes = userState.getReportTimes();
		if(reportTimes<20){
			userState.setReportTimes(reportTimes+1);
		}else{
			userState.setState("5");
		}
//		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
//		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
		statesUserStatesMapper.updateByPrimaryKeySelective(userState);
//		userState.merge();
		return true;
	}





}
