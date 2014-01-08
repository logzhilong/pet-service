package com.momoplan.pet.framework.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.momoplan.pet.commons.bean.Page;
import com.momoplan.pet.commons.domain.bbs.po.SpecialSubject;

@Service
public interface SpecialSubjectService {

	public Page<List<SpecialSubject>> getSpecialSubjectList(int pageSize,int pageNo)throws Exception;
	
	public List<SpecialSubject> getSpecialSubjectListByGid(SpecialSubject ss)throws Exception ;
	
}