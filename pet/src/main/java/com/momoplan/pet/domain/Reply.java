package com.momoplan.pet.domain;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findReplysByUserStateid" ,"findMyReplysByUserStateid" })
public class Reply {

    private long userStateid;

    private String msg;

    private Date replyTime;

    private long petUserid;
    
    public static Query findReplysByUserStateid(UserStates userState,long petUserid,long lastReplyid,String whos) {
    	Query q  = null;
    	EntityManager em = Reply.entityManager();
    	StringBuffer sql = new StringBuffer("");
    	sql.append(" SELECT o.* FROM reply o WHERE o.user_stateid = :userStateid ");
    	if(-1!=lastReplyid){
    		sql.append(" and o.id < :lastReplyid ");
    	}
    	if("myself".compareTo(whos)!=0){
    		sql.append(" and (func_if_friend(:petUserid,o.pet_userid)>0 or o.pet_userid = :petUserid) ");
    	}
    	sql.append(" ORDER BY o.reply_time desc ");
    	q = em.createNativeQuery(sql.toString(), Reply.class);
        q.setFirstResult(0);
        q.setMaxResults(20);
        q.setParameter("userStateid", userState.getId());
        if("myself".compareTo(whos)!=0){
    		q.setParameter("petUserid", petUserid);
    	}
        if(-1!=lastReplyid){
        	q.setParameter("lastReplyid", lastReplyid);
        }
        return q;
    }
    
    public static long countReplysByUserStateid(long userStateid) {
    	if(-1==userStateid){
    		return -1;
    	}
    	EntityManager em = Reply.entityManager();
        TypedQuery<Long> createQuery = em.createQuery("SELECT COUNT(o) FROM Reply o where o.userStateid = :userStateid", Long.class);
        createQuery.setParameter("userStateid", userStateid);
        
        return createQuery.getSingleResult();
    }
}
