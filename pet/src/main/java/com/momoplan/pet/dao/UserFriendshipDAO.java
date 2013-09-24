package com.momoplan.pet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.momoplan.pet.domain.UserFriendship;

@Component
public class UserFriendshipDAO {
	@PersistenceContext
	transient EntityManager entityManager;

	public List<UserFriendship> findUserFriendshipsByABId(Long aId, Long bId) {
		TypedQuery<UserFriendship> createQuery = entityManager.createQuery("select uf from UserFriendship uf where (uf.aId = :aId and uf.bId = :bId) or (uf.aId = :bId and uf.bId = :aId) ",UserFriendship.class);
		createQuery.setParameter("aId",aId);
		createQuery.setParameter("bId",bId);
		List<UserFriendship> userFriendships = createQuery.getResultList();
		return userFriendships;
	}
	
	public String findAliasName(Long userid, Long myid) {
		TypedQuery<UserFriendship> createQuery = entityManager.createQuery("select uf from UserFriendship uf where uf.aId = :aId and uf.bId = :bId ",UserFriendship.class);
		createQuery.setParameter("aId",userid);
		createQuery.setParameter("bId",myid);
		List<UserFriendship> userFriendship = createQuery.getResultList();
		if(userFriendship.size()>0){
			return userFriendship.get(0).getAliasA();
		}
		TypedQuery<UserFriendship> createQuery2 = entityManager.createQuery("select uf from UserFriendship uf where uf.aId = :aId and uf.bId = :bId ",UserFriendship.class);
		createQuery2.setParameter("aId",myid);
		createQuery2.setParameter("bId",userid);
		List<UserFriendship> userFriendship2 = createQuery2.getResultList();
		if(userFriendship2.size()>0){
			return userFriendship2.get(0).getAliasB();
		}
		return null;
	}
}
