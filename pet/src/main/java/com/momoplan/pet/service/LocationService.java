package com.momoplan.pet.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.momoplan.pet.dao.UserLocationDAO;
import com.momoplan.pet.domain.UserLocation;

@Service
public class LocationService {
	
	@Autowired
	UserLocationDAO userLocationDAO;
	
	/**
	 * 获取附近用户，未注册状态也能获取
	 * @param longitude
	 * @param latitude
	 * @param gender
	 * @param type
	 * @param pageIndex
	 * @return
	 */
	public List<UserLocation> getNearbyUser(double longitude,double latitude,String gender,Integer type,int pageIndex,String ifFraudulent){
		
		 List<UserLocation> nearByUsers = userLocationDAO.getNearByUser(longitude, latitude,gender,type,pageIndex,ifFraudulent);
		 return nearByUsers;
	}
	
	/**
	 * 获取附近好友的一个重载添加了userid,作用是排除本用户自己的信息
	 * @param longitude
	 * @param latitude
	 * @param gender
	 * @param type
	 * @param pageIndex
	 * @param userId
	 * @return
	 */
	public List<UserLocation> getNearbyUser(double longitude, double latitude,String gender, Integer type, int pageIndex, long userId,String ifFraudulent) {
		List<UserLocation> nearByUsers = userLocationDAO.getNearByUser(longitude, latitude, gender, type,pageIndex, userId,ifFraudulent);
		return nearByUsers;
	}

	public UserLocation setUserLocation(double longitude, double latitude, long userid) {
		UserLocation userLocation=UserLocation.findUserLocation(userid);
		if(userLocation==null){
			userLocation=new UserLocation();
			userLocation.setUserid(userid);
			userLocation.setCreateDate(new Date());
			userLocation.setLatitude(latitude);
			userLocation.setLongitude(longitude);
			userLocation.persist();
			return userLocation;
		}
		userLocation.setCreateDate(new Date());
		userLocation.setLatitude(latitude);
		userLocation.setLongitude(longitude);
		userLocation.merge();
		return userLocation;
	}

	
}
