package com.momoplan.pet.framework.petservice.bbs.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.bbs.mapper.SpecialSubjectMapper;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubjectCriteria;
import com.momoplan.pet.framework.base.service.BaseService;
import com.momoplan.pet.framework.base.vo.Page;
import com.momoplan.pet.framework.petservice.bbs.vo.SpecialSubjectState;
import com.momoplan.pet.framework.petservice.bbs.vo.SpecialSubjectType;

@Service
public class SpecialSubjectService extends BaseService {

	private static Logger logger = LoggerFactory.getLogger(SpecialSubjectService.class);
	
	private static Gson gson = MyGson.getInstance();
	@Autowired
	private SpecialSubjectMapper specialSubjectMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	
	public Page<SpecialSubject> getSpecialSubjectList(Page<SpecialSubject> page,SpecialSubject vo)throws Exception {
		logger.debug("getSpecialSubjectList: "+gson.toJson(vo));
		
		SpecialSubjectCriteria SpecialSubjectCriteria = new SpecialSubjectCriteria();
		SpecialSubjectCriteria.Criteria criteria = SpecialSubjectCriteria.createCriteria();
		criteria.andStateNotEqualTo(SpecialSubjectState.DELETE.getCode());
		criteria.andTypeEqualTo(SpecialSubjectType.F.getCode());//如果是组，则只显示组记录的第一条
		int totalCount = specialSubjectMapper.countByExample(SpecialSubjectCriteria);
		SpecialSubjectCriteria.setMysqlOffset( (page.getPageNo()-1)*page.getPageSize() );
		SpecialSubjectCriteria.setMysqlLength( page.getPageSize() );
		SpecialSubjectCriteria.setOrderByClause("et desc");
		List<SpecialSubject> list = specialSubjectMapper.selectByExample(SpecialSubjectCriteria);
		
		page.setTotalCount(totalCount);
		page.setData(list);
		return page;
	}
	
	public List<SpecialSubject> getSpecialSubjectListByGid(String gid)throws Exception {
		SpecialSubjectCriteria SpecialSubjectCriteria = new SpecialSubjectCriteria();
		SpecialSubjectCriteria.Criteria criteria = SpecialSubjectCriteria.createCriteria();
		criteria.andStateNotEqualTo(SpecialSubjectState.DELETE.getCode());
		criteria.andGidEqualTo(gid);
		SpecialSubjectCriteria.setOrderByClause("seq asc");
		List<SpecialSubject> list = specialSubjectMapper.selectByExample(SpecialSubjectCriteria);
		return list;
	}

	public void groupLeader(String gid)throws Exception {
		List<SpecialSubject> l = getSpecialSubjectListByGid(gid);
		if(l!=null&&l.size()>0){
			Date now = new Date();
			SpecialSubject s = l.get(0);
			s.setType(SpecialSubjectType.F.getCode());
			s.setEt(now);
			mapperOnCache.updateByPrimaryKeySelective(s, s.getId());
			for(int i=1;i<l.size();i++){
				SpecialSubject ss = l.get(i);
				ss.setType(SpecialSubjectType.N.getCode());
				ss.setEt(now);
				mapperOnCache.updateByPrimaryKeySelective(ss, s.getId());
			}
		}
	}

	
}
