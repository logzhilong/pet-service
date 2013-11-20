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
import com.momoplan.pet.commons.domain.manager.mapper.MgrChannelDictMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrChannelDict;
import com.momoplan.pet.commons.domain.manager.po.MgrChannelDictCriteria;

@Service
public class ChannelDictService {

	private static Logger logger = LoggerFactory.getLogger(ChannelDictService.class);
	private static Gson gson = MyGson.getInstance();
	
	@Autowired
	private MgrChannelDictMapper mgrChannelDictMapper = null;

	public List<MgrChannelDict> getChannelDictList()throws Exception {
		MgrChannelDictCriteria mgrChannelDictCriteria = new MgrChannelDictCriteria();
		List<MgrChannelDict> list = mgrChannelDictMapper.selectByExample(mgrChannelDictCriteria);
		return list;
	}

	public void addOrEdit(MgrChannelDict vo)throws Exception {
		logger.debug("addOrEditServiceDict input : "+gson.toJson(vo));
		String id = vo.getId();
		Date now = new Date();
		if(StringUtils.isNotEmpty(id)){
			logger.debug("update");
			vo.setEt(now);
			mgrChannelDictMapper.updateByPrimaryKeySelective(vo);
		}else{
			logger.debug("insert");
			vo.setId(IDCreater.uuid());
			vo.setCt(now);
			vo.setEt(now);
			vo.setCb(vo.getEb());
			mgrChannelDictMapper.insertSelective(vo);
		}
	}

	public MgrChannelDict getChannelDict(String channel)throws Exception {
		MgrChannelDictCriteria mgrChannelDictCriteria = new MgrChannelDictCriteria();
		mgrChannelDictCriteria.createCriteria().andCodeEqualTo(channel);
		List<MgrChannelDict> list = mgrChannelDictMapper.selectByExample(mgrChannelDictCriteria);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	
	
}
