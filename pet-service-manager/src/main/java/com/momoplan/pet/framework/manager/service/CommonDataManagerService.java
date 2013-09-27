package com.momoplan.pet.framework.manager.service;

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.framework.manager.vo.PageBean;

public interface CommonDataManagerService {
	
	public PageBean<CommonAreaCode> listAreaCode(PageBean<CommonAreaCode> pageBean,CommonAreaCode vo) throws Exception;
	
	public int insertOrUpdateAreaCode(CommonAreaCode vo)throws Exception;

}
