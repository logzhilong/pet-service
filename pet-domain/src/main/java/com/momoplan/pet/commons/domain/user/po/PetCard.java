package com.momoplan.pet.commons.domain.user.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* PetCard
* table:pet_card
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-02-13 14:16:41
*/
public class PetCard implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键-编号
     */
    private String id;

    /**
     * 宠物名片所有者
     */
    private String userId;

    /**
     * 宠物类型
     */
    private String petType;

    /**
     * 宠物昵称
     */
    private String petNickname;

    /**
     * 宠物主人
     */
    private String petOwner;

    /**
     * 主人电话
     */
    private String petOwnerTel;

    private String petOwnerMsg;

    /**
     * 宠物图片
     */
    private String petImg;

    private Date ct;

    private Date et;

    /**
     * create by
     */
    private String cb;

    private String eb;

    private Integer num;

    /**
     * @return 主键-编号
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键-编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 宠物名片所有者
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId 
	 *            宠物名片所有者
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return 宠物类型
     */
    public String getPetType() {
        return petType;
    }

    /**
     * @param petType 
	 *            宠物类型
     */
    public void setPetType(String petType) {
        this.petType = petType;
    }

    /**
     * @return 宠物昵称
     */
    public String getPetNickname() {
        return petNickname;
    }

    /**
     * @param petNickname 
	 *            宠物昵称
     */
    public void setPetNickname(String petNickname) {
        this.petNickname = petNickname;
    }

    /**
     * @return 宠物主人
     */
    public String getPetOwner() {
        return petOwner;
    }

    /**
     * @param petOwner 
	 *            宠物主人
     */
    public void setPetOwner(String petOwner) {
        this.petOwner = petOwner;
    }

    /**
     * @return 主人电话
     */
    public String getPetOwnerTel() {
        return petOwnerTel;
    }

    /**
     * @param petOwnerTel 
	 *            主人电话
     */
    public void setPetOwnerTel(String petOwnerTel) {
        this.petOwnerTel = petOwnerTel;
    }

    public String getPetOwnerMsg() {
        return petOwnerMsg;
    }

    public void setPetOwnerMsg(String petOwnerMsg) {
        this.petOwnerMsg = petOwnerMsg;
    }

    /**
     * @return 宠物图片
     */
    public String getPetImg() {
        return petImg;
    }

    /**
     * @param petImg 
	 *            宠物图片
     */
    public void setPetImg(String petImg) {
        this.petImg = petImg;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    public Date getEt() {
        return et;
    }

    public void setEt(Date et) {
        this.et = et;
    }

    /**
     * @return create by
     */
    public String getCb() {
        return cb;
    }

    /**
     * @param cb 
	 *            create by
     */
    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getEb() {
        return eb;
    }

    public void setEb(String eb) {
        this.eb = eb;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
        PetCard other = (PetCard) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getPetType() == null ? other.getPetType() == null : this.getPetType().equals(other.getPetType()))
            && (this.getPetNickname() == null ? other.getPetNickname() == null : this.getPetNickname().equals(other.getPetNickname()))
            && (this.getPetOwner() == null ? other.getPetOwner() == null : this.getPetOwner().equals(other.getPetOwner()))
            && (this.getPetOwnerTel() == null ? other.getPetOwnerTel() == null : this.getPetOwnerTel().equals(other.getPetOwnerTel()))
            && (this.getPetOwnerMsg() == null ? other.getPetOwnerMsg() == null : this.getPetOwnerMsg().equals(other.getPetOwnerMsg()))
            && (this.getPetImg() == null ? other.getPetImg() == null : this.getPetImg().equals(other.getPetImg()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()))
            && (this.getEt() == null ? other.getEt() == null : this.getEt().equals(other.getEt()))
            && (this.getCb() == null ? other.getCb() == null : this.getCb().equals(other.getCb()))
            && (this.getEb() == null ? other.getEb() == null : this.getEb().equals(other.getEb()))
            && (this.getNum() == null ? other.getNum() == null : this.getNum().equals(other.getNum()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getPetType() == null) ? 0 : getPetType().hashCode());
        result = prime * result + ((getPetNickname() == null) ? 0 : getPetNickname().hashCode());
        result = prime * result + ((getPetOwner() == null) ? 0 : getPetOwner().hashCode());
        result = prime * result + ((getPetOwnerTel() == null) ? 0 : getPetOwnerTel().hashCode());
        result = prime * result + ((getPetOwnerMsg() == null) ? 0 : getPetOwnerMsg().hashCode());
        result = prime * result + ((getPetImg() == null) ? 0 : getPetImg().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        result = prime * result + ((getEt() == null) ? 0 : getEt().hashCode());
        result = prime * result + ((getCb() == null) ? 0 : getCb().hashCode());
        result = prime * result + ((getEb() == null) ? 0 : getEb().hashCode());
        result = prime * result + ((getNum() == null) ? 0 : getNum().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}