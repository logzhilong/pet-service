package com.momoplan.pet.commons.domain.bbs.po;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
* UserForumCondition
* table:bbs_user_forum_condition
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-12 11:25:18
*/
public class UserForumCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String forumId;

    private String condition;

    private String conditionType;

    private Date ct;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getForumId() {
        return forumId;
    }

    public void setForumId(String forumId) {
        this.forumId = forumId;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public Date getCt() {
        return ct;
    }

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
        UserForumCondition other = (UserForumCondition) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getForumId() == null ? other.getForumId() == null : this.getForumId().equals(other.getForumId()))
            && (this.getCondition() == null ? other.getCondition() == null : this.getCondition().equals(other.getCondition()))
            && (this.getConditionType() == null ? other.getConditionType() == null : this.getConditionType().equals(other.getConditionType()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getForumId() == null) ? 0 : getForumId().hashCode());
        result = prime * result + ((getCondition() == null) ? 0 : getCondition().hashCode());
        result = prime * result + ((getConditionType() == null) ? 0 : getConditionType().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}