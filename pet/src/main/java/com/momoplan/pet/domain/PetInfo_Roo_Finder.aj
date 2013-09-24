// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.PetInfo;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

privileged aspect PetInfo_Roo_Finder {
    
    public static TypedQuery<PetInfo> PetInfo.findPetInfoesByTypeAndUserid(long type, long userid) {
        EntityManager em = PetInfo.entityManager();
        TypedQuery<PetInfo> q = em.createQuery("SELECT o FROM PetInfo AS o WHERE o.type = :type AND o.userid = :userid", PetInfo.class);
        q.setParameter("type", type);
        q.setParameter("userid", userid);
        return q;
    }
    
    public static TypedQuery<PetInfo> PetInfo.findPetInfoesByUserid(long userid) {
        EntityManager em = PetInfo.entityManager();
        TypedQuery<PetInfo> q = em.createQuery("SELECT o FROM PetInfo AS o WHERE o.userid = :userid", PetInfo.class);
        q.setParameter("userid", userid);
        return q;
    }
    
}
