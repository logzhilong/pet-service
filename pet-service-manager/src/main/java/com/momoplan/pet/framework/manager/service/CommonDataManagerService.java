package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.framework.manager.vo.PageBean;

public interface CommonDataManagerService {
	
	public PageBean<CommonAreaCode> listAreaCode(PageBean<CommonAreaCode> pageBean,CommonAreaCode vo) throws Exception;
	
	public int insertOrUpdateAreaCode(CommonAreaCode vo)throws Exception;
	/**
	 * 获取所有省份
	 */
	public List<CommonAreaCode> getConmonArealist() throws Exception;
	/**
	 * 根据id获取该城市
	 */
	public CommonAreaCode getCommonAreaCodeByid(CommonAreaCode  areaCode) throws Exception;
	/**
	 * 根据pid获取城市集合
	 */
	public List<CommonAreaCode> getConmonArealistBypid(CommonAreaCode  areaCode)throws Exception;
	/**
	 * 根据id删除该城市
	 */
	public void areaCodeDelByid(CommonAreaCode  areaCode) throws Exception;
}
