package com.momoplan.pet.framework.manager.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.manager.mapper.MgrUserRoleRelMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRelCriteria;
import com.momoplan.pet.framework.manager.service.RoleUserManageService;

@Service
public class RoleUserManageServiceImpl implements RoleUserManageService {
	private static Logger logger = LoggerFactory.getLogger(RoleUserManageServiceImpl.class);
	@Autowired
	private MgrUserRoleRelMapper userRoleRelMapper=null;
	
	/**
	 * 根据关系表id查找
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public MgrUserRoleRel getRoleUserByid(MgrUserRoleRel userRoleRel) throws Exception{
			logger.debug("welcome to 根据id获取对应角色信息.....................");
			logger.debug("用户id....................."+userRoleRel.getId());
			if("" !=userRoleRel.getId() && null !=userRoleRel.getId()){
				logger.debug("角色信息:"+userRoleRelMapper.selectByPrimaryKey(userRoleRel.getId()));
				return userRoleRelMapper.selectByPrimaryKey(userRoleRel.getId());
			}else {
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
		    logger.debug("welcome to getRoleUserListbyRoleid.....................");
			MgrUserRoleRelCriteria userRoleRelCriteria =new MgrUserRoleRelCriteria();
			MgrUserRoleRelCriteria.Criteria criteria=userRoleRelCriteria.createCriteria();
			criteria.andRoleIdEqualTo(userRoleRel.getRoleId());
			logger.debug("根据角色id"+userRoleRel.getRoleId()+"获取角色结合"+userRoleRelMapper.selectByExample(userRoleRelCriteria));
			return userRoleRelMapper.selectByExample(userRoleRelCriteria);
	}
	/**
	 * 
	 * 根据关系表的用户id获取关系集合
	 * @param userRoleRel
	 * @return
	 * @throws Exception
	 */
	public List<MgrUserRoleRel> getRoleUserListbyUserid(MgrUserRoleRel userRoleRel)throws Exception{
			MgrUserRoleRelCriteria userRoleRelCriteria =new MgrUserRoleRelCriteria();
			MgrUserRoleRelCriteria.Criteria criteria=userRoleRelCriteria.createCriteria();
			criteria.andUserIdEqualTo(userRoleRel.getUserId());
			logger.debug("根据用户id...."+userRoleRel.getUserId());
			logger.debug("获取角色集合......"+userRoleRelMapper.selectByExample(userRoleRelCriteria));
			return userRoleRelMapper.selectByExample(userRoleRelCriteria);
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
				userRoleRel.setId(IDCreater.uuid());
				logger.debug("增加用户关系表:"+userRoleRel.toString());
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
		    logger.debug("welcome to delRoleUser....................."+userRoleRel.toString());
			if("" != userRoleRel.getId() && null != userRoleRel.getId()){
				userRoleRelMapper.deleteByPrimaryKey(userRoleRel.getId());
				logger.debug("删除用户与角色关系成功!");
			}
	}

}
