package com.momoplan.pet.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.framework.bbs.service.DemoService;


public class DemoServiceTest extends AbstractTest {

	@Autowired
	private DemoService demoService;
	
	@Test
	public void insertTest(){
		demoService.insertDemo();
	}
	
}
