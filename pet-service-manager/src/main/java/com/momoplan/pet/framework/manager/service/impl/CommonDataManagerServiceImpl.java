package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.bbs.mapper.CommonAreaCodeMapper;
import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCode;
import com.momoplan.pet.commons.domain.bbs.po.CommonAreaCodeCriteria;
import com.momoplan.pet.framework.manager.service.CommonDataManagerService;
import com.momoplan.pet.framework.manager.vo.PageBean;

/**
 * 公共数据管理
 * @author liangc
 */
@Service
public class CommonDataManagerServiceImpl implements CommonDataManagerService {
	
	private Logger logger = LoggerFactory.getLogger(CommonDataManagerServiceImpl.class);
	
	@Autowired
	private CommonAreaCodeMapper commonAreaCodeMapper = null;
	
	@Override
	public PageBean<CommonAreaCode> listAreaCode(String father,String grandsunid,PageBean<CommonAreaCode> pageBean, CommonAreaCode vo) throws Exception {
		CommonAreaCodeCriteria commonAreaCodeCriteria = new CommonAreaCodeCriteria();
		CommonAreaCodeCriteria.Criteria criteria = commonAreaCodeCriteria.createCriteria();
		if("all".equals(grandsunid) && "all".equals(grandsunid)){
			
		}
		else if(!"".equals(grandsunid) && null != grandsunid){
			criteria.andPidEqualTo(grandsunid);
		}
		else if(!"".equals(father) && null != father){
			criteria.andPidEqualTo(father);
		}
		int totalCount = commonAreaCodeMapper.countByExample(commonAreaCodeCriteria);
		commonAreaCodeCriteria.setMysqlOffset((pageBean.getPageNo()-1)*pageBean.getPageSize());
		commonAreaCodeCriteria.setMysqlLength(pageBean.getPageSize());
		List<CommonAreaCode> list = commonAreaCodeMapper.selectByExample(commonAreaCodeCriteria);
		pageBean.setData(list);
		pageBean.setTotalRecorde(totalCount);
		return pageBean;
	}
	/**
	* 增加或者更新方法
	*/
	@Override
	public int insertOrUpdateAreaCode(CommonAreaCode vo) throws Exception {
		String id = vo.getId();
		CommonAreaCode po = commonAreaCodeMapper.selectByPrimaryKey(id);
		if(po!=null&&!"".equals(po.getId())){
			logger.debug("selectByPK.po="+po.toString());
			return commonAreaCodeMapper.updateByPrimaryKeySelective(vo);
		}else{
			return commonAreaCodeMapper.insertSelective(vo);
		}
	}
	/**
	 * 获取所有国家
	 */
	@Override
	public List<CommonAreaCode> getConmonArealist() throws Exception
	{
		try {
			CommonAreaCodeCriteria areaCodeCriteria=new CommonAreaCodeCriteria();
			CommonAreaCodeCriteria.Criteria criteria=areaCodeCriteria.createCriteria();
			criteria.andPidEqualTo("00000");
			List<CommonAreaCode> codes=commonAreaCodeMapper.selectByExample(areaCodeCriteria);
			return codes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据id获取该城市
	 */
	@Override
	public CommonAreaCode  getCommonAreaCodeByid(CommonAreaCode  areaCode){
		try {
			CommonAreaCode code=commonAreaCodeMapper.selectByPrimaryKey(areaCode.getId());
			return code;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据pid获取城市集合
	 */
	@Override
	public List<CommonAreaCode> getConmonArealistBypid(CommonAreaCode  areaCode)throws Exception
	{
		try {
			CommonAreaCodeCriteria areaCodeCriteria=new CommonAreaCodeCriteria();
			CommonAreaCodeCriteria.Criteria criteria=areaCodeCriteria.createCriteria();
			criteria.andPidEqualTo(areaCode.getPid());
			List<CommonAreaCode> codes=commonAreaCodeMapper.selectByExample(areaCodeCriteria);
			return codes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 *根据id删除该城市 
	 * 
	 */
	public void areaCodeDelByid(CommonAreaCode  areaCode){
		try {
			CommonAreaCodeCriteria areaCodeCriteria=new CommonAreaCodeCriteria();
			CommonAreaCodeCriteria.Criteria criteria=areaCodeCriteria.createCriteria();
			criteria.andPidEqualTo(areaCode.getId());
			List<CommonAreaCode> codes=commonAreaCodeMapper.selectByExample(areaCodeCriteria);
			if(codes.size()>0){
				for(CommonAreaCode code:codes){
					commonAreaCodeMapper.deleteByPrimaryKey(code.getId());
				}
			}
			commonAreaCodeMapper.deleteByPrimaryKey(areaCode.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 }
