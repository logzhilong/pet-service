package com.momoplan.pet.commons.domain.statistic.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* StatHistoryLog
* table:history_log
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-09-27 10:49:40
*/
public class StatHistoryLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long connectTimes;

    private Date historyDate;

    private Long userId;

    private Integer version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConnectTimes() {
        return connectTimes;
    }

    public void setConnectTimes(Long connectTimes) {
        this.connectTimes = connectTimes;
    }

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        StatHistoryLog other = (StatHistoryLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getConnectTimes() == null ? other.getConnectTimes() == null : this.getConnectTimes().equals(other.getConnectTimes()))
            && (this.getHistoryDate() == null ? other.getHistoryDate() == null : this.getHistoryDate().equals(other.getHistoryDate()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getConnectTimes() == null) ? 0 : getConnectTimes().hashCode());
        result = prime * result + ((getHistoryDate() == null) ? 0 : getHistoryDate().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}