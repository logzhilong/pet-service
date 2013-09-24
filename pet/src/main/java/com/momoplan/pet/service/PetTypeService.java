package com.momoplan.pet.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.momoplan.pet.domain.PetTypeRelationship;

@Service
public class PetTypeService {
	
	/**
	 * 获取该种类下所有的具体类型
	 * @param parentid
	 * @return
	 */
	public void getAlltypes(long parentid,HashSet<PetTypeRelationship> petTypeRelationships){
		List<PetTypeRelationship> subtypes = getSubtypes(parentid);
		petTypeRelationships.addAll(subtypes);
		for(PetTypeRelationship relationship:subtypes){
			if(petTypeRelationships.contains(relationship)){
				continue;
			}
			petTypeRelationships.add(relationship);
			getAlltypes(relationship.getChildid(), petTypeRelationships);
		}
	}
	
	public List<PetTypeRelationship> getSubtypes(long parentid){
		return PetTypeRelationship.findPetTypeRelationshipsByParentid(parentid).getResultList();
	}
}
