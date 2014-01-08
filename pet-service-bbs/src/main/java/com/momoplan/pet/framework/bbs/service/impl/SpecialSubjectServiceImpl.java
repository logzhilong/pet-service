package com.momoplan.pet.framework.bbs.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.SpecialSubjectMapper;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubjectCriteria;
import com.momoplan.pet.framework.bbs.service.SpecialSubjectService;
import com.momoplan.pet.framework.bbs.vo.SpecialSubjectState;
import com.momoplan.pet.framework.bbs.vo.SpecialSubjectType;

/**
 * 专题
 * TOTO :2014-01-08:没有加缓存，以后量大了，必须加到缓存中
 * @author liangc
 *
 */
@Service
public class SpecialSubjectServiceImpl implements SpecialSubjectService {

	private static Logger logger = LoggerFactory.getLogger(SpecialSubjectServiceImpl.class);
	@Autowired
	private SpecialSubjectMapper specialSubjectMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	public Page<List<SpecialSubject>> getSpecialSubjectList(int pageSize,int pageNo)throws Exception {
		Page<List<SpecialSubject>> page = new Page<List<SpecialSubject>>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		SpecialSubjectCriteria SpecialSubjectCriteria = new SpecialSubjectCriteria();
		SpecialSubjectCriteria.Criteria criteria = SpecialSubjectCriteria.createCriteria();
		criteria.andStateEqualTo(SpecialSubjectState.PUSHED.getCode());
		criteria.andTypeEqualTo(SpecialSubjectType.F.getCode());//如果是组，则只显示组记录的第一条
		int totalCount = specialSubjectMapper.countByExample(SpecialSubjectCriteria);
		SpecialSubjectCriteria.setMysqlOffset( page.getPageNo()*page.getPageSize() );
		SpecialSubjectCriteria.setMysqlLength( page.getPageSize() );
		SpecialSubjectCriteria.setOrderByClause("et desc");
		List<SpecialSubject> list = specialSubjectMapper.selectByExample(SpecialSubjectCriteria);
		page.setTotalCount(totalCount);
		if(totalCount>0){
			List<List<SpecialSubject>> dataList = new ArrayList<List<SpecialSubject>>();
			for(SpecialSubject ss : list){
				List<SpecialSubject> ssl = getSpecialSubjectListByGid(ss);
				dataList.add(ssl);
			}
			page.setData(dataList);
		}
		return page;
	}
	
	public List<SpecialSubject> getSpecialSubjectListByGid(SpecialSubject ss)throws Exception {
		List<SpecialSubject> list = new ArrayList<SpecialSubject>();
		String gid = ss.getGid();
		if(StringUtils.isEmpty(gid)){
			list.add(ss);
		}else{
			SpecialSubjectCriteria SpecialSubjectCriteria = new SpecialSubjectCriteria();
			SpecialSubjectCriteria.Criteria criteria = SpecialSubjectCriteria.createCriteria();
			criteria.andStateNotEqualTo(SpecialSubjectState.DELETE.getCode());
			criteria.andGidEqualTo(gid);
			SpecialSubjectCriteria.setOrderByClause("seq asc");
			list = specialSubjectMapper.selectByExample(SpecialSubjectCriteria);
		}
		return list;
	}
	
}