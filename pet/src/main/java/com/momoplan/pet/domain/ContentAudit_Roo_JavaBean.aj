// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.ContentAudit;

privileged aspect ContentAudit_Roo_JavaBean {
    
    public String ContentAudit.getBiz() {
        return this.biz;
    }
    
    public void ContentAudit.setBiz(String biz) {
        this.biz = biz;
    }
    
    public String ContentAudit.getContent() {
        return this.content;
    }
    
    public void ContentAudit.setContent(String content) {
        this.content = content;
    }
    
    public String ContentAudit.getBid() {
        return this.bid;
    }
    
    public void ContentAudit.setBid(String bid) {
        this.bid = bid;
    }
    
}