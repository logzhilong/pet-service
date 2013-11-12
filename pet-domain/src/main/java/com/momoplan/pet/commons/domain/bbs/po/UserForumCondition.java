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
* @date 2013-11-12 14:11:25
*/
public class UserForumCondition implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String forumId;

    private String conditionValue;

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

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
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
            && (this.getConditionValue() == null ? other.getConditionValue() == null : this.getConditionValue().equals(other.getConditionValue()))
            && (this.getConditionType() == null ? other.getConditionType() == null : this.getConditionType().equals(other.getConditionType()))
            && (this.getCt() == null ? other.getCt() == null : this.getCt().equals(other.getCt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getForumId() == null) ? 0 : getForumId().hashCode());
        result = prime * result + ((getConditionValue() == null) ? 0 : getConditionValue().hashCode());
        result = prime * result + ((getConditionType() == null) ? 0 : getConditionType().hashCode());
        result = prime * result + ((getCt() == null) ? 0 : getCt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}