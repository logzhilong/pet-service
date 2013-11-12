package com.momoplan.pet.commons.domain.bbs.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* ForumCriteria 条件查询类.
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-11-12 11:25:18
*/
public class ForumCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer mysqlOffset;

    protected Integer mysqlLength;

    public ForumCriteria() {
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
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPidIsNull() {
            addCriterion("PID is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("PID is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(String value) {
            addCriterion("PID =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(String value) {
            addCriterion("PID <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(String value) {
            addCriterion("PID >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(String value) {
            addCriterion("PID >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(String value) {
            addCriterion("PID <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(String value) {
            addCriterion("PID <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLike(String value) {
            addCriterion("PID like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotLike(String value) {
            addCriterion("PID not like", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<String> values) {
            addCriterion("PID in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<String> values) {
            addCriterion("PID not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(String value1, String value2) {
            addCriterion("PID between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(String value1, String value2) {
            addCriterion("PID not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptIsNull() {
            addCriterion("DESCRIPT is null");
            return (Criteria) this;
        }

        public Criteria andDescriptIsNotNull() {
            addCriterion("DESCRIPT is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptEqualTo(String value) {
            addCriterion("DESCRIPT =", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptNotEqualTo(String value) {
            addCriterion("DESCRIPT <>", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptGreaterThan(String value) {
            addCriterion("DESCRIPT >", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptGreaterThanOrEqualTo(String value) {
            addCriterion("DESCRIPT >=", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptLessThan(String value) {
            addCriterion("DESCRIPT <", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptLessThanOrEqualTo(String value) {
            addCriterion("DESCRIPT <=", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptLike(String value) {
            addCriterion("DESCRIPT like", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptNotLike(String value) {
            addCriterion("DESCRIPT not like", value, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptIn(List<String> values) {
            addCriterion("DESCRIPT in", values, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptNotIn(List<String> values) {
            addCriterion("DESCRIPT not in", values, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptBetween(String value1, String value2) {
            addCriterion("DESCRIPT between", value1, value2, "descript");
            return (Criteria) this;
        }

        public Criteria andDescriptNotBetween(String value1, String value2) {
            addCriterion("DESCRIPT not between", value1, value2, "descript");
            return (Criteria) this;
        }

        public Criteria andClientCountIsNull() {
            addCriterion("CLIENT_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andClientCountIsNotNull() {
            addCriterion("CLIENT_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andClientCountEqualTo(Long value) {
            addCriterion("CLIENT_COUNT =", value, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountNotEqualTo(Long value) {
            addCriterion("CLIENT_COUNT <>", value, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountGreaterThan(Long value) {
            addCriterion("CLIENT_COUNT >", value, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountGreaterThanOrEqualTo(Long value) {
            addCriterion("CLIENT_COUNT >=", value, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountLessThan(Long value) {
            addCriterion("CLIENT_COUNT <", value, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountLessThanOrEqualTo(Long value) {
            addCriterion("CLIENT_COUNT <=", value, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountIn(List<Long> values) {
            addCriterion("CLIENT_COUNT in", values, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountNotIn(List<Long> values) {
            addCriterion("CLIENT_COUNT not in", values, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountBetween(Long value1, Long value2) {
            addCriterion("CLIENT_COUNT between", value1, value2, "clientCount");
            return (Criteria) this;
        }

        public Criteria andClientCountNotBetween(Long value1, Long value2) {
            addCriterion("CLIENT_COUNT not between", value1, value2, "clientCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountIsNull() {
            addCriterion("REPLY_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andReplyCountIsNotNull() {
            addCriterion("REPLY_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andReplyCountEqualTo(Long value) {
            addCriterion("REPLY_COUNT =", value, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountNotEqualTo(Long value) {
            addCriterion("REPLY_COUNT <>", value, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountGreaterThan(Long value) {
            addCriterion("REPLY_COUNT >", value, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountGreaterThanOrEqualTo(Long value) {
            addCriterion("REPLY_COUNT >=", value, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountLessThan(Long value) {
            addCriterion("REPLY_COUNT <", value, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountLessThanOrEqualTo(Long value) {
            addCriterion("REPLY_COUNT <=", value, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountIn(List<Long> values) {
            addCriterion("REPLY_COUNT in", values, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountNotIn(List<Long> values) {
            addCriterion("REPLY_COUNT not in", values, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountBetween(Long value1, Long value2) {
            addCriterion("REPLY_COUNT between", value1, value2, "replyCount");
            return (Criteria) this;
        }

        public Criteria andReplyCountNotBetween(Long value1, Long value2) {
            addCriterion("REPLY_COUNT not between", value1, value2, "replyCount");
            return (Criteria) this;
        }

        public Criteria andLogoImgIsNull() {
            addCriterion("LOGO_IMG is null");
            return (Criteria) this;
        }

        public Criteria andLogoImgIsNotNull() {
            addCriterion("LOGO_IMG is not null");
            return (Criteria) this;
        }

        public Criteria andLogoImgEqualTo(String value) {
            addCriterion("LOGO_IMG =", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotEqualTo(String value) {
            addCriterion("LOGO_IMG <>", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgGreaterThan(String value) {
            addCriterion("LOGO_IMG >", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgGreaterThanOrEqualTo(String value) {
            addCriterion("LOGO_IMG >=", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLessThan(String value) {
            addCriterion("LOGO_IMG <", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLessThanOrEqualTo(String value) {
            addCriterion("LOGO_IMG <=", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgLike(String value) {
            addCriterion("LOGO_IMG like", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotLike(String value) {
            addCriterion("LOGO_IMG not like", value, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgIn(List<String> values) {
            addCriterion("LOGO_IMG in", values, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotIn(List<String> values) {
            addCriterion("LOGO_IMG not in", values, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgBetween(String value1, String value2) {
            addCriterion("LOGO_IMG between", value1, value2, "logoImg");
            return (Criteria) this;
        }

        public Criteria andLogoImgNotBetween(String value1, String value2) {
            addCriterion("LOGO_IMG not between", value1, value2, "logoImg");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNull() {
            addCriterion("AREA_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIsNotNull() {
            addCriterion("AREA_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAreaCodeEqualTo(String value) {
            addCriterion("AREA_CODE =", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotEqualTo(String value) {
            addCriterion("AREA_CODE <>", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThan(String value) {
            addCriterion("AREA_CODE >", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeGreaterThanOrEqualTo(String value) {
            addCriterion("AREA_CODE >=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThan(String value) {
            addCriterion("AREA_CODE <", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLessThanOrEqualTo(String value) {
            addCriterion("AREA_CODE <=", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeLike(String value) {
            addCriterion("AREA_CODE like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotLike(String value) {
            addCriterion("AREA_CODE not like", value, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeIn(List<String> values) {
            addCriterion("AREA_CODE in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotIn(List<String> values) {
            addCriterion("AREA_CODE not in", values, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeBetween(String value1, String value2) {
            addCriterion("AREA_CODE between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaCodeNotBetween(String value1, String value2) {
            addCriterion("AREA_CODE not between", value1, value2, "areaCode");
            return (Criteria) this;
        }

        public Criteria andAreaDescIsNull() {
            addCriterion("AREA_DESC is null");
            return (Criteria) this;
        }

        public Criteria andAreaDescIsNotNull() {
            addCriterion("AREA_DESC is not null");
            return (Criteria) this;
        }

        public Criteria andAreaDescEqualTo(String value) {
            addCriterion("AREA_DESC =", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescNotEqualTo(String value) {
            addCriterion("AREA_DESC <>", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescGreaterThan(String value) {
            addCriterion("AREA_DESC >", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescGreaterThanOrEqualTo(String value) {
            addCriterion("AREA_DESC >=", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescLessThan(String value) {
            addCriterion("AREA_DESC <", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescLessThanOrEqualTo(String value) {
            addCriterion("AREA_DESC <=", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescLike(String value) {
            addCriterion("AREA_DESC like", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescNotLike(String value) {
            addCriterion("AREA_DESC not like", value, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescIn(List<String> values) {
            addCriterion("AREA_DESC in", values, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescNotIn(List<String> values) {
            addCriterion("AREA_DESC not in", values, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescBetween(String value1, String value2) {
            addCriterion("AREA_DESC between", value1, value2, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andAreaDescNotBetween(String value1, String value2) {
            addCriterion("AREA_DESC not between", value1, value2, "areaDesc");
            return (Criteria) this;
        }

        public Criteria andCtIsNull() {
            addCriterion("CT is null");
            return (Criteria) this;
        }

        public Criteria andCtIsNotNull() {
            addCriterion("CT is not null");
            return (Criteria) this;
        }

        public Criteria andCtEqualTo(Date value) {
            addCriterion("CT =", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtNotEqualTo(Date value) {
            addCriterion("CT <>", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtGreaterThan(Date value) {
            addCriterion("CT >", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtGreaterThanOrEqualTo(Date value) {
            addCriterion("CT >=", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtLessThan(Date value) {
            addCriterion("CT <", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtLessThanOrEqualTo(Date value) {
            addCriterion("CT <=", value, "ct");
            return (Criteria) this;
        }

        public Criteria andCtIn(List<Date> values) {
            addCriterion("CT in", values, "ct");
            return (Criteria) this;
        }

        public Criteria andCtNotIn(List<Date> values) {
            addCriterion("CT not in", values, "ct");
            return (Criteria) this;
        }

        public Criteria andCtBetween(Date value1, Date value2) {
            addCriterion("CT between", value1, value2, "ct");
            return (Criteria) this;
        }

        public Criteria andCtNotBetween(Date value1, Date value2) {
            addCriterion("CT not between", value1, value2, "ct");
            return (Criteria) this;
        }

        public Criteria andCbIsNull() {
            addCriterion("CB is null");
            return (Criteria) this;
        }

        public Criteria andCbIsNotNull() {
            addCriterion("CB is not null");
            return (Criteria) this;
        }

        public Criteria andCbEqualTo(String value) {
            addCriterion("CB =", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotEqualTo(String value) {
            addCriterion("CB <>", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbGreaterThan(String value) {
            addCriterion("CB >", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbGreaterThanOrEqualTo(String value) {
            addCriterion("CB >=", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbLessThan(String value) {
            addCriterion("CB <", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbLessThanOrEqualTo(String value) {
            addCriterion("CB <=", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbLike(String value) {
            addCriterion("CB like", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotLike(String value) {
            addCriterion("CB not like", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbIn(List<String> values) {
            addCriterion("CB in", values, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotIn(List<String> values) {
            addCriterion("CB not in", values, "cb");
            return (Criteria) this;
        }

        public Criteria andCbBetween(String value1, String value2) {
            addCriterion("CB between", value1, value2, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotBetween(String value1, String value2) {
            addCriterion("CB not between", value1, value2, "cb");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("SEQ is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("SEQ is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Long value) {
            addCriterion("SEQ =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Long value) {
            addCriterion("SEQ <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Long value) {
            addCriterion("SEQ >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Long value) {
            addCriterion("SEQ >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Long value) {
            addCriterion("SEQ <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Long value) {
            addCriterion("SEQ <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Long> values) {
            addCriterion("SEQ in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Long> values) {
            addCriterion("SEQ not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Long value1, Long value2) {
            addCriterion("SEQ between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Long value1, Long value2) {
            addCriterion("SEQ not between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("TYPE is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("TYPE =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("TYPE <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("TYPE >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("TYPE <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("TYPE <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("TYPE like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("TYPE not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("TYPE in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("TYPE not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("TYPE between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("TYPE not between", value1, value2, "type");
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