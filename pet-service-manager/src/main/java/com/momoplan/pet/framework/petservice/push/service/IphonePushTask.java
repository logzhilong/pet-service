package com.momoplan.pet.framework.petservice.push.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PushApn;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.commons.domain.user.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoUserCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;

/**
 * 批量发送IPHONE推送
 * @author liangc
 */
@Service
public class IphonePushTask implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(IphonePushTask.class);

	public static BlockingQueue<MgrPush> queue = new ArrayBlockingQueue<MgrPush>(32);
	
	@Autowired
	private SsoUserMapper ssoUserMapper = null;
	@Autowired
	private CommonConfig commonConfig = null;
	
	@PostConstruct
	public void run(){
		new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(true){
					try {
						logger.info(i+" 准备执行");
						MgrPush vo = queue.take();
						logger.info(i+" 获取任务 "+MyGson.getInstance().toJson(vo));
						List<SsoUser> ssoUser = getUsers();
						//TODO 这里可以使用线程池技术
						iphonePushTask(vo,ssoUser);
						logger.info(i+" 推送完成.");
					} catch (Exception e) {
						logger.error("iphone push error",e);
					}finally{
						i++;
					}
				}
			}
		}).start();
	}
	
	private void iphonePushTask(MgrPush vo,List<SsoUser> list) throws Exception{
		String pwd = commonConfig.get("ios.push.pwd","110110");
		String test = commonConfig.get("iphone.push");
		String threads = commonConfig.get("ios.push.threads","20");
		logger.debug("[commonConfig] ios.push.pwd = "+pwd);
		logger.debug("[commonConfig] iphone.push = "+test);
		logger.debug("[commonConfig] ios.push.threads = "+threads);

		boolean t = false;
		if("test".equalsIgnoreCase(test)){
			logger.debug("测试证书");
			t = true;
		}
		Set<String> set = new HashSet<String>();
		List<Device> devices = new ArrayList<Device>();
		for(SsoUser u : list){
			String d = u.getDeviceToken();
			if(StringUtils.isNotEmpty(d)){
				set.add(d);
			}
		}
		for(Iterator<String> it = set.iterator();it.hasNext();){
			String d = it.next();
			Device device = new BasicDevice();
			device.setToken(d);
			devices.add(device);
		}
		logger.info("IPHONE 实际推送用户数 : "+devices.size());
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("type", vo.getSrc());
		params.put("id", vo.getId());
		logger.info("msg="+vo.getName()+" ; params="+params);
		PushApn.sendMsgApn(vo.getName(),pwd, t, params, Integer.parseInt(threads), devices);
	}
	
	//TODO 这里可以使用缓存，每30分钟更新一次用户池，HASH 存储，或者干脆就缓存一份 DEVICE TOKEN
	private List<SsoUser> getUsers(){
		SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
		ssoUserCriteria.createCriteria().andDeviceTokenIsNotNull();
		List<SsoUser> users = ssoUserMapper.selectByExample(ssoUserCriteria);
		int totalCount = 0;
		if(users!=null){
			totalCount = users.size();
		}
		logger.info("IPHONE 待推送用户数 ： "+totalCount);
		return users;
	}
	
}