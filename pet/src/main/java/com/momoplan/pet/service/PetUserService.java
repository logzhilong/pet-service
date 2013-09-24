package com.momoplan.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.dao.PetUserDAO;
import com.momoplan.pet.domain.PetUser;
import com.momoplan.pet.vo.PetUserWithPetInfoView;
@Service
public class PetUserService {
	@Autowired
	PetUserDAO petUserDao;

	public List<PetUser> selectUserByNameOrUserId(PetUser petUser) {
		return petUserDao.selectUserByNameOrUserId(petUser.getUsername());
	}
	
	public List<PetUserWithPetInfoView> getPetUserViews(PetUser petUser,int pegeIndex) {
		return petUserDao.getPetUserViews(petUser,pegeIndex);
	}
	
	public List<PetUserWithPetInfoView> getFriendsView(PetUser petUser,int pegeIndex) {
		return petUserDao.getFriendsView(petUser,pegeIndex);
	}

	/**
	 * @param petUser
	 * 要查询的用户
	 * @param userid
	 * 查询的用户id
	 * @return
	 */
	public List<PetUserWithPetInfoView> getOneFriend(PetUser petUser,Long userid) {
		return petUserDao.getOneFriend(petUser,userid);
	}

	public PetUser findPetUser(long userid, String city) {
		List<PetUser> petUsers = petUserDao.getOneFriend(userid,city);
		if(petUsers.isEmpty()){
			return null;
		}else{
			return petUsers.get(0);
		}
	}

}
