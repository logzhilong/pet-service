package com.momoplan.pet.commons.repository.states;


public interface CacheKeysConstance {
	
	/**
	 * 用户的动态列表，根据用户ID来获取, key = LIST_USER_STATES + userId
	 */
	public static final String LIST_USER_STATES = "service_states.list.user_states:";
	
	/**
	 * 动态的回复列表，根据动态的ID来获取, key = LIST_USER_STATES_REPLY + statesId
	 */
	public static final String LIST_USER_STATES_REPLY = "service_states.list.user_states_reply:";

}
