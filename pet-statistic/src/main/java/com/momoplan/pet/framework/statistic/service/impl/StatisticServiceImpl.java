package com.momoplan.pet.framework.statistic.service.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyLiveMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.BizDailyRegistorMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.DataUsers0Mapper;
import com.momoplan.pet.commons.domain.statistic.mapper.DataUsers1Mapper;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLive;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyLiveCriteria;
import com.momoplan.pet.commons.domain.statistic.po.BizDailyRegistor;
import com.momoplan.pet.commons.domain.statistic.po.DataUsers0;
import com.momoplan.pet.commons.domain.statistic.po.DataUsers0Criteria;
import com.momoplan.pet.commons.domain.statistic.po.DataUsers1;
import com.momoplan.pet.framework.statistic.common.PetStatisticUtil;
import com.momoplan.pet.framework.statistic.service.StatisticService;
import com.momoplan.pet.framework.statistic.vo.ClientRequest;
import com.momoplan.pet.framework.statistic.vo.PetComRequest;

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
	
	DataUsers0Mapper dataUsers0Mapper = null;
	DataUsers1Mapper dataUsers1Mapper = null;
	BizDailyLiveMapper bizDailyLiveMapper = null;
	BizDailyRegistorMapper bizDailyRegistorMapper = null;
	
	@Autowired
	public StatisticServiceImpl(DataUsers0Mapper dataUsers0Mapper, DataUsers1Mapper dataUsers1Mapper,BizDailyLiveMapper bizDailyLiveMapper,BizDailyRegistorMapper bizDailyRegistorMapper) {
		super();
		this.dataUsers0Mapper = dataUsers0Mapper;
		this.dataUsers1Mapper = dataUsers1Mapper;
		this.bizDailyLiveMapper = bizDailyLiveMapper;
		this.bizDailyRegistorMapper = bizDailyRegistorMapper;
	}

	@Override
	public void persistDevice(ClientRequest clientRequest) {
		
		DataUsers0 dataUsers0 = new DataUsers0();
		dataUsers0.setId(IDCreater.uuid());
		dataUsers0.setImei(clientRequest.getImei());
		dataUsers0.setMac(clientRequest.getMac());
		dataUsers0.setChannel(clientRequest.getChannel());
		dataUsers0.setUserid(PetStatisticUtil.getParameter(clientRequest, "jmsuserid"));
		dataUsers0.setCreateTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		dataUsers0.setConnectTime(dataUsers0.getCreateTime());
		DataUsers1 dataUsers1 = new DataUsers1();
		dataUsers1.setId(IDCreater.uuid());
		dataUsers1.setImei(dataUsers0.getImei());
		dataUsers1.setMac(dataUsers0.getMac());
		dataUsers1.setChannel(dataUsers0.getChannel());
		dataUsers1.setUserid(dataUsers0.getUserid());
		dataUsers1.setCreateTime(dataUsers0.getCreateTime());
		dataUsers1.setConnectTime(dataUsers0.getCreateTime());
		if(null != dataUsers0 && null != dataUsers1){
			dataUsers0Mapper.insertSelective(dataUsers0);//入库
			dataUsers1Mapper.insertSelective(dataUsers1);//入库
		}else{
			logger.debug("dataUsers0 : "+dataUsers0);
			logger.debug("dataUsers1 : "+dataUsers1);
		}
		
	}

	/**
	 * 查询是否存在device
	 * @param clientRequest
	 * @return
	 */
	@Override
	public DataUsers0 findDevice(ClientRequest clientRequest) {
		if (StringUtils.hasLength(clientRequest.getMac())) {
			DataUsers0Criteria dataUsers0Criteria = new DataUsers0Criteria();
			DataUsers0Criteria.Criteria criteria = dataUsers0Criteria.createCriteria();
			criteria.andMacEqualTo(clientRequest.getMac());
			List<DataUsers0> dataUsers0List = dataUsers0Mapper.selectByExample(dataUsers0Criteria);
			if (dataUsers0List.size() > 1) {
				logger.warn("duplicated mac found. {}", clientRequest.getMac());
			}
			if (dataUsers0List.size() > 0) {
				return dataUsers0List.get(0);
			}
		}
		if (StringUtils.hasLength(clientRequest.getImei())) {
			DataUsers0Criteria dataUsers0Criteria = new DataUsers0Criteria();
			DataUsers0Criteria.Criteria criteria = dataUsers0Criteria.createCriteria();
			criteria.andImeiEqualTo(clientRequest.getImei());
			List<DataUsers0> dataUsers0List = dataUsers0Mapper.selectByExample(dataUsers0Criteria);
			if (dataUsers0List.size() > 1) {
				logger.warn("duplicated imei found. {}",
						clientRequest.getImei());
			}
			if (dataUsers0List.size() > 0) {
				return dataUsers0List.get(0);
			}
		}
		return null;
	}
	
	@Override
	public void mergeUsageState(ClientRequest clientRequest) throws Exception{
		String userid = PetStatisticUtil.getParameter(clientRequest, "jmsuserid");
		if(userid.contains("0")){
			logger.debug("userid is found. {}",userid);
			return;
		}
		
		DataUsers0 dataUsers0 = new DataUsers0();
		DataUsers1 dataUsers1 = new DataUsers1();
		dataUsers0.setId(IDCreater.uuid());
		dataUsers0.setConnectTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		dataUsers0.setUserid(userid);
		dataUsers1.setId(IDCreater.uuid());
		dataUsers1.setConnectTime(dataUsers0.getConnectTime());
		dataUsers1.setUserid(dataUsers0.getUserid());
		
		dataUsers0Mapper.insertSelective(dataUsers0);
		dataUsers1Mapper.insertSelective(dataUsers1);
		
	}

	@Override
	public void persistRegisters(ClientRequest clientRequest,String ret) throws JsonProcessingException,
			IOException {
		PetComRequest retRequest = new ObjectMapper().reader(PetComRequest.class).readValue(ret);
		if(null == retRequest){
			logger.warn("duplicated jmsRequest found. {}",retRequest);
		}
		String userid = PetStatisticUtil.getParameter(retRequest, "jmsuserid");
		
		DataUsers0 dataUsers0 = new DataUsers0();
		dataUsers0.setId(IDCreater.uuid());
		dataUsers0.setConnectTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		dataUsers0.setUserid(userid);
		dataUsers0.setBirthdate(PetStatisticUtil.getParameter(clientRequest, "birthdate"));
		dataUsers0.setUsername(PetStatisticUtil.getParameter(clientRequest, "username"));
		dataUsers0.setNickname(PetStatisticUtil.getParameter(clientRequest, "nickname"));
		dataUsers0.setPassword(PetStatisticUtil.getParameter(clientRequest, "password"));
		dataUsers0.setPhoneNumber(PetStatisticUtil.getParameter(clientRequest, "phonenumber"));
		dataUsers0.setEmail(PetStatisticUtil.getParameter(clientRequest, "email"));
		dataUsers0.setCreateTime(dataUsers0.getConnectTime());
		dataUsers0.setCity(PetStatisticUtil.getParameter(clientRequest, "city"));
		dataUsers0.setGender(PetStatisticUtil.getParameter(clientRequest, "gender"));
		dataUsers0.setImg(PetStatisticUtil.getParameter(clientRequest, "img"));
		dataUsers0.setHobby(PetStatisticUtil.getParameter(clientRequest, "hobby"));
		dataUsers0.setSignature(PetStatisticUtil.getParameter(clientRequest, "signature"));
		DataUsers1 dataUsers1 = new DataUsers1();
		dataUsers1.setId(IDCreater.uuid());
		dataUsers1.setConnectTime(dataUsers0.getConnectTime());
		dataUsers1.setUserid(userid);
		dataUsers1.setBirthdate(dataUsers0.getBirthdate());
		dataUsers1.setUsername(dataUsers0.getUsername());
		dataUsers1.setNickname(dataUsers0.getNickname());
		dataUsers1.setPassword(dataUsers0.getPassword());
		dataUsers1.setPhoneNumber(dataUsers0.getPhoneNumber());
		dataUsers1.setEmail(dataUsers0.getEmail());
		dataUsers1.setCreateTime(dataUsers0.getConnectTime());
		dataUsers1.setCity(dataUsers0.getCity());
		dataUsers1.setGender(dataUsers0.getGender());
		dataUsers1.setImg(dataUsers0.getImg());
		dataUsers1.setHobby(dataUsers0.getHobby());
		dataUsers1.setSignature(dataUsers0.getSignature());
		
		dataUsers0Mapper.insertSelective(dataUsers0);
		dataUsers1Mapper.insertSelective(dataUsers1);
	}

	@Override
	public void productDailyLivingTask() throws Exception {
		JdbcTemplate getBizDailyLives = new JdbcTemplate(statisticDataSource);
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.channel as channel,count(*) as totally_user from (select distinct du.userid,du.channel, to_days(connect_time) from data_users_1 du where du.connect_time is not null and du.userid !='' and to_days(now())-1 = to_days(du.connect_time)) t group by t.channel ");
		List<BizDailyLive> bizDailyLives = getBizDailyLives.query(sql.toString(), new RowMapper<BizDailyLive>(){
			@Override
			public BizDailyLive mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizDailyLive bizDailyLive = new BizDailyLive();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				String channel = rs.getString("channel");
				String total = rs.getString("totally_user");
				bizDailyLive.setChannel(channel);
				bizDailyLive.setTotallyUser(total);
				bizDailyLive.setBizDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				return bizDailyLive;
			}
		});
		logger.info("get bizDailyLives : "+new Gson().toJson(bizDailyLives));
		try {
			insertBizDailyLives(bizDailyLives);
		} catch (Exception e) {
			logger.debug("insert bizDailyLives error");
			e.printStackTrace();
		}
	}

	/**
	 * 各个渠道当天的活跃量入库
	 * @param bizDailyLives
	 * @throws Exception
	 */
	public void insertBizDailyLives(List<BizDailyLive> bizDailyLives) throws Exception{
		final List<BizDailyLive> listBiz =bizDailyLives;
		JdbcTemplate insertBizDailyLives = new JdbcTemplate(statisticDataSource);
		insertBizDailyLives.batchUpdate(" insert into biz_daily_live(id,biz_date,channel,totally_user) values(?,?,?,?) ", new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
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
	}
	
	@Override
	public void productDailyRegistorsTask() throws Exception {
		JdbcTemplate getBizDailyRegistor = new JdbcTemplate(statisticDataSource);
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.channel as channel,count(*) as totally_user from (select distinct du.userid,du.channel, to_days(create_time) from data_users_1 du where du.create_time is not null and du.userid !='' and to_days(now())-1 = to_days(du.create_time)) t group by t.channel ");
		List<BizDailyRegistor> bizDailyRegistors = getBizDailyRegistor.query(sql.toString(), new RowMapper<BizDailyRegistor>(){
			@Override
			public BizDailyRegistor mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizDailyRegistor bizDailyRegistor = new BizDailyRegistor();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				String channel = rs.getString("channel");
				String total = rs.getString("totally_user");
				bizDailyRegistor.setChannel(channel);
				bizDailyRegistor.setTotallyUser(total);
				bizDailyRegistor.setBizDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				return bizDailyRegistor;
			}
		});
		logger.info("get bizDailyRegistors : "+new Gson().toJson(bizDailyRegistors));
		try {
			insertBizDailyRegistors(bizDailyRegistors);
		} catch (Exception e) {
			logger.debug("insert bizDailyRegistors error");
			e.printStackTrace();
		}
	}
	
	/**
	 * 各个渠道当天的注册用户入库
	 * @param bizDailyRegistors
	 * @throws Exception
	 */
	public void insertBizDailyRegistors(List<BizDailyRegistor> bizDailyRegistors) throws Exception{
		final List<BizDailyRegistor> listBiz =bizDailyRegistors;
		JdbcTemplate insertBizDailyLives = new JdbcTemplate(statisticDataSource);
		insertBizDailyLives.batchUpdate(" insert into biz_daily_registor(id,biz_date,channel,totally_user) values(?,?,?,?) ", new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
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
	}

	@Override
	public void UpdateDataUsers1() throws Exception {
		JdbcTemplate updateDataUsers1 = new JdbcTemplate(statisticDataSource);
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from data_users_1 where (to_days(connect_time)<to_days(now())-2) or (to_days(create_time)<to_days(now())-2) ");
		updateDataUsers1.execute(sql.toString());
		logger.info("updateDataUsers1 finished.");
	}

	
//	DataUsers0Mapper dataUsers0Mapper = null;
//	DataUsers1Mapper dataUsers1Mapper = null;
//	BizDailyLiveMapper bizDailyLiveMapper = null;
//	BizDailyRegistorMapper bizDailyRegistorMapper = null;
	
	/**
	 * 当天的用户活跃量jdbc
	 */
	public List<BizDailyLive> dailyUsersByChannel(){
		
		JdbcTemplate getBizDailyLives = new JdbcTemplate(statisticDataSource);
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.channel as channel,count(*) as totally_user from (select distinct du.userid,du.channel, to_days(connect_time) from data_users_1 du where du.connect_time is not null and du.userid !='' and to_days(now()) = to_days(du.connect_time)) t group by t.channel ");
		List<BizDailyLive> bizDailyLives = getBizDailyLives.query(sql.toString(), new RowMapper<BizDailyLive>(){
			@Override
			public BizDailyLive mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizDailyLive bizDailyLive = new BizDailyLive();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				String channel = rs.getString("channel");
				String total = rs.getString("totally_user");
				bizDailyLive.setChannel(channel);
				bizDailyLive.setTotallyUser(total);
				bizDailyLive.setBizDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				return bizDailyLive;
			}
		});
		if(bizDailyLives.size()<=0){
			logger.debug("no dailyLives data ....");
		}
		return bizDailyLives;
	}
	
	/**
	 * 
	 */
	public List<BizDailyLive> weeklyUsersByChannel(){
		BizDailyLiveCriteria bizDailyLiveCriteria = new BizDailyLiveCriteria();
		BizDailyLiveCriteria.Criteria criteria = bizDailyLiveCriteria.createCriteria();
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		//时间标移到7天前
		cal.add(Calendar.DAY_OF_MONTH, -7);
		String weekBefore = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		criteria.andBizDateBetween(weekBefore, today);
		List<BizDailyLive> bizDailyLives = bizDailyLiveMapper.selectByExample(bizDailyLiveCriteria);
		if(bizDailyLives.size()<=0){
			logger.debug("no dailyLives data ....");
		}
		List<BizDailyLive> weeklyLives = new ArrayList<BizDailyLive>();
		for (int i = 0; i < bizDailyLives.size(); i++) {
			BizDailyLive weeklyLive = new BizDailyLive();
			weeklyLive.setTotallyUser("0");
			for (int j = 0; j < bizDailyLives.size(); j++) {
				if(bizDailyLives.get(i).getChannel().compareTo(bizDailyLives.get(j).getChannel())==0){
					weeklyLive.setChannel(bizDailyLives.get(i).getChannel());
					weeklyLive.setTotallyUser(Long.parseLong(weeklyLive.getTotallyUser())+Long.parseLong(bizDailyLives.get(j).getTotallyUser())+"");
				}
			}
			weeklyLives.add(weeklyLive);
		}
		return weeklyLives;
	}
	
	/**
	 * 
	 */
	public List<BizDailyLive> monthlyUsersByChannel(){
		BizDailyLiveCriteria bizDailyLiveCriteria = new BizDailyLiveCriteria();
		BizDailyLiveCriteria.Criteria criteria = bizDailyLiveCriteria.createCriteria();
		Calendar cal = Calendar.getInstance();
		String today = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		//时间标移到7天前
		cal.add(Calendar.DAY_OF_MONTH, -30);
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
	
	/**
	 * 当天的用户注册量jdbc获取
	 */
	public List<BizDailyRegistor> dailyRegistorsByChannel(){
		JdbcTemplate getBizDailyRegistor = new JdbcTemplate(statisticDataSource);
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.channel as channel,count(*) as totally_user from (select distinct du.userid,du.channel, to_days(create_time) from data_users_1 du where du.create_time is not null and du.userid !='' and to_days(now()) = to_days(du.create_time)) t group by t.channel ");
		List<BizDailyRegistor> bizDailyRegistors = getBizDailyRegistor.query(sql.toString(), new RowMapper<BizDailyRegistor>(){
			@Override
			public BizDailyRegistor mapRow(ResultSet rs, int rowNum) throws SQLException {
				BizDailyRegistor bizDailyRegistor = new BizDailyRegistor();
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				String channel = rs.getString("channel");
				String total = rs.getString("totally_user");
				bizDailyRegistor.setChannel(channel);
				bizDailyRegistor.setTotallyUser(total);
				bizDailyRegistor.setBizDate(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
				return bizDailyRegistor;
			}
		});
		if(bizDailyRegistors.size()<=0){
			logger.debug("no dailyRegistors data ....");
		}
		return bizDailyRegistors;
	}
	
	/**
	 * 
	 */
	public void totallyUsersByChannel(){
//		BizDailyLiveCriteria bizDailyLiveCriteria = new BizDailyLiveCriteria();
//		BizDailyLiveCriteria.Criteria criteria = bizDailyLiveCriteria.createCriteria();
//		criteria.and
//		bizDailyLiveMapper.countByExample(bizDailyLiveCriteria)
	}
}

