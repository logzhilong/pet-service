package com.momoplan.pet.framework.statistic.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyLiveMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyMethodMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyRegistorMapper;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLiveCriteria;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethod;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyMethodCriteria;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistorCriteria;
import com.momoplan.pet.framework.statistic.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

	private Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);
	@Autowired
	ComboPooledDataSource statisticDataSource = null;

/*
	@Autowired
	ComboPooledDataSource statisticDataSource = null;
	{
		JdbcTemplate jt = new JdbcTemplate(statisticDataSource);
		List<StatDevice> res = jt.query("select id,name from fff ", new RowMapper<StatDevice>(){
			@Override
			public StatDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
				StatDevice device = new StatDevice();
				long id = rs.getLong("id");
				String name = rs.getString("name");
				device.setId(id);
				return null;
			}
		});
	}
*/
	BizDailyLiveMapper bizDailyLiveMapper = null;
	BizDailyRegistorMapper bizDailyRegistorMapper = null;
	BizDailyMethodMapper bizDailyMethodMapper = null;
	
	@Autowired
	public StatisticServiceImpl(BizDailyMethodMapper bizDailyMethodMapper,BizDailyLiveMapper bizDailyLiveMapper,BizDailyRegistorMapper bizDailyRegistorMapper) {
		super();
		this.bizDailyLiveMapper = bizDailyLiveMapper;
		this.bizDailyRegistorMapper = bizDailyRegistorMapper;
		this.bizDailyMethodMapper = bizDailyMethodMapper;
	}
	
	/**
	 * 将method插入到bizDailyMethod表中
	 * @throws Exception 
	 * 
	 */
	public void insertBizDailyMethod(final List<BizDailyMethod> listBiz) throws Exception{
		try {
			JdbcTemplate insertBizDailyMdthods = new JdbcTemplate(statisticDataSource);
			insertBizDailyMdthods.batchUpdate(" insert into biz_daily_method(id,biz_date,service,method,total_count) values(?,?,?,?,?) ", new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					logger.debug("element is : "+MyGson.getInstance().toJson(listBiz.get(i)));
					String id = IDCreater.uuid();
					String service = listBiz.get(i).getService();
					String method = listBiz.get(i).getMethod();
					String totalCount = listBiz.get(i).getTotalCount();
					String bizDate = listBiz.get(i).getBizDate();
					ps.setString(1, id);
					ps.setString(2, bizDate);
					ps.setString(3, service);
					ps.setString(4, method);
					ps.setString(5, totalCount);
				}
				@Override
				public int getBatchSize() {
					return listBiz.size();
				}
			});
		} catch (Exception e) {
			logger.error("insert BizDailyMethod error :"+e);
			throw e;
		}
		
	}

	/**
	 * 各个渠道当天的注册用户入库
	 * @param bizDailyRegistors
	 * @throws Exception
	 */
	public void insertBizDailyRegistors(final List<BizDailyRegistor> listBiz) throws Exception{
		try {
			JdbcTemplate insertBizDailyLives = new JdbcTemplate(statisticDataSource);
			insertBizDailyLives.batchUpdate(" insert into biz_daily_registor(id,biz_date,channel,totally_user) values(?,?,?,?) ", new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					logger.debug("element is : "+MyGson.getInstance().toJson(listBiz.get(i)));
					String id = IDCreater.uuid();
					String channel = listBiz.get(i).getChannel();
					String total = listBiz.get(i).getTotallyUser();
					String bizDate = listBiz.get(i).getBizDate();
					ps.setString(1, id);
					ps.setString(2, bizDate);
					ps.setString(3, channel);
					ps.setString(4, total);
				}
				@Override
				public int getBatchSize() {
					return listBiz.size();
				}
			});
		} catch (Exception e) {
			logger.error("insert BizDailyRegistor error : "+e);
			throw e;
		}
	}
	
	/**
	 * 各个渠道当天的活跃量入库
	 * @param bizDailyLives
	 * @throws Exception
	 */
	public void insertBizDailyLives(final List<BizDailyLive> listBiz) throws Exception{
		try {
			JdbcTemplate insertBizDailyLives = new JdbcTemplate(statisticDataSource);
			insertBizDailyLives.batchUpdate(" insert into biz_daily_live(id,biz_date,channel,totally_user) values(?,?,?,?) ", new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					logger.debug("element is : "+MyGson.getInstance().toJson(listBiz.get(i)));
					String id = IDCreater.uuid();
					String channel = listBiz.get(i).getChannel();
					String total = listBiz.get(i).getTotallyUser();
					String bizDate = listBiz.get(i).getBizDate();
					ps.setString(1, id);
					ps.setString(2, bizDate);
					ps.setString(3, channel);
					ps.setString(4, total);
				}
				@Override
				public int getBatchSize() {
					return listBiz.size();
				}
			});
		} catch (Exception e) {
			logger.error("insert BizDailyLive error : "+e);
			throw e;
		}
	}
	
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

}

