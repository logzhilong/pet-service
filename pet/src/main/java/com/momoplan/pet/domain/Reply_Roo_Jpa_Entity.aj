// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.Reply;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Reply_Roo_Jpa_Entity {
    
    declare @type: Reply: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Reply.id;
    
    @Version
    @Column(name = "version")
    private Integer Reply.version;
    
    public Long Reply.getId() {
        return this.id;
    }
    
    public void Reply.setId(Long id) {
        this.id = id;
    }
    
    public Integer Reply.getVersion() {
        return this.version;
    }
    
    public void Reply.setVersion(Integer version) {
        this.version = version;
    }
    
}