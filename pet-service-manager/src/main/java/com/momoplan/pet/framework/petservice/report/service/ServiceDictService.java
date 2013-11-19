package com.momoplan.pet.framework.petservice.report.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.domain.manager.mapper.MgrServiceDictMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrServiceDict;
import com.momoplan.pet.commons.domain.manager.po.MgrServiceDictCriteria;

@Service
public class ServiceDictService {

	private static Logger logger = LoggerFactory.getLogger(ServiceDictService.class);
	private static Gson gson = MyGson.getInstance();
	
	@Autowired
	private MgrServiceDictMapper mgrServiceDictMapper = null;
	public List<MgrServiceDict> getServiceDictList()throws Exception {
		MgrServiceDictCriteria mgrServiceDictCriteria = new MgrServiceDictCriteria();
		List<MgrServiceDict> list = mgrServiceDictMapper.selectByExample(mgrServiceDictCriteria);
		return list;
	}

	public void addOrEditServiceDict(MgrServiceDict vo)throws Exception {
		logger.debug("addOrEditServiceDict input : "+gson.toJson(vo));
		String id = vo.getId();
		Date now = new Date();
		if(StringUtils.isNotEmpty(id)){
			logger.debug("update");
			vo.setEt(now);
			mgrServiceDictMapper.updateByPrimaryKeySelective(vo);
		}else{
			logger.debug("insert");
			vo.setId(IDCreater.uuid());
			vo.setCt(now);
			vo.setEt(now);
			vo.setCb(vo.getEb());
			mgrServiceDictMapper.insertSelective(vo);
		}
	}
	
	public MgrServiceDict getMgrServiceDict(String service,String method)throws Exception{
		try{
			MgrServiceDictCriteria mgrServiceDictCriteria = new MgrServiceDictCriteria();
			mgrServiceDictCriteria.createCriteria().andServiceEqualTo(service).andMethodEqualTo(method);
			List<MgrServiceDict> list = mgrServiceDictMapper.selectByExample(mgrServiceDictCriteria);
			return list.get(0);
		}catch(Exception e){
			logger.debug("此服务没有别名 service="+service+";method="+method);
			logger.debug(e.getMessage());
		}
		return null;
	}
	
	
}
