package com.momoplan.pet.commons.domain.statistic.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* StatPetUser
* table:pet_user
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-27 10:49:40
*/
public class StatPetUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String birthdate;

    private Date createTime;

    private String email;

    private String gender;

    private String hobby;

    private String ifFraudulent;

    private String img;

    private String nickname;

    private String password;

    private String phoneNumber;

    private String signature;

    private String username;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getIfFraudulent() {
        return ifFraudulent;
    }

    public void setIfFraudulent(String ifFraudulent) {
        this.ifFraudulent = ifFraudulent;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        StatPetUser other = (StatPetUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBirthdate() == null ? other.getBirthdate() == null : this.getBirthdate().equals(other.getBirthdate()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getHobby() == null ? other.getHobby() == null : this.getHobby().equals(other.getHobby()))
            && (this.getIfFraudulent() == null ? other.getIfFraudulent() == null : this.getIfFraudulent().equals(other.getIfFraudulent()))
            && (this.getImg() == null ? other.getImg() == null : this.getImg().equals(other.getImg()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBirthdate() == null) ? 0 : getBirthdate().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getHobby() == null) ? 0 : getHobby().hashCode());
        result = prime * result + ((getIfFraudulent() == null) ? 0 : getIfFraudulent().hashCode());
        result = prime * result + ((getImg() == null) ? 0 : getImg().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}