package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.UsersStatisticVo;

public interface StatisticService {

	/**
	 * @param timeBucket
	 * 时间段（week:7,month:30）
	 * @return
	 */
	public List<BizDailyMethod> dateServiceMethod(int timeBucket);
	
	/**
	 * @param timeBucket
	 * 时间段（week:7,month:30）
	 * @return
	 */
	public List<BizDailyLive> dateUsersByChannel(int timeBucket);
	
	/**
	 * @param timeBucket
	 * 时间段（week:7,month:30）
	 * @return
	 */
	public List<BizDailyRegistor> dateRegistersByChannel(int timeBucket);
	
	public PageBean<UsersStatisticVo> getUsersData();
}
