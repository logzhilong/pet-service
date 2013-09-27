package com.momoplan.pet.framework.statistic.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.statistic.mapper.StatDeviceMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.StatHistoryLogMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.StatPetUserMapper;
import com.momoplan.pet.commons.domain.statistic.mapper.StatUsageStateMapper;
import com.momoplan.pet.commons.domain.statistic.po.StatDevice;
import com.momoplan.pet.framework.statistic.service.StatisticService;

@Service
public class StatisticServiceImpl implements StatisticService {

	private Logger logger = LoggerFactory.getLogger(StatisticServiceImpl.class);

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
