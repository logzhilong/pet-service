package com.momoplan.pet.commons.domain.statistic.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* BizDailyMethod
* table:biz_daily_method
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-11 17:28:39
*/
public class BizDailyMethod implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 日期
     */
    private String bizDate;

    /**
     * 服务
     */
    private String service;

    /**
     * 方法名
     */
    private String method;

    /**
     * 该service的method触发次数
     */
    private String totalCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 日期
     */
    public String getBizDate() {
        return bizDate;
    }

    /**
     * @param bizDate 
	 *            日期
     */
    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }

    /**
     * @return 服务
     */
    public String getService() {
        return service;
    }

    /**
     * @param service 
	 *            服务
     */
    public void setService(String service) {
        this.service = service;
    }

    /**
     * @return 方法名
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method 
	 *            方法名
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return 该service的method触发次数
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     * @param totalCount 
	 *            该service的method触发次数
     */
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
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
        BizDailyMethod other = (BizDailyMethod) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBizDate() == null ? other.getBizDate() == null : this.getBizDate().equals(other.getBizDate()))
            && (this.getService() == null ? other.getService() == null : this.getService().equals(other.getService()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getTotalCount() == null ? other.getTotalCount() == null : this.getTotalCount().equals(other.getTotalCount()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBizDate() == null) ? 0 : getBizDate().hashCode());
        result = prime * result + ((getService() == null) ? 0 : getService().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getTotalCount() == null) ? 0 : getTotalCount().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}