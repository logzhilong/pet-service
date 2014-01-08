package com.momoplan.pet.framework.petservice.push.service;

import java.util.Date;
import java.util.List;

import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.commons.domain.manager.mapper.MgrPushMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.commons.domain.manager.po.MgrPushCriteria;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.framework.base.service.BaseService;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.petservice.push.vo.PushState;

@Controller
public class PushService extends BaseService {
	
	static Gson gson = MyGson.getInstance();
	private static Logger logger = LoggerFactory.getLogger(PushService.class);
	@Autowired
	private MgrPushMapper mgrPushMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private JmsTemplate apprequestTemplate = null;

	public Page<MgrPush> getMgrPushList(Page<MgrPush> pages,MgrPush vo)throws Exception{
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		MgrPushCriteria mgrPushCriteria = new MgrPushCriteria();
		if(vo!=null&&StringUtils.isNotEmpty(vo.getState())){
			mgrPushCriteria.createCriteria().andStateEqualTo(vo.getState());
		}
		mgrPushCriteria.setOrderByClause("et desc");
		int totalCount = mgrPushMapper.countByExample(mgrPushCriteria);
		mgrPushCriteria.setMysqlOffset((pageNo-1) * pageSize);
		mgrPushCriteria.setMysqlLength(pageSize);
		List<MgrPush> data = mgrPushMapper.selectByExample(mgrPushCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
	public void save(MgrPush vo,String push) throws Exception{
		logger.debug("save : "+vo.toString());
		Date now = new Date();
		String id = vo.getId();
		String src = vo.getSrc();
		
		MgrPush p = mapperOnCache.selectByPrimaryKey(MgrPush.class, id);
		if("OK".equalsIgnoreCase(push)){
			logger.debug("//TODO 把消息推出去 根据 push = OK");
			//其实，应该时 pending 状态，由异步线程负责更新状态
			vo.setState(PushState.PUSHED.getCode());
			logger.debug("添加一个IOS推送任务");
			push2mq4xmpp(p);
			IphonePushTask.queue.put(p);
		}
		
		if(p!=null){
			logger.debug("更新 "+gson.toJson(vo));
			if(StringUtils.isNotEmpty(vo.getSrc())){
				throw new Exception("此记录已加入推送列表，不能重复添加");
			}
			logger.debug("state="+p.getState());
			if(PushState.PUSHED.equals(PushState.valueOf(p.getState()))){
				String e = DateUtils.formatDate(p.getEt());
				String n = DateUtils.formatDate(now);
				logger.debug("n="+n);
				logger.debug("e="+e);
				if(n.equals(e)){
					throw new Exception("此记录已推送，当天不能重复推送。");
				}
			}
			vo.setEt(now);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			String name = getNameBySrc(src,id);
			logger.debug("新增 "+gson.toJson(vo));
			vo.setName(name);
			vo.setCt(now);
			vo.setCb(vo.getEb());
			vo.setEt(now);
			vo.setState(PushState.NEW.getCode());
			mapperOnCache.insertSelective(vo, vo.getId());
		}
		push2mq4state(mapperOnCache.selectByPrimaryKey(MgrPush.class, vo.getId()));
	}
	
	private void push2mq4xmpp(MgrPush vo) {
		try{
			String dest = "pet_push_xmpp_pubsub";
			ActiveMQQueue queue = new ActiveMQQueue();
			queue.setPhysicalName(dest);
			TextMessage tm = new ActiveMQTextMessage();
			String msg = gson.toJson(vo);
			tm.setText(msg);
			apprequestTemplate.convertAndSend(queue, tm);
			logger.debug("dest=pet_push_xmpp_pubsub ; msg="+msg);
		}catch(Exception e){
			logger.debug("推送消息异常不能中断程序");
			logger.error("send message",e);
		}
	}
	private void push2mq4state(MgrPush vo) {
		try{
			String dest = "pet_push_state";
			ActiveMQTopic topic = new ActiveMQTopic();
			topic.setPhysicalName(dest);
			TextMessage tm = new ActiveMQTextMessage();
			String msg = gson.toJson(vo);
			tm.setText(msg);
			apprequestTemplate.convertAndSend(topic, tm);
			logger.debug("dest="+dest+" ; msg="+msg);
		}catch(Exception e){
			logger.debug("推送消息异常不能中断程序");
			logger.error("send message",e);
		}
	}
	
	private String getNameBySrc(String src,String id) throws Exception{
		String name = null;
		if("bbs_note".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Note.class, id).getName();
			logger.debug("帖子 : "+name);
		}else if("ency".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Ency.class, id).getName();
			logger.debug("百科 : "+name);
		}else if("exper".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Exper.class, id).getName();
			logger.debug("经验 : "+name);
		}else if("notice".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(Notice.class, id).getName();
			logger.debug("通知 : "+name);
		}else if("bbs_special_subject".equalsIgnoreCase(src)){
			name = mapperOnCache.selectByPrimaryKey(SpecialSubject.class, id).getName();
			logger.debug("专题 : "+name);
		}
		return name;
	}
	
	public void saveTimer(MgrPush myForm,String at_str,String currentUser)throws Exception{
		Date now = new Date();
		myForm.setEb(currentUser);
		myForm.setEt(now);
		myForm.setState(PushState.LAZZY.getCode());
		mapperOnCache.updateByPrimaryKeySelective(myForm, myForm.getId());
		logger.info("修正推送状态 LAZZY："+myForm.getId());
		super.addTimerTask(at_str, myForm.getId(), "mgr_push", myForm.getName(), currentUser);
		logger.info("增加到定时任务："+myForm.getId());
	}
	
}
