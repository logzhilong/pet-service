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
@RooJpaActiveRecord
public class UserLocation {

    @Id
    private long userid;

    private double longitude;

    private double latitude;

    private Date createDate;
}
