package com.momoplan.pet.commons.domain.manager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* MgrPublishedApk
* table:mgr_published_apk
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-29 15:20:32
*/
public class MgrPublishedApk implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 文件ID
     */
    private String fileId;

    /**
     * 版本
     */
    private String version;

    /**
     * 渠道号
     */
    private String channel;

    private Date ct;

    /**
     * create by
     */
    private String cb;

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
     * @return 文件ID
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId 
	 *            文件ID
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return 版本
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version 
	 *            版本
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return 渠道号
     */
    public String getChannel() {
        return channel;
    }

    /**
     * @param channel 
	 *            渠道号
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
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
        MgrPublishedApk other = (MgrPublishedApk) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFileId() == null ? other.getFileId() == null : this.getFileId().equals(other.getFileId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getChannel() == null ? other.getChannel() == null : this.getChannel().equals(other.getChannel()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()))
            && (this.getCb() == null ? other.getCb() == null : this.getCb().equals(other.getCb()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFileId() == null) ? 0 : getFileId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getChannel() == null) ? 0 : getChannel().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        result = prime * result + ((getCb() == null) ? 0 : getCb().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}