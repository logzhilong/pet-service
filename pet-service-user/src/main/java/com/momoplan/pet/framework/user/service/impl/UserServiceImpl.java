package com.momoplan.pet.framework.user.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.cache.MapperOnCache;
import com.momoplan.pet.commons.domain.ssoserver.mapper.PetInfoMapper;
import com.momoplan.pet.commons.domain.ssoserver.po.PetInfo;
import com.momoplan.pet.commons.domain.ssoserver.po.PetInfoCriteria;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.ssoserver.po.SsoUser;
import com.momoplan.pet.commons.domain.ssoserver.po.UserLocation;
import com.momoplan.pet.framework.user.service.UserService;
import com.momoplan.pet.framework.user.vo.UserVo;
/**
 * TODO 关于 token 相关的操作，应该在 redis 中完成，此处留一个作业
 * @author liangc
 */
@Service
public class UserServiceImpl implements UserService {

	private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Resource
	private MapperOnCache mapperOnCache = null;
	@Autowired
	private PetInfoMapper petInfoMapper = null; 
	
	@Override
	public void updateUser(SsoUser user) throws Exception {
		mapperOnCache.updateByPrimaryKeySelective(user.getClass(), user.getId());
	}

	@Override
	public void updateUserLocation(String userId, double longitude, double latitude) throws Exception {
		Long uid = Long.parseLong(userId);
		UserLocation ul = mapperOnCache.selectByPrimaryKey(UserLocation.class, uid);
		if(ul == null){
			ul = new UserLocation();
			ul.setUserid(uid);
			ul.setCreateDate(new Date());
			ul.setLatitude(latitude);
			ul.setLongitude(longitude);
			mapperOnCache.insertSelective(ul, uid);
		}else{
			ul.setCreateDate(new Date());
			ul.setLatitude(latitude);
			ul.setLongitude(longitude);
			mapperOnCache.updateByPrimaryKeySelective(ul, uid);
		}
	}
	
	@Override
	public UserVo getUser(SsoAuthenticationToken tokenObj) throws Exception {
		Long userid = tokenObj.getUserid();
		SsoUser user = mapperOnCache.selectByPrimaryKey(SsoUser.class, userid);
		UserLocation userLocation = mapperOnCache.selectByPrimaryKey(UserLocation.class, userid);
		UserVo userVo = null;
		user.setPassword(null);
		if(userLocation!=null){
			userVo = new UserVo(user,userLocation.getLongitude(),userLocation.getLatitude());
		}else{
			userVo = new UserVo(user,0,0);
		}
		return userVo;
	}

	@Override
	public void updatePetInfo(PetInfo petInfo) throws Exception {
		
	}

	@Override
	public List<PetInfo> getPetInfo(String userid) throws Exception {
		PetInfoCriteria petInfoCriteria = new PetInfoCriteria();
		PetInfoCriteria.Criteria criter =  petInfoCriteria.createCriteria();
		criter.andUseridEqualTo(Long.parseLong(userid));
		List<PetInfo> list = petInfoMapper.selectByExample(petInfoCriteria);
		return list;
	}
	
}