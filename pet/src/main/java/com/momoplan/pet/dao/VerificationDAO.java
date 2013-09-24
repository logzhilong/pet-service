package com.momoplan.pet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.momoplan.pet.domain.Verification;

@Component
public class VerificationDAO {
	@PersistenceContext
	transient EntityManager entityManager;

	public String getCode() {
		Query createQuery = entityManager.createNativeQuery(" select func_range_string_mod(4) ");
		return (String) createQuery.getSingleResult();
	}

	public List<Verification> getVerfication(Verification verification) {
		TypedQuery<Verification> createQuery = entityManager.createQuery("SELECT o FROM Verification o where o.phoneNum = :phoneNum and o.verificationCode = :verificationCode",Verification.class);
		createQuery.setParameter("phoneNum", verification.getPhoneNum());
		createQuery.setParameter("verificationCode", verification.getVerificationCode());
		return createQuery.getResultList();
	}
	
}
