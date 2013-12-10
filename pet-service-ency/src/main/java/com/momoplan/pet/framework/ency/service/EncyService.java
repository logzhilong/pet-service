package com.momoplan.pet.framework.ency.service;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.ency.mapper.EncyMapper;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.commons.domain.ency.po.EncyCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;
/**
 * @author liangc
 */
@Service
public class EncyService {
	private static Logger logger = LoggerFactory.getLogger(EncyService.class);
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private EncyMapper encyMapper = null;
	public Page<Ency> getEncyList(Page<Ency> pages,String pid) throws Exception {
		logger.debug("pid="+pid);
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		EncyCriteria encyCriteria = new EncyCriteria();
		EncyCriteria.Criteria criteria = encyCriteria.createCriteria();
		encyCriteria.setOrderByClause("seq asc");
		if(StringUtils.isEmpty(pid)){
			criteria.andPidIsNull();
		}else{
			criteria.andPidEqualTo(pid);
		}
		int totalCount = encyMapper.countByExample(encyCriteria);
		encyCriteria.setMysqlOffset(pageNo * pageSize);
		encyCriteria.setMysqlLength(pageSize);
		List<Ency> data = encyMapper.selectByExample(encyCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
}