package com.momoplan.pet.framework.petservice.version.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.user.mapper.SsoVersionMapper;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;
import com.momoplan.pet.commons.domain.user.po.SsoVersionCriteria;

@Service
public class VersionService {

	private static Logger logger = LoggerFactory.getLogger(VersionService.class);
	private static Gson gson = MyGson.getInstance();
	
	@Autowired
	private SsoVersionMapper ssoVersionMapper = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	public List<SsoVersion> getSsoVersionList()throws Exception {
		SsoVersionCriteria ssoVersionCriteria = new SsoVersionCriteria();
		List<SsoVersion> list = ssoVersionMapper.selectByExample(ssoVersionCriteria);
		return list;
	}

	public void update(SsoVersion vo)throws Exception {
		logger.debug("addOrEdit input : "+gson.toJson(vo));
		String id = vo.getId();
		Date now = new Date();
		if(StringUtils.isNotEmpty(id)){
			logger.debug("update");
			vo.setCreateDate(now);
			mapperOnCache.updateByPrimaryKeySelective(vo, vo.getId());
		}
	}
	
	public SsoVersion get(String id)throws Exception{
		try{
			return ssoVersionMapper.selectByPrimaryKey(id);
		}catch(Exception e){
			logger.error("",e.getMessage());
		}
		return null;
	}
	
}
