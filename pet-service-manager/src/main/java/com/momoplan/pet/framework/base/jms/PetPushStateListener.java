package com.momoplan.pet.framework.base.jms;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.po.Note;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.commons.domain.manager.po.MgrPush;
import com.momoplan.pet.commons.domain.notice.po.Notice;
import com.momoplan.pet.framework.petservice.bbs.vo.SpecialSubjectState;
import com.momoplan.pet.framework.petservice.push.vo.PushState;


@Component
public class PetPushStateListener implements MessageListener {
	
	@Autowired
	private MapperOnCache mapperOnCache = null;
	private static Gson gson = MyGson.getInstance();
	
	private static Logger logger = LoggerFactory.getLogger(PetPushStateListener.class);

	@Override
	public void onMessage(Message message) {
		TextMessage msg = (TextMessage)message;
		String j = null;
		try {
			j = msg.getText();
			logger.info("states change => "+j);
			MgrPush mgrPush = gson.fromJson(j, MgrPush.class);
			updateState(mgrPush);
		} catch (Exception e) {
			logger.error(j,e);
		}
	}
	private void updateState(MgrPush mgrPush) throws Exception{
		//需要同步修改状态的，就在这里进行
		String id = mgrPush.getId();
		String src = mgrPush.getSrc();
		String state = mgrPush.getState();
		if("bbs_note".equalsIgnoreCase(src)){
		}else if("ency".equalsIgnoreCase(src)){
		}else if("exper".equalsIgnoreCase(src)){
		}else if("notice".equalsIgnoreCase(src)){
		}else if("bbs_special_subject".equalsIgnoreCase(src)){
			//专题
			SpecialSubject ss = mapperOnCache.selectByPrimaryKey(SpecialSubject.class, id);
			if(PushState.NEW.getCode().equals(state)){
				ss.setState(SpecialSubjectState.WAIT.getCode());
			}else if(PushState.PUSHED.getCode().equals(state)){
				ss.setState(SpecialSubjectState.PUSHED.getCode());
			}
			mapperOnCache.updateByPrimaryKeySelective(ss, ss.getId());
		}
	}
}
