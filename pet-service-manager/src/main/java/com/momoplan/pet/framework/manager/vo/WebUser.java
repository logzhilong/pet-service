package com.momoplan.pet.framework.manager.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.momoplan.pet.commons.domain.manager.po.MgrRole;
import com.momoplan.pet.commons.domain.manager.po.MgrUser;
/**
 * 登录的用户
 * @author liangc
 */
public class WebUser extends MgrUser implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 角色集合
	 */
	public Collection<MgrRole> roles = new ArrayList<MgrRole>();
	
	public Map<String,Boolean> roleMap = new HashMap<String,Boolean>();
	
	public Collection<MgrRole> getRoles() {
		return roles;
	}

	public void setRoles(Collection<MgrRole> roles) {
		this.roles = roles;
		for(MgrRole role : roles){
			roleMap.put(role.getCode(), Boolean.valueOf(role.getEnable()));
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	@Override
	public String getPassword() {
		return super.getPassword();
	}
	@Override
	public String getUsername() {
		return super.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return getEnable();
	}
}
