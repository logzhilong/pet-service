package com.momoplan.pet.framework.feedback.service;


import com.momoplan.pet.framework.feedback.CacheKeysConstance;

public interface FeedbackService extends CacheKeysConstance{

	public void feedback(String feedback,String email, String userid) throws Exception;
	
}