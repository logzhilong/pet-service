package com.momoplan.pet.framework.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyLiveMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyMethodMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyRegistorMapper;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLiveCriteria;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethodCriteria;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistorCriteria;
import com.momoplan.pet.framework.manager.service.StatisticService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.UsersStatisticVo;

@Service
public class StatisticServiceImpl implements StatisticService{
	Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);
	
	@Autowired
	BizDailyLiveMapper bizDailyLiveMapper = null;
	@Autowired
	BizDailyRegistorMapper bizDailyRegistorMapper = null;
	@Autowired
	BizDailyMethodMapper bizDailyMethodMapper = null;
	
	@Override
	public List<BizDailyMethod> dateServiceMethod(int timeBucket) {
		BizDailyMethodCriteria bizDailyMethodCriteria = new BizDailyMethodCriteria();
		BizDailyMethodCriteria.Criteria criteria = bizDailyMethodCriteria.createCriteria();
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		//时间标移到timeBucket天前
		cal.add(Calendar.DAY_OF_MONTH, -timeBucket);
		String weekBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		criteria.andBizDateBetween(weekBefore, today);
		List<BizDailyMethod> bizDailyMethods = bizDailyMethodMapper.selectByExample(bizDailyMethodCriteria);
		if(bizDailyMethods.size()<=0){
			logger.debug("no dailyLives data ....");
		}
		List<BizDailyMethod> monthlyMethods = new ArrayList<BizDailyMethod>();
		for (int i = 0; i < bizDailyMethods.size(); i++) {
			BizDailyMethod monthlyMethod = new BizDailyMethod();
			monthlyMethod.setTotalCount("0");
			for (int j = 0; j < bizDailyMethods.size(); j++) {
				if(bizDailyMethods.get(i).getService().compareTo(bizDailyMethods.get(j).getService())==0&&bizDailyMethods.get(i).getMethod().compareTo(bizDailyMethods.get(j).getMethod())==0){
					monthlyMethod.setService(bizDailyMethods.get(i).getService());
					monthlyMethod.setMethod(bizDailyMethods.get(i).getMethod());
					monthlyMethod.setTotalCount(Long.parseLong(monthlyMethod.getTotalCount())+Long.parseLong(bizDailyMethods.get(j).getTotalCount())+"");
				}
			}
			monthlyMethods.add(monthlyMethod);
		}
		return monthlyMethods;
	}

	@Override
	public List<BizDailyLive> dateUsersByChannel(int timeBucket) {
		BizDailyLiveCriteria bizDailyLiveCriteria = new BizDailyLiveCriteria();
		BizDailyLiveCriteria.Criteria criteria = bizDailyLiveCriteria.createCriteria();
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		//时间标移到timeBucket天前
		cal.add(Calendar.DAY_OF_MONTH, -timeBucket);
		String weekBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		criteria.andBizDateBetween(weekBefore, today);
		List<BizDailyLive> bizDailyLives = bizDailyLiveMapper.selectByExample(bizDailyLiveCriteria);
		if(bizDailyLives.size()<=0){
			logger.debug("no dailyLives data ....");
		}
		List<BizDailyLive> monthlyLives = new ArrayList<BizDailyLive>();
		for (int i = 0; i < bizDailyLives.size(); i++) {
			BizDailyLive monthlyLive = new BizDailyLive();
			monthlyLive.setTotallyUser("0");
			for (int j = 0; j < bizDailyLives.size(); j++) {
				if(bizDailyLives.get(i).getChannel().compareTo(bizDailyLives.get(j).getChannel())==0){
					monthlyLive.setChannel(bizDailyLives.get(i).getChannel());
					monthlyLive.setTotallyUser(Long.parseLong(monthlyLive.getTotallyUser())+Long.parseLong(bizDailyLives.get(j).getTotallyUser())+"");
				}
			}
			monthlyLives.add(monthlyLive);
		}
		return monthlyLives;
	}

	@Override
	public List<BizDailyRegistor> dateRegistersByChannel(int timeBucket) {
		BizDailyRegistorCriteria bizDailyRegistorCriteria = new BizDailyRegistorCriteria();
		BizDailyRegistorCriteria.Criteria criteria = bizDailyRegistorCriteria.createCriteria();
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		//时间标移到timeBucket天前
		cal.add(Calendar.DAY_OF_MONTH, -timeBucket);
		String monthBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		criteria.andBizDateBetween(monthBefore, today);
		List<BizDailyRegistor> bizDailyRegistors = bizDailyRegistorMapper.selectByExample(bizDailyRegistorCriteria);
		if(bizDailyRegistors.size()<=0){
			logger.debug("no dailyLives data ....");
		}
		List<BizDailyRegistor> monthlyRegistors = new ArrayList<BizDailyRegistor>();
		for (int i = 0; i < bizDailyRegistors.size(); i++) {
			BizDailyRegistor monthlyRegistor = new BizDailyRegistor();
			monthlyRegistor.setTotallyUser("0");
			for (int j = 0; j < bizDailyRegistors.size(); j++) {
				if(bizDailyRegistors.get(i).getChannel().compareTo(bizDailyRegistors.get(j).getChannel())==0){
					monthlyRegistor.setChannel(bizDailyRegistors.get(i).getChannel());
					monthlyRegistor.setTotallyUser(Long.parseLong(monthlyRegistor.getTotallyUser())+Long.parseLong(bizDailyRegistors.get(j).getTotallyUser())+"");
				}
			}
			monthlyRegistors.add(monthlyRegistor);
		}
		return monthlyRegistors;
	}

	@Override
	public PageBean<UsersStatisticVo> getUsersData() {
		//声明一个list实例用于存储列表
		List<UsersStatisticVo> usvs = new ArrayList<UsersStatisticVo>();
//		List<BizDailyLive> dailyLives = null;
//		List<BizDailyRegistor> dailyRegisters = null;
		List<BizDailyLive> weeklyLives = dateUsersByChannel(7);
		List<BizDailyLive> monthlyLives = dateUsersByChannel(30);
		List<BizDailyRegistor> weeklyRegisters = dateRegistersByChannel(7);
		List<BizDailyRegistor> monthlyRegisters = dateRegistersByChannel(30);
		//遍历月用户活跃量统计出所有的可用于统计的渠道O(n)
		Set<String> channels = new HashSet<String>();
		for(int i = 0;i<monthlyLives.size();i++){
			channels.add(monthlyLives.get(i).getChannel());
		}
		//遍历月用户注册量统计出所有的可用于统计的渠道O(n)
		for(int i = 0;i<monthlyRegisters.size();i++){
			channels.add(monthlyRegisters.get(i).getChannel());
		}
		//统计channel下的数据4*O(n)
		Iterator<String> iterChannel = channels.iterator();
//		long countDailyRegisters = 0;
		long countWeeklyRegisters = 0;
		long countMonthlyRegister = 0;
//		long countDailyUsers = 0;
		long countWeeklyUsers = 0;
		long countMonthlyUsers = 0;
		for (Iterator<String> iterator = channels.iterator(); iterator.hasNext();) {
//			countDailyRegisters = 0;
			countWeeklyRegisters = 0;
			countMonthlyRegister = 0;
//			countDailyUsers = 0;
			countWeeklyUsers = 0;
			countMonthlyUsers = 0;
			String channel = iterator.next();
			for(int i = 0;i<weeklyRegisters.size();i++){
				if(channel.contains(weeklyRegisters.get(i).getChannel())){
					countWeeklyRegisters = countWeeklyRegisters+Long.parseLong(weeklyRegisters.get(i).getTotallyUser());
				}
			}
			for(int i = 0;i<weeklyLives.size();i++){
				if(channel.contains(weeklyLives.get(i).getChannel())){
					countMonthlyRegister = countWeeklyRegisters+Long.parseLong(weeklyLives.get(i).getTotallyUser());
				}
			}
			for(int i = 0;i<monthlyRegisters.size();i++){
				if(channel.contains(monthlyRegisters.get(i).getChannel())){
					countWeeklyUsers = countWeeklyUsers+Long.parseLong(monthlyRegisters.get(i).getTotallyUser());
				}
			}
			for(int i = 0;i<monthlyLives.size();i++){
				if(channel.contains(monthlyLives.get(i).getChannel())){
					countMonthlyUsers = countMonthlyUsers+Long.parseLong(monthlyLives.get(i).getTotallyUser());
				}
			}
			UsersStatisticVo usv = new UsersStatisticVo();
			usv.setChannel(channel);
			usv.setDailyRegisters(0);
			usv.setWeeklyRegisters(countWeeklyRegisters);
			usv.setMonthlyRegister(countMonthlyRegister);
			usv.setDailyUsers(0);
			usv.setDailyUsers(countWeeklyUsers);
			usv.setMonthlyUsers(countMonthlyUsers);
			usvs.add(usv);
		}
		PageBean<UsersStatisticVo> bean = new PageBean<UsersStatisticVo>();
		bean.setData(usvs);
		bean.setTotalRecorde(usvs.size());
		return bean;
	}

}
