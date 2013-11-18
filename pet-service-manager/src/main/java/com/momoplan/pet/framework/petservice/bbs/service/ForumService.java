package com.momoplan.pet.framework.petservice.bbs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.framework.petservice.bbs.vo.ForumVo;

@Service
public class ForumService {
	private static Logger logger = LoggerFactory.getLogger(ForumService.class);

	private static Gson gson = MyGson.getInstance();

	@Autowired
	private ForumMapper forumMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;

	public List<Forum> getForumList(ForumVo vo)throws Exception{
		logger.debug("查询圈子 "+gson.toJson(vo));
		String pid = vo.getPid();
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		if(StringUtils.isNotEmpty(pid)){
			criteria.andPidEqualTo(pid);
		}else{
			criteria.andPidIsNull();
		}
		forumCriteria.setOrderByClause("seq asc");
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		return list;
	}

	public void saveForum(Forum vo) throws Exception{
		if("".equals(vo.getPid()))
			vo.setPid(null);
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新圈子 "+gson.toJson(vo));
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			vo.setId(IDCreater.uuid());
			vo.setCt(new Date());
			logger.debug("新增圈子 "+gson.toJson(vo));
			mapperOnCache.insertSelective(vo, vo.getId());
		}
	}
	
}
