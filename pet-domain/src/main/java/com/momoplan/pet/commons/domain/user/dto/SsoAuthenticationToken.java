package com.momoplan.pet.commons.domain.user.dto;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* SsoAuthenticationToken
* table:authentication_token
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 19:21:27
*/
public class SsoAuthenticationToken implements Serializable {
    private static final long serialVersionUID = 1L;

    private String token;

    private Date createDate;

    private Long expire;

    private String userid;

    private Integer version;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
    }

    public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SsoAuthenticationToken other = (SsoAuthenticationToken) that;
        return (this.getToken() == null ? other.getToken() == null : this.getToken().equals(other.getToken()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getExpire() == null ? other.getExpire() == null : this.getExpire().equals(other.getExpire()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getToken() == null) ? 0 : getToken().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getExpire() == null) ? 0 : getExpire().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}