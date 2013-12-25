package com.momoplan.pet.framework.petservice.report.vo;

/**
 * 渠道统计实体
 * @author liangc
 */
public class ChannelCounterVo {

//	\"all_user\": 0, 
//	\"new_user\": 0, 
//	\"new_register\": 0, 
//	\"all_register\": 0, 
//	\"new_pv\": 10, 
//	\"all_pv\": 483, 
//	\"new_rate\": 0.0, 
//	\"all_rate\": 0.0, 
//	\"channel\": \"Default\"
	private String channel = null;
	private String channelName = null;
	private Integer new_user = 0;
	private Integer new_register = 0;
	private Float new_rate = 0F;
	private Integer all_user = 0;
	private Integer all_register = 0;
	private Float all_rate = 0F;
	
	private Integer new_online = 0;
	private Integer all_online = 0;
	
	private Integer new_pv = 0;
	private Integer all_pv = 0;
	
	private boolean reg = true;
	
	
	public Integer getNew_online() {
		return new_online;
	}
	public void setNew_online(Integer new_online) {
		this.new_online = new_online;
	}
	public Integer getAll_online() {
		return all_online;
	}
	public void setAll_online(Integer all_online) {
		this.all_online = all_online;
	}
	public boolean isReg() {
		return reg;
	}
	public void setReg(boolean reg) {
		this.reg = reg;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getNew_user() {
		return new_user;
	}
	public void setNew_user(Integer new_user) {
		this.new_user = new_user;
	}
	public Integer getNew_register() {
		return new_register;
	}
	public void setNew_register(Integer new_register) {
		this.new_register = new_register;
	}
	public Float getNew_rate() {
		return new_rate;
	}
	public void setNew_rate(Float new_rate) {
		this.new_rate = new_rate;
	}
	public Integer getAll_user() {
		return all_user;
	}
	public void setAll_user(Integer all_user) {
		this.all_user = all_user;
	}
	public Integer getAll_register() {
		return all_register;
	}
	public void setAll_register(Integer all_register) {
		this.all_register = all_register;
	}
	public Float getAll_rate() {
		return all_rate;
	}
	public void setAll_rate(Float all_rate) {
		this.all_rate = all_rate;
	}
	public Integer getNew_pv() {
		return new_pv;
	}
	public void setNew_pv(Integer new_pv) {
		this.new_pv = new_pv;
	}
	public Integer getAll_pv() {
		return all_pv;
	}
	public void setAll_pv(Integer all_pv) {
		this.all_pv = all_pv;
	}
	
}