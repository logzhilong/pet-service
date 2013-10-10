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
@RooJpaActiveRecord(finders = { "findUserStatesesByPetUserid" ,"finderUserStatesWithFriendship","findUserStateByLatitudeOrLongitudeOrGenderEquals"})
public class UserStates{

    private String transmitMsg;

    private String transmitUrl;

    private String ifTransmitMsg;

    private Date submitTime;

    private String msg;

    private String imgid;
    
    private String stateType;

    private long petUserid;
    
    private double longitude;

    private double latitude;
    
    private int reportTimes;
    
    
    public static TypedQuery<UserStates> findUserStatesesByPetUserid(long petUserid,long lastStateid) {
        EntityManager em = UserStates.entityManager();
        StringBuffer hql = new StringBuffer("");
        hql.append(" SELECT o.* FROM (select u.* from user_states u where u.pet_userid = :petUserid or (u.pet_userid != :petUserid and u.state_type != '3' and u.state_type != '4' and u.state_type != '5' )) AS o WHERE o.pet_userid = :petUserid ");
        if(-1!=lastStateid){
        	hql.append(" AND o.id < :lastStateid ");
        }
        hql.append(" ORDER BY o.submit_time desc ");
        TypedQuery<UserStates> q = em.createQuery(hql.toString() , UserStates.class);
        q.setFirstResult(0);
        q.setMaxResults(20);
        q.setParameter("petUserid", petUserid);
        if(-1!=lastStateid){
        	q.setParameter("lastStateid", lastStateid);
        }
        return q;
    }
    
    public static Query finderUserStatesWithFriendship(long petUserid,long lastStateid) {
        EntityManager em = UserStates.entityManager();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT o.* FROM (select u.* from user_states u where u.pet_userid = :petUserid or (u.pet_userid != :petUserid and u.state_type != '3' and u.state_type != '4' and u.state_type != '5' )) AS o WHERE (func_if_friend(o.pet_userid,:petUserid)>0 or o.pet_userid = :petUserid) ");
        if(-1!=lastStateid){
        	sql.append(" and o.id < :lastStateid ");
        }
        sql.append(" ORDER BY o.submit_time desc ");
        Query q = em.createNativeQuery(sql.toString(), UserStates.class);
        q.setFirstResult(0);
        q.setMaxResults(20);
        q.setParameter("petUserid", petUserid);
        if(-1!=lastStateid){
        	q.setParameter("lastStateid", lastStateid);
        }
        return q;
        
    }
    
    public static Query findUserStateByLatitudeOrLongitudeOrGenderEquals(double latitude, double longitude,long petUserid,int pageIndex) {
    	double left_longitude = longitude-1;
	  	double right_longitude = longitude+1;
	  	double up_latitude = latitude+1;
	  	double down_latitude = latitude-1;
	  	StringBuffer sql = new StringBuffer("SELECT o.* FROM (select u.* from user_states u where u.pet_userid = :petUserid or (u.pet_userid != :petUserid and u.state_type != '3' and u.state_type != '4' and u.state_type != '5' )) AS o WHERE (o.longitude between :left_longitude and :right_longitude) and (o.latitude between :down_latitude and :up_latitude) and o.state_type = :stateType");
	  	sql.append(" ORDER BY o.submit_time DESC ");
	  	
        EntityManager em = UserStates.entityManager();
        Query q = em.createNativeQuery(sql.toString(), UserStates.class);
        q.setParameter("left_longitude", left_longitude);
        q.setParameter("right_longitude",right_longitude);
        q.setParameter("up_latitude", up_latitude);
        q.setParameter("down_latitude", down_latitude);
        q.setParameter("petUserid", petUserid);
        q.setParameter("stateType", "0");
        q.setFirstResult(pageIndex);
		q.setMaxResults(20);
        return q;
    }
    
    public static TypedQuery<UserStates> findUserStateWithType(String stateType) {
	  	StringBuffer sql = new StringBuffer("SELECT o FROM UserStates AS o WHERE o.stateType = :stateType ");
	  	sql.append(" ORDER BY o.submitTime DESC ");
        EntityManager em = UserStates.entityManager();
        TypedQuery<UserStates> q = em.createQuery(sql.toString(), UserStates.class);
        q.setParameter("stateType", stateType);
        q.setFirstResult(0);
		q.setMaxResults(5);
        return q;
    }

}
