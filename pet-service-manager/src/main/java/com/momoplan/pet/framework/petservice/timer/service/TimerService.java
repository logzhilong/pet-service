package com.momoplan.pet.framework.petservice.timer.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.manager.mapper.MgrTimerTaskMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTask;
import com.momoplan.pet.commons.domain.manager.po.MgrTimerTaskCriteria;
import com.momoplan.pet.framework.base.vo.Page;

@Controller
public class TimerService {
	
	static Gson gson = MyGson.getInstance();
	private static Logger logger = LoggerFactory.getLogger(TimerService.class);
	@Autowired
	private MgrTimerTaskMapper mgrTimerTaskMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	public Page<MgrTimerTask> getMgrTimerTaskList(Page<MgrTimerTask> pages,MgrTimerTask vo)throws Exception{
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		MgrTimerTaskCriteria  mgrTimerTaskCriteria = new MgrTimerTaskCriteria();
		MgrTimerTaskCriteria.Criteria criteria = mgrTimerTaskCriteria.createCriteria();
		if(vo!=null && StringUtils.isNotEmpty(vo.getState())){
			criteria.andStateEqualTo(vo.getState());
		}
		mgrTimerTaskCriteria.setOrderByClause("et desc");
		int totalCount = mgrTimerTaskMapper.countByExample(mgrTimerTaskCriteria);
		mgrTimerTaskCriteria.setMysqlOffset((pageNo-1) * pageSize);
		mgrTimerTaskCriteria.setMysqlLength(pageSize);
		List<MgrTimerTask> data = mgrTimerTaskMapper.selectByExample(mgrTimerTaskCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
}
