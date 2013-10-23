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
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesReplyMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesCriteria;
import com.momoplan.pet.commons.domain.states.po.StatesUserStatesReply;
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
	ComboPooledDataSource statisticDataSource = null;
	
	@Autowired
	StatesUserStatesMapper statesUserStatesMapper =null;
	@Autowired
	StatesUserStatesReplyMapper statesUserStatesReplyMapper = null;
	@Autowired
	StatesUserStatesAuditMapper statesUserStatesAuditMapper = null;
	
	@Override
	public StateResponse addReply(ClientRequest clientRequest) throws Exception {
		StateResponse stateResponse = new StateResponse();
		StatesUserStatesReply reply = new StatesUserStatesReply();
		reply.setId(IDCreater.uuid());
		reply.setCt(new Date());
		reply.setMsg(PetUtil.getParameter(clientRequest, "msg"));
		reply.setPid(PetUtil.getParameter(clientRequest, "pid"));
		reply.setPuserid(PetUtil.getParameter(clientRequest, "puserid"));
		reply.setUserid(PetUtil.getParameter(clientRequest, "userid"));
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
			throw e;
		}
		return stateResponse;
	}
	
	@Override
	public StateResponse addUserState(ClientRequest clientRequest) throws Exception {
		StateResponse stateResponse = new StateResponse();
		StatesUserStates userState = new StatesUserStates();
		userState.setId(IDCreater.uuid());
		userState.setUserid(PetUtil.getParameter(clientRequest,"userid"));
		userState.setMsg(PetUtil.getParameter(clientRequest, "msg"));
		userState.setImgid(PetUtil.getParameter(clientRequest, "imgid"));
		userState.setCt(new Date());
		userState.setIfTransmitMsg(PetUtil.getParameterBoolean(clientRequest,"ifTransmitMsg"));
		userState.setTransmitUrl(PetUtil.getParameter(clientRequest,
				"transmitUrl"));
		userState.setTransmitMsg(PetUtil.getParameter(clientRequest,
				"transmitMsg"));
		userState.setLatitude(PetUtil.getParameterDouble(clientRequest,
				"latitude"));
		userState.setLongitude(PetUtil.getParameterDouble(clientRequest,
				"longitude"));
		userState.setState("3");
		userState.setReportTimes(0);
		statesUserStatesMapper.insertSelective(userState);
		sendJMS(userState,"user_states");
		stateResponse.setStateView(this.getStateView(userState,userState.getUserid(), "myself"));
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
			e.printStackTrace();
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
		stateView.setPatUserPat(getPatUserPat(userState,userid));
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
			String method = Constants.GET_USERINFO;
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



	private String getAliasname(String myid, String friendid) {
//		String method = "getAliasname";
//		Map<String,String> param = new HashMap<String,String>();
//		param.put("myid", myid);
//		param.put("friendid", friendid);
//		dopost(method,param);
		String aliasname = "aliasname";
		return aliasname;
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
//		String method = "getPatUserPat";
//		Map<String,String> param = new HashMap<String,String>();
//		param.put("userid", userid);
//		param.put("type", "states");
//		dopost(method,param);
		return null;
	}

	private int countZan(StatesUserStates userState) {
//		String method = "countZan";
//		Map<String,String> param = new HashMap<String,String>();
//		param.put("srcid", userState.getId());
//		param.put("type", "states");
//		List<PatUserPat> patUserPats = dopost(method,param);
//		return patUserPats.size();
		return 0;
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
	private Object dopost(String url,String method, Map<String, String> params) throws Exception {
		StringBuffer req = new StringBuffer();
		req.append("{\"method\":");
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
		req.append("}");
		logger.debug("requestBody : "+req.toString());
		return PostRequest.postText(url, req.toString());
	}

	
	@Override
	public int countReply(ClientRequest clientRequest,SsoAuthenticationToken authenticationToken) throws Exception {
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
		// String method = "getFriendsInfo";
		// Map<String,String> params = new HashMap<String,String>();
		// params.put("userid", userid);
		// List<String> userids = dopost();
		List<String> userids = new ArrayList<String>();
		criteria.andUseridIn(userids);
		int count = statesUserStatesMapper.countByExample(statesUserStatesCriteria);
		return count;
	}

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
//		//jdbc获取动态
//		JdbcTemplate jt = new JdbcTemplate(statisticDataSource);
//		StringBuffer sql = new StringBuffer();
//		String id = "";
//		Object[] params = new Object[]{id};
//		sql.append(" select id,pet_userid from user_states where pet_userid in (select uf.a_id from user_friendship uf where uf.b_id = 2 union select uf.b_id from user_friendship uf where uf.a_id = 2) and submit_time<now(); ");
//		List<StatesUserStates> states = jt.query(sql.toString(),params, new RowMapper<StatesUserStates>(){
//			@Override
//			public StatesUserStates mapRow(ResultSet rs, int rowNum) throws SQLException {
//				StatesUserStates states = new StatesUserStates();
//				states.setId(rs.getString(0));
//				return states;
//			}
//		});
////		logger.info("get bizDailyLives : "+new Gson().toJson(bizDailyLives));
//		try {
////			insertBizDailyLives(bizDailyLives);
//		} catch (Exception e) {
//			logger.debug("insert bizDailyLives error");
//			e.printStackTrace();
//		}
		
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
//		String method = "getFriendsInfo";
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("userid", userid);
//		List<String> userids = dopost();
		List<String> userids = new ArrayList<String>();
		criteria.andUseridIn(userids);
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
		String userid = PetUtil.getParameter(clientRequest,"userid");
		String lastReplyid = PetUtil.getParameter(clientRequest,"lastReplyid");
		StatesUserStatesReply lastReply = new StatesUserStatesReply();
		if(lastReplyid!=""){
			lastReply = statesUserStatesReplyMapper.selectByPrimaryKey(lastReplyid);
		}
		StatesUserStatesCriteria statesUserStatesCriteria = new StatesUserStatesCriteria();
		StatesUserStatesCriteria.Criteria criteria = statesUserStatesCriteria.createCriteria();
//		String method = "getFriendsInfo";
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("userid", userid);
//		List<String> userids = dopost();
		List<String> userids = new ArrayList<String>();
		criteria.andUseridIn(userids);
		if(lastReplyid!=""){
			criteria.andCtGreaterThan(lastReply.getCt());
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





}
