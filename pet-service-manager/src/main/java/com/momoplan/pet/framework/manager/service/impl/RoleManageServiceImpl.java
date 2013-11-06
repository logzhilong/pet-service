package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.manager.mapper.MgrRoleMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrRoleCriteria;
import com.momoplan.pet.framework.manager.service.RoleManageService;

@Service
public class RoleManageServiceImpl implements RoleManageService {

	private static Logger logger = LoggerFactory.getLogger(RoleManageServiceImpl.class);
	@Autowired
	private MgrRoleMapper mgrroleMaper=null;

	/**
	 * 获取所有角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MgrRole> getAllRole() throws Exception {
		logger.debug("welcome to getAllRole.....................");
			MgrRoleCriteria mgrRoleCriteria=new MgrRoleCriteria();
			MgrRoleCriteria.Criteria criteria=mgrRoleCriteria.createCriteria();
			criteria.andIdIsNotNull();
			List<MgrRole> roles=mgrroleMaper.selectByExample(mgrRoleCriteria);
			return roles;
	}

	/**
	 * 增加或者修改用户角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public int SaveOrUpdateRole(MgrRole role) throws Exception {
		logger.debug("welcome to SaveOrUpdateRole.....................");
		String id = role.getId();
		MgrRole mo = mgrroleMaper.selectByPrimaryKey(id);
		if(mo!=null&&!"".equals(mo.getId())){
			logger.debug("selectByPK.po="+mo.toString());
			role.setEt(new Date());
			return mgrroleMaper.updateByPrimaryKeySelective(role);
		}else{
			role.setId(IDCreater.uuid());
			role.setCt(new Date());
			role.setEt(new Date());
			role.setEnable(true);
			return mgrroleMaper.insertSelective(role);
		}
	}

	/**
	 * 根据用户id获取用户
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public MgrRole getRoleByid(MgrRole role) throws Exception {
		logger.debug("welcome to getRoleByid.....................");
			return mgrroleMaper.selectByPrimaryKey(role.getId());
	}
	
	
	/**
	 * 根据角色id删除角色
	 * @param role
	 * @throws Exception
	 */
	public void delRoleByid(MgrRole role)throws Exception{
		logger.debug("welcome to delRoleByid.....................");
			if("" != role.getId() && null !=role.getId()){
				mgrroleMaper.deleteByPrimaryKey(role.getId());
			}
	}
}
