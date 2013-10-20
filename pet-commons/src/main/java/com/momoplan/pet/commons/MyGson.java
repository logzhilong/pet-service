package com.momoplan.pet.commons;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MyGson {
	
	private static Gson myGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	public static Gson getInstance(){
		return myGson;
	}
	
}
