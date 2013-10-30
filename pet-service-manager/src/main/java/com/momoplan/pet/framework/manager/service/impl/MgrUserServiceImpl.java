package com.momoplan.pet.framework.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.domain.manager.mapper.MgrRoleMapper;
import com.momoplan.pet.commons.domain.manager.mapper.MgrUserMapper;
import com.momoplan.pet.commons.domain.manager.mapper.MgrUserRoleRelMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrRoleCriteria;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.commons.domain.manager.po.MgrUserCriteria;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRelCriteria;
import com.momoplan.pet.framework.manager.service.MgrUserService;

@Service
public class MgrUserServiceImpl implements MgrUserService{
	
	private static Logger logger = LoggerFactory.getLogger(MgrUserServiceImpl.class);
	
	private MgrUserMapper mgrUserMapper = null;
	private MgrRoleMapper mgrRoleMapper = null;
	private MgrUserRoleRelMapper mgrUserRoleRelMapper = null;
	
	@Autowired
	public MgrUserServiceImpl(MgrUserMapper mgrUserMapper, MgrRoleMapper mgrRoleMapper, MgrUserRoleRelMapper mgrUserRoleRelMapper) {
		super();
		this.mgrUserMapper = mgrUserMapper;
		this.mgrRoleMapper = mgrRoleMapper;
		this.mgrUserRoleRelMapper = mgrUserRoleRelMapper;
	}

	@Override
	public List<MgrRole> getRoleByUserId(String userId) throws Exception {
		MgrUserRoleRelCriteria mgrUserRoleRelCriteria = new MgrUserRoleRelCriteria();
		mgrUserRoleRelCriteria.createCriteria().andUserIdEqualTo(userId);
		List<MgrUserRoleRel> mgrUserRoleRel = mgrUserRoleRelMapper.selectByExample(mgrUserRoleRelCriteria);
		List<String> roleIds = new ArrayList<String>();
		
		for(MgrUserRoleRel rel: mgrUserRoleRel){
			roleIds.add(rel.getRoleId());
		}
		MgrRoleCriteria mgrRoleCriteria = new MgrRoleCriteria();
		mgrRoleCriteria.createCriteria().andIdIn(roleIds);
		List<MgrRole> roles = mgrRoleMapper.selectByExample(mgrRoleCriteria);
		
		if(roles!=null&&roles.size()>0)
			logger.debug("GET ROLES : "+MyGson.getInstance().toJson(roles));
		return roles;
	}

	@Override
	public MgrUser getMgrUserByName(String username) throws Exception {
		MgrUserCriteria mgrUserCriteria = new MgrUserCriteria();
		mgrUserCriteria.createCriteria().andNameEqualTo(username);
		List<MgrUser> userList = mgrUserMapper.selectByExample(mgrUserCriteria);
		MgrUser user = null;
		if(userList==null||userList.size()<=0)
			return null;
		else
			user = userList.get(0);
			if("root".equals(username))
				user.setEnable(true);//管理员总是有效的
			logger.debug("GET USER : "+MyGson.getInstance().toJson(user));
			return user;
	}

}
