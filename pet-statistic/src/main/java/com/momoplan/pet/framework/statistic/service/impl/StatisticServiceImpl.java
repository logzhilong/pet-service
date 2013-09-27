package com.momoplan.pet.framework.statistic.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.momoplan.pet.commons.domain.statistic.mapper.StatDeviceMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.StatHistoryLogMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.StatPetUserMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.StatUsageStateMapper;
import com.momoplan.pet.commons.domain.statistic.po.StatDevice;
import com.momoplan.pet.framework.statistic.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

	private Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

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
	
	StatDeviceMapper statDeviceMapper = null;
	StatHistoryLogMapper statHistoryLogMapper = null;
	StatPetUserMapper statPetUserMapper = null;
	StatUsageStateMapper statUsageStateMapper = null;
	@Autowired
	public StatisticServiceImpl(StatDeviceMapper statDeviceMapper, StatHistoryLogMapper statHistoryLogMapper, StatPetUserMapper statPetUserMapper, StatUsageStateMapper statUsageStateMapper) {
		super();
		this.statDeviceMapper = statDeviceMapper;
		this.statHistoryLogMapper = statHistoryLogMapper;
		this.statPetUserMapper = statPetUserMapper;
		this.statUsageStateMapper = statUsageStateMapper;
	}

	@Override
	public void testInsert() throws Exception {
		StatDevice statDevice = new StatDevice();//PO
		statDevice.setUserid(10001L);
		statDevice.setCreateTime(new Date());
		logger.debug(statDevice.toString());
		statDeviceMapper.insertSelective(statDevice);//入库
	}

}
