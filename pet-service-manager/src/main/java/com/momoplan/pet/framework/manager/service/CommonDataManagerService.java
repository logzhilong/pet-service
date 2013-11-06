package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.framework.manager.vo.PageBean;

public interface CommonDataManagerService {
	/**
	 * 获取所有Area
	 * @param fatherid省市id
	 * @param grandsunid区县id
	 * @param pageBean
	 * @param vo地区表实体
	 * @return
	 * @throws Exception
	 */
	public PageBean<CommonAreaCode> listAreaCode(String fatherid,String grandsunid,PageBean<CommonAreaCode> pageBean,CommonAreaCode vo) throws Exception;
	/**
	 * 增加或者修改地区
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public int insertOrUpdateAreaCode(CommonAreaCode vo)throws Exception;
	/**
	 * 获取所有省份
	 * @return
	 * @throws Exception
	 */
	public List<CommonAreaCode> getConmonArealist() throws Exception;
	/**
	 * 根据城市id获取城市
	 * @param areaCode
	 * @return
	 * @throws Exception
	 */
	public CommonAreaCode getCommonAreaCodeByid(CommonAreaCode  areaCode) throws Exception;
	/**
	 * 根据省市id获取区县集合
	 * @param areaCode
	 * @return
	 * @throws Exception
	 */
	public List<CommonAreaCode> getConmonArealistBypid(CommonAreaCode  areaCode)throws Exception;
	/**
	 * 根据id删除城市
	 * @param areaCode
	 * @throws Exception
	 */
	public void areaCodeDelByid(CommonAreaCode  areaCode) throws Exception;
}
