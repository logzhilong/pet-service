package com.momoplan.pet.commons.domain.manager.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* MgrServiceDict
* table:mgr_service_dict
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-19 14:45:08
*/
public class MgrServiceDict implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 别名
     */
    private String alias;

    /**
     * 服务注册名
     */
    private String service;

    /**
     * 方法名
     */
    private String method;

    private Date ct;

    private Date et;

    /**
     * create by
     */
    private String cb;

    /**
     * edit by
     */
    private String eb;

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
     * @return 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias 
	 *            别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return 服务注册名
     */
    public String getService() {
        return service;
    }

    /**
     * @param service 
	 *            服务注册名
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

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    public Date getEt() {
        return et;
    }

    public void setEt(Date et) {
        this.et = et;
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

    /**
     * @return edit by
     */
    public String getEb() {
        return eb;
    }

    /**
     * @param eb 
	 *            edit by
     */
    public void setEb(String eb) {
        this.eb = eb;
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
        MgrServiceDict other = (MgrServiceDict) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAlias() == null ? other.getAlias() == null : this.getAlias().equals(other.getAlias()))
            && (this.getService() == null ? other.getService() == null : this.getService().equals(other.getService()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()))
            && (this.getEt() == null ? other.getEt() == null : this.getEt().equals(other.getEt()))
            && (this.getCb() == null ? other.getCb() == null : this.getCb().equals(other.getCb()))
            && (this.getEb() == null ? other.getEb() == null : this.getEb().equals(other.getEb()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAlias() == null) ? 0 : getAlias().hashCode());
        result = prime * result + ((getService() == null) ? 0 : getService().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        result = prime * result + ((getEt() == null) ? 0 : getEt().hashCode());
        result = prime * result + ((getCb() == null) ? 0 : getCb().hashCode());
        result = prime * result + ((getEb() == null) ? 0 : getEb().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}