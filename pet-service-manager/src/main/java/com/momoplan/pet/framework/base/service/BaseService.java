package com.momoplan.pet.framework.base.service;

import java.util.Date;

import com.momoplan.pet.commons.DateUtils;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTask;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.framework.petservice.timer.vo.TimerState;

public class BaseService {
	
	private MapperOnCache mapperOnCache = Bootstrap.getBean(MapperOnCache.class);
	
	protected void addTimerTask(String at_str,String id,String src,String name,String cb) throws Exception{
		Date now = new Date();
		MgrTimerTask task = new MgrTimerTask();
		task.setId(id);
		task.setAt(DateUtils.getDate(at_str, DateUtils.DEFAULT_DATETIME_FORMAT));
		task.setName(name);
		task.setSrc(src);
		task.setCb(cb);
		task.setEb(cb);
		task.setState(TimerState.NEW.getCode());
		task.setCt(now);
		task.setEt(now);
		mapperOnCache.insertSelective(task, id);
	}
}
