package com.momoplan.pet.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.momoplan.pet.domain.UserLocation;

@Component
public class UserLocationDAO {

	  @PersistenceContext
	  transient EntityManager entityManager;
	  
	  public List<UserLocation> getNearByUser(double longitude,double latitude,String gender,Integer type,int pageIndex,String ifFraudulent) {
		  	double left_longitude = longitude-1;
		  	double right_longitude = longitude+1;
		  	double up_latitude = latitude+1;
		  	double down_latitude = latitude-1;
		  	StringBuffer sql = new StringBuffer();
		  	sql.append("select u.* from user_location u,(select pu.* from pet_user pu,pet_info pi where pu.id = pi.userid  ");
		  	if(-1==type){
		  	}else{
		  		sql.append(" and pi.type like :type ");
		  	}
	        if(StringUtils.isEmpty(gender)||gender.compareTo("")==0){
	        }else{
	        	sql.append(" and pu.gender = :gender ");
	        }
	        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
	        }else{
	        	left_longitude = -180;
			  	right_longitude = 180;
			  	up_latitude = 90;
			  	down_latitude = -90;
	        }
	        sql.append(" and pu.if_fraudulent = :ifFraudulent ");
		  	sql.append(" group by pu.id) p where u.userid = p.id ");
		  	sql.append(" and (u.longitude between :left_longitude and :right_longitude) and (u.latitude between :down_latitude and :up_latitude) order by (POW((u.latitude-:latitude),2)+POW((u.longitude-:longitude),2)) asc");
		  	Query createQuery = entityManager.createNativeQuery(sql.toString(), UserLocation.class);
	        createQuery.setParameter("left_longitude", left_longitude);
	        createQuery.setParameter("right_longitude",right_longitude);
	        createQuery.setParameter("up_latitude", up_latitude);
	        createQuery.setParameter("down_latitude", down_latitude);
	        createQuery.setParameter("longitude", longitude);
	        createQuery.setParameter("latitude", latitude);
//	        createQuery.setParameter("pageIndex", pageIndex);
	        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
	        	createQuery.setFirstResult(pageIndex);
	        	createQuery.setMaxResults(20);
	        }else{
	        	createQuery.setFirstResult(0);
	        	createQuery.setMaxResults(20-pageIndex);
	        }
			
			if(-1==type){
		  	}else{
		  		createQuery.setParameter("type", type+"%");
		  	}
	        if(StringUtils.isEmpty(gender)||gender.compareTo("0")==0){
	        }else{
	        	createQuery.setParameter("gender", gender);
	        }
	        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("")==0){
	        	ifFraudulent = "0";
	        }createQuery.setParameter("ifFraudulent", ifFraudulent);
			return createQuery.getResultList();
	   }

	public List<UserLocation> getNearByUser(double longitude, double latitude,String gender, Integer type, int pageIndex, long userId,String ifFraudulent) {
		  	double left_longitude = longitude-1;
		  	double right_longitude = longitude+1;
		  	double up_latitude = latitude+1;
		  	double down_latitude = latitude-1;
		  	StringBuffer sql = new StringBuffer();
		  	sql.append("select u.* from user_location u,(select pu.* from pet_user pu,pet_info pi where ");
		  	if(-1==type&&(StringUtils.isEmpty(gender)||gender.compareTo("")==0)){
		  		sql.append(" 1=1 ");
		  	}else{
		  		sql.append(" pu.id = pi.userid ");
		  	}
	        if(-1==type){
		  	}else{
		  		sql.append(" and pi.type like :type ");
		  	}
	        if(StringUtils.isEmpty(gender)||gender.compareTo("")==0){
	        }else{
	        	sql.append(" and pu.gender = :gender ");
	        }
	        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
	        }else{
	        	left_longitude = -180;
			  	right_longitude = 180;
			  	up_latitude = 90;
			  	down_latitude = -90;
	        }
	        sql.append(" and pu.if_fraudulent = :ifFraudulent ");
		  	sql.append(" group by pu.id) p where u.userid = p.id ");
		  	sql.append(" and (u.longitude between :left_longitude and :right_longitude) and (u.latitude between :down_latitude and :up_latitude) and u.userid != :userId order by (POW((u.latitude-:latitude),2)+POW((u.longitude-:longitude),2)) asc");
		  	Query createQuery = entityManager.createNativeQuery(sql.toString(), UserLocation.class);
	        createQuery.setParameter("left_longitude", left_longitude);
	        createQuery.setParameter("right_longitude",right_longitude);
	        createQuery.setParameter("up_latitude", up_latitude);
	        createQuery.setParameter("down_latitude", down_latitude);
	        createQuery.setParameter("longitude", longitude);
	        createQuery.setParameter("latitude", latitude);
	        createQuery.setParameter("userId", userId);
	        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
	        	createQuery.setFirstResult(pageIndex);
	        	createQuery.setMaxResults(20);
	        }else{
	        	createQuery.setFirstResult(0);
	        	createQuery.setMaxResults(20-pageIndex);
	        }
			if(-1==type){
		  	}else{
		  		createQuery.setParameter("type", type+"%");
		  	}
	        if(StringUtils.isEmpty(gender)||gender.compareTo("")==0){
	        }else{
	        	createQuery.setParameter("gender", gender);
	        }
	        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
	        	ifFraudulent = "0";
	        }createQuery.setParameter("ifFraudulent", ifFraudulent);
	        
			return createQuery.getResultList();
	}
	
	public List<UserLocation> getNearByUserAndPet(double longitude, double latitude,String gender, Integer type, int pageIndex, long userId,String ifFraudulent) {
	  	double left_longitude = longitude-1;
	  	double right_longitude = longitude+1;
	  	double up_latitude = latitude+1;
	  	double down_latitude = latitude-1;
	  	StringBuffer sql = new StringBuffer();
	  	sql.append("select u.* from user_location u,(select pu.* from pet_user pu,pet_info pi where pu.id = pi.userid ");
        if(-1==type){
	  	}else{
	  		sql.append(" and pi.type like :type ");
	  	}
        if(StringUtils.isEmpty(gender)||gender.compareTo("")==0){
        }else{
        	sql.append(" and pu.gender = :gender ");
        }
        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
        }else{
        	left_longitude = -180;
		  	right_longitude = 180;
		  	up_latitude = 90;
		  	down_latitude = -90;
        }
        sql.append(" and pu.if_fraudulent = :ifFraudulent ");
	  	sql.append(" group by pu.id) p where u.userid = p.id ");
	  	sql.append(" and (u.longitude between :left_longitude and :right_longitude) and (u.latitude between :down_latitude and :up_latitude) and u.userid != :userId order by (POW((u.latitude-:latitude),2)+POW((u.longitude-:longitude),2)) asc");
	  	Query createQuery = entityManager.createNativeQuery(sql.toString(), UserLocation.class);
        createQuery.setParameter("left_longitude", left_longitude);
        createQuery.setParameter("right_longitude",right_longitude);
        createQuery.setParameter("up_latitude", up_latitude);
        createQuery.setParameter("down_latitude", down_latitude);
        createQuery.setParameter("longitude", longitude);
        createQuery.setParameter("latitude", latitude);
        createQuery.setParameter("userId", userId);
        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
        	createQuery.setFirstResult(pageIndex);
        	createQuery.setMaxResults(20);
        }else{
        	createQuery.setFirstResult(0);
        	createQuery.setMaxResults(20-pageIndex);
        }
		if(-1==type){
	  	}else{
	  		createQuery.setParameter("type", type+"%");
	  	}
        if(StringUtils.isEmpty(gender)||gender.compareTo("")==0){
        }else{
        	createQuery.setParameter("gender", gender);
        }
        if(StringUtils.isEmpty(ifFraudulent)||ifFraudulent.compareTo("0")==0){
        	ifFraudulent = "0";
        }createQuery.setParameter("ifFraudulent", ifFraudulent);
        
		return createQuery.getResultList();
}
	
}
