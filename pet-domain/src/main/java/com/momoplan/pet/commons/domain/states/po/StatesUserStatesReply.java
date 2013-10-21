package com.momoplan.pet.commons.domain.states.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* StatesUserStatesReply
* table:user_states_reply
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-21 15:14:36
*/
public class StatesUserStatesReply implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 动态id
     */
    private String stateid;

    /**
     * 父id
     */
    private String pid;

    /**
     * 回复信息
     */
    private String msg;

    /**
     * 发送回复的用户id
     */
    private String puserid;

    /**
     * 发送回复的用户id
     */
    private String userid;

    /**
     * 回复时间
     */
    private Date ct;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return 动态id
     */
    public String getStateid() {
        return stateid;
    }

    /**
     * @param stateid 
	 *            动态id
     */
    public void setStateid(String stateid) {
        this.stateid = stateid;
    }

    /**
     * @return 父id
     */
    public String getPid() {
        return pid;
    }

    /**
     * @param pid 
	 *            父id
     */
    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * @return 回复信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg 
	 *            回复信息
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return 发送回复的用户id
     */
    public String getPuserid() {
        return puserid;
    }

    /**
     * @param puserid 
	 *            发送回复的用户id
     */
    public void setPuserid(String puserid) {
        this.puserid = puserid;
    }

    /**
     * @return 发送回复的用户id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid 
	 *            发送回复的用户id
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return 回复时间
     */
    public Date getCt() {
        return ct;
    }

    /**
     * @param ct 
	 *            回复时间
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
        StatesUserStatesReply other = (StatesUserStatesReply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getStateid() == null ? other.getStateid() == null : this.getStateid().equals(other.getStateid()))
            && (this.getPid() == null ? other.getPid() == null : this.getPid().equals(other.getPid()))
            && (this.getMsg() == null ? other.getMsg() == null : this.getMsg().equals(other.getMsg()))
            && (this.getPuserid() == null ? other.getPuserid() == null : this.getPuserid().equals(other.getPuserid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getStateid() == null) ? 0 : getStateid().hashCode());
        result = prime * result + ((getPid() == null) ? 0 : getPid().hashCode());
        result = prime * result + ((getMsg() == null) ? 0 : getMsg().hashCode());
        result = prime * result + ((getPuserid() == null) ? 0 : getPuserid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}