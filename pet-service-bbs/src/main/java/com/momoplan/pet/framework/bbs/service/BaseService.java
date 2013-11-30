package com.momoplan.pet.framework.bbs.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.momoplan.pet.commons.domain.bbs.mapper.UserForumRelMapper;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRel;
import com.momoplan.pet.commons.domain.bbs.po.UserForumRelCriteria;

public abstract class BaseService {

	private static Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Autowired
	private UserForumRelMapper userForumRelMapper = null;
	
	protected Map<String, String> getUserForumMap(String userId){
		UserForumRelCriteria userForumRelCriteria = new UserForumRelCriteria();
		userForumRelCriteria.createCriteria().andUserIdEqualTo(userId);
		List<UserForumRel> userForumRelList = userForumRelMapper.selectByExample(userForumRelCriteria);
		logger.debug("转换成 hash 结构，当作字典用");
		Map<String, String> userForumRelMap = new HashMap<String, String>();
		if (userForumRelList != null && userForumRelList.size() > 0){
			for (UserForumRel userForumRel : userForumRelList) {
				userForumRelMap.put(userForumRel.getForumId(), userForumRel.getUserId());
			}
		}
		return userForumRelMap;
	}
	
	/**
	 * 获取被关注总数
	 * @param forumId
	 * @return
	 */
	protected Long getTotalAtte(String forumId){
		//TODO 稍后挪到缓存中实现
		UserForumRelCriteria userForumRelCriteria = new UserForumRelCriteria();
		userForumRelCriteria.createCriteria().andForumIdEqualTo(forumId);
		int count = userForumRelMapper.countByExample(userForumRelCriteria);
		logger.debug(forumId+" 被关注数 : "+count);
		return Long.valueOf(count);
	}
}
