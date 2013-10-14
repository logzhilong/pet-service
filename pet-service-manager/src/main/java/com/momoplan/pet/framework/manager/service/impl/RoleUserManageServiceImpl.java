package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.momoplan.pet.commons.domain.manager.mapper.MgrUserRoleRelMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRelCriteria;
import com.momoplan.pet.framework.manager.service.RoleUserManageService;

@Service
public class RoleUserManageServiceImpl implements RoleUserManageService {
	private static Logger logger = LoggerFactory.getLogger(RoleManageServiceImpl.class);
	@Autowired
	private MgrUserRoleRelMapper userRoleRelMapper=null;
	
	/**
	 * 根据关系表id查找
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public MgrUserRoleRel getRoleUserByid(MgrUserRoleRel userRoleRel) throws Exception{
		try {
			if("" !=userRoleRel.getId() && null !=userRoleRel.getId()){
				return userRoleRelMapper.selectByPrimaryKey(userRoleRel.getId());
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据关系表的角色id获取关系集合
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyRoleid(MgrUserRoleRel userRoleRel)throws Exception{
		try {
			MgrUserRoleRelCriteria userRoleRelCriteria =new MgrUserRoleRelCriteria();
			MgrUserRoleRelCriteria.Criteria criteria=userRoleRelCriteria.createCriteria();
			criteria.andRoleIdEqualTo(userRoleRel.getRoleId());
			return userRoleRelMapper.selectByExample(userRoleRelCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 
	 * 根据关系表的用户id获取关系集合
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyUserid(MgrUserRoleRel userRoleRel)throws Exception{
		try {
			MgrUserRoleRelCriteria userRoleRelCriteria =new MgrUserRoleRelCriteria();
			MgrUserRoleRelCriteria.Criteria criteria=userRoleRelCriteria.createCriteria();
			criteria.andUserIdEqualTo(userRoleRel.getUserId());
			return userRoleRelMapper.selectByExample(userRoleRelCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 增加或者修改关系
	 * @param userRoleRel
	 * @throws Exception
	 */
	public void saveOrUpdateRoleUser(MgrUserRoleRel userRoleRel)throws Exception{
		try {
			String id=userRoleRel.getId();
			MgrUserRoleRel ur=userRoleRelMapper.selectByPrimaryKey(id);
			if(ur!=null&&!"".equals(ur.getId())){
				logger.debug("selectByPK.po="+ur.toString());
				 userRoleRelMapper.updateByPrimaryKeySelective(userRoleRel);
			}else{
				 userRoleRelMapper.insertSelective(userRoleRel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除关系
	 * @param userRoleRel
	 * @throws Exception
	 */
	public void delRoleUser(MgrUserRoleRel userRoleRel)throws Exception{
		try {
			if("" != userRoleRel.getId() && null != userRoleRel.getId()){
				userRoleRelMapper.deleteByPrimaryKey(userRoleRel.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
