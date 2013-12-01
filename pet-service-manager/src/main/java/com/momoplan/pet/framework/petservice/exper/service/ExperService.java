package com.momoplan.pet.framework.petservice.exper.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.domain.exper.mapper.ExperMapper;
import com.momoplan.pet.commons.domain.exper.po.Exper;
import com.momoplan.pet.commons.domain.exper.po.ExperCriteria;
import com.momoplan.pet.framework.base.controller.BaseAction;

@Controller
public class ExperService extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(ExperService.class);
	@Autowired
	private ExperMapper experMapper = null;
	
	public Page<Exper> getExperList(Page<Exper> pages,Exper vo)throws Exception{
		String pid = vo.getPid();
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
	
	public void save(Exper vo) throws Exception{
		Date now = new Date();
		if("".equals(vo.getPid()))
			vo.setPid(null);
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新 "+gson.toJson(vo));
			vo.setEt(now);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			vo.setId(IDCreater.uuid());
			vo.setCt(now);
			vo.setCb(vo.getEb());
			vo.setEt(now);
			logger.debug("新增 "+gson.toJson(vo));
			mapperOnCache.insertSelective(vo, vo.getId());
		}
	}
	
}
