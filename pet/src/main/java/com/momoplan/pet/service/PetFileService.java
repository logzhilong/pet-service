package com.momoplan.pet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.dao.PetFileDAO;
import com.momoplan.pet.domain.PetFile;
import com.momoplan.pet.domain.PetVersion;
@Service
public class PetFileService {
	@Autowired
	PetFileDAO petFileDao;

	public List<PetFile> selectPetFile(PetFile petFile) {
		return petFileDao.selectPetFile(petFile.getType(),petFile.getId());
	}

	public long getNewImg(int type) {
		return petFileDao.getNewImg(type);
	}
	
	public PetVersion getNewVersion(String phoneType) {
		return petFileDao.getNewVersion(phoneType);
	}
}
