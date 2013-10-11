package com.momoplan.pet.domain.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.momoplan.pet.commons.domain.statistic.mapper.DataUsers0Mapper;
import com.momoplan.pet.commons.domain.statistic.po.DataUsers0;

public class StatisticTest extends AbstractTest {

	@Resource
	private DataUsers0Mapper dataUsers0Mapper = null;
	
	public void insert(){
		System.out.println(dataUsers0Mapper);
		DataUsers0 dataUsers0 = new DataUsers0();
		dataUsers0.setId("test");
		dataUsers0Mapper.insertSelective(dataUsers0);
		System.out.println("OK...");
	}
	@Test
	public void select(){
		System.out.println(dataUsers0Mapper.selectByPrimaryKey("test"));
	}
	
}