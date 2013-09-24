package com.momoplan.pet.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findPetInfoesByUserid", "findPetInfoesByTypeAndUserid" })
public class PetInfo {

    private String nickname;

    private String birthdate;

    private long type;

    private String gender;

    private String img;

    private long userid;

    private String trait;
}
