package com.momoplan.pet.commons.domain.ssoserver.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* UserFriendship
* table:user_friendship
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 19:21:27
*/
public class UserFriendship implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long aId;

    private Long bId;

    private String remark;

    private Integer verified;

    private Integer version;

    private String aliasa;

    private String aliasb;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getaId() {
        return aId;
    }

    public void setaId(Long aId) {
        this.aId = aId;
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVerified() {
        return verified;
    }

    public void setVerified(Integer verified) {
        this.verified = verified;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getAliasa() {
        return aliasa;
    }

    public void setAliasa(String aliasa) {
        this.aliasa = aliasa;
    }

    public String getAliasb() {
        return aliasb;
    }

    public void setAliasb(String aliasb) {
        this.aliasb = aliasb;
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
        UserFriendship other = (UserFriendship) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getaId() == null ? other.getaId() == null : this.getaId().equals(other.getaId()))
            && (this.getbId() == null ? other.getbId() == null : this.getbId().equals(other.getbId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getVerified() == null ? other.getVerified() == null : this.getVerified().equals(other.getVerified()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getAliasa() == null ? other.getAliasa() == null : this.getAliasa().equals(other.getAliasa()))
            && (this.getAliasb() == null ? other.getAliasb() == null : this.getAliasb().equals(other.getAliasb()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getaId() == null) ? 0 : getaId().hashCode());
        result = prime * result + ((getbId() == null) ? 0 : getbId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getVerified() == null) ? 0 : getVerified().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getAliasa() == null) ? 0 : getAliasa().hashCode());
        result = prime * result + ((getAliasb() == null) ? 0 : getAliasb().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}