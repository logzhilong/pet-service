// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.UserLocation;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

privileged aspect UserLocation_Roo_Jpa_Entity {
    
    declare @type: UserLocation: @Entity;
    
    @Version
    @Column(name = "version")
    private Integer UserLocation.version;
    
    public Integer UserLocation.getVersion() {
        return this.version;
    }
    
    public void UserLocation.setVersion(Integer version) {
        this.version = version;
    }
    
}
