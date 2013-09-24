package com.momoplan.pet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class UsageStateDAO {
	@PersistenceContext
	transient EntityManager entityManager;

}
