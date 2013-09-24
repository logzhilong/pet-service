package com.momoplan.pet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import com.momoplan.pet.domain.PetFile;
import com.momoplan.pet.domain.PetVersion;

@Component
public class PetFileDAO {
	@PersistenceContext
	transient EntityManager entityManager;
    /**
     * 根据图片id和type获取图片实体
     * @param type
     * @param id
     * @return
     */
    public List<PetFile> selectPetFile(int type, Long id) {
    	TypedQuery<PetFile> createQuery = entityManager.createQuery("SELECT o FROM PetFile o where o.type = :type and o.id = :id",PetFile.class);
		createQuery.setParameter("type", type);
		createQuery.setParameter("id", id);
		return createQuery.getResultList();
    }
    
	/**
	 * 获取最新的图片实体
	 * @return
	 */
	public long getNewImg(int type) {
		TypedQuery<Long> createQuery = entityManager.createQuery("SELECT max(o.id) FROM PetFile o where o.type = :type",Long.class);
		createQuery.setParameter("type", type);//2表示为系统图片类型
		Long singleResult = createQuery.getSingleResult();
		if(null==singleResult){
			return -1;
		}
		return singleResult;
	}
	
	public PetVersion getNewVersion(String phoneType) {
		TypedQuery<PetVersion> createQuery = entityManager.createQuery(" SELECT o FROM PetVersion o where o.id = (select max(p.id) from PetVersion p where p.phoneType = :phoneType) ",PetVersion.class);
		createQuery.setParameter("phoneType", phoneType);
		return createQuery.getSingleResult();
	}
}
