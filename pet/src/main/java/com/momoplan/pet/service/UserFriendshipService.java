package com.momoplan.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.dao.UserFriendshipDAO;
import com.momoplan.pet.domain.UserFriendship;

@Service
public class UserFriendshipService {
	@Autowired
	UserFriendshipDAO userFriendshipDao;
	
	public UserFriendship findUserFriendshipsByABId(Long aId, Long bId) {
		List<UserFriendship> userFriendships = userFriendshipDao.findUserFriendshipsByABId(aId,bId);
		if(userFriendships.size()==1){
			return userFriendships.get(0);
		}else{
			return null;
		}
	}

	public String getAliasName(long userid, long myid) {
		return userFriendshipDao.findAliasName(userid, myid);
		
	}

}
