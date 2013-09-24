package com.momoplan.pet.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.momoplan.pet.domain.PetUser;

public class PetUserDetail implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PetUser petUser;

	public PetUserDetail(PetUser petUser) {
		this.petUser = petUser;
	}

	public PetUser getPetUser() {
		return petUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return petUser.getPassword();
	}

	@Override
	public String getUsername() {
		return petUser.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
