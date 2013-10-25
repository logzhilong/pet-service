package com.momoplan.pet.framework.servicestate.vo;

import java.util.Date;

public class UserZan {
    private String id;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 源ID
     */
    private String srcId;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date ct;
    
    private String nickename;
    
    private String aliasname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSrcId() {
		return srcId;
	}

	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCt() {
		return ct;
	}

	public void setCt(Date ct) {
		this.ct = ct;
	}

	public String getNickename() {
		return nickename;
	}

	public void setNickename(String nickename) {
		this.nickename = nickename;
	}

	public String getAliasname() {
		return aliasname;
	}

	public void setAliasname(String aliasname) {
		this.aliasname = aliasname;
	}
    
}
