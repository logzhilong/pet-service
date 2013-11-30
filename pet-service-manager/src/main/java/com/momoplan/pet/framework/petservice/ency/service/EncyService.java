package com.momoplan.pet.framework.petservice.ency.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.domain.ency.mapper.EncyMapper;
import com.momoplan.pet.commons.domain.ency.po.Ency;
import com.momoplan.pet.commons.domain.ency.po.EncyCriteria;
import com.momoplan.pet.framework.base.controller.BaseAction;

@Controller
public class EncyService extends BaseAction{
	
	private static Logger logger = LoggerFactory.getLogger(EncyService.class);
	@Autowired
	private EncyMapper encyMapper = null;
	
	public Page<Ency> getEncyList(Page<Ency> pages,Ency vo)throws Exception{
		String pid = vo.getPid();
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
		encyCriteria.setMysqlLength((pageNo+1)*pageSize);
		List<Ency> data = encyMapper.selectByExample(encyCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
	public void save(Ency vo) throws Exception{
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
