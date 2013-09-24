package com.momoplan.pet.framework.bbs.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.bbs.service.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	
	@Resource
	ForumMapper forumMapper = null;
	
	@Override
	public void insertDemo() {
		String id = IDCreater.uuid();
		Forum forum = new Forum();
		forum.setId(id);
		forum.setCt(new Date());
		forum.setCb("admin");
		forum.setName("测试006");
		forumMapper.insertSelective(forum);
		System.out.println("OK...");
	}

}
