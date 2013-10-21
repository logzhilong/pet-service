package com.momoplan.pet.commons.domain.states.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* StatesUserStates
* table:user_states
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 15:14:36
*/
public class StatesUserStates implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private Boolean ifTransmitMsg;

    private String imgid;

    private String msg;

    private String userid;

    private Date ct;

    private String transmitUrl;

    private Double latitude;

    private Double longitude;

    private String state;

    private Integer reportTimes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIfTransmitMsg() {
        return ifTransmitMsg;
    }

    public void setIfTransmitMsg(Boolean ifTransmitMsg) {
        this.ifTransmitMsg = ifTransmitMsg;
    }

    public String getImgid() {
        return imgid;
    }

    public void setImgid(String imgid) {
        this.imgid = imgid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    public String getTransmitUrl() {
        return transmitUrl;
    }

    public void setTransmitUrl(String transmitUrl) {
        this.transmitUrl = transmitUrl;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getReportTimes() {
        return reportTimes;
    }

    public void setReportTimes(Integer reportTimes) {
        this.reportTimes = reportTimes;
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
        StatesUserStates other = (StatesUserStates) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIfTransmitMsg() == null ? other.getIfTransmitMsg() == null : this.getIfTransmitMsg().equals(other.getIfTransmitMsg()))
            && (this.getImgid() == null ? other.getImgid() == null : this.getImgid().equals(other.getImgid()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()))
            && (this.getTransmitUrl() == null ? other.getTransmitUrl() == null : this.getTransmitUrl().equals(other.getTransmitUrl()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getReportTimes() == null ? other.getReportTimes() == null : this.getReportTimes().equals(other.getReportTimes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIfTransmitMsg() == null) ? 0 : getIfTransmitMsg().hashCode());
        result = prime * result + ((getImgid() == null) ? 0 : getImgid().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        result = prime * result + ((getTransmitUrl() == null) ? 0 : getTransmitUrl().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getReportTimes() == null) ? 0 : getReportTimes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}