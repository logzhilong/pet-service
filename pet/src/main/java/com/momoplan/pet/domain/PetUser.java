package com.momoplan.pet.domain;

import java.util.Date;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findPetUsersByUsername","findPetUserByUserId" })
public class PetUser {

    private String username;

    private String password;

    private String nickname;
    
    private String birthdate;
    
    private String signature;
    
    private String gender;
    
    private String img;
    
    private String phoneNumber;
    
    private String email;
    
    private String hobby;
    
    private Date createTime;

    private String ifFraudulent;
    
    private String city;
    
    private String deviceToken;
}
