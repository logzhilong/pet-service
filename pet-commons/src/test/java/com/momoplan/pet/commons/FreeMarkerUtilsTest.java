package com.momoplan.pet.commons;

import java.util.HashMap;
import java.util.Map;

public class FreeMarkerUtilsTest {
	
	public static void main(String[] args) {
		Map<String,Object> root = new HashMap<String,Object>();
		root.put("id", "123");
		FreeMarkerUtils freeMarkerUtils = new FreeMarkerUtils("TestSql.ftl",root);
		String sql = freeMarkerUtils.getText();
		System.out.println(sql);
	}
	
}
