package com.momoplan.pet.commons.domain.statistic.po;

import java.util.ArrayList;
import java.util.List;

/**
* BizDailyRegistorCriteria 条件查询类.
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-12 17:27:19
*/
public class BizDailyRegistorCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer mysqlOffset;

    protected Integer mysqlLength;

    public BizDailyRegistorCriteria() {
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

        public Criteria andBizDateIsNull() {
            addCriterion("biz_date is null");
            return (Criteria) this;
        }

        public Criteria andBizDateIsNotNull() {
            addCriterion("biz_date is not null");
            return (Criteria) this;
        }

        public Criteria andBizDateEqualTo(String value) {
            addCriterion("biz_date =", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateNotEqualTo(String value) {
            addCriterion("biz_date <>", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateGreaterThan(String value) {
            addCriterion("biz_date >", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateGreaterThanOrEqualTo(String value) {
            addCriterion("biz_date >=", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateLessThan(String value) {
            addCriterion("biz_date <", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateLessThanOrEqualTo(String value) {
            addCriterion("biz_date <=", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateLike(String value) {
            addCriterion("biz_date like", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateNotLike(String value) {
            addCriterion("biz_date not like", value, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateIn(List<String> values) {
            addCriterion("biz_date in", values, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateNotIn(List<String> values) {
            addCriterion("biz_date not in", values, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateBetween(String value1, String value2) {
            addCriterion("biz_date between", value1, value2, "bizDate");
            return (Criteria) this;
        }

        public Criteria andBizDateNotBetween(String value1, String value2) {
            addCriterion("biz_date not between", value1, value2, "bizDate");
            return (Criteria) this;
        }

        public Criteria andChannelIsNull() {
            addCriterion("channel is null");
            return (Criteria) this;
        }

        public Criteria andChannelIsNotNull() {
            addCriterion("channel is not null");
            return (Criteria) this;
        }

        public Criteria andChannelEqualTo(String value) {
            addCriterion("channel =", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotEqualTo(String value) {
            addCriterion("channel <>", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThan(String value) {
            addCriterion("channel >", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelGreaterThanOrEqualTo(String value) {
            addCriterion("channel >=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThan(String value) {
            addCriterion("channel <", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLessThanOrEqualTo(String value) {
            addCriterion("channel <=", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelLike(String value) {
            addCriterion("channel like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotLike(String value) {
            addCriterion("channel not like", value, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelIn(List<String> values) {
            addCriterion("channel in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotIn(List<String> values) {
            addCriterion("channel not in", values, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelBetween(String value1, String value2) {
            addCriterion("channel between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andChannelNotBetween(String value1, String value2) {
            addCriterion("channel not between", value1, value2, "channel");
            return (Criteria) this;
        }

        public Criteria andTotallyUserIsNull() {
            addCriterion("totally_user is null");
            return (Criteria) this;
        }

        public Criteria andTotallyUserIsNotNull() {
            addCriterion("totally_user is not null");
            return (Criteria) this;
        }

        public Criteria andTotallyUserEqualTo(String value) {
            addCriterion("totally_user =", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserNotEqualTo(String value) {
            addCriterion("totally_user <>", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserGreaterThan(String value) {
            addCriterion("totally_user >", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserGreaterThanOrEqualTo(String value) {
            addCriterion("totally_user >=", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserLessThan(String value) {
            addCriterion("totally_user <", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserLessThanOrEqualTo(String value) {
            addCriterion("totally_user <=", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserLike(String value) {
            addCriterion("totally_user like", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserNotLike(String value) {
            addCriterion("totally_user not like", value, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserIn(List<String> values) {
            addCriterion("totally_user in", values, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserNotIn(List<String> values) {
            addCriterion("totally_user not in", values, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserBetween(String value1, String value2) {
            addCriterion("totally_user between", value1, value2, "totallyUser");
            return (Criteria) this;
        }

        public Criteria andTotallyUserNotBetween(String value1, String value2) {
            addCriterion("totally_user not between", value1, value2, "totallyUser");
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