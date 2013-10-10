package com.momoplan.pet.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.httpclient.HttpException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.momoplan.common.HttpRequestProxy;
import com.momoplan.common.PetConstants;
import com.momoplan.common.PetUtil;
import com.momoplan.exception.DuplicatedUsernameException;
import com.momoplan.exception.PetException;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.domain.AuthenticationToken;
import com.momoplan.pet.domain.Feedback;
import com.momoplan.pet.domain.PetFile;
import com.momoplan.pet.domain.PetInfo;
import com.momoplan.pet.domain.PetUser;
import com.momoplan.pet.domain.PetVersion;
import com.momoplan.pet.domain.Reply;
import com.momoplan.pet.domain.ReplyComments;
import com.momoplan.pet.domain.UsageState;
import com.momoplan.pet.domain.UserFriendship;
import com.momoplan.pet.domain.UserLocation;
import com.momoplan.pet.domain.UserStates;
import com.momoplan.pet.domain.UserZan;
import com.momoplan.pet.domain.Verification;
import com.momoplan.pet.service.ChatServerService;
import com.momoplan.pet.service.LocationService;
import com.momoplan.pet.service.PetFileService;
import com.momoplan.pet.service.PetUserService;
import com.momoplan.pet.service.UploadService;
import com.momoplan.pet.service.UsageStateService;
import com.momoplan.pet.service.UserFriendshipService;
import com.momoplan.pet.service.VerificationService;
import com.momoplan.pet.vo.PetInfoView;
import com.momoplan.pet.vo.PetUserView;
import com.momoplan.pet.vo.PetUserWithPetInfoView;
import com.momoplan.pet.vo.ReplyCommentViews;
import com.momoplan.pet.vo.ReplyView;
import com.momoplan.pet.vo.StateView;

@RequestMapping("/client")
@Controller
public class ClientController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PasswordEncoder passwordEncoder;
	@Value("$salt")
	String salt = "Pet!@";
	@Autowired
	LocationService locationService;
	@Autowired
	UploadService uploadService;
	@Autowired
	PetUserService petUserService;
	@Autowired
	PetFileService petFileService;
	@Autowired
	UsageStateService usageStateService;
	@Autowired
	VerificationService verificationService;
	@Autowired
	UserFriendshipService userFriendshipService;
	@Autowired
	ChatServerService chatServerService;
//	@Autowired
//	JmsService jmsService;

	
//	@Value("#{config['xmpp.server']}")
//	private String xmpppath = null;
	//add by liangc 130929 : XMPP的域不能写死，正式跟测试环境会用到不同的域，所以把这块配置到 spring 中，然后用 pom.xml 中的 profile 重写
	@Autowired
	private String xmppServer = null;
	@Autowired
	private String xmppDomain = null;
			
	@Resource
	private JmsTemplate apprequestTemplate;
	
	/**
	 * @param body
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("request")
	public @ResponseBody
	Object request(@RequestParam("body") String body,HttpServletResponse response) throws Exception {
		logger.debug("request input : "+body);
		String ret = null;
		ClientRequest clientRequest = new ObjectMapper().reader(ClientRequest.class).readValue(body);
		try {
			Object retObj = doRequest(clientRequest, response);
			ret = new ObjectMapper().writeValueAsString(retObj);
			if(ret.contains("needProxy")){
				ret = HttpRequestProxy.doPostHttpClient(retObj.toString().substring(10), body);
			}
		}catch(Exception e){
			logger.error("request error",e);
			e.printStackTrace();
			throw e;
		} finally { 
			try {
				TextMessage tm = new ActiveMQTextMessage();
				Map<String,String> proxyJms = proxyJms(body,ret);
				tm.setStringProperty("body", proxyJms.get("body"));
				tm.setStringProperty("ret", proxyJms.get("ret"));
				apprequestTemplate.convertAndSend(tm);
			} catch (Exception e) {
				return ret;
			}
		}
		return ret;
	}

	private Map<String,String> proxyJms(String body,String ret){
		try {
			JSONObject bodyJson = new JSONObject(body);
			JSONObject retJson = new JSONObject();;
			if(!ret.contains("null")){
				retJson =  new JSONObject(ret);
			}
			String token = bodyJson.get("token")!=null?bodyJson.get("token").toString():retJson.get("token").toString();
			if(!StringUtils.hasLength(token)){
				return null;
			}
			long userid = AuthenticationToken.findAuthenticationToken(token).getUserid();
			if(userid == 0){
				return null;
			}
			JSONObject newbody = new JSONObject(body);
			JSONObject newparams = newbody.getJSONObject("params");
			JSONObject newret = new JSONObject();
			newparams.accumulate("jsmuserid", String.valueOf(userid));
			newbody.remove("params");
			newbody.accumulate("params", newparams);
			newret.accumulate("method", bodyJson.get("method"));
			newret.accumulate("params", new JSONObject().accumulate("jsmuserid", String.valueOf(userid)));
			Map<String,String> map = new HashMap<String, String>();
			map.put("body", newbody.toString());
			map.put("ret", newret.toString());
			return map;
		} catch (Exception e) {
			return null;
		}
	}
	
	private Object doRequest(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		// ClientRequest clientRequest = new
		// ObjectMapper().reader(ClientRequest.class).readValue(body);
		if (clientRequest.getMethod().equals("open")) {
			return handleOpen(clientRequest);
		}
		if (clientRequest.getMethod().equals("register")) {
			return handleRegister(clientRequest);
		}
		if (clientRequest.getMethod().equals("register2")) {// v2
			return handleRegister2(clientRequest);
		}
		if (clientRequest.getMethod().equals("resetPassword")) {
			return handleResetPassword(clientRequest);
		}
		if (clientRequest.getMethod().equals("isUsernameInuse")) {
			return handleIsUsernameInuse(clientRequest);
		}
		if (clientRequest.getMethod().equals("getChatServer")) {
			return handleGetChatServerList(clientRequest);
		}
		if (clientRequest.getMethod().equals("login")) {
			return handlelogin(clientRequest);
		}
		if (clientRequest.getMethod().equals("login2")) {
			return handlelogin2(clientRequest);
		}
		if (clientRequest.getMethod().equals("getNearbyUser")) {
			return handleNearbyUser(clientRequest);
		}
		if (clientRequest.getMethod().equals("setUserLocation")) {
			return handleSetUserLocation(clientRequest);
		}
		if (clientRequest.getMethod().equals("saveUserinfo")) {
			return handleSaveUserInfo(clientRequest);
		}
		if (clientRequest.getMethod().equals("saveUserinfo2")) {// v2
			return handleSaveUserInfo2(clientRequest);
		}
		if (clientRequest.getMethod().equals("getUserinfo")) {
			return handleGetUserInfo(clientRequest);
		}
		if (clientRequest.getMethod().equals("savePetinfo")) {
			return handleSavePetInfo(clientRequest);
		}
		// 根据用户编号和用户名查询用户
		if (clientRequest.getMethod().equals("selectUserByNameOrUserId")) {
			return handleSelectUserByNameOrUserId(clientRequest);
		}
		// 修改宠物信息
		if (clientRequest.getMethod().equals("updatePetinfo")) {
			return handleUpdatePetInfo(clientRequest);
		}
		// 删除宠物信息
		if (clientRequest.getMethod().equals("delPetInfo")) {
			return handleDelPetInfo(clientRequest);
		}
		// 更新开机画面下载图片
		// if(clientRequest.getMethod().equals("updateOpenImg")){
		// handleUpdateOpenImg(clientRequest,response);
		// }
		// 获取最新版本信息
		if (clientRequest.getMethod().equals("updateVersion")) {
			return handleUpdateVersion(clientRequest);
		}
		// 获取用户信息
		if (clientRequest.getMethod().equals("getUesr")) {
			return handleGetUser(clientRequest);
		}
		if (clientRequest.getMethod().equals("getFriends")) {
			return handleGetFriends(clientRequest);
		}
		if (clientRequest.getMethod().equals("getOneFriend")) {
			return handleGetOneFriend(clientRequest);
		}
		// 获取验证码
		if (clientRequest.getMethod().equals("getVerificationCode")) {
			return handldGetVerificationCode(clientRequest);
		}
		// 验证随机码
		if (clientRequest.getMethod().equals("verifyCode")) {
			return handldVerifyCode(clientRequest);
		}
		// 根据用户名查询一个用户的所有信息
		if (clientRequest.getMethod().equals("selectUserViewByUserName")) {
			return handleSelectUserViewByUserName(clientRequest);
		}
		// 查询附近用户信息，根据宠物过滤一次
		if (clientRequest.getMethod().equals("getNearbyUserAndPet")) {
			return handleNearbyUserAndPet(clientRequest);
		}
		// 根据图片号删除一张图片id
		if (clientRequest.getMethod().equals("delOneImg")) {
			return handleDelOneImg(clientRequest);
		}
		// 分页获取用户动态
		if (clientRequest.getMethod().equals("getUserStateView")) {
			return handleGetUserState(clientRequest);
		}
		// 用户添加状态
		if (clientRequest.getMethod().equals("addUserState")) {
			return handleAddUserState(clientRequest);
		}
		// 删除用户动态
		if (clientRequest.getMethod().equals("delUserState")) {
			return handleDelUserState(clientRequest);
		}
		// 评论用户动态
		if (clientRequest.getMethod().equals("addReply")) {
			return handleAddReply(clientRequest);
		}
		// 回复评论
		if (clientRequest.getMethod().equals("addCommentReply")) {
			return handleAddCommentReply(clientRequest);
		}
		// 删除评论
		if (clientRequest.getMethod().equals("delReply")) {
			return handleDelReply(clientRequest);
		}
		// 修改好友备注
		if (clientRequest.getMethod().equals("updatefriendremark")) {
			return changeFriendRemark(clientRequest);
		}
		// 删除回复评论
		if (clientRequest.getMethod().equals("delCommentReply")) {
			return handleDelCommentReply(clientRequest);
		}
		// 赞动态
		if (clientRequest.getMethod().equals("addZan")) {
			return handleAddZan(clientRequest);
		}
		// 取消赞
		if (clientRequest.getMethod().equals("delZan")) {
			return handleDelZan(clientRequest);
		}
		// 查询自己的动态
		if (clientRequest.getMethod().equals("findMyStates")) {
			return handleFindMyStates(clientRequest);
		}
		// 查询好友的所有动态
		if (clientRequest.getMethod().equals("findFriendStates")) {
			return handleFindFriendStates(clientRequest);
		}
		// 查询所有好友的动态
		if (clientRequest.getMethod().equals("getAllFriendStates")) {
			return handleGetAllFriendStates(clientRequest);
		}
		// 分页获取评论
		if (clientRequest.getMethod().equals("getRepliesByTimeIndex")) {
			return handleGetRepliesByTimeIndex(clientRequest);
		}
		// 分页获取回复评论
		if (clientRequest.getMethod().equals("getCommentsByTimeIndex")) {
			return handleGetCommentsByTimeIndex(clientRequest);
		}
		// 意见反馈
		if (clientRequest.getMethod().equals("feedback")) {
			handleFeedback(clientRequest);
		}
		//获取一条动态的详细信息
		if (clientRequest.getMethod().equals("findOneState")) {
			return handleFindOneState(clientRequest);
		}
		//proxy发帖
		if (clientRequest.getMethod().equals("sendNote")) {
//			Object petResponse = handleSendNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy回帖
		if (clientRequest.getMethod().equals("replyNote")) {
//			Object petResponse = handleReplyNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy搜索
		if (clientRequest.getMethod().equals("searchNote")) {
//			Object petResponse = handleSearchNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy根据id查看帖子详情
		if (clientRequest.getMethod().equals("detailNote")) {
//			Object petResponse = handleDetailNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy删除帖子
		if (clientRequest.getMethod().equals("delNote")) {
//			Object petResponse = handleDelNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy关注圈子
		if (clientRequest.getMethod().equals("attentionForum")) {
//			Object petResponse = handleAttentionForum(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy退出圈子
		if (clientRequest.getMethod().equals("quitForum")) {
//			Object petResponse = handleQuitForum(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy获取最新帖子
		if (clientRequest.getMethod().equals("newNote")) {
//			Object petResponse = handleNewNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy举报帖子
		if (clientRequest.getMethod().equals("reportNote")) {
//			Object petResponse = handleReportNote(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy获取当前帖子所有回复
		if (clientRequest.getMethod().equals("getAllReplyNoteByNoteid")) {
//			Object petResponse = handleGetAllReplyNoteByNoteid(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy根据回帖id获取回帖
		if (clientRequest.getMethod().equals("getReplyNoteSubByReplyNoteid")) {
//			Object petResponse = handleGetReplyNoteSubByReplyNoteid(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy今日新增帖子列表
		if (clientRequest.getMethod().equals("getTodayNewNoteList")) {
//			Object petResponse = handleGetTodayNewNoteList(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy我发表过的帖子列表
		if (clientRequest.getMethod().equals("getMyNotedListByuserid")) {
//			Object petResponse = handleGetMyNotedListByuserid(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy我回复过的帖子列表
		if (clientRequest.getMethod().equals("getMyReplyNoteListByUserid")) {
//			Object petResponse = handleGetMyReplyNoteListByUserid(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy获取某圈子下所有帖子数
		if (clientRequest.getMethod().equals("getNoteCountByForumid")) {
//			Object petResponse = handleGetNoteCountByForumid(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//proxy获取某圈子下所有回复数
		if (clientRequest.getMethod().equals("getNoteSubCountByForumid")) {
//			Object petResponse = handleGetNoteSubCountByForumid(clientRequest);
			Object petResponse = handleProxyRequest(clientRequest);
			return petResponse;
		}
		//举报
		if (clientRequest.getMethod().equals("reportContent")) {
			return handleReportContent(clientRequest);
		}
		
		return clientRequest;
		
		
	}

	private Object handleProxyRequest(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
		if (null == authenticationToken) {
			return "false";
		}
		return "needProxy:" + Bootstrap.configWatcher.getProperty(PetConstants.SERVICE_URI_PET_BBS, null);
	}

//	private Object handleSendNote(ClientRequest clientRequest) {
//		AuthenticationToken authenticationToken = AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
//		if(null==authenticationToken){
//			return null;
//		}
//		return "needProxy:"+pet_bbs;
//	}

	/**
	 * 举报一条动态
	 * @param clientRequest
	 * @return
	 */
	private Object handleReportContent(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
		if (null == authenticationToken) {
			return "false";
		}
		long stateid = PetUtil.getParameterLong(clientRequest,"stateid");
		//举报类型（暂时不用）
		int reporttype = PetUtil.getParameterInteger(clientRequest,"reporttype");
		UserStates userState = UserStates.findUserStates(stateid);
		if(null == userState){
			return "false";
		}
		//举报次数少于20的情况下
		int reportTimes = userState.getReportTimes();
		if(reportTimes<20){
			userState.setReportTimes(reportTimes+1);
		}else{
			userState.setStateType("5");
		}
		userState.merge();
		return "true";
	}
	
	private Object handleFindOneState(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
		long stateid = PetUtil.getParameterLong(clientRequest,"stateid");
		UserStates userState = UserStates.findUserStates(stateid);
		StateView stateView  = new StateView();
		if (userState.getPetUserid() == authenticationToken.getUserid()) {
			stateView = this.getStateView(userState,authenticationToken.getUserid(), "myself");
		} else {
			stateView = this.getStateView(userState,authenticationToken.getUserid(), "others");
		}
		return stateView;
	}

	private void handleFeedback(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		Feedback feedback = new Feedback();
		feedback.setFeedback(PetUtil.getParameter(clientRequest, "feedback"));
		feedback.setUserid(authenticationToken.getUserid());
		feedback.setEmail(PetUtil.getParameter(clientRequest, "email"));
		feedback.setCreateTime(new Date(System.currentTimeMillis()));
		feedback.persist();
	}

	private UserLocation handleSetUserLocation(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		if (null == authenticationToken) {
			return null;
		}
		return this.locationService.setUserLocation(
				Double.parseDouble(clientRequest.getParams().get("longitude")
						.toString()),
				Double.parseDouble(clientRequest.getParams().get("latitude")
						.toString()), authenticationToken.getUserid());
	}

	/**
	 * 修改好友备注
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object changeFriendRemark(ClientRequest clientRequest) {
		try {
			AuthenticationToken.findAuthenticationToken(clientRequest
					.getToken());
			Long aId = PetUtil.getParameterLong(clientRequest, "aId");
			Long bId = PetUtil.getParameterLong(clientRequest, "bId");
			String newremark = PetUtil.getParameter(clientRequest, "aliasB");
			UserFriendship userFriendship = userFriendshipService
					.findUserFriendshipsByABId(aId, bId);
			if (null != userFriendship) {
				if (userFriendship.getAId() == aId
						&& userFriendship.getBId() == bId) {
					userFriendship.setAliasB(newremark);
					userFriendship.merge();
				} else if (userFriendship.getAId() == bId
						&& userFriendship.getBId() == aId) {
					userFriendship.setAliasA(newremark);
					userFriendship.merge();
				}
			}
			return userFriendship;
		} catch (Exception e) {
			e.printStackTrace();
			return "修改备注失败!";
		}
	}

	/**
	 * 删除评论回复
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleDelCommentReply(ClientRequest clientRequest) {
		try {
			AuthenticationToken.findAuthenticationToken(clientRequest
					.getToken());
			ReplyComments replyComments = new ReplyComments();
			replyComments.setId(PetUtil.getParameterLong(clientRequest,
					"replyCommonid"));
			replyComments.remove();

			return replyComments;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除用户评论
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleDelReply(ClientRequest clientRequest) {
		try {
			AuthenticationToken.findAuthenticationToken(clientRequest
					.getToken());
			Reply reply = new Reply();
			reply.setId(PetUtil.getParameterLong(clientRequest, "replyId"));
			reply.remove();

			return reply;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 动态评论回复
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleAddCommentReply(ClientRequest clientRequest) {
		AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
		ReplyComments replyComments = new ReplyComments();
		// 评论者id:commentuserid
		replyComments.setCommentUserid(PetUtil.getParameterLong(clientRequest,
				"commentUserid"));
		// 被评论者id:replyuserid
		replyComments.setReplyUserid(PetUtil.getParameterLong(clientRequest,
				"replyUserid"));
		// 被评论的评论id(父级评论id):replyid
		replyComments.setReplyid(PetUtil.getParameterLong(clientRequest,
				"replyId"));
		// 动态id:userstateid
		replyComments.setUserStateid(PetUtil.getParameterLong(clientRequest,
				"userStateid"));
		replyComments
				.setCommentsMsg(PetUtil.getParameter(clientRequest, "msg"));
		try {
			replyComments.setCommentTime(PetUtil.getParameterDate(
					clientRequest, "commentTime"));
		} catch (ParseException e) {
			return false;
		}
		replyComments.persist();
		ReplyCommentViews replyCommentViews = getReplyCommentView(replyComments);

		// 给接收者发送提醒
		XMPPRequest xr = new XMPPRequest();
		PetUser sendUser = PetUser.findPetUser(replyComments.getCommentUserid());
		xr.setSendUser(sendUser.getUsername());
		xr.setReceiveUser(PetUser.findPetUser(replyComments.getReplyUserid()).getUsername());
		xr.setType("reply");
		xr.setPrams(replyComments.getUserStateid());
		xr.setWords(replyComments.getCommentsMsg());
		xr.setMsgTime(replyComments.getCommentTime());
		xr.setRegion("@"+Bootstrap.configWatcher.getProperty(PetConstants.XMPP_DOMAIN, "hadoop7.ruyicai.com"));
		xr.setFromHeadImg(sendUser.getImg());
		xr.setFromNickname(sendUser.getNickname());
		xr.setXmpppath(Bootstrap.configWatcher.getProperty(PetConstants.XMPP_SERVER, "http://192.168.99.53:5280/rest"));
		try {
			xr.SendMessage();
		} catch (Exception e) {
			return replyCommentViews;
		}
		if (replyComments.getCommentUserid() == replyComments.getReplyid()) {// 自己评论自己的时候只发一次消息提醒
			return replyCommentViews;
		}
		// 给动态发布者发送提醒
		XMPPRequest xr2 = new XMPPRequest();
		PetUser sendUser2 = PetUser.findPetUser(replyComments.getCommentUserid());
		xr2.setSendUser(sendUser2.getUsername());
		xr2.setReceiveUser(PetUser.findPetUser(UserStates.findUserStates(replyComments.getUserStateid()).getPetUserid()).getUsername());
		xr2.setType("reply");
		xr2.setPrams(replyComments.getUserStateid());
		xr2.setWords(replyComments.getCommentsMsg());
		xr2.setRegion("@"+Bootstrap.configWatcher.getProperty(PetConstants.XMPP_DOMAIN, "hadoop7.ruyicai.com"));
		xr2.setFromHeadImg(sendUser2.getImg());
		xr2.setFromNickname(sendUser2.getNickname());
		xr2.setXmpppath(Bootstrap.configWatcher.getProperty(PetConstants.XMPP_SERVER, "http://192.168.99.53:5280/rest"));
		try {
			xr2.SendMessage();
		} catch (Exception e) {
			return replyCommentViews;
		}
		return replyCommentViews;

	}

	private ReplyCommentViews getReplyCommentView(ReplyComments replyComments) {
		ReplyCommentViews replyCommentViews = new ReplyCommentViews();
		replyCommentViews.setCommentsMsg(replyComments.getCommentsMsg());
		replyCommentViews.setCommentTime(replyComments.getCommentTime());
		replyCommentViews.setCommentUserView(getPetUserview(replyComments
				.getCommentUserid()));
		replyCommentViews.setId(replyComments.getId());
		replyCommentViews.setReplyid(replyComments.getReplyid());
		replyCommentViews.setReplyUserView(getPetUserview(replyComments
				.getReplyUserid()));
		replyCommentViews.setUserStateid(replyComments.getUserStateid());
		return replyCommentViews;
	}

	/**
	 * 用户动态评论
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleAddReply(ClientRequest clientRequest) {
		try {
			AuthenticationToken authenticationToken = AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
			Reply reply = new Reply();
			// 评论者id:petuserid
			reply.setPetUserid(PetUtil.getParameterLong(clientRequest, "petuserId"));
			// 被评论动态id:userstateid
			reply.setUserStateid(PetUtil.getParameterLong(clientRequest, "userstateId"));
			reply.setMsg(PetUtil.getParameter(clientRequest, "msg"));
			try {
				reply.setReplyTime(PetUtil.getParameterDate(clientRequest, "replyTime"));
			} catch (ParseException e) {
				logger.error("liangc_debug: ", e);
				return false;
			}
			reply.persist();
			ReplyView replyView = getReplyView(reply, authenticationToken.getUserid());
			XMPPRequest xr = new XMPPRequest();
			PetUser sendUser = PetUser.findPetUser(reply.getPetUserid());
			xr.setSendUser(sendUser.getUsername());
			xr.setReceiveUser(PetUser.findPetUser(UserStates.findUserStates(reply.getUserStateid()).getPetUserid()).getUsername());
			xr.setType("reply");
			xr.setPrams(reply.getUserStateid());
			xr.setWords(reply.getMsg());
			xr.setMsgTime(reply.getReplyTime());
			xr.setRegion("@"+Bootstrap.configWatcher.getProperty(PetConstants.XMPP_DOMAIN, "hadoop7.ruyicai.com"));
			xr.setFromHeadImg(sendUser.getImg());
			xr.setFromNickname(sendUser.getNickname());
			xr.setXmpppath(Bootstrap.configWatcher.getProperty(PetConstants.XMPP_SERVER, "http://192.168.99.53:5280/rest"));
			try {
				xr.SendMessage();
			} catch (HttpException e) {
				logger.error("liangc_debug: ", e);
				return replyView;
			} catch (IOException e) {
				logger.error("liangc_debug: ", e);
				return replyView;
			}
			return replyView;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("liangc_debug: ", e);
			return e.getMessage();
		}
	}

	private ReplyView getReplyView(Reply reply, long petUserid) {
		ReplyView replyView = new ReplyView();
		replyView.setId(reply.getId());
		replyView.setMsg(reply.getMsg());
		replyView.setPetUserView(getPetUserview(reply.getPetUserid()));
		replyView.setReplyCommentViews(getReplyCommentsView(reply, -1,
				petUserid));
		replyView.setReplyTime(reply.getReplyTime());
		replyView.setUserStateid(reply.getUserStateid());
		replyView.setCountReplyComment(ReplyComments
				.countReplyCommentsesByReplyid(reply.getId()));
		return replyView;
	}

	/**
	 * 分页获取回复评论
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleGetCommentsByTimeIndex(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		Reply reply = Reply.findReply(PetUtil.getParameterLong(clientRequest,
				"replyid"));
		int lastComment = PetUtil.getParameterInteger(clientRequest,
				"lastComment");
		List<ReplyCommentViews> commentsViews = getReplyCommentsView(reply,
				lastComment, authenticationToken.getUserid());
		return commentsViews;
	}

	/**
	 * 分页获取评论
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleGetRepliesByTimeIndex(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		long petUserid = authenticationToken.getUserid();
		UserStates ustate = UserStates.findUserStates(PetUtil.getParameterLong(
				clientRequest, "userStateid"));
		int lastReplyid = PetUtil.getParameterInteger(clientRequest,
				"lastReplyid");
		List<ReplyView> replyViews = getReplyViews(ustate, petUserid,
				lastReplyid, "myself");
		return replyViews;
	}

	/**
	 * 
	 * 取消赞
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleDelZan(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		long userid = authenticationToken.getUserid();
		long zanUserid = PetUtil.getParameterLong(clientRequest, "zanUserid");// 被赞的人
		long userStateid = PetUtil.getParameterLong(clientRequest,
				"userStateid");// 被赞的动态
		long petid = PetUtil.getParameterLong(clientRequest, "petid");// 被赞的宠物
		String zanType = PetUtil.getParameter(clientRequest, "zanType");// 被赞的类型
		if (UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid,
						userStateid, zanUserid, petid, zanType).getResultList()
				.size() <= 0) {
			return "没赞过";
		}
		List<UserZan> userZans = UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid,
						userStateid, zanUserid, petid, zanType).getResultList();
		for (UserZan userZan : userZans) {
			userZan.remove();
		}
		return userZans;
	}

	/**
	 * 点赞
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleAddZan(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		long userid = authenticationToken.getUserid();
		long zanUserid = PetUtil.getParameterLong(clientRequest, "zanUserid");// 被赞的人
		long userStateid = PetUtil.getParameterLong(clientRequest,
				"userStateid");// 被赞的动态
		long petid = PetUtil.getParameterLong(clientRequest, "petid");// 被赞的宠物
		String zanType = PetUtil.getParameter(clientRequest, "zanType");// 被赞的类型
		if (UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid,
						userStateid, zanUserid, petid, zanType).getResultList()
				.size() > 0) {
			return "已赞";
		}
		UserZan userZan = new UserZan();
		userZan.setUserid(userid);
		userZan.setZanUserid(zanUserid);
		userZan.setUserStateid(userStateid);
		userZan.setPetid(petid);
		userZan.setZanType(zanType);
		userZan.persist();
		try {
			XMPPRequest xr = new XMPPRequest();
			PetUser sendUser = PetUser.findPetUser(userZan.getUserid());
			xr.setSendUser(sendUser.getUsername());
			xr.setReceiveUser(PetUser.findPetUser(userZan.getZanUserid())
					.getUsername());
			xr.setMsgTime(new Date(System.currentTimeMillis()));
			if (zanType.contains("0")) {// 动态
				xr.setType("zanDynamic");
				xr.setPrams(userZan.getUserStateid());
			} else if (zanType.contains("1")) {// 宠物
				xr.setType("zanPet");
				xr.setPrams(userZan.getPetid());
			} else if (zanType.contains("2")) {// 用户
				xr.setType("zanPeople");
			}
			xr.setFromHeadImg(sendUser.getImg());
			xr.setFromNickname(sendUser.getNickname());
			xr.setRegion("@"+Bootstrap.configWatcher.getProperty(PetConstants.XMPP_DOMAIN, "hadoop7.ruyicai.com"));
			xr.setXmpppath(Bootstrap.configWatcher.getProperty(PetConstants.XMPP_SERVER, "http://192.168.99.53:5280/rest"));
			xr.SendMessage();
		} catch (Exception e) {
			return userZan;
		}

		return userZan;
	}

	/**
	 * 
	 * 分页获取所有好友的动态
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleGetAllFriendStates(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		long userid = authenticationToken.getUserid();
		// int pageIndex = getParameterInteger(clientRequest, "pageIndex");
		long lastStateid = PetUtil.getParameterLong(clientRequest,
				"lastStateid");
		List<UserStates> userStates = UserStates.finderUserStatesWithFriendship(userid, lastStateid).getResultList();
		List<StateView> userViews = new ArrayList<StateView>();// 用户状态视图
		for (UserStates userState : userStates) {
			// 获取状态视图 
			StateView stateView = new StateView();
			if (userState.getPetUserid() == authenticationToken.getUserid()) {
				stateView = this.getStateView(userState,
						authenticationToken.getUserid(), "myself");
			} else {
				stateView = this.getStateView(userState,
						authenticationToken.getUserid(), "others");
			}
			userViews.add(stateView);
		}
		return userViews;
	}

	/**
	 * 分页获取好友（任意用户）的动态
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleFindFriendStates(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		long userid = PetUtil.getParameterLong(clientRequest, "userid");
		// int pageIndex = getParameterInteger(clientRequest, "pageIndex");
		long lastStateid = PetUtil.getParameterLong(clientRequest,
				"lastStateid");
		List<UserStates> userStates = UserStates.findUserStatesesByPetUserid(
				userid, lastStateid,"others").getResultList();
		List<StateView> userViews = new ArrayList<StateView>();// 用户状态视图
		for (UserStates userState : userStates) {
			// 获取状态视图
			StateView stateView = new StateView();
			if (userState.getPetUserid() == authenticationToken.getUserid()) {
				stateView = this.getStateView(userState,
						authenticationToken.getUserid(), "myself");
			} else {
				stateView = this.getStateView(userState,
						authenticationToken.getUserid(), "others");
			}
			userViews.add(stateView);
		}
		return userViews;
	}

	/**
	 * 分页获取自己的动态
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleFindMyStates(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		long userid = authenticationToken.getUserid();
		// int pageIndex = getParameterInteger(clientRequest, "pageIndex");
		long lastStateid = PetUtil.getParameterLong(clientRequest,
				"lastStateid");
		List<UserStates> userStates = UserStates.findUserStatesesByPetUserid(userid, lastStateid,"myself").getResultList();
		List<StateView> userViews = new ArrayList<StateView>();// 用户状态视图
		for (UserStates userState : userStates) {
			StateView stateView = this
					.getStateView(userState, userid, "myself");
			// stateView.setPageIndex(pageIndex++);
			userViews.add(stateView);
		}
		return userViews;
	}

	/**
	 * 重载此方法，根据用户状态表获取用户状态视图（时间排序）
	 * 
	 * @param userStates
	 * @param userid
	 * @return
	 */
	private StateView getStateView(UserStates userState, long userid,
			String whos) {
		if (null == userState) {
			return null;
		}
		// 封装到状态视图中
		StateView stateView = new StateView();
		stateView.setReplyViews(this.getReplyViews(userState, userid, -1, whos));// 根据获取的本条状态获取评价视图
		stateView.setPetUserView(getPetUserviewWithAlias(
				userState.getPetUserid(), userid));// 封装发布状态的用户信息
		stateView.setId(userState.getId());
		stateView.setImgid(userState.getImgid());
		stateView.setMsg(userState.getMsg());
		stateView.setSubmitTime(userState.getSubmitTime());
		stateView.setIfTransmitMsg(userState.getIfTransmitMsg());
		stateView.setTransmitMsg(userState.getTransmitMsg());
		stateView.setTransmitUrl(userState.getTransmitUrl());
		stateView.setStateType(userState.getStateType());
		if(userState.getStateType().contains("1")||userState.getStateType().contains("2")){
			stateView.setCountZan(UserZan.countUserZans(userid, userState.getId(),
					userState.getPetUserid(), -1, "0")+12);
		}else{
			stateView.setCountZan(UserZan.countUserZans(userid, userState.getId(),
					userState.getPetUserid(), -1, "0"));
		}
		
		stateView.setIfIZaned(UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid,
						userState.getId(), userState.getPetUserid(), -1, "0")
				.getResultList().isEmpty() ? "0" : "1");
		stateView.setCountReplys(Reply.countReplysByUserStateid(userState
				.getId()));
		return stateView;
	}

	// /**
	// * 获取动态视图
	// *
	// * @param userLocation
	// * @return
	// */
	// private StateView getUserStateView(UserStateViews userStateView,long
	// petUserid) {
	// // 根据用户id查询用户状态表
	// UserStates ustate = UserStates.findUserStates(userStateView.getId());
	// if(null == ustate){
	// return null;
	// }
	// // 封装到状态视图中
	// StateView stateView = new StateView();
	// stateView.setReplyViews(this.getReplyViews(ustate, petUserid,
	// 0,"others"));// 根据获取的本条状态获取评价视图
	// stateView.setPetUser(PetUser.findPetUser(userStateView.getUserid()));//
	// 封装发布状态的用户信息
	// stateView.setId(ustate.getId());
	// stateView.setImgid(ustate.getImgid());
	// stateView.setMsg(ustate.getMsg());
	// stateView.setSubmitTime(ustate.getSubmitTime());
	// stateView.setIfTransmitMsg(ustate.getIfTransmitMsg());
	// stateView.setTransmitMsg(ustate.getTransmitMsg());
	// stateView.setTransmitUrl(ustate.getTransmitUrl());
	// stateView.setCountZan(UserZan.countUserZans(petUserid,ustate.getId(),
	// ustate.getPetUserid(),-1,"0"));
	// stateView.setIfIZaned(UserZan.findUserZansByUseridAndUserStateidAndZanUserid(petUserid,ustate.getId(),
	// ustate.getPetUserid(),-1,"0").getResultList().isEmpty() ? "0" : "1");
	// stateView.setCountReplys(Reply.countReplysByUserStateid(ustate.getId()));
	// return stateView;
	// }

	/**
	 * 
	 * 删除用户动态
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleDelUserState(ClientRequest clientRequest) {
		AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
		UserStates userState = UserStates.findUserStates(PetUtil
				.getParameterLong(clientRequest, "userStateId"));
		userState.remove();
		return userState;
	}

	/**
	 * 
	 * 添加用户状态
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleAddUserState(ClientRequest clientRequest) {
		try {
			AuthenticationToken authenticationToken = AuthenticationToken
					.findAuthenticationToken(clientRequest.getToken());
			UserStates userState = new UserStates();
			userState.setPetUserid(PetUtil.getParameterLong(clientRequest,
					"userid"));
			userState.setMsg(PetUtil.getParameter(clientRequest, "msg"));
			userState.setImgid(PetUtil.getParameter(clientRequest, "imgid"));
			userState.setSubmitTime(PetUtil.getParameterTimestamp(
					clientRequest, "submitTime"));
			userState.setIfTransmitMsg(PetUtil.getParameter(clientRequest,
					"ifTransmitMsg"));
			userState.setTransmitUrl(PetUtil.getParameter(clientRequest,
					"transmitUrl"));
			userState.setTransmitMsg(PetUtil.getParameter(clientRequest,
					"transmitMsg"));
			userState.setLatitude(PetUtil.getParameterDouble(clientRequest,
					"latitude"));
			userState.setLongitude(PetUtil.getParameterDouble(clientRequest,
					"longitude"));
			userState.setStateType("3");
			userState.setReportTimes(0);
			userState.persist();
			sendJMS(userState,"user_states");
			return this.getStateView(userState,authenticationToken.getUserid(), "myself");
			
		} catch (ParseException e) {
			return null;
		} 
	}

	/**
	 * 向校验内容的应用send消息
	 * @param userState
	 * @param biz
	 */
	private void sendJMS(UserStates userState,String biz) {
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
	 * 分页获取用户动态
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleGetUserState(ClientRequest clientRequest) throws Exception{
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		double longitude = Double.parseDouble(clientRequest.getParams()
				.get("longitude").toString());
		double latitude = Double.parseDouble(clientRequest.getParams()
				.get("latitude").toString());
		int pageIndex = PetUtil.getParameterInteger(clientRequest, "pageIndex");
		// 获取用户地理位置状态视图（地理位置，时间，性别）
		List<UserStates> userStates = UserStates.findUserStateByLatitudeOrLongitudeOrGenderEquals(latitude,longitude,authenticationToken.getUserid(),pageIndex).getResultList();
		List<StateView> userViews = new ArrayList<StateView>();// 用户状态视图
		// 用户地理位置视图遍历查询，获取附近用户状态视图
		for (UserStates userState : userStates) {
			// 获取状态视图
			StateView stateView = new StateView();
			if (userState.getPetUserid() == authenticationToken.getUserid()) {
				stateView = this.getStateView(userState,authenticationToken.getUserid(), "myself");
				stateView.setDistance(getDistance(userState, longitude,latitude, true));
				stateView.setPageIndex(pageIndex++);
			} else {
				stateView = this.getStateView(userState,
						authenticationToken.getUserid(), "others");
				stateView.setDistance(getDistance(userState, longitude,
						latitude, true));
				stateView.setPageIndex(pageIndex++);
			}
			// Collections.sort(petUserViews);
			userViews.add(stateView);
		}
		if (userViews.size() < 5 && pageIndex == 0) {
			List<UserStates> userStates2 = UserStates.findUserStateWithType("1").getResultList();
			// 用户地理位置视图遍历查询，获取附近用户状态视图
			for (UserStates userState : userStates2) {
				// 获取状态视图
				StateView stateView = new StateView();
				if (userState.getPetUserid() == authenticationToken.getUserid()) {
					stateView = this.getStateView(userState,
							authenticationToken.getUserid(), "myself");
					stateView.setPageIndex(pageIndex++);
				} else {
					stateView = this.getStateView(userState,
							authenticationToken.getUserid(), "others");
					stateView.setPageIndex(pageIndex++);
				}
				// Collections.sort(petUserViews);
				userViews.add(stateView);
			}
		}
		if(userViews!=null && userViews.size()>0){
			Collections.sort(userViews);
			logger.debug("suerViews output : "+new Gson().toJson(userViews));
			return userViews;
		}
		logger.debug("suerViews output : NULL 有问题");
		return null;
	}

	/**
	 * 获取评价视图
	 * 
	 * @param ustate
	 * @return
	 */
	private List<ReplyView> getReplyViews(UserStates ustate, long petUserid,
			int lastReplyid, String whos) {
		List<Reply> replys = Reply.findReplysByUserStateid(ustate, petUserid,
				lastReplyid, whos).getResultList();
		List<ReplyView> replyViews = new ArrayList<ReplyView>();
		// 将评论的list封装到评论视图
		for (int i = 0; i < replys.size(); i++) {
			ReplyView replyView = new ReplyView();
			replyView.setMsg(replys.get(i).getMsg());
			replyView.setId(replys.get(i).getId());
			replyView.setPetUserView(getPetUserviewWithAlias(replys.get(i)
					.getPetUserid(), petUserid));// 封装发表评论的用户
			replyView.setReplyTime(replys.get(i).getReplyTime());
			replyView.setUserStateid(replys.get(i).getUserStateid());// 将评价中的状态id插入
			replyView.setReplyCommentViews(getReplyCommentsView(replys.get(i),
					-1, petUserid));// 根据获取的评价获取“回复评价”视图
			replyView.setPageIndex(i);
			replyView.setCountReplyComment(ReplyComments
					.countReplyCommentsesByReplyid(replys.get(i).getId()));
			replyViews.add(replyView);
		}
		return replyViews;
	}

	/**
	 * 获取回复评论视图
	 * 
	 * @param reply
	 * @param pageIndex
	 * @return
	 */
	private List<ReplyCommentViews> getReplyCommentsView(Reply reply,
			long lastComment, long petUserid) {
		// 根据评价的id查询回复评价表
		List<ReplyComments> replyComments = ReplyComments
				.findReplyCommentsesByReplyid(reply.getId(), lastComment)
				.getResultList();
		List<ReplyCommentViews> replyCommentViews = new ArrayList<ReplyCommentViews>();
		// 将回复评论的list封装到回复评论视图
		for (int i = 0; i < replyComments.size(); i++) {
			ReplyCommentViews replyCommentView = new ReplyCommentViews();
			replyCommentView.setId(replyComments.get(i).getId());
			replyCommentView.setCommentsMsg(replyComments.get(i)
					.getCommentsMsg());
			replyCommentView.setCommentTime(replyComments.get(i)
					.getCommentTime());
			replyCommentView.setUserStateid(reply.getUserStateid());// 插入动态id
			replyCommentView.setReplyid(reply.getId());// 插入评论id
			replyCommentView.setReplyUserView(getPetUserviewWithAlias(
					replyComments.get(i).getReplyUserid(), petUserid));// 插入（被）评价用户信息
			replyCommentView.setCommentUserView(getPetUserviewWithAlias(
					replyComments.get(i).getCommentUserid(), petUserid));// 插入回复评价用户信息
			replyCommentView.setPageIndex(i);
			replyCommentViews.add(replyCommentView);
		}
		return replyCommentViews;
	}

	/**
	 * 删图片
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleDelOneImg(ClientRequest clientRequest) {
		try {
			AuthenticationToken authenticationToken = AuthenticationToken
					.findAuthenticationToken(clientRequest.getToken());
			PetUser petUser = PetUser.findPetUser(authenticationToken
					.getUserid());
			return PetUtil.delOneImg(petUser.getImg(),
					PetUtil.getParameter(clientRequest, "delImgid"));
		} catch (Exception e) {
			return "图片删除异常";
		}
	}


	/**
	 * 
	 * 验证手机发送的校验码
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handldVerifyCode(ClientRequest clientRequest) {
		String phoneNum = PetUtil.getParameter(clientRequest, "phoneNum");
		String verificationCode = PetUtil.getParameter(clientRequest,
				"verificationCode");
		Verification verification = new Verification();
		verification.setPhoneNum(phoneNum);
		verification.setVerificationCode(verificationCode);
		return verificationService.getVerfication(verification);
	}

	/**
	 * 
	 * 获取手机号生成验证码并向第三方发送post请求
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handldGetVerificationCode(ClientRequest clientRequest) {
		try {
			String phoneNum = PetUtil.getParameter(clientRequest, "phoneNum");
			Verification verification = new Verification();
			String verificationCode = verificationService.getCode();
			List<Verification> verifications = Verification
					.findVerificationsByPhoneNumEquals(phoneNum)
					.getResultList();
			if (verifications.size() > 0) {
				verification = verifications.get(0);
				verification.setVerificationCode(verificationCode);
				verification.merge();
			} else {
				verification.setPhoneNum(phoneNum);
				verification.setVerificationCode(verificationCode);
				verification.persist();
			}
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("userId",Bootstrap.configWatcher.getProperty(PetConstants.SMS_USERNAME, null));
			map.put("password", Bootstrap.configWatcher.getProperty(PetConstants.SMS_PASSWORD, null));
			map.put("pszMobis", phoneNum);
			map.put("pszMsg", verificationCode);
			map.put("iMobiCount", "1");
			map.put("pszSubPort", "***********");
			HttpRequestProxy.doPost(Bootstrap.configWatcher.getProperty(PetConstants.SMS_PATH, null),map, "utf-8");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	/**
	 * 根据版本号提供更新的文件地址
	 * 
	 * @param clientRequest
	 * @param response
	 */
	private Object handleUpdateVersion(ClientRequest clientRequest) {
		return petFileService.getNewVersion(clientRequest.getImei());
	}


	/**
	 * 获取好友信息
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleGetFriends(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetUser petUser = new PetUser();
		// long id = authenticationToken.getUserid();
		petUser.setId(authenticationToken.getUserid());
		petUser.setUsername(PetUtil.getParameter(clientRequest, "username"));
		List<PetUserWithPetInfoView> friendsViews = new ArrayList<PetUserWithPetInfoView>();
		friendsViews = petUserService.getFriendsView(petUser, Integer
				.parseInt(PetUtil.getParameter(clientRequest, "pageIndex")));
		return friendsViews;
	}

	/**
	 * 根据用户名获取好友信息 ps：用于在聊天记录中获取好友信息
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleGetOneFriend(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetUser petUser = new PetUser();
		// long id = authenticationToken.getUserid();
		// petUser.setId(authenticationToken.getUserid());
		petUser.setUsername(PetUtil.getParameter(clientRequest, "username"));
		List<PetUserWithPetInfoView> friendsViews = new ArrayList<PetUserWithPetInfoView>();
		friendsViews = petUserService.getOneFriend(petUser,
				authenticationToken.getUserid());
		return friendsViews;
	}

	/**
	 * 分页获取用户信息
	 * 
	 * @param clientRequest
	 */
	private Object handleGetUser(ClientRequest clientRequest) {
		PetUser petUser = new PetUser();
		AuthenticationToken.findAuthenticationToken(clientRequest.getToken());
		petUser.setUsername(PetUtil.getParameter(clientRequest, "unname"));
		List<PetUserWithPetInfoView> petUserViews = new ArrayList<PetUserWithPetInfoView>();
		petUserViews = petUserService.getPetUserViews(petUser, Integer
				.parseInt(PetUtil.getParameter(clientRequest, "pageIndex")));
		return petUserViews;
	}

	private Object handleGetUserInfo(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		return getPetUserview(authenticationToken.getUserid());
	}

	private Object handleSavePetInfo(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetInfo petInfo = new PetInfo();
		petInfo.setUserid(authenticationToken.getUserid());
		petInfo.setType(PetUtil.getParameterLong(clientRequest, "type"));
		petInfo.setNickname(PetUtil.getParameter(clientRequest, "nickname"));
		petInfo.setBirthdate(PetUtil.getParameter(clientRequest, "birthdate"));
		petInfo.setGender(PetUtil.getParameter(clientRequest, "gender"));
		petInfo.setTrait(PetUtil.getParameter(clientRequest, "trait"));// 添加了特点属性
		petInfo.setImg(PetUtil.getParameter(clientRequest, "img"));
		petInfo.persist();
		return getPetInfoView(authenticationToken.getUserid(), petInfo);
	}

	/**
	 * 修改宠物信息
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleUpdatePetInfo(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetInfo petInfo = PetInfo.findPetInfo(PetUtil.getParameterLong(
				clientRequest, "id"));
		// petInfo.setUserid(authenticationToken.getUserid());
		// petInfo.setId(getParameterLong(clientRequest, "id"));
		petInfo.setType(PetUtil.getParameterLong(clientRequest, "type"));
		petInfo.setNickname(PetUtil.getParameter(clientRequest, "nickname"));
		petInfo.setBirthdate(PetUtil.getParameter(clientRequest, "birthdate"));
		petInfo.setGender(PetUtil.getParameter(clientRequest, "gender"));
		// petInfo.setVersion(getParameterInteger(clientRequest,
		// "version"));//version字段基于hibernate乐观锁机制，不能强行赋值
		petInfo.setTrait(PetUtil.getParameter(clientRequest, "trait"));// 添加了特点属性
		if (org.apache.commons.lang3.StringUtils.isEmpty(petInfo.getImg())) {
			petInfo.setImg(PetUtil.getParameter(clientRequest, "img"));
		} else {
			petInfo.setImg(PetUtil.getParameter(clientRequest, "img"));
		}
		petInfo.merge();
		return getPetInfoView(authenticationToken.getUserid(), petInfo);
	}

	/**
	 * 
	 * 重置密码
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleResetPassword(ClientRequest clientRequest) {
		String password = PetUtil.getParameter(clientRequest, "password");
		String phoneNumber = PetUtil.getParameter(clientRequest, "phonenumber");
		PetUser petUser = PetUser.findPetUsersByUsername(phoneNumber)
				.getSingleResult();
		petUser.setPassword(this.passwordEncoder.encodePassword(password,
				this.salt));
		try {
			petUser.merge();
		} catch (DataAccessException e) {
			return false;
		}
		return true;
	}

	/**
	 * 删除宠物信息
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleDelPetInfo(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetInfo petInfo = PetInfo.findPetInfo(PetUtil.getParameterLong(
				clientRequest, "id"));
		petInfo.remove();
		return getPetInfoView(authenticationToken.getUserid(), petInfo);
	}

	/**
	 * 根据用户id和姓名查询用户
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleSelectUserByNameOrUserId(ClientRequest clientRequest) {
		AuthenticationToken.findAuthenticationToken(clientRequest.getToken());// 校验token
		PetUser petUser = new PetUser();
		// petUser.setUserId(getParameterLong(clientRequest, "userid"));
		// petUser.setUsername(getParameter(clientRequest, "username"));
		petUser.setUsername(PetUtil.getParameter(clientRequest, "usernameOrId"));
		return petUserService.selectUserByNameOrUserId(petUser);
	}

	/**
	 * 根据用户id和姓名查询用户
	 * 
	 * @param clientRequest
	 * @return
	 */
	private Object handleSelectUserViewByUserName(ClientRequest clientRequest) {
		AuthenticationToken.findAuthenticationToken(clientRequest.getToken());// 校验token
		PetUser petUser = PetUser.findPetUsersByUsername(
				PetUtil.getParameter(clientRequest, "username"))
				.getSingleResult();
		PetUserView petUserView = getPetUserview(petUser.getId());
		return petUserView;
	}

	@RequestMapping("upload")
	public @ResponseBody
	Object upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			return "";
		}
		PetFile petFile = new PetFile();
		petFile.setName(file.getOriginalFilename());
		petFile.setCreateDate(new Date());
		petFile.persist();
		FileOutputStream fileOS = new FileOutputStream(
				uploadService.getUploadUrl() + File.separator + petFile.getId());
		fileOS.write(file.getBytes());
		fileOS.close();
		return petFile.getId();
	}

	/**
	 * 开机下载图片功能
	 * 
	 * @param clientRequest
	 * @param response
	 * @废弃
	 */
	public void handleUpdateOpenImg(ClientRequest clientRequest,
			HttpServletResponse response) {
		PetFile petFile = new PetFile();
		petFile.setId(Long.parseLong(PetUtil.getParameter(clientRequest, "id")));
		petFile.setType(PetUtil.getParameterInteger(clientRequest, "type"));
		if (petFileService.selectPetFile(petFile).size() > 0) {
			petFile = petFileService.selectPetFile(petFile).get(0);
			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");

			response.setHeader("Content-Disposition", "attachment;fileName="
					+ petFile.getName());
			try {
				InputStream inputStream = new FileInputStream(
						uploadService.getUploadUrl() + File.separator
								+ petFile.getId());
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					os.write(b, 0, length);
				}
				inputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private Object handleOpen(ClientRequest clientRequest) {
		OpenResponse openResponse = new OpenResponse();
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		if (authenticationToken != null) {
			int type = PetUtil.getParameterInteger(clientRequest, "type");
			openResponse.setPetUserView(getPetUserview(
					authenticationToken.getUserid(), type));
			openResponse.setChatserver(chatServerService
					.getAvailableServer(authenticationToken.getUserid()));
			openResponse.setToken(clientRequest.getToken());
		}
		String version = clientRequest.getVersion();
		if (null == version || "" == version) {
			return null;
		}
		List<PetVersion> upVersions = new ArrayList<PetVersion>();
		upVersions.add(petFileService.getNewVersion(clientRequest.getImei()));
		openResponse.setForceUpdate(false);
		openResponse.setNeedUpdate(false);
		if (Double.parseDouble(upVersions.get(0).getPetVersion()) > Double.parseDouble(version)) {
			openResponse.setNeedUpdate(true);
			openResponse.setUpdatefiles(upVersions);
			openResponse.setIosurl(upVersions.get(0).getIosurl());
		}
		return openResponse;
	}


	private Object handleSaveUserInfo(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetUser petUser = PetUser.findPetUser(authenticationToken.getUserid());
		petUser.setNickname(PetUtil.getParameter(clientRequest, "nickname"));
		petUser.setGender(PetUtil.getParameter(clientRequest, "gender"));
		petUser.setSignature(PetUtil.getParameter(clientRequest, "signature"));
		petUser.setBirthdate(PetUtil.getParameter(clientRequest, "birthdate"));
		petUser.setHobby(PetUtil.getParameter(clientRequest, "hobby"));
		petUser.setImg(PetUtil.getParameter(clientRequest, "img"));
		petUser.merge();
		return getPetUserview(petUser.getId());
	}

	private Object handleSaveUserInfo2(ClientRequest clientRequest) {// v2
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		PetUser petUser = PetUser.findPetUser(authenticationToken.getUserid());
		petUser.setNickname(PetUtil.getParameter(clientRequest, "nickname"));
		petUser.setGender(PetUtil.getParameter(clientRequest, "gender"));
		petUser.setSignature(PetUtil.getParameter(clientRequest, "signature"));
		petUser.setBirthdate(PetUtil.getParameter(clientRequest, "birthdate"));
		petUser.setHobby(PetUtil.getParameter(clientRequest, "hobby"));
		petUser.setImg(PetUtil.getParameter(clientRequest, "img"));
		petUser.setCity(PetUtil.getParameter(clientRequest, "city"));
		petUser.merge();
		return getPetUserview(petUser.getId());
	}

	private Object handleIsUsernameInuse(ClientRequest clientRequest) {
		String username = PetUtil.getParameter(clientRequest, "username");
		if (!StringUtils.hasLength(username)) {
			return true;
		}
		List<PetUser> resultList = PetUser.findPetUsersByUsername(username)
				.getResultList();
		if (resultList.size() > 0) {
			return true;
		}
		return false;
	}

	private Object handleNearbyUser(ClientRequest clientRequest)
			throws ParseException {
		long userId = 0;

		try {
			String token = clientRequest.getToken();
			AuthenticationToken authenticationToken = AuthenticationToken
					.findAuthenticationToken(token);
			userId = authenticationToken.getUserid();

		} catch (Exception e) {
			double longitude = Double.parseDouble(clientRequest.getParams().get("longitude").toString());
			double latitude = Double.parseDouble(clientRequest.getParams().get("latitude").toString());
			int pageIndex = PetUtil.getParameterInteger(clientRequest,"pageIndex");
			// String city = getParameter(clientRequest, "city");
			List<UserLocation> nearbyUser = this.locationService.getNearbyUser(longitude, latitude,PetUtil.getParameter(clientRequest, "gender"),
					PetUtil.getParameterInteger(clientRequest, "type"),pageIndex,"0");
			
			List<PetUserView> petUserViews = new ArrayList<PetUserView>();
			for (UserLocation userLocation : nearbyUser) {
				PetUserView petUserview = this.getPetUserview(userLocation.getUserid());
				petUserview.setDistance(getDistance(userLocation, longitude,latitude, true));
				petUserview.setPageIndex(pageIndex++);
				if (null != userLocation.getCreateDate()) {
					petUserview.setCreateDate(userLocation.getCreateDate());
				}
				petUserViews.add(petUserview);
			}
			
			if(pageIndex==0&&nearbyUser.size()<20){
				List<UserLocation> nearByUserFraudulents = this.locationService.getNearbyUser(longitude, latitude, PetUtil.getParameter(clientRequest, "gender"), PetUtil
								.getParameterInteger(clientRequest, "type"),nearbyUser.size(), "1");
				int distance = 400;
				for (UserLocation userLocation : nearByUserFraudulents) {
					
					PetUserView petUserview = this.getPetUserview(userLocation
							.getUserid());
//					if(distance<=1000){
//						return String.valueOf((int) distance / 100 * 100 + 100) + "米以内";
//					}else{
//						petUserview.setDistance((distance / 100 * 100/1000 + 0.1) + "千米以内");
//					}
					petUserview.setDistance(distance / 100 * 100 + 100
							+ "米以内");
					distance = distance + 50;
					petUserview.setPageIndex(pageIndex++);
					if (null != userLocation.getCreateDate()) {
						petUserview.setCreateDate(userLocation.getCreateDate());
					}
					petUserViews.add(petUserview);
				}
			}
			Collections.sort(petUserViews);
			return petUserViews;
		}

		if (userId != 0) {
			double longitude = Double.parseDouble(clientRequest.getParams()
					.get("longitude").toString());
			double latitude = Double.parseDouble(clientRequest.getParams()
					.get("latitude").toString());
			int pageIndex = PetUtil.getParameterInteger(clientRequest,
					"pageIndex");
			List<UserLocation> nearbyUser = this.locationService.getNearbyUser(longitude, latitude,PetUtil.getParameter(clientRequest, "gender"),
					PetUtil.getParameterInteger(clientRequest, "type"),pageIndex,userId,"0");
			
			List<PetUserView> petUserViews = new ArrayList<PetUserView>();
			for (UserLocation userLocation : nearbyUser) {
				PetUserView petUserview = this.getPetUserview(userLocation.getUserid());
				petUserview.setDistance(getDistance(userLocation, longitude,latitude, true));
				petUserview.setPageIndex(pageIndex++);
				if (null != userLocation.getCreateDate()) {
					petUserview.setCreateDate(userLocation.getCreateDate());
				}
				petUserViews.add(petUserview);
			}
			
			if(pageIndex==0&&nearbyUser.size()<20){
				List<UserLocation> nearByUserFraudulents = this.locationService.getNearbyUser(longitude, latitude, PetUtil.getParameter(clientRequest, "gender"), PetUtil
								.getParameterInteger(clientRequest, "type"),nearbyUser.size(), "1");
				int distance = 400;
				for (UserLocation userLocation : nearByUserFraudulents) {
					
					PetUserView petUserview = this.getPetUserview(userLocation
							.getUserid());
					petUserview.setDistance(distance / 100 * 100 + 100
							+ "米以内");
					distance = distance + 50;
					petUserview.setPageIndex(pageIndex++);
					if (null != userLocation.getCreateDate()) {
						petUserview.setCreateDate(userLocation.getCreateDate());
					}
					petUserViews.add(petUserview);
				}
			}
			Collections.sort(petUserViews);
			
			return petUserViews;
		}
		return userId;
	}

	/**
	 * 查询附近用户根据宠物过滤
	 * 
	 * @param clientRequest
	 * @return
	 * @throws ParseException
	 */
	private Object handleNearbyUserAndPet(ClientRequest clientRequest)
			throws ParseException {
		long userId = 0;

		try {
			String token = clientRequest.getToken();
			AuthenticationToken authenticationToken = AuthenticationToken
					.findAuthenticationToken(token);
			userId = authenticationToken.getUserid();

		} catch (Exception e) {
			double longitude = Double.parseDouble(clientRequest.getParams()
					.get("longitude").toString());
			double latitude = Double.parseDouble(clientRequest.getParams()
					.get("latitude").toString());
			// String city = getParameter(clientRequest, "city");
			int pageIndex = PetUtil.getParameterInteger(clientRequest,
					"pageIndex");
			List<UserLocation> nearbyUser = this.locationService.getNearbyUser(longitude, latitude,PetUtil.getParameter(clientRequest, "gender"),
					PetUtil.getParameterInteger(clientRequest, "type"),pageIndex,"0");
			
			List<PetUserView> petUserViews = new ArrayList<PetUserView>();
			for (UserLocation userLocation : nearbyUser) {
				PetUserView petUserview = this.getPetUserviewByPet(userLocation.getUserid(),PetUtil.getParameterInteger(clientRequest, "type"));
				petUserview.setDistance(getDistance(userLocation, longitude,latitude, true));
				petUserview.setPageIndex(pageIndex++);
				if (null != userLocation.getCreateDate()) {
					petUserview.setCreateDate(userLocation.getCreateDate());
				}
				petUserViews.add(petUserview);
			}
			
			if(pageIndex==0&&nearbyUser.size()<20){
				List<UserLocation> nearByUserFraudulents = this.locationService.getNearbyUser(longitude, latitude, PetUtil.getParameter(clientRequest, "gender"), PetUtil
								.getParameterInteger(clientRequest, "type"),nearbyUser.size(), "1");
				int distance = 0;
				for (UserLocation userLocation : nearByUserFraudulents) {
					
					PetUserView petUserview = this.getPetUserviewByPet(userLocation.getUserid(),PetUtil.getParameterInteger(clientRequest, "type"));
					petUserview.setDistance(distance / 100 * 100 + 100
							+ "米以内");
					distance = distance + 50;
					petUserview.setPageIndex(pageIndex++);
					if (null != userLocation.getCreateDate()) {
						petUserview.setCreateDate(userLocation.getCreateDate());
					}
					petUserViews.add(petUserview);
				}
			}
			Collections.sort(petUserViews);
			return petUserViews;
		}

		if (userId != 0) {
			double longitude = Double.parseDouble(clientRequest.getParams()
					.get("longitude").toString());
			double latitude = Double.parseDouble(clientRequest.getParams()
					.get("latitude").toString());
			// String city = getParameter(clientRequest, "city");
			int pageIndex = PetUtil.getParameterInteger(clientRequest,
					"pageIndex");
			List<UserLocation> nearbyUser = this.locationService.getNearByUserAndPet(longitude, latitude,PetUtil.getParameter(clientRequest, "gender"),
					PetUtil.getParameterInteger(clientRequest, "type"),pageIndex,userId,"0");
			
			List<PetUserView> petUserViews = new ArrayList<PetUserView>();
			for (UserLocation userLocation : nearbyUser) {
				PetUserView petUserview = this.getPetUserviewByPet(userLocation.getUserid(),PetUtil.getParameterInteger(clientRequest, "type"));
				petUserview.setDistance(getDistance(userLocation, longitude,latitude, true));
				petUserview.setPageIndex(pageIndex++);
				if (null != userLocation.getCreateDate()) {
					petUserview.setCreateDate(userLocation.getCreateDate());
				}
				petUserViews.add(petUserview);
			}
			
			if(pageIndex==0&&nearbyUser.size()<20){
				List<UserLocation> nearByUserFraudulents = this.locationService.getNearbyUser(longitude, latitude, PetUtil.getParameter(clientRequest, "gender"), PetUtil
								.getParameterInteger(clientRequest, "type"),nearbyUser.size(), "1");
				int distance = 400;
				for (UserLocation userLocation : nearByUserFraudulents) {
					
					PetUserView petUserview = this.getPetUserviewByPet(userLocation.getUserid(),PetUtil.getParameterInteger(clientRequest, "type"));
					petUserview.setDistance(distance / 100 * 100 + 100
							+ "米以内");
					distance = distance + 50;
					petUserview.setPageIndex(pageIndex++);
					if (null != userLocation.getCreateDate()) {
						petUserview.setCreateDate(userLocation.getCreateDate());
					}
					petUserViews.add(petUserview);
				}
			}
			Collections.sort(petUserViews);
			return petUserViews;
		}
		return userId;
	}

	/**
	 * 获取距离
	 * 
	 * @param userLocation
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	private String getDistance(UserLocation userLocation, double longitude,
			double latitude, boolean ifGroup) {
		double uLongitude = userLocation.getLongitude();
		double uLatitude = userLocation.getLatitude();
		double distance = PetUtil.getDistance(uLongitude, uLatitude, longitude,
				latitude);
		// if (!ifGroup||distance<100) {
		// return String.valueOf((int) distance) + "米";
		// }else{
		// return String.valueOf((int) distance / 50 * 50) + "米";
		// }
		if(distance<=1000){
			return String.valueOf((int) distance / 100 * 100 + 100) + "米以内";
		}else{
			return String.valueOf((int) distance / 100 * 100/1000 + 0.1) + "千米以内";
		}
	}

	private String getDistance(UserStates userStates, double longitude,
			double latitude, boolean ifGroup) {
		double uLongitude = userStates.getLongitude();
		double uLatitude = userStates.getLatitude();
		double distance = PetUtil.getDistance(uLongitude, uLatitude, longitude,
				latitude);
		if(distance<=1000){
			return String.valueOf((int) distance / 100 * 100 + 100) + "米以内";
		}else{
			return String.valueOf((int) distance / 100 * 100/1000 + 0.1) + "千米以内";
		}
		
	}

	private Object handleGetChatServerList(ClientRequest clientRequest) {
		AuthenticationToken authenticationToken = AuthenticationToken
				.findAuthenticationToken(clientRequest.getToken());
		return chatServerService.getAvailableServer(authenticationToken
				.getUserid());
	}

	private Object handlelogin(ClientRequest clientRequest) {
		String username = PetUtil.getParameter(clientRequest, "username");
		String password = PetUtil.getParameter(clientRequest, "password");
		PetUser petUser;
		try {
			List<PetUser> resultList = PetUser.findPetUsersByUsername(username)
					.getResultList();
			if (resultList.size() <= 0) {
				return "用户不存在";
			}
			petUser = PetUser.findPetUsersByUsername(username)
					.getSingleResult();
		} catch (NoResultException e) {
			throw new PetException("用户不存在");
		}

		if (!petUser.getPassword().equals(
				this.passwordEncoder.encodePassword(password, this.salt))) {
			return "密码错误";
		}
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setExpire(-1);
		authenticationToken.setToken(UUID.randomUUID().toString());
		authenticationToken.setUserid(petUser.getId());
		authenticationToken.setCreateDate(new Date());
		authenticationToken.persist();
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAuthenticationToken(authenticationToken);
		loginResponse.setChatserver(chatServerService
				.getAvailableServer(authenticationToken.getUserid()));

		return loginResponse;
	}

	private Object handlelogin2(ClientRequest clientRequest) {
		String username = PetUtil.getParameter(clientRequest, "username");
		String password = PetUtil.getParameter(clientRequest, "password");
		String deviceToken = PetUtil.getParameter(clientRequest, "deviceToken");
		PetUser petUser;
		try {
			List<PetUser> resultList = PetUser.findPetUsersByUsername(username)
					.getResultList();
			if (resultList.size() <= 0) {
				return "用户不存在";
			}
			petUser = PetUser.findPetUsersByUsername(username)
					.getSingleResult();
			petUser.setDeviceToken(deviceToken);
			petUser.merge();
		} catch (NoResultException e) {
			throw new PetException("用户不存在");
		}

		if (!petUser.getPassword().equals(
				this.passwordEncoder.encodePassword(password, this.salt))) {
			return "密码错误";
		}
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setExpire(-1);
		authenticationToken.setToken(UUID.randomUUID().toString());
		authenticationToken.setUserid(petUser.getId());
		authenticationToken.setCreateDate(new Date());
		authenticationToken.persist();
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAuthenticationToken(authenticationToken);
		loginResponse.setChatserver(chatServerService
				.getAvailableServer(authenticationToken.getUserid()));

		return loginResponse;
	}

	private Object handleRegister(ClientRequest clientRequest) {

		String username = PetUtil.getParameter(clientRequest, "username");
		String password = PetUtil.getParameter(clientRequest, "password");
		String phoneNumber = PetUtil.getParameter(clientRequest, "phonenumber");
		String email = PetUtil.getParameter(clientRequest, "email");
		PetUser petUser = new PetUser();
		petUser.setUsername(username);
		petUser.setNickname(username);// 移动端只传过来username,保存时将用户名同时保存到昵称里
		petUser.setPhoneNumber(phoneNumber);
		petUser.setEmail(email);
		petUser.setPassword(this.passwordEncoder.encodePassword(password,
				this.salt));
		petUser.setIfFraudulent("0");
		try {
			petUser.setCreateTime(PetUtil.getParameterDate(clientRequest,
					"createTime"));
			petUser.persist();
		} catch (DataAccessException e) {
			throw new DuplicatedUsernameException(e.getMessage());
		} catch (ParseException e) {
			throw new DuplicatedUsernameException(e.getMessage());
		}
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setExpire(-1);
		authenticationToken.setToken(UUID.randomUUID().toString());
		authenticationToken.setUserid(petUser.getId());
		authenticationToken.setCreateDate(new Date());
		authenticationToken.persist();
		return authenticationToken;
	}

	private Object handleRegister2(ClientRequest clientRequest) {

		// String username = getParameter(clientRequest, "username");
		String password = PetUtil.getParameter(clientRequest, "password");
		String phoneNumber = PetUtil.getParameter(clientRequest, "phonenumber");
		// String email = getParameter(clientRequest, "email");
		String nickname = PetUtil.getParameter(clientRequest, "nickname");
		String birthdate = PetUtil.getParameter(clientRequest, "birthdate");
		String gender = PetUtil.getParameter(clientRequest, "gender");
		String city = PetUtil.getParameter(clientRequest, "city");
		String signature = PetUtil.getParameter(clientRequest, "signature");
		String img = PetUtil.getParameter(clientRequest, "img");
		String hobby = PetUtil.getParameter(clientRequest, "hobby");
		String deviceToken = PetUtil.getParameter(clientRequest, "deviceToken");
		PetUser petUser = new PetUser();
		petUser.setUsername(phoneNumber);// 将手机号作为用户名
		petUser.setSignature(signature);
		// petUser.setEmail(email);
		petUser.setBirthdate(birthdate);
		petUser.setGender(gender);
		petUser.setCity(city);
		petUser.setNickname(nickname);
		petUser.setPhoneNumber(phoneNumber);
		petUser.setHobby(hobby);
		petUser.setImg(img);
		petUser.setIfFraudulent("0");
		petUser.setDeviceToken(deviceToken);
		petUser.setPassword(this.passwordEncoder.encodePassword(password,
				this.salt));
		try {
			petUser.setCreateTime(new Date(System.currentTimeMillis()));
			petUser.persist();
		} catch (DataAccessException e) {
			throw new DuplicatedUsernameException(e.getMessage());
		}
		AuthenticationToken authenticationToken = new AuthenticationToken();
		authenticationToken.setExpire(-1);
		authenticationToken.setToken(UUID.randomUUID().toString());
		authenticationToken.setUserid(petUser.getId());
		authenticationToken.setCreateDate(new Date());
		authenticationToken.persist();
		return authenticationToken;
	}

	/**
	 * 获取用户视图
	 * 
	 * @param userid
	 * @return
	 */
	private PetUserView getPetUserview(long userid) {
		PetUser petUser = PetUser.findPetUser(userid);
		PetUserView petUserView = new PetUserView();
		petUserView.setUserid(petUser.getId());
		petUserView.setSignature(petUser.getSignature());
		petUserView.setUsername(petUser.getUsername());
		petUserView.setNickname(petUser.getNickname());
		petUserView.setGender(petUser.getGender());
		petUserView.setBirthdate(petUser.getBirthdate());
		petUserView.setHobby(petUser.getHobby());
		petUserView.setCity(petUser.getCity());
		petUserView.setImg(petUser.getImg());
		petUserView.setIfIZaned(UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid, -1, -1,
						-1, "2").getResultList().isEmpty() ? "0" : "1");
		petUserView.setCountZan(UserZan.countUserZans(userid, -1, -1, -1, "2"));
		List<PetInfoView> resultList = getPetInfoView(userid);// 查询宠物信息，返回宠物列表
		petUserView.setPetInfoViews(resultList);// 插入petUserView的PetInfos中
		return petUserView;
	}

	/**
	 * 获取用户视图
	 * 
	 * @param userid
	 * @return
	 */
	private PetUserView getPetUserviewWithAlias(long userid, long myid) {
		PetUser petUser = PetUser.findPetUser(userid);
		PetUserView petUserView = new PetUserView();
		petUserView.setUserid(petUser.getId());
		petUserView.setSignature(petUser.getSignature());
		petUserView.setUsername(petUser.getUsername());
		petUserView.setNickname(petUser.getNickname());
		petUserView.setGender(petUser.getGender());
		petUserView.setBirthdate(petUser.getBirthdate());
		petUserView.setHobby(petUser.getHobby());
		petUserView.setCity(petUser.getCity());
		petUserView.setImg(petUser.getImg());
		petUserView.setAliasName(userFriendshipService.getAliasName(userid,
				myid));
		petUserView.setIfIZaned(UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid, -1, -1,
						-1, "2").getResultList().isEmpty() ? "0" : "1");
		petUserView.setCountZan(UserZan.countUserZans(userid, -1, -1, -1, "2"));
		List<PetInfoView> resultList = getPetInfoView(userid);// 查询宠物信息，返回宠物列表
		petUserView.setPetInfoViews(resultList);// 插入petUserView的PetInfos中
		return petUserView;
	}

	/**
	 * 根据用户id宠物id获取宠物，并封装成view
	 * 
	 * @param userid
	 * @return
	 */
	private PetUserView getPetUserviewByPet(long userid, long type) {
		PetUser petUser = PetUser.findPetUser(userid);
		PetUserView petUserView = new PetUserView();
		petUserView.setUserid(petUser.getId());
		petUserView.setSignature(petUser.getSignature());
		petUserView.setUsername(petUser.getUsername());
		petUserView.setNickname(petUser.getNickname());
		petUserView.setGender(petUser.getGender());
		petUserView.setBirthdate(petUser.getBirthdate());
		petUserView.setHobby(petUser.getHobby());
		petUserView.setImg(petUser.getImg());
		petUserView.setCity(petUser.getCity());
		petUserView.setIfIZaned(UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid, -1, -1,
						-1, "2").getResultList().isEmpty() ? "0" : "1");
		petUserView.setCountZan(UserZan.countUserZans(userid, -1, -1, -1, "2"));
		List<PetInfoView> resultList = getPetInfoView(userid);// 查询宠物信息，返回宠物列表
		petUserView.setPetInfoViews(resultList);// 插入petUserView的PetInfos中
		return petUserView;
	}

	/**
	 * 重载此方法获取一个城市内的用户信息
	 * 
	 * @param userid
	 * @param city
	 * @return
	 */
	// private PetUserView getPetUserview(long userid, String city) {
	// PetUser petUser = petUserService.findPetUser(userid, "假的");
	// PetUserView petUserView = new PetUserView();
	// petUserView.setUserid(petUser.getId());
	// petUserView.setSignature(petUser.getSignature());
	// petUserView.setUsername(petUser.getUsername());
	// petUserView.setNickname(petUser.getNickname());
	// petUserView.setGender(petUser.getGender());
	// petUserView.setBirthdate(petUser.getBirthdate());
	// petUserView.setHobby(petUser.getHobby());
	// petUserView.setImg(petUser.getImg());
	// petUserView.setIfIZaned(UserZan.findUserZansByUseridAndUserStateidAndZanUserid(userid,-1,
	// -1,-1,"2").getResultList().isEmpty() ? "0" : "1");
	// petUserView.setCountZan(UserZan.countUserZans(userid,-1, -1,-1,"2"));
	//
	// List<PetInfoView> resultList = getPetInfoView(userid);// 查询宠物信息，返回宠物列表
	// petUserView.setPetInfoViews(resultList);// 插入petUserView的PetInfos中
	// return petUserView;
	// }

	/**
	 * 获取宠物的信息视图
	 * 
	 * @param userid
	 * @return
	 */
	private List<PetInfoView> getPetInfoView(long userid) {
		List<PetInfo> petInfos = PetInfo.findPetInfoesByUserid(userid)
				.getResultList();
		List<PetInfoView> petInfoViews = new ArrayList<PetInfoView>();
		for (PetInfo petInfo : petInfos) {
			PetInfoView petInfoView = new PetInfoView();
			petInfoView.setBirthdate(petInfo.getBirthdate());
			petInfoView.setGender(petInfo.getGender());
			petInfoView.setImg(petInfo.getImg());
			petInfoView.setNickname(petInfo.getNickname());
			petInfoView.setTrait(petInfo.getTrait());
			petInfoView.setType(petInfo.getType());
			petInfoView.setUserid(petInfo.getUserid());
			petInfoView.setId(petInfo.getId());
			petInfoView.setCountZan(UserZan.countUserZans(userid, -1, -1,
					petInfo.getId(), "1"));
			petInfoView.setIfIZaned(UserZan.findUserZansByUseridAndUserStateidAndZanUserid(
									userid, -1, -1, petInfo.getId(), "1")
							.getResultList().isEmpty() ? "0" : "1");
			petInfoViews.add(petInfoView);
		}
		return petInfoViews;
	}

	/**
	 * 根据petInfo封装成petInfoView
	 * 
	 * @return
	 */
	private PetInfoView getPetInfoView(long userid, PetInfo petInfo) {
		PetInfoView petInfoView = new PetInfoView();
		petInfoView.setBirthdate(petInfo.getBirthdate());
		petInfoView.setGender(petInfo.getGender());
		petInfoView.setImg(petInfo.getImg());
		petInfoView.setNickname(petInfo.getNickname());
		petInfoView.setTrait(petInfo.getTrait());
		petInfoView.setType(petInfo.getType());
		petInfoView.setUserid(petInfo.getUserid());
		petInfoView.setId(petInfo.getId());
		petInfoView.setCountZan(UserZan.countUserZans(userid, -1, -1,
				petInfo.getId(), "1"));
		petInfoView.setIfIZaned(UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid, -1, -1,
						petInfo.getId(), "1").getResultList().isEmpty() ? "0"
				: "1");
		return petInfoView;
	}

	private PetUserView getPetUserview(long userid, int type) {
		PetUser petUser = PetUser.findPetUser(userid);
		PetUserView petUserView = new PetUserView();
		petUserView.setUserid(petUser.getId());
		petUserView.setSignature(petUser.getSignature());
		petUserView.setUsername(petUser.getUsername());
		petUserView.setNickname(petUser.getNickname());
		petUserView.setGender(petUser.getGender());
		petUserView.setBirthdate(petUser.getBirthdate());
		petUserView.setHobby(petUser.getHobby());
		petUserView.setImg(petUser.getImg());
		petUserView.setCity(petUser.getCity());
		petUserView.setIfIZaned(UserZan
				.findUserZansByUseridAndUserStateidAndZanUserid(userid, -1, -1,
						-1, "2").getResultList().isEmpty() ? "0" : "1");
		petUserView.setCountZan(UserZan.countUserZans(userid, -1, -1, -1, "2"));
		// 获取数据库中最新的图片id然后存如实体
		petUserView.setImgId(petFileService.getNewImg(type));
		petUserView.setImgType(type);
		List<PetInfoView> resultList = getPetInfoView(userid);// 查询宠物信息，返回宠物列表
		petUserView.setPetInfoViews(resultList);// 插入petUserView的PetInfos中
		return petUserView;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	ErrorResponse handleException(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		logger.error("系统内部错误", ex);
		if (ex instanceof DuplicatedUsernameException) {
			response.setStatus(500);
			return new ErrorResponse("10001", "用户名重复");
		}
		if (ex instanceof PetException) {
			response.setStatus(500);
			return new ErrorResponse("10002", ex.getMessage());
		}
		response.setStatus(500);
		return new ErrorResponse("10000", "系统内部错误");
	}

	public void mergeUsageState(Long userId, Date connectTime) {
		List<UsageState> resultList = UsageState
				.findUsageStatesByUserId(userId).getResultList();
		if (resultList.size() <= 0) {
			UsageState uS = new UsageState();
			uS.setUserId(userId);
			uS.setLastConnectTime(connectTime);
			uS.persist();
		} else {
			for (UsageState uS : resultList) {
				uS.setUserId(userId);
				uS.setLastConnectTime(connectTime);
				uS.merge();
			}
		}
	}

}
