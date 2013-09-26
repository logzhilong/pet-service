package com.momoplan.pet.framework.bbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.framework.bbs.service.ForumService;
@Service
public class ForumServiceImpl implements ForumService {

	@Resource
	ForumMapper forumMapper=null;

	
}
