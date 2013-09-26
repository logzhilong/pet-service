package com.momoplan.pet.framework.manager.service;

import com.momoplan.pet.commons.domain.bbs.po.Forum;
import com.momoplan.pet.framework.manager.vo.PageBean;


public interface BBSManagerService {
	
	/**
	 * 圈子列表
	 * @return
	 * @throws Exception
	 */
	public PageBean<Forum> listForum(PageBean<Forum> pageBean , Forum vo) throws Exception;
	
}
