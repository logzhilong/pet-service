package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;

public interface StatisticService {
	public List<BizDailyLive> getDailyLives();
	public List<BizDailyMethod> getDailyMethod();
	public List<BizDailyRegistor> getDailyRegistor();
	
}
