package com.momoplan.pet.domain.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;

import com.momoplan.pet.AbstractTest;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;
import com.momoplan.pet.commons.domain.states.po.StatesUserStates;

public class StatesTest extends AbstractTest {

	
	@Resource
	private StatesUserStatesMapper statesUserStatesMapper = null;
	
	@Test
	public void test(){
		StatesUserStates statesUserStates = new StatesUserStates();
		statesUserStates.setId(IDCreater.uuid());
		statesUserStates.setCt(new Date());
		statesUserStatesMapper.insertSelective(statesUserStates);
		System.out.println("OK.........");
		System.out.println("OK.........");
		System.out.println("OK.........");
		System.out.println("OK.........");
		System.out.println("OK.........");
	}
	
}
