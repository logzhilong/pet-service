package com.momoplan.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.dao.VerificationDAO;
import com.momoplan.pet.domain.Verification;

@Service
public class VerificationService {
	@Autowired
	VerificationDAO verificationDao;
	public String getCode() {
		return verificationDao.getCode();
	}
	
	/**
	 * 根据实体查询是否存在
	 * @param verification
	 * @return 
	 */
	public Boolean getVerfication(Verification verification) {
		List<Verification> verifications = verificationDao.getVerfication(verification);
		if(verifications.isEmpty()){
			return false;
		}else{
			return true;
		}
		
	}

}
