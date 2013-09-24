package com.momoplan.pet.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findVerificationsByPhoneNumEquals" })
public class Verification {

    private String phoneNum;

    private String verificationCode;
}
