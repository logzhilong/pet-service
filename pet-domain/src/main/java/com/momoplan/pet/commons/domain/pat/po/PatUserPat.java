package com.momoplan.pet.commons.domain.pat.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* PatUserPat
* table:user_pat
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 15:07:16
*/
public class PatUserPat implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 源ID
     */
    private String srcId;

    /**
     * 类型
     */
    private String type;

    /**
     * 创建时间
     */
    private Date ct;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
     * @return 源ID
     */
    public String getSrcId() {
        return srcId;
    }

    /**
     * @param srcId 
	 *            源ID
     */
    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    /**
     * @return 类型
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 
	 *            类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return 创建时间
     */
    public Date getCt() {
        return ct;
    }

    /**
     * @param ct 
	 *            创建时间
     */
    public void setCt(Date ct) {
        this.ct = ct;
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
        PatUserPat other = (PatUserPat) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getSrcId() == null ? other.getSrcId() == null : this.getSrcId().equals(other.getSrcId()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getSrcId() == null) ? 0 : getSrcId().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}