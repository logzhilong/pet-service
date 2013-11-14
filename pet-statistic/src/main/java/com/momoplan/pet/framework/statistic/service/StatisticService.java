package com.momoplan.pet.framework.statistic.service;

import java.util.List;

import org.json.JSONObject;

import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;

public interface StatisticService {

	/**
	 * 将文件解析后得到的json处理成可持久化的实体，然后进行持久化 注册用户持久化
	 * 
	 * @param jList
	 * @throws Exception
	 */
	public void insertBizDailyRegistors(final List<BizDailyRegistor> listBiz)
			throws Exception;

	/**
	 * 将文件解析后得到的json处理成可持久化的实体，然后进行持久化 日活跃量持久化
	 * 
	 * @param jList
	 * @throws Exception
	 */
	public void insertBizDailyLives(final List<BizDailyLive> listBiz) throws Exception;

	/**
	 * 将文件解析后得到的json处理成可持久化的实体，然后进行持久化 当天method统计持久化
	 * 
	 * @param jList
	 * @throws Exception
	 */
	public void insertBizDailyMethod(final List<BizDailyMethod> listBiz) throws Exception;

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
	
}
