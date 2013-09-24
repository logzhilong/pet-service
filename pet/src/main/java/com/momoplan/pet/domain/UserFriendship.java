package com.momoplan.pet.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;


@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord
public class UserFriendship {
	private long aId;
	private long bId;
	private int verified;
	private String remark;
	private String aliasA;
	private String aliasB;
}
