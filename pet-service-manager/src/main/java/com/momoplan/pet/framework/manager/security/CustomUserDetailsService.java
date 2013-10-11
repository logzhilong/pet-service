package com.momoplan.pet.framework.manager.security;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.framework.manager.service.MgrUserService;
import com.momoplan.pet.framework.manager.vo.WebUser;

/**
 * 取得用户详细信息，用户对象实现 UserDetails 接口即可
 * @author liangc
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
	
	@Autowired
	private MgrUserService MgrUserService = null;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		try{
			MgrUser user = MgrUserService.getMgrUserByName(username);//用户
			List<MgrRole> roles = MgrUserService.getRoleByUserId(user.getId());//角色
			//组装最终结果
			WebUser webUser = new WebUser();
			BeanUtils.copyProperties(user, webUser);
			webUser.setRoles(roles);
			return webUser;
		}catch(Exception e){
			logger.error("security error ",e);
			e.printStackTrace();
		}
		return null;
	}
	
}
