package com.momoplan.pet.framework.bbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumRelMapper;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;
@Service
public class UserForumRelServiceImpl implements UserForumRelService {

	@Autowired
	private UserForumRelMapper userForumRelMapper=null;
	
	/**
	 * 退出圈子
	 * @return
	 */
	@Override
	public Object quitForum(UserForumRel userForumRel) throws Exception{
			UserForumRelCriteria userForumRelCriteria=new UserForumRelCriteria();
			UserForumRelCriteria.Criteria criteria=userForumRelCriteria.createCriteria();
			String forumid=userForumRel.getForumId();
			String userid=userForumRel.getUserId();
			criteria.andForumIdEqualTo((forumid));
			criteria.andUserIdEqualTo(userid);
			userForumRelMapper.deleteByExample(userForumRelCriteria);
			return "quitForumSuccss";
	}
	
	/**
	 * 关注圈子
	 * @return
	 */
	@Override
	public void attentionForum(UserForumRel po) throws Exception{
			po.setId(IDCreater.uuid());
			userForumRelMapper.insertSelective(po);	
	}
	
}
