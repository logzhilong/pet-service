package com.momoplan.pet.framework.bbs.controller;

import java.util.UUID;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class AbstractUuid extends AbstractJUnit4SpringContextTests {
	
	public String uuid(){
		
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}
	
}