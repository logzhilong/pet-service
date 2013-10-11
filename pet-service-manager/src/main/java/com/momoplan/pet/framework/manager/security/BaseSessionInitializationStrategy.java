package com.momoplan.pet.framework.manager.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;
import com.momoplan.pet.framework.manager.service.MgrUserService;
import com.momoplan.pet.framework.manager.vo.WebUser;

/**
 * 校验通过后，在此构建 session 啊
 * @author liangc
 *
 */
@Component("baseSessionInitializationStrategy")
public class BaseSessionInitializationStrategy implements SessionAuthenticationStrategy {
	
	private static Logger logger = LoggerFactory.getLogger(BaseSessionInitializationStrategy.class);

	@Autowired
	private MgrUserService mgrUserService = null;

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
		//TODO 通过用户名，找到用户对象，并构建 session ，其中应该包括角色，权限等信息
		String userName = authentication.getName();
		WebUser cu = SessionManager.getCurrentUser(request);
		try {
			if(cu==null||cu.getId()==null){
				MgrUser user = mgrUserService.getMgrUserByName(userName);
				List<MgrRole> roles = mgrUserService.getRoleByUserId(user.getId());
				cu = new WebUser();
				BeanUtils.copyProperties(user, cu);
				cu.setRoles(roles);
				SessionManager.initSession(request, cu, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}