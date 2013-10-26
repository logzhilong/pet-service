package com.momoplan.pet.framework.pat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.pat.po.PatUserPat;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.repository.pat.PatUserPatRepository;
import com.momoplan.pet.framework.pat.service.PatService;
/**
 * @author liangc
 */
@Service
public class PatServiceImpl implements PatService {

	private static Logger logger = LoggerFactory.getLogger(PatServiceImpl.class);
	@Autowired
	private PatUserPatRepository patUserPatRepository = null;
	@Autowired
	private MapperOnCache mapperOnCache = null;
	
	@Override
	public void addPat(String userid, String srcid,String type) throws Exception {
		PatUserPat po = new PatUserPat();
		po.setId(IDCreater.uuid());
		po.setCt(new Date());
		po.setSrcId(srcid);
		po.setType(type);
		po.setUserid(userid);
		logger.debug("赞:po="+po.toString());
		patUserPatRepository.insertSelective(po);
	}

	@Override
	public void delPat(String userid, String srcid) throws Exception {
		patUserPatRepository.delete(userid,srcid);
	}

	@Override
	public List<SsoUser> getPat(String srcid) throws Exception {
		List<String> list = patUserPatRepository.getPatUserListBySrcId(srcid);
		List<SsoUser> ulist = null;
		if(list!=null&&list.size()>0){
			ulist = new ArrayList<SsoUser>(list.size());
			for(String json : list){
				JSONObject jo = new JSONObject(json);
				String uid = jo.getString("userid");
				SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, uid);
				ulist.add(user);
			}
		}
		return ulist;
	}
	
}