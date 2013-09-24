package com.momoplan.pet.domain;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findReplyCommentsesByReplyid" ,"countReplyCommentsesByReplyid"})
public class ReplyComments {

    private long userStateid;

    private long replyid;

    private long replyUserid;
    
    private long commentUserid;

    private String commentsMsg;

    private Date commentTime;
    
    public static TypedQuery<ReplyComments> findReplyCommentsesByReplyid(long replyid,long lastCommentid) {
        EntityManager em = ReplyComments.entityManager();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT o FROM ReplyComments AS o WHERE o.replyid = :replyid ");
        
        if(-1!=lastCommentid){
    		sql.append(" AND o.id < :lastCommentid ");
    	}
        sql.append(" ORDER BY o.commentTime DESC ");
        TypedQuery<ReplyComments> q = em.createQuery( sql.toString(), ReplyComments.class);
        q.setFirstResult(0);
        q.setMaxResults(20);
        q.setParameter("replyid", replyid);
        if(-1!=lastCommentid){
    		q.setParameter("lastCommentid", lastCommentid);
    	}
        return q;
    }
    
    public static long countReplyCommentsesByReplyid(long replyid) {
    	if(-1==replyid){
    		return -1;
    	}
    	EntityManager em = Reply.entityManager();
    	TypedQuery<Long> createQuery = em.createQuery("SELECT COUNT(o) FROM ReplyComments o WHERE o.replyid = :replyid", Long.class);
    	createQuery.setParameter("replyid", replyid);
    	return createQuery.getSingleResult();
    }
}
