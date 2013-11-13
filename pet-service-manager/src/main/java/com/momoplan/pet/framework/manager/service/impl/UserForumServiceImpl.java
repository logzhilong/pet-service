package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumConditionMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.commons.domain.bbs.po.UserForumCondition;
import com.momoplan.pet.commons.domain.bbs.po.UserForumConditionCriteria;
import com.momoplan.pet.framework.manager.service.UserForumService;

@Service
public class UserForumServiceImpl implements UserForumService {
	private static Logger logger = LoggerFactory.getLogger(UserForumServiceImpl.class);
	@Autowired
	UserForumConditionMapper conditionMapper=null;
	@Autowired
	ForumMapper forumMapper;
	@Override
	public List<UserForumCondition> GetUserForumList() throws Exception {
		logger.debug("welcome to GetUserForumList.....................");
		UserForumConditionCriteria forumConditionCriteria=new UserForumConditionCriteria();
		return conditionMapper.selectByExample(forumConditionCriteria);
	}
	
	@Override
	public void addOrUpdateUserForum(UserForumCondition condition)throws Exception {
		logger.debug("welcome to addOrUpdateUserForum.....................");
		String id=condition.getId();
		UserForumCondition userForum=conditionMapper.selectByPrimaryKey(id);
		if(userForum != null && !"" .equals(userForum.getId())){
			condition.setCt(new Date());
			conditionMapper.updateByPrimaryKey(condition);
		}else {
			condition.setId(IDCreater.uuid());
			condition.setCt(new Date());
			conditionMapper.insertSelective(condition);
		}
		
	}
	
	@Override
	public void deleteUserForoum(UserForumCondition condition) throws Exception {
		logger.debug("welcome to deleteUserForoum.....................");
		String id=condition.getId();
		UserForumCondition userForum=conditionMapper.selectByPrimaryKey(id);
		if(userForum != null && !"" .equals(userForum.getId())){
			conditionMapper.deleteByPrimaryKey(condition.getId());
		}
	}

	@Override
	public UserForumCondition getuserforumByid(UserForumCondition condition)throws Exception {
		logger.debug("welcome to getuserforumByid...  ..................");
		if("" != condition.getId() && null != condition.getId()){
			logger.debug("getuserforumByid"+conditionMapper.selectByPrimaryKey(condition.getId()));
			return conditionMapper.selectByPrimaryKey(condition.getId());
		}else {
			return null;
		}
	}

	@Override
	public List<Forum> getForumlist() throws Exception {
			logger.debug("welcome to getForumlist.....................");
			ForumCriteria forumCriteria = new ForumCriteria();
			ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
			criteria.andPidIsNotNull();
			List<Forum> forums = forumMapper.selectByExample(forumCriteria);
			return forums;
	}

}
