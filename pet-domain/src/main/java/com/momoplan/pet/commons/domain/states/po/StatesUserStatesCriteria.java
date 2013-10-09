package com.momoplan.pet.commons.domain.states.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* StatesUserStatesCriteria 条件查询类.
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2013-10-09 16:26:51
*/
public class StatesUserStatesCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer mysqlOffset;

    protected Integer mysqlLength;

    public StatesUserStatesCriteria() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgIsNull() {
            addCriterion("if_transmit_msg is null");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgIsNotNull() {
            addCriterion("if_transmit_msg is not null");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgEqualTo(String value) {
            addCriterion("if_transmit_msg =", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgNotEqualTo(String value) {
            addCriterion("if_transmit_msg <>", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgGreaterThan(String value) {
            addCriterion("if_transmit_msg >", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgGreaterThanOrEqualTo(String value) {
            addCriterion("if_transmit_msg >=", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgLessThan(String value) {
            addCriterion("if_transmit_msg <", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgLessThanOrEqualTo(String value) {
            addCriterion("if_transmit_msg <=", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgLike(String value) {
            addCriterion("if_transmit_msg like", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgNotLike(String value) {
            addCriterion("if_transmit_msg not like", value, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgIn(List<String> values) {
            addCriterion("if_transmit_msg in", values, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgNotIn(List<String> values) {
            addCriterion("if_transmit_msg not in", values, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgBetween(String value1, String value2) {
            addCriterion("if_transmit_msg between", value1, value2, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andIfTransmitMsgNotBetween(String value1, String value2) {
            addCriterion("if_transmit_msg not between", value1, value2, "ifTransmitMsg");
            return (Criteria) this;
        }

        public Criteria andImgidIsNull() {
            addCriterion("imgid is null");
            return (Criteria) this;
        }

        public Criteria andImgidIsNotNull() {
            addCriterion("imgid is not null");
            return (Criteria) this;
        }

        public Criteria andImgidEqualTo(String value) {
            addCriterion("imgid =", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidNotEqualTo(String value) {
            addCriterion("imgid <>", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidGreaterThan(String value) {
            addCriterion("imgid >", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidGreaterThanOrEqualTo(String value) {
            addCriterion("imgid >=", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidLessThan(String value) {
            addCriterion("imgid <", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidLessThanOrEqualTo(String value) {
            addCriterion("imgid <=", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidLike(String value) {
            addCriterion("imgid like", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidNotLike(String value) {
            addCriterion("imgid not like", value, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidIn(List<String> values) {
            addCriterion("imgid in", values, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidNotIn(List<String> values) {
            addCriterion("imgid not in", values, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidBetween(String value1, String value2) {
            addCriterion("imgid between", value1, value2, "imgid");
            return (Criteria) this;
        }

        public Criteria andImgidNotBetween(String value1, String value2) {
            addCriterion("imgid not between", value1, value2, "imgid");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("msg not between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andPetUseridIsNull() {
            addCriterion("pet_userid is null");
            return (Criteria) this;
        }

        public Criteria andPetUseridIsNotNull() {
            addCriterion("pet_userid is not null");
            return (Criteria) this;
        }

        public Criteria andPetUseridEqualTo(Long value) {
            addCriterion("pet_userid =", value, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridNotEqualTo(Long value) {
            addCriterion("pet_userid <>", value, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridGreaterThan(Long value) {
            addCriterion("pet_userid >", value, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridGreaterThanOrEqualTo(Long value) {
            addCriterion("pet_userid >=", value, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridLessThan(Long value) {
            addCriterion("pet_userid <", value, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridLessThanOrEqualTo(Long value) {
            addCriterion("pet_userid <=", value, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridIn(List<Long> values) {
            addCriterion("pet_userid in", values, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridNotIn(List<Long> values) {
            addCriterion("pet_userid not in", values, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridBetween(Long value1, Long value2) {
            addCriterion("pet_userid between", value1, value2, "petUserid");
            return (Criteria) this;
        }

        public Criteria andPetUseridNotBetween(Long value1, Long value2) {
            addCriterion("pet_userid not between", value1, value2, "petUserid");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNull() {
            addCriterion("submit_time is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(Date value) {
            addCriterion("submit_time =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(Date value) {
            addCriterion("submit_time <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(Date value) {
            addCriterion("submit_time >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("submit_time >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(Date value) {
            addCriterion("submit_time <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("submit_time <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<Date> values) {
            addCriterion("submit_time in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<Date> values) {
            addCriterion("submit_time not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("submit_time between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("submit_time not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgIsNull() {
            addCriterion("transmit_msg is null");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgIsNotNull() {
            addCriterion("transmit_msg is not null");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgEqualTo(String value) {
            addCriterion("transmit_msg =", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgNotEqualTo(String value) {
            addCriterion("transmit_msg <>", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgGreaterThan(String value) {
            addCriterion("transmit_msg >", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgGreaterThanOrEqualTo(String value) {
            addCriterion("transmit_msg >=", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgLessThan(String value) {
            addCriterion("transmit_msg <", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgLessThanOrEqualTo(String value) {
            addCriterion("transmit_msg <=", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgLike(String value) {
            addCriterion("transmit_msg like", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgNotLike(String value) {
            addCriterion("transmit_msg not like", value, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgIn(List<String> values) {
            addCriterion("transmit_msg in", values, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgNotIn(List<String> values) {
            addCriterion("transmit_msg not in", values, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgBetween(String value1, String value2) {
            addCriterion("transmit_msg between", value1, value2, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitMsgNotBetween(String value1, String value2) {
            addCriterion("transmit_msg not between", value1, value2, "transmitMsg");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlIsNull() {
            addCriterion("transmit_url is null");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlIsNotNull() {
            addCriterion("transmit_url is not null");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlEqualTo(String value) {
            addCriterion("transmit_url =", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlNotEqualTo(String value) {
            addCriterion("transmit_url <>", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlGreaterThan(String value) {
            addCriterion("transmit_url >", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlGreaterThanOrEqualTo(String value) {
            addCriterion("transmit_url >=", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlLessThan(String value) {
            addCriterion("transmit_url <", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlLessThanOrEqualTo(String value) {
            addCriterion("transmit_url <=", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlLike(String value) {
            addCriterion("transmit_url like", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlNotLike(String value) {
            addCriterion("transmit_url not like", value, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlIn(List<String> values) {
            addCriterion("transmit_url in", values, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlNotIn(List<String> values) {
            addCriterion("transmit_url not in", values, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlBetween(String value1, String value2) {
            addCriterion("transmit_url between", value1, value2, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andTransmitUrlNotBetween(String value1, String value2) {
            addCriterion("transmit_url not between", value1, value2, "transmitUrl");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNull() {
            addCriterion("latitude is null");
            return (Criteria) this;
        }

        public Criteria andLatitudeIsNotNull() {
            addCriterion("latitude is not null");
            return (Criteria) this;
        }

        public Criteria andLatitudeEqualTo(Double value) {
            addCriterion("latitude =", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotEqualTo(Double value) {
            addCriterion("latitude <>", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThan(Double value) {
            addCriterion("latitude >", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("latitude >=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThan(Double value) {
            addCriterion("latitude <", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeLessThanOrEqualTo(Double value) {
            addCriterion("latitude <=", value, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeIn(List<Double> values) {
            addCriterion("latitude in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotIn(List<Double> values) {
            addCriterion("latitude not in", values, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeBetween(Double value1, Double value2) {
            addCriterion("latitude between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLatitudeNotBetween(Double value1, Double value2) {
            addCriterion("latitude not between", value1, value2, "latitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(Double value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(Double value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(Double value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(Double value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(Double value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(Double value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<Double> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<Double> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(Double value1, Double value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(Double value1, Double value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andStateTypeIsNull() {
            addCriterion("state_type is null");
            return (Criteria) this;
        }

        public Criteria andStateTypeIsNotNull() {
            addCriterion("state_type is not null");
            return (Criteria) this;
        }

        public Criteria andStateTypeEqualTo(String value) {
            addCriterion("state_type =", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotEqualTo(String value) {
            addCriterion("state_type <>", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeGreaterThan(String value) {
            addCriterion("state_type >", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeGreaterThanOrEqualTo(String value) {
            addCriterion("state_type >=", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeLessThan(String value) {
            addCriterion("state_type <", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeLessThanOrEqualTo(String value) {
            addCriterion("state_type <=", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeLike(String value) {
            addCriterion("state_type like", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotLike(String value) {
            addCriterion("state_type not like", value, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeIn(List<String> values) {
            addCriterion("state_type in", values, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotIn(List<String> values) {
            addCriterion("state_type not in", values, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeBetween(String value1, String value2) {
            addCriterion("state_type between", value1, value2, "stateType");
            return (Criteria) this;
        }

        public Criteria andStateTypeNotBetween(String value1, String value2) {
            addCriterion("state_type not between", value1, value2, "stateType");
            return (Criteria) this;
        }

        public Criteria andReportTimesIsNull() {
            addCriterion("report_times is null");
            return (Criteria) this;
        }

        public Criteria andReportTimesIsNotNull() {
            addCriterion("report_times is not null");
            return (Criteria) this;
        }

        public Criteria andReportTimesEqualTo(Integer value) {
            addCriterion("report_times =", value, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesNotEqualTo(Integer value) {
            addCriterion("report_times <>", value, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesGreaterThan(Integer value) {
            addCriterion("report_times >", value, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("report_times >=", value, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesLessThan(Integer value) {
            addCriterion("report_times <", value, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesLessThanOrEqualTo(Integer value) {
            addCriterion("report_times <=", value, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesIn(List<Integer> values) {
            addCriterion("report_times in", values, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesNotIn(List<Integer> values) {
            addCriterion("report_times not in", values, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesBetween(Integer value1, Integer value2) {
            addCriterion("report_times between", value1, value2, "reportTimes");
            return (Criteria) this;
        }

        public Criteria andReportTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("report_times not between", value1, value2, "reportTimes");
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