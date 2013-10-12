package com.momoplan.pet.commons.domain.statistic.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* BizDailyLive
* table:biz_daily_live
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-12 17:27:19
*/
public class BizDailyLive implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 业务产生日期
     */
    private String bizDate;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 该天连接用户总量
     */
    private String totallyUser;

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
     * @return 业务产生日期
     */
    public String getBizDate() {
        return bizDate;
    }

    /**
     * @param bizDate 
	 *            业务产生日期
     */
    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
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
     * @return 该天连接用户总量
     */
    public String getTotallyUser() {
        return totallyUser;
    }

    /**
     * @param totallyUser 
	 *            该天连接用户总量
     */
    public void setTotallyUser(String totallyUser) {
        this.totallyUser = totallyUser;
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
        BizDailyLive other = (BizDailyLive) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBizDate() == null ? other.getBizDate() == null : this.getBizDate().equals(other.getBizDate()))
            && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()))
            && (this.getTotallyUser() == null ? other.getTotallyUser() == null : this.getTotallyUser().equals(other.getTotallyUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBizDate() == null) ? 0 : getBizDate().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        result = prime * result + ((getTotallyUser() == null) ? 0 : getTotallyUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}