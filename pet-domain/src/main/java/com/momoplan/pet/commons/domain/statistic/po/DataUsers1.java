package com.momoplan.pet.commons.domain.statistic.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* DataUsers1
* table:data_users_1
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-11 12:03:37
*/
public class DataUsers1 implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 添加时间
     */
    private String createTime;

    /**
     * imei
     */
    private String imei;

    /**
     * mac地址
     */
    private String mac;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 连接时间
     */
    private String connectTime;

    /**
     * 生日
     */
    private String birthdate;

    /**
     * email
     */
    private String email;

    /**
     * 性别
     */
    private String gender;

    /**
     * 爱好
     */
    private String hobby;

    /**
     * 图片id
     */
    private String img;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 城市
     */
    private String city;

    /**
     * @return 主键
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 渠道
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel 
	 *            渠道
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * @return 添加时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 
	 *            添加时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     * @return imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei 
	 *            imei
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return mac地址
     */
    public String getMac() {
        return mac;
    }

    /**
     * @param mac 
	 *            mac地址
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return 用户id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            用户id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return 连接时间
     */
    public String getConnectTime() {
        return connectTime;
    }

    /**
     * @param connectTime 
	 *            连接时间
     */
    public void setConnectTime(String connectTime) {
        this.connectTime = connectTime;
    }

    /**
     * @return 生日
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate 
	 *            生日
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 性别
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender 
	 *            性别
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return 爱好
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * @param hobby 
	 *            爱好
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * @return 图片id
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img 
	 *            图片id
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 
	 *            用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 用户昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname 
	 *            用户昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
	 *            密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 用户手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber 
	 *            用户手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return 个性签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * @param signature 
	 *            个性签名
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * @return 城市
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 
	 *            城市
     */
    public void setCity(String city) {
        this.city = city;
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
        DataUsers1 other = (DataUsers1) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getImei() == null ? other.getImei() == null : this.getImei().equals(other.getImei()))
            && (this.getMac() == null ? other.getMac() == null : this.getMac().equals(other.getMac()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getConnectTime() == null ? other.getConnectTime() == null : this.getConnectTime().equals(other.getConnectTime()))
            && (this.getBirthdate() == null ? other.getBirthdate() == null : this.getBirthdate().equals(other.getBirthdate()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getGender() == null ? other.getGender() == null : this.getGender().equals(other.getGender()))
            && (this.getHobby() == null ? other.getHobby() == null : this.getHobby().equals(other.getHobby()))
            && (this.getImg() == null ? other.getImg() == null : this.getImg().equals(other.getImg()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getImei() == null) ? 0 : getImei().hashCode());
        result = prime * result + ((getMac() == null) ? 0 : getMac().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getConnectTime() == null) ? 0 : getConnectTime().hashCode());
        result = prime * result + ((getBirthdate() == null) ? 0 : getBirthdate().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
        result = prime * result + ((getHobby() == null) ? 0 : getHobby().hashCode());
        result = prime * result + ((getImg() == null) ? 0 : getImg().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}