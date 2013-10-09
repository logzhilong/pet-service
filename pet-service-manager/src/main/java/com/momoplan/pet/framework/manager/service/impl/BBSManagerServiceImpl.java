package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.bbs.mapper.ForumMapper;
import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.commons.domain.bbs.po.ForumCriteria;
import com.momoplan.pet.framework.manager.service.BBSManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;


@Service
public class BBSManagerServiceImpl implements BBSManagerService {
	
	@Autowired
	private ForumMapper forumMapper = null;
	
	@Override
	public PageBean<Forum> listForum(PageBean<Forum> pageBean , Forum vo) throws Exception {
		String id = vo.getId();
		ForumCriteria forumCriteria = new ForumCriteria();
		ForumCriteria.Criteria criteria = forumCriteria.createCriteria();
		if(StringUtils.isEmpty(id)){
			criteria.andPidIsNull();
		}else{
			criteria.andPidEqualTo(id);
		}
		int totalCount = forumMapper.countByExample(forumCriteria);
		forumCriteria.setMysqlOffset((pageBean.getPageNo()-1)*pageBean.getPageSize());
		forumCriteria.setMysqlLength(pageBean.getPageSize());
		List<Forum> list = forumMapper.selectByExample(forumCriteria);
		pageBean.setData(list);
		pageBean.setTotalRecorde(totalCount);
		return pageBean;
	}

}
