package com.momoplan.pet.framework.bbs.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.UserForumRelMapper;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;

@Service
public class UserForumRelServiceImpl implements UserForumRelService {

	@Autowired
	private UserForumRelMapper userForumRelMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	private static Logger logger = LoggerFactory.getLogger(UserForumRelServiceImpl.class);

	/**
	 * 退出圈子
	 * @return
	 */
	@Override
	public void quitForum(UserForumRel po) throws Exception {
		UserForumRelCriteria userForumRelCriteria = new UserForumRelCriteria();
		UserForumRelCriteria.Criteria criteria = userForumRelCriteria.createCriteria();
		String forumid = po.getForumId();
		String userid = po.getUserId();
		criteria.andForumIdEqualTo((forumid));
		criteria.andUserIdEqualTo(userid);
		List<UserForumRel> list = userForumRelMapper.selectByExample(userForumRelCriteria);
		if(list!=null)
			for(UserForumRel p : list){
				logger.debug("取消关注 : "+p.toString());
				mapperOnCache.deleteByPrimaryKey(p.getClass(), p.getId());
			}
	}

	/**
	 * 关注圈子
	 * 
	 * @return
	 */
	@Override
	public void attentionForum(UserForumRel po) throws Exception {
		po.setId(IDCreater.uuid());
		mapperOnCache.insertSelective(po, po.getId());
	}

}
