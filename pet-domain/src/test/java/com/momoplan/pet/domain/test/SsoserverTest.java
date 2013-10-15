package com.momoplan.pet.domain.test;

import javax.annotation.Resource;

import org.junit.Test;

import com.momoplan.pet.AbstractTest;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoAuthenticationTokenMapper;
import com.momoplan.pet.commons.domain.ssoserver.mapper.SsoUserMapper;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationTokenCriteria;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUserCriteria;

public class SsoserverTest extends AbstractTest {

	@Resource
	private SsoUserMapper ssoUserMapper = null;
	
	@Resource
	private SsoAuthenticationTokenMapper ssoAuthenticationTokenMapper = null;
	@Test
	public void select(){
		SsoUserCriteria ssoUserCriteria = new SsoUserCriteria();
		int c1 = ssoUserMapper.countByExample(ssoUserCriteria);
		System.out.println("userCount : "+c1);
		
		SsoAuthenticationTokenCriteria ssoAuthenticationTokenCriteria = new SsoAuthenticationTokenCriteria();
		int c2 = ssoAuthenticationTokenMapper.countByExample(ssoAuthenticationTokenCriteria);
		System.out.println("tokenCount : "+c2);
		
	}
	
}