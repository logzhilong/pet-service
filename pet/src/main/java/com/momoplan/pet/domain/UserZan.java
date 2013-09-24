package com.momoplan.pet.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findUserZansByZanUserid", "findUserZansByUseridAndUserStateidAndZanUserid" })
public class UserZan {

    private long userid;

    private long zanUserid;

    private long userStateid;
    
    private long petid;
    
    private String zanType;
    
    public static TypedQuery<UserZan> findUserZansByUseridAndUserStateidAndZanUserid(long userid, long userStateid, long zanUserid,long petid,String zanType) {
        EntityManager em = UserZan.entityManager();
        StringBuffer hql = new StringBuffer(" SELECT o FROM UserZan AS o WHERE o.userid = :userid ");
        if(zanType.compareTo("0")==0){//动态
        	hql.append(" AND o.userStateid = :userStateid AND o.zanUserid = :zanUserid ");
        }else if(zanType.compareTo("1")==0){//宠物
        	hql.append(" AND o.petid = :petid ");
        }else if(zanType.compareTo("2")==0){//用户
        }else{
        	return null;
        }
        hql.append(" AND o.zanType = :zanType ");
        TypedQuery<UserZan> q = em.createQuery(hql.toString(), UserZan.class);
        if(zanType.compareTo("0")==0){//动态
        	q.setParameter("userStateid", userStateid);
            q.setParameter("zanUserid", zanUserid);
        }else if(zanType.compareTo("1")==0){//宠物
        	q.setParameter("petid", petid);
        }else if(zanType.compareTo("2")==0){//用户
        }
        q.setParameter("userid", userid);
        q.setParameter("zanType", zanType);
        return q;
    }
    
    public static TypedQuery<UserZan> findUserZansByZanUserid(long zanUserid) {
        EntityManager em = UserZan.entityManager();
        TypedQuery<UserZan> q = em.createQuery("SELECT o FROM UserZan AS o WHERE o.zanUserid = :zanUserid", UserZan.class);
        q.setParameter("zanUserid", zanUserid);
        return q;
    }
    
    /**
     * @param userStateid 动态id
     * @param userid 用户id
     * @param petid 宠物id
     * @param zanType 赞类型
     * @return
     */
    public static long countUserZans(long userid, long userStateid, long zanUserid,long petid,String zanType) {
    	EntityManager em = new UserZan().entityManager;
    	StringBuffer hql = new StringBuffer("  SELECT COUNT(o) FROM UserZan o where o.zanUserid = :zanUserid  ");
    	if(zanType.compareTo("0")==0){
    		hql.append(" AND o.userStateid = :userStateid ");
    	}else if(zanType.compareTo("1")==0){
    		hql.append("  AND o.petid = :petid  ");
    	}else if(zanType.compareTo("2")==0){
    	}else{
    		return 0;
    	}
    	hql.append("  AND o.zanType = :zanType  ");
        TypedQuery<Long> q = em.createQuery(hql.toString(), Long.class);
        if(zanType.compareTo("0")==0){
        	q.setParameter("userStateid", userStateid);
//        	q.setParameter("zanUserid", zanUserid);
    	}else if(zanType.compareTo("1")==0){
    		q.setParameter("petid", petid);
    	}else if(zanType.compareTo("2")==0){
    	}
        q.setParameter("zanUserid", zanUserid);
        q.setParameter("zanType", zanType);
        return q.getSingleResult();
    }
   
}
