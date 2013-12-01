package com.momoplan.pet.framework.exper.service;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.exper.mapper.ExperMapper;
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.commons.domain.exper.po.ExperCriteria;
import com.momoplan.pet.commons.spring.CommonConfig;
/**
 * @author liangc
 */
@Service
public class ExperService {
	private static Logger logger = LoggerFactory.getLogger(ExperService.class);
	@Autowired
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private CommonConfig commonConfig = null;
	@Autowired
	private ExperMapper experMapper = null;
	
	public Page<Exper> getExperList(Page<Exper> pages,String pid) throws Exception {
		logger.debug("pid="+pid);
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		ExperCriteria experCriteria = new ExperCriteria();
		ExperCriteria.Criteria criteria = experCriteria.createCriteria();
		experCriteria.setOrderByClause("seq asc");
		if(StringUtils.isEmpty(pid)){
			criteria.andPidIsNull();
		}else{
			criteria.andPidEqualTo(pid);
		}
		int totalCount = experMapper.countByExample(experCriteria);
		experCriteria.setMysqlOffset(pageNo * pageSize);
		experCriteria.setMysqlLength((pageNo+1)*pageSize);
		List<Exper> data = experMapper.selectByExample(experCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
}