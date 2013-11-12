package com.momoplan.pet.bbs.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.AbstractTest;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumConditionMapper;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.commons.domain.bbs.po.UserForumConditionCriteria;

public class UserForumConditionTest extends AbstractTest{
	
	@Autowired
	private UserForumConditionMapper userForumConditionMapper = null;
	
	@Test
	public void testSelect(){
		UserForumConditionCriteria userForumConditionCriteria = new UserForumConditionCriteria();
		List<UserForumCondition> list = userForumConditionMapper.selectByExample(userForumConditionCriteria);
		logger.debug("获取被推荐的圈子列表，供新注册用户关注 list="+list);
	}
	
}
