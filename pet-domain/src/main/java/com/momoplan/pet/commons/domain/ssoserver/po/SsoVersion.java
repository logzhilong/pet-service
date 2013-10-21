package com.momoplan.pet.commons.domain.ssoserver.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* SsoVersion
* table:pet_version
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 17:10:57
*/
public class SsoVersion implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long version;

    /**
     *  版本号
     */
    private String petVersion;

    private Date createDate;

    private String phoneType;

    private String iosurl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * @return  版本号
     */
    public String getPetVersion() {
        return petVersion;
    }

    /**
     * @param petVersion 
	 *             版本号
     */
    public void setPetVersion(String petVersion) {
        this.petVersion = petVersion;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getIosurl() {
        return iosurl;
    }

    public void setIosurl(String iosurl) {
        this.iosurl = iosurl;
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
        SsoVersion other = (SsoVersion) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getPetVersion() == null ? other.getPetVersion() == null : this.getPetVersion().equals(other.getPetVersion()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getPhoneType() == null ? other.getPhoneType() == null : this.getPhoneType().equals(other.getPhoneType()))
            && (this.getIosurl() == null ? other.getIosurl() == null : this.getIosurl().equals(other.getIosurl()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getPetVersion() == null) ? 0 : getPetVersion().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getPhoneType() == null) ? 0 : getPhoneType().hashCode());
        result = prime * result + ((getIosurl() == null) ? 0 : getIosurl().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}