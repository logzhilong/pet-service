package com.momoplan.pet.framework.notice.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.notice.mapper.AdsMapper;
import com.momoplan.pet.commons.domain.notice.po.Ads;
import com.momoplan.pet.commons.domain.notice.po.AdsCriteria;
/**
 * @author liangc
 */
@Service
public class AdsService {
	private static Logger logger = LoggerFactory.getLogger(AdsService.class);
	@Autowired
	private AdsMapper adsMapper = null;
	public List<Ads> getAdsList() throws Exception {
		AdsCriteria adsCriteria = new AdsCriteria();
		AdsCriteria.Criteria criteria = adsCriteria.createCriteria();
		criteria.andStateEqualTo("active");
		List<Ads> list = adsMapper.selectByExample(adsCriteria);
		return list;
	}
	
}