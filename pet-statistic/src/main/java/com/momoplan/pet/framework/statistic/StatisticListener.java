package com.momoplan.pet.framework.statistic;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.momoplan.pet.framework.statistic.service.StatisticService;
import com.momoplan.pet.framework.statistic.vo.ClientRequest;

@Component
public class StatisticListener implements MessageListener {

	private Logger logger = LoggerFactory.getLogger(StatisticListener.class);

	@Autowired
	private StatisticService statisticService;

	@Override
	public void onMessage(Message message) {
		logger.debug(message.toString());
		if (message instanceof TextMessage) {
			TextMessage msg = (TextMessage) message;
			try {
				petStatisticHandle(msg.getStringProperty("body"),msg.getStringProperty("ret"));
//				statisticService.testInsert();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * 根据消息参数调用对应的处理方法
	 * @param request
	 * @param ret
	 * @throws Exception
	 */
	public void petStatisticHandle(String request,String ret) throws Exception{
		if(!StringUtils.hasLength(ret)){
			return;
		}
		ClientRequest clientRequest = new ObjectMapper().reader(ClientRequest.class).readValue(request);
		if(clientRequest.getMethod().equals("open")){
//			DataUsers0 device=statisticService.findDevice(clientRequest);
//			if(device==null){
//				statisticService.persistDevice(clientRequest);
//			}
			statisticService.persistDevice(clientRequest); 
		}
		if (clientRequest.getMethod().equals("register")) {
			statisticService.persistRegisters(clientRequest,ret);
		}
		if (clientRequest.getMethod().equals("isUsernameInuse")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("getChatServer")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("login")) {
			statisticService.mergeUsageState(clientRequest);
		}
		
		if (clientRequest.getMethod().equals("getNearbyUser")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("setUserLocation")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("saveUserinfo")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("getUserinfo")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("savePetinfo")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 根据用户编号和用户名查询用户
		if (clientRequest.getMethod().equals("selectUserByNameOrUserId")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 修改宠物信息
		if (clientRequest.getMethod().equals("updatePetinfo")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 删除宠物信息
		if (clientRequest.getMethod().equals("delPetInfo")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//获取最新版本信息
		if (clientRequest.getMethod().equals("updateVersion")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 获取用户信息
		if (clientRequest.getMethod().equals("getUesr")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("getFriends")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("getOneFriend")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 获取十条假数据
		if (clientRequest.getMethod().equals("cheatUser")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 获取验证码
		if (clientRequest.getMethod().equals("getVerificationCode")) {
			return;
		}
		// 验证随机码
		if (clientRequest.getMethod().equals("verifyCode")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 建立好友关系
		if (clientRequest.getMethod().equals("addFriend")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 建立非好友好友关系
		if (clientRequest.getMethod().equals("delFriend")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 建立黑名单关系
		if (clientRequest.getMethod().equals("insBlackList")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 根据用户名查询一个用户的所有信息
		if (clientRequest.getMethod().equals("selectUserViewByUserName")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 查询附近用户信息，根据宠物过滤一次
		if (clientRequest.getMethod().equals("getNearbyUserAndPet")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 根据图片号删除一张图片id
		if (clientRequest.getMethod().equals("delOneImg")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 分页获取用户动态
		if (clientRequest.getMethod().equals("getUserStateView")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 用户添加状态
		if (clientRequest.getMethod().equals("addUserState")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 删除用户动态
		if (clientRequest.getMethod().equals("delUserState")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 评论用户动态
		if (clientRequest.getMethod().equals("addReply")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 回复评论
		if (clientRequest.getMethod().equals("addCommentReply")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 删除评论
		if (clientRequest.getMethod().equals("delReply")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 修改好友备注
		if (clientRequest.getMethod().equals("updatefriendremark")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 删除回复评论
		if (clientRequest.getMethod().equals("delCommentReply")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 赞动态
		if (clientRequest.getMethod().equals("addZan")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 取消赞
		if (clientRequest.getMethod().equals("delZan")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 查询自己的动态
		if (clientRequest.getMethod().equals("findMyStates")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 查询好友的所有动态
		if (clientRequest.getMethod().equals("findFriendStates")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 查询所有好友的动态
		if (clientRequest.getMethod().equals("getAllFriendStates")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 分页获取评论
		if (clientRequest.getMethod().equals("getRepliesByPageIndex")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 分页获取回复评论
		if (clientRequest.getMethod().equals("getCommentsByPageIndex")) {
			statisticService.mergeUsageState(clientRequest);
		}
		if (clientRequest.getMethod().equals("saveUserinfo2")) {//v2
			statisticService.mergeUsageState(clientRequest);
		}
		if(clientRequest.getMethod().equals("resetPassword")){
			return;
		}
		if (clientRequest.getMethod().equals("register2")) {//v2
			statisticService.persistRegisters(clientRequest,ret);
		}
		if (clientRequest.getMethod().equals("login2")) {
			statisticService.mergeUsageState(clientRequest);
		}
		// 意见反馈
		if (clientRequest.getMethod().equals("feedback")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//举报
		if (clientRequest.getMethod().equals("reportContent")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//添加背景图片
		if (clientRequest.getMethod().equals("addBackgroundImg")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//添加背景图片
		if (clientRequest.getMethod().equals("getAllForumAsTree")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//proxy发帖																									
		if (clientRequest.getMethod().equals("sendNote")) {         
			statisticService.mergeUsageState(clientRequest);   
		}                                                           
		//proxy回帖                                                 
		if (clientRequest.getMethod().equals("replyNote")) {        
			statisticService.mergeUsageState(clientRequest);   
		}                                                           
		//proxy关注圈子                                                 
		if (clientRequest.getMethod().equals("attentionForum")) {       
			statisticService.mergeUsageState(clientRequest);                                                                                                             
		}                                                               
		//proxy退出圈子                                   
		if (clientRequest.getMethod().equals("quitForum")) {           
			statisticService.mergeUsageState(clientRequest);       
		}                                                               
		//proxy搜索(如果Forumid为0则全站搜索,否则圈子内搜索)                                                
		if (clientRequest.getMethod().equals("searchNote")) {              
			statisticService.mergeUsageState(clientRequest);       
		}                                                               
		//proxy根据id查看帖子详情                                                 
		if (clientRequest.getMethod().equals("detailNote")) {       
			statisticService.mergeUsageState(clientRequest);       
		}                                                               
		//proxy删除帖子                                                 
		if (clientRequest.getMethod().equals("delNote")) {            
			statisticService.mergeUsageState(clientRequest);       
		}                                                                           
		//proxy举报帖子                                                                      
		if (clientRequest.getMethod().equals("reportNote")) {         
			statisticService.mergeUsageState(clientRequest);                                    
		}                                                                   
		//proxy根据帖子id获取所有回复                                                                                                                                           
		if (clientRequest.getMethod().equals("getAllReplyNoteByNoteid")) {                                                                   
			statisticService.mergeUsageState(clientRequest);                                                                   
		}
		//proxy(全站)我发表过的帖子列表
		if (clientRequest.getMethod().equals("getMyNotedListByuserid")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//proxy最新帖子(forumid为0则表示全站搜索否则圈子内部搜索)
		if (clientRequest.getMethod().equals("newNoteByFid")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//proxy今日新增帖子列表(forumPid为0则全站搜索,否则为圈子内部搜索)
		if (clientRequest.getMethod().equals("getTodayNewNoteListByFid")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//proxy获取精华(forumPid为0则全站搜索,否则为圈子内部搜索)
		if (clientRequest.getMethod().equals("getEuteNoteList")) {
			statisticService.mergeUsageState(clientRequest);
		}
		//proxy(全站)最新回复(根据回复时间将帖子显示{不显示置顶帖子})
		if (clientRequest.getMethod().equals("getNewReplysByReplyct")) {
			statisticService.mergeUsageState(clientRequest);
		} 
		//proxy查看圈子列表
		if (clientRequest.getMethod().equals("getAllForumAsTree")) {
			statisticService.mergeUsageState(clientRequest);
		}  
		//proxy最新回复(根据回复时间将帖子显示{不显示置顶帖子})(forumPid为0则全站最新回复,否则为圈子内部最新回复)
		if (clientRequest.getMethod().equals("getNewReplysByReplyct")) {
			statisticService.mergeUsageState(clientRequest);
		}   
		
		
	}

}