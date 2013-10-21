package com.momoplan.pet.framework.bbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
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
	public Object quitForum(ClientRequest ClientRequest){
		try {
			UserForumRelCriteria userForumRelCriteria=new UserForumRelCriteria();
			UserForumRelCriteria.Criteria criteria=userForumRelCriteria.createCriteria();
			String forumid=PetUtil.getParameter(ClientRequest, "forumid");
			criteria.andIdEqualTo(forumid);
			userForumRelMapper.deleteByExample(userForumRelCriteria);
			return "quitForumSuccss";
		} catch (Exception e) {
			e.printStackTrace();
			return "quitForumFail";
		}
	}
	
	/**
	 * 关注圈子
	 * @return
	 */
	@Override
	public Object attentionForum(ClientRequest ClientRequest){
		try {
			UserForumRel userForumRel=new UserForumRel();
			userForumRel.setId(IDCreater.uuid());
			userForumRel.setUserId(PetUtil.getParameter(ClientRequest, "userId"));
			userForumRel.setForumId(PetUtil.getParameter(ClientRequest, "forumid"));
			userForumRelMapper.insertSelective(userForumRel);
			return "attentionForumSuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "attentionForumFail";
		}
	}
	
}
