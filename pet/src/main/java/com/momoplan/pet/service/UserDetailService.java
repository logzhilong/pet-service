package com.momoplan.pet.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.momoplan.pet.domain.PetUser;

public class UserDetailService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		PetUser singleResult = PetUser.findPetUsersByUsername(username).getSingleResult();
		PetUserDetail petUserDetail=new PetUserDetail(singleResult);
		return petUserDetail;
	}

}
