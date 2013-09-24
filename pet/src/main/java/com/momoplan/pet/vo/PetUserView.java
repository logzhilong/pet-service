package com.momoplan.pet.vo;

import java.util.Date;
import java.util.List;

public class PetUserView implements Comparable<PetUserView>{
	long userid;
	String username;
	String nickname;
	String signature;
	String img;
	double longitude;
	double latitude;
	String distance;
	String birthdate;//生日
	String gender;//性别
	String hobby;//爱好
	long imgId;//需要更新的文件id
	int imgType;//需要更新的文件type
	int pageIndex;//当前页面索引
	List<PetInfoView> petInfoViews;
	Date createDate;
	Date connectTime;//连接时间
	private long countZan;
	private String ifIZaned;
	private String city;
	private String aliasName;
	
	
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getCountZan() {
		return countZan;
	}
	public void setCountZan(long countZan) {
		this.countZan = countZan;
	}
	public String getIfIZaned() {
		return ifIZaned;
	}
	public void setIfIZaned(String ifIZaned) {
		this.ifIZaned = ifIZaned;
	}
	public Date getConnectTime() {
		return connectTime;
	}
	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public long getImgId() {
		return imgId;
	}
	public void setImgId(long imgId) {
		this.imgId = imgId;
	}
	public int getImgType() {
		return imgType;
	}
	public void setImgType(int imgType) {
		this.imgType = imgType;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<PetInfoView> getPetInfoViews() {
		return petInfoViews;
	}
	public void setPetInfoViews(List<PetInfoView> petInfoViews) {
		this.petInfoViews = petInfoViews;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	@Override
    public int compareTo(PetUserView arg0) {
		int thisDis = Integer.parseInt(this.getDistance().substring(0, this.getDistance().length()-3));
		int argDis = Integer.parseInt(arg0.getDistance().substring(0, arg0.getDistance().length()-3));
        return thisDis-argDis;
    }
}
