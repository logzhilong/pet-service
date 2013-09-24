package com.momoplan.pet.domain;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findAuthenticationTokensByToken", "findAuthenticationTokensByUserid" })
public class AuthenticationToken {

    @Id
    private String token;

    private long expire;

    private long userid;

    private Date createDate;
}
