package com.momoplan.pet.framework.manager.service;

import java.util.List;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;

public interface MgrUserService {

	public List<MgrRole> getRoleByUserId(String userId) throws Exception;
	
	public MgrUser getMgrUserByName(String username) throws Exception;
	
}
