package com.momoplan.pet.commons.domain.bbs.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* UserForumConditionCriteria 条件查询类.
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-01-06 12:06:10
*/
public class UserForumConditionCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer mysqlOffset;

    protected Integer mysqlLength;

    public UserForumConditionCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * @param mysqlOffset 
	 *            指定返回记录行的偏移量<br>
	 *            mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public void setMysqlOffset(int mysqlOffset) {
        this.mysqlOffset=mysqlOffset;
    }

    /**
     * @param mysqlLength 
	 *            指定返回记录行的最大数目<br>
	 *            mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public Integer getMysqlOffset() {
        return mysqlOffset;
    }

    /**
     * @param mysqlOffset 
	 *            指定返回记录行的偏移量<br>
	 *            mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public void setMysqlLength(int mysqlLength) {
        this.mysqlLength=mysqlLength;
    }

    /**
     * @param mysqlLength 
	 *            指定返回记录行的最大数目<br>
	 *            mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public Integer getMysqlLength() {
        return mysqlLength;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andForumIdIsNull() {
            addCriterion("forum_id is null");
            return (Criteria) this;
        }

        public Criteria andForumIdIsNotNull() {
            addCriterion("forum_id is not null");
            return (Criteria) this;
        }

        public Criteria andForumIdEqualTo(String value) {
            addCriterion("forum_id =", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdNotEqualTo(String value) {
            addCriterion("forum_id <>", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdGreaterThan(String value) {
            addCriterion("forum_id >", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdGreaterThanOrEqualTo(String value) {
            addCriterion("forum_id >=", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdLessThan(String value) {
            addCriterion("forum_id <", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdLessThanOrEqualTo(String value) {
            addCriterion("forum_id <=", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdLike(String value) {
            addCriterion("forum_id like", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdNotLike(String value) {
            addCriterion("forum_id not like", value, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdIn(List<String> values) {
            addCriterion("forum_id in", values, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdNotIn(List<String> values) {
            addCriterion("forum_id not in", values, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdBetween(String value1, String value2) {
            addCriterion("forum_id between", value1, value2, "forumId");
            return (Criteria) this;
        }

        public Criteria andForumIdNotBetween(String value1, String value2) {
            addCriterion("forum_id not between", value1, value2, "forumId");
            return (Criteria) this;
        }

        public Criteria andConditionValueIsNull() {
            addCriterion("condition_value is null");
            return (Criteria) this;
        }

        public Criteria andConditionValueIsNotNull() {
            addCriterion("condition_value is not null");
            return (Criteria) this;
        }

        public Criteria andConditionValueEqualTo(String value) {
            addCriterion("condition_value =", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotEqualTo(String value) {
            addCriterion("condition_value <>", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueGreaterThan(String value) {
            addCriterion("condition_value >", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueGreaterThanOrEqualTo(String value) {
            addCriterion("condition_value >=", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueLessThan(String value) {
            addCriterion("condition_value <", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueLessThanOrEqualTo(String value) {
            addCriterion("condition_value <=", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueLike(String value) {
            addCriterion("condition_value like", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotLike(String value) {
            addCriterion("condition_value not like", value, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueIn(List<String> values) {
            addCriterion("condition_value in", values, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotIn(List<String> values) {
            addCriterion("condition_value not in", values, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueBetween(String value1, String value2) {
            addCriterion("condition_value between", value1, value2, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionValueNotBetween(String value1, String value2) {
            addCriterion("condition_value not between", value1, value2, "conditionValue");
            return (Criteria) this;
        }

        public Criteria andConditionTypeIsNull() {
            addCriterion("condition_type is null");
            return (Criteria) this;
        }

        public Criteria andConditionTypeIsNotNull() {
            addCriterion("condition_type is not null");
            return (Criteria) this;
        }

        public Criteria andConditionTypeEqualTo(String value) {
            addCriterion("condition_type =", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotEqualTo(String value) {
            addCriterion("condition_type <>", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeGreaterThan(String value) {
            addCriterion("condition_type >", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeGreaterThanOrEqualTo(String value) {
            addCriterion("condition_type >=", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeLessThan(String value) {
            addCriterion("condition_type <", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeLessThanOrEqualTo(String value) {
            addCriterion("condition_type <=", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeLike(String value) {
            addCriterion("condition_type like", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotLike(String value) {
            addCriterion("condition_type not like", value, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeIn(List<String> values) {
            addCriterion("condition_type in", values, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotIn(List<String> values) {
            addCriterion("condition_type not in", values, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeBetween(String value1, String value2) {
            addCriterion("condition_type between", value1, value2, "conditionType");
            return (Criteria) this;
        }

        public Criteria andConditionTypeNotBetween(String value1, String value2) {
            addCriterion("condition_type not between", value1, value2, "conditionType");
            return (Criteria) this;
        }

        public Criteria andCtIsNull() {
            addCriterion("ct is null");
            return (Criteria) this;
        }

        public Criteria andCtIsNotNull() {
            addCriterion("ct is not null");
            return (Criteria) this;
        }

        public Criteria andCtEqualTo(Date value) {
            addCriterion("ct =", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtNotEqualTo(Date value) {
            addCriterion("ct <>", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtGreaterThan(Date value) {
            addCriterion("ct >", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtGreaterThanOrEqualTo(Date value) {
            addCriterion("ct >=", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtLessThan(Date value) {
            addCriterion("ct <", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtLessThanOrEqualTo(Date value) {
            addCriterion("ct <=", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtIn(List<Date> values) {
            addCriterion("ct in", values, "ct");
            return (Criteria) this;
        }

        public Criteria andCtNotIn(List<Date> values) {
            addCriterion("ct not in", values, "ct");
            return (Criteria) this;
        }

        public Criteria andCtBetween(Date value1, Date value2) {
            addCriterion("ct between", value1, value2, "ct");
            return (Criteria) this;
        }

        public Criteria andCtNotBetween(Date value1, Date value2) {
            addCriterion("ct not between", value1, value2, "ct");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}