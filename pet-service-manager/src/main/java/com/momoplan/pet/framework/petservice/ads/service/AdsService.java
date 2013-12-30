package com.momoplan.pet.framework.petservice.ads.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.notice.mapper.AdsMapper;
import com.momoplan.pet.commons.domain.notice.po.Ads;
import com.momoplan.pet.commons.domain.notice.po.AdsCriteria;
import com.momoplan.pet.framework.base.vo.Page;

@Controller
public class AdsService {
	
	static Gson gson = MyGson.getInstance();
	private static Logger logger = LoggerFactory.getLogger(AdsService.class);
	@Autowired
	private AdsMapper adsMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	public Page<Ads> getAdsList(Page<Ads> pages,Ads vo)throws Exception{
		int pageSize = pages.getPageSize();
		int pageNo = pages.getPageNo();
		AdsCriteria adsCriteria = new AdsCriteria();
		adsCriteria.setOrderByClause("et desc");
		int totalCount = adsMapper.countByExample(adsCriteria);
		adsCriteria.setMysqlOffset((pageNo-1) * pageSize);
		adsCriteria.setMysqlLength(pageSize);
		List<Ads> data = adsMapper.selectByExample(adsCriteria);
		pages.setTotalCount(totalCount);
		pages.setData(data);
		return pages;
	}
	
	public void save(Ads vo) throws Exception{
		Date now = new Date();
		if(vo.getId()!=null&&!"".equals(vo.getId())){
			logger.debug("更新 "+gson.toJson(vo));
			vo.setEt(now);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}else{
			vo.setId(IDCreater.uuid());
			vo.setCt(now);
			vo.setCb(vo.getEb());
			vo.setEt(now);
			vo.setState("active");
			logger.debug("新增 "+gson.toJson(vo));
			mapperOnCache.insertSelective(vo, vo.getId());
		}
	}
	
}
