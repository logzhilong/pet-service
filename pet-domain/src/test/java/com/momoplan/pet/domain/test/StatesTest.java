package com.momoplan.pet.domain.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.momoplan.pet.AbstractTest;
import com.momoplan.pet.commons.domain.states.mapper.CommonContentAuditMapper;
import com.momoplan.pet.commons.domain.states.mapper.StatesUserStatesMapper;

public class StatesTest extends AbstractTest {

	@Resource
	private CommonContentAuditMapper commonContentAuditMapper = null;
	
	@Resource
	private StatesUserStatesMapper statesUserStatesMapper = null;
	
	@Test
	public void test(){
		System.out.println(commonContentAuditMapper);
		System.out.println(statesUserStatesMapper);
	}
	
}
