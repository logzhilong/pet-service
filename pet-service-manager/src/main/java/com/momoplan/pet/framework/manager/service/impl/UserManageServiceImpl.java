package com.momoplan.pet.framework.manager.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.IDCreater;
import com.momoplan.pet.commons.domain.manager.mapper.MgrRoleMapper;
import com.momoplan.pet.commons.domain.manager.mapper.MgrUserMapper;
import com.momoplan.pet.commons.domain.manager.mapper.MgrUserRoleRelMapper;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.commons.domain.manager.po.MgrUserCriteria;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRel;
import com.momoplan.pet.commons.domain.manager.po.MgrUserRoleRelCriteria;
import com.momoplan.pet.framework.manager.security.Md5PlusShaPasswordEncoder;
import com.momoplan.pet.framework.manager.security.SessionManager;
import com.momoplan.pet.framework.manager.service.RoleUserManageService;
import com.momoplan.pet.framework.manager.service.UserManageService;
import com.momoplan.pet.framework.manager.vo.WebUser;

@Service
public class UserManageServiceImpl implements UserManageService {

	private static Logger logger = LoggerFactory
			.getLogger(RoleUserManageServiceImpl.class);
	@Autowired
	private MgrRoleMapper mgrroleMaper = null;
	@Autowired
	private MgrUserMapper mgrUserMapper = null;
	@Autowired
	private RoleUserManageService roleUserManageService;
	@Autowired
	private Md5PlusShaPasswordEncoder md5 = null;
	@Autowired
	private MgrUserRoleRelMapper userRoleRelMapper = null;

	/**
	 * 获取所有角色
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<MgrUser> getAllUser() throws Exception {
		logger.debug("welcome to getAllUser.....................");
		try {
			MgrUserCriteria mgrUserCriteria = new MgrUserCriteria();
			return mgrUserMapper.selectByExample(mgrUserCriteria);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 增加或者修改用户角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public int SaveOrUpdateUser(String[] ruty, MgrUser mgrUser)
			throws Exception {

		logger.debug("welcome to SaveOrUpdateUser.....................");
		String id = mgrUser.getId();
		MgrUser uo = mgrUserMapper.selectByPrimaryKey(id);
		if (uo != null && !"".equals(uo.getId())) {
			logger.debug("selectByPK.po=" + uo.toString());
			mgrUser.setEt(new Date());
			mgrUser.setEb("root");

			// 循环更新或者添加角色用户关系表
			MgrUserRoleRel rel = new MgrUserRoleRel();
			rel.setUserId(mgrUser.getId());
			List<MgrUserRoleRel> list = roleUserManageService
					.getRoleUserListbyUserid(rel);
			for (MgrUserRoleRel rel2 : list) {
				roleUserManageService.delRoleUser(rel2);
			}
			if (ruty.length > 0) {
				for (int i = 0; i < ruty.length; i++) {
					MgrUserRoleRel rel1 = new MgrUserRoleRel();
					rel1.setRoleId(ruty[i]);
					rel1.setUserId(mgrUser.getId());
					roleUserManageService.saveOrUpdateRoleUser(rel1);
				}
			}
			return mgrUserMapper.updateByPrimaryKeySelective(mgrUser);
		} else {
			mgrUser.setId(IDCreater.uuid());
			mgrUser.setCt(new Date());
			mgrUser.setEt(new Date());
			mgrUser.setEnable(true);
			mgrUser.setCb("root");
			mgrUser.setEb("root");
			String rawPass = md5.encodePassword(mgrUser.getPassword(), null);
			mgrUser.setPassword(rawPass);
			if (ruty.length > 0) {
				for (int i = 0; i < ruty.length; i++) {
					MgrUserRoleRel mgrUserRoleRel = new MgrUserRoleRel();
					mgrUserRoleRel.setId(IDCreater.uuid());
					mgrUserRoleRel.setRoleId(ruty[i]);
					mgrUserRoleRel.setUserId(mgrUser.getId());
					roleUserManageService.saveOrUpdateRoleUser(mgrUserRoleRel);
				}
			}
			return mgrUserMapper.insertSelective(mgrUser);
		}
	}

	/**
	 * 根据用户id获取用户
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public MgrUser getUserByid(MgrUser mgrUser) throws Exception {
		logger.debug("welcome to getRoleByid.....................");
		try {
			return mgrUserMapper.selectByPrimaryKey(mgrUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据角色id删除角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void delUserByid(MgrUser mgrUser) throws Exception {
		logger.debug("welcome to delRoleByid.....................");
		if ("" != mgrUser.getId() && null != mgrUser.getId()) {
			MgrUserRoleRelCriteria userRoleRelCriteria = new MgrUserRoleRelCriteria();
			MgrUserRoleRelCriteria.Criteria criteria = userRoleRelCriteria
					.createCriteria();
			criteria.andRoleIdEqualTo(mgrUser.getId());
			userRoleRelMapper.deleteByExample(userRoleRelCriteria);
			mgrUserMapper.deleteByPrimaryKey(mgrUser.getId());
		}
	}

	/**
	 * 修改密码
	 */
	public void upUserPwd(String opassword, String npassword,
			String rnpassword, HttpServletRequest request) throws Exception {
		logger.debug("welcome to upUserPwd.....................");
		SessionManager manager = null;
		// 获取当前用户
		@SuppressWarnings("static-access")
		WebUser user = manager.getCurrentUser(request);
		if (user != null && opassword != null && rnpassword != null) {
			String inopwd = md5.encodePassword(opassword, null);
			String opwd = user.getPassword();
			// if (inopwd .equals(opwd)) {
			if (rnpassword.equals(npassword)) {
				MgrUser user2 = mgrUserMapper.selectByPrimaryKey(user.getId());
				user2.setPassword(md5.encodePassword(npassword, null));
				user2.setEb(user.getName());
				mgrUserMapper.updateByPrimaryKey(user2);
				logger.debug("message" + "密码修改操作成功!");
			} else {
				logger.debug("message" + "新密码不一致!");
			}
			// } else {
			// logger.debug("message" + "旧密码输入错误!");
			// }
		}
	}
	
}
