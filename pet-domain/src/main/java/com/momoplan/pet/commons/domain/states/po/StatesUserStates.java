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
* @date 2013-10-09 15:38:15
*/
public class StatesUserStates implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String ifTransmitMsg;

    private String imgid;

    private String msg;

    private Long petUserid;

    private Date submitTime;

    private String transmitMsg;

    private String transmitUrl;

    private Integer version;

    private Double latitude;

    private Double longitude;

    private String stateType;

    private Integer reportTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIfTransmitMsg() {
        return ifTransmitMsg;
    }

    public void setIfTransmitMsg(String ifTransmitMsg) {
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

    public Long getPetUserid() {
        return petUserid;
    }

    public void setPetUserid(Long petUserid) {
        this.petUserid = petUserid;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getTransmitMsg() {
        return transmitMsg;
    }

    public void setTransmitMsg(String transmitMsg) {
        this.transmitMsg = transmitMsg;
    }

    public String getTransmitUrl() {
        return transmitUrl;
    }

    public void setTransmitUrl(String transmitUrl) {
        this.transmitUrl = transmitUrl;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
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
            && (this.getPetUserid() == null ? other.getPetUserid() == null : this.getPetUserid().equals(other.getPetUserid()))
            && (this.getSubmitTime() == null ? other.getSubmitTime() == null : this.getSubmitTime().equals(other.getSubmitTime()))
            && (this.getTransmitMsg() == null ? other.getTransmitMsg() == null : this.getTransmitMsg().equals(other.getTransmitMsg()))
            && (this.getTransmitUrl() == null ? other.getTransmitUrl() == null : this.getTransmitUrl().equals(other.getTransmitUrl()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getLatitude() == null ? other.getLatitude() == null : this.getLatitude().equals(other.getLatitude()))
            && (this.getLongitude() == null ? other.getLongitude() == null : this.getLongitude().equals(other.getLongitude()))
            && (this.getStateType() == null ? other.getStateType() == null : this.getStateType().equals(other.getStateType()))
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
        result = prime * result + ((getPetUserid() == null) ? 0 : getPetUserid().hashCode());
        result = prime * result + ((getSubmitTime() == null) ? 0 : getSubmitTime().hashCode());
        result = prime * result + ((getTransmitMsg() == null) ? 0 : getTransmitMsg().hashCode());
        result = prime * result + ((getTransmitUrl() == null) ? 0 : getTransmitUrl().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getLatitude() == null) ? 0 : getLatitude().hashCode());
        result = prime * result + ((getLongitude() == null) ? 0 : getLongitude().hashCode());
        result = prime * result + ((getStateType() == null) ? 0 : getStateType().hashCode());
        result = prime * result + ((getReportTimes() == null) ? 0 : getReportTimes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}