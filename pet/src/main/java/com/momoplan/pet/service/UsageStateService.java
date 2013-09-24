package com.momoplan.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.dao.UsageStateDAO;

@Service
public class UsageStateService {
	@Autowired
	UsageStateDAO usageStateDAO = new UsageStateDAO();

	
}
