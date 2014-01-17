package com.momoplan.pet.commons.domain.manager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* MgrPush
* table:mgr_push
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-17 11:06:40
*/
public class MgrPush implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 标题
     */
    private String name;

    /**
     * 类型：帖子、百科、经验
     */
    private String src;

    /**
     * 状态：未推送、已推送
     */
    private String state;

    private Date ct;

    /**
     * create by
     */
    private String cb;

    private Date et;

    private String eb;

    private String expir;

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
     * @return 标题
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
	 *            标题
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return 类型：帖子、百科、经验
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src 
	 *            类型：帖子、百科、经验
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * @return 状态：未推送、已推送
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态：未推送、已推送
     */
    public void setState(String state) {
        this.state = state;
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

    public Date getEt() {
        return et;
    }

    public void setEt(Date et) {
        this.et = et;
    }

    public String getEb() {
        return eb;
    }

    public void setEb(String eb) {
        this.eb = eb;
    }

    public String getExpir() {
        return expir;
    }

    public void setExpir(String expir) {
        this.expir = expir;
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
        MgrPush other = (MgrPush) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getSrc() == null ? other.getSrc() == null : this.getSrc().equals(other.getSrc()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()))
            && (this.getCb() == null ? other.getCb() == null : this.getCb().equals(other.getCb()))
            && (this.getEt() == null ? other.getEt() == null : this.getEt().equals(other.getEt()))
            && (this.getEb() == null ? other.getEb() == null : this.getEb().equals(other.getEb()))
            && (this.getExpir() == null ? other.getExpir() == null : this.getExpir().equals(other.getExpir()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSrc() == null) ? 0 : getSrc().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        result = prime * result + ((getCb() == null) ? 0 : getCb().hashCode());
        result = prime * result + ((getEt() == null) ? 0 : getEt().hashCode());
        result = prime * result + ((getEb() == null) ? 0 : getEb().hashCode());
        result = prime * result + ((getExpir() == null) ? 0 : getExpir().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}