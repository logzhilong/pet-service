package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
	public PageBean<CommonAreaCode> listAreaCode(PageBean<CommonAreaCode> pageBean, CommonAreaCode vo) throws Exception {
		int pageNo = pageBean.getPageNo();
		int pageSize = pageBean.getPageSize();
		String id = vo.getId();
		CommonAreaCodeCriteria commonAreaCodeCriteria = new CommonAreaCodeCriteria();
		CommonAreaCodeCriteria.Criteria criteria = commonAreaCodeCriteria.createCriteria();
		if(StringUtils.isEmpty(id)){
			criteria.andPidEqualTo("0");
		}else{
			criteria.andPidEqualTo(id);
		}
		int totalCount = commonAreaCodeMapper.countByExample(commonAreaCodeCriteria);
		commonAreaCodeCriteria.setMysqlOffset((pageNo-1)*pageSize);
		commonAreaCodeCriteria.setMysqlLength(pageSize);
		List<CommonAreaCode> list = commonAreaCodeMapper.selectByExample(commonAreaCodeCriteria);
		pageBean.setData(list);
		pageBean.setTotalCount(totalCount);
		return pageBean;
	}

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

}
