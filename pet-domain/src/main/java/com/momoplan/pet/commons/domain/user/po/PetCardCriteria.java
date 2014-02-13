package com.momoplan.pet.commons.domain.user.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* PetCardCriteria 条件查询类.
* 
* @author liangc [cc14514@icloud.com]
* @version v1.0
* @copy pet
* @date 2014-02-13 14:16:41
*/
public class PetCardCriteria {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer mysqlOffset;

    protected Integer mysqlLength;

    public PetCardCriteria() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPetTypeIsNull() {
            addCriterion("pet_type is null");
            return (Criteria) this;
        }

        public Criteria andPetTypeIsNotNull() {
            addCriterion("pet_type is not null");
            return (Criteria) this;
        }

        public Criteria andPetTypeEqualTo(String value) {
            addCriterion("pet_type =", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeNotEqualTo(String value) {
            addCriterion("pet_type <>", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeGreaterThan(String value) {
            addCriterion("pet_type >", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pet_type >=", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeLessThan(String value) {
            addCriterion("pet_type <", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeLessThanOrEqualTo(String value) {
            addCriterion("pet_type <=", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeLike(String value) {
            addCriterion("pet_type like", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeNotLike(String value) {
            addCriterion("pet_type not like", value, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeIn(List<String> values) {
            addCriterion("pet_type in", values, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeNotIn(List<String> values) {
            addCriterion("pet_type not in", values, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeBetween(String value1, String value2) {
            addCriterion("pet_type between", value1, value2, "petType");
            return (Criteria) this;
        }

        public Criteria andPetTypeNotBetween(String value1, String value2) {
            addCriterion("pet_type not between", value1, value2, "petType");
            return (Criteria) this;
        }

        public Criteria andPetNicknameIsNull() {
            addCriterion("pet_nickname is null");
            return (Criteria) this;
        }

        public Criteria andPetNicknameIsNotNull() {
            addCriterion("pet_nickname is not null");
            return (Criteria) this;
        }

        public Criteria andPetNicknameEqualTo(String value) {
            addCriterion("pet_nickname =", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameNotEqualTo(String value) {
            addCriterion("pet_nickname <>", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameGreaterThan(String value) {
            addCriterion("pet_nickname >", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameGreaterThanOrEqualTo(String value) {
            addCriterion("pet_nickname >=", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameLessThan(String value) {
            addCriterion("pet_nickname <", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameLessThanOrEqualTo(String value) {
            addCriterion("pet_nickname <=", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameLike(String value) {
            addCriterion("pet_nickname like", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameNotLike(String value) {
            addCriterion("pet_nickname not like", value, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameIn(List<String> values) {
            addCriterion("pet_nickname in", values, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameNotIn(List<String> values) {
            addCriterion("pet_nickname not in", values, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameBetween(String value1, String value2) {
            addCriterion("pet_nickname between", value1, value2, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetNicknameNotBetween(String value1, String value2) {
            addCriterion("pet_nickname not between", value1, value2, "petNickname");
            return (Criteria) this;
        }

        public Criteria andPetOwnerIsNull() {
            addCriterion("pet_owner is null");
            return (Criteria) this;
        }

        public Criteria andPetOwnerIsNotNull() {
            addCriterion("pet_owner is not null");
            return (Criteria) this;
        }

        public Criteria andPetOwnerEqualTo(String value) {
            addCriterion("pet_owner =", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerNotEqualTo(String value) {
            addCriterion("pet_owner <>", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerGreaterThan(String value) {
            addCriterion("pet_owner >", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("pet_owner >=", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerLessThan(String value) {
            addCriterion("pet_owner <", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerLessThanOrEqualTo(String value) {
            addCriterion("pet_owner <=", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerLike(String value) {
            addCriterion("pet_owner like", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerNotLike(String value) {
            addCriterion("pet_owner not like", value, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerIn(List<String> values) {
            addCriterion("pet_owner in", values, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerNotIn(List<String> values) {
            addCriterion("pet_owner not in", values, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerBetween(String value1, String value2) {
            addCriterion("pet_owner between", value1, value2, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerNotBetween(String value1, String value2) {
            addCriterion("pet_owner not between", value1, value2, "petOwner");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelIsNull() {
            addCriterion("pet_owner_tel is null");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelIsNotNull() {
            addCriterion("pet_owner_tel is not null");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelEqualTo(String value) {
            addCriterion("pet_owner_tel =", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelNotEqualTo(String value) {
            addCriterion("pet_owner_tel <>", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelGreaterThan(String value) {
            addCriterion("pet_owner_tel >", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelGreaterThanOrEqualTo(String value) {
            addCriterion("pet_owner_tel >=", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelLessThan(String value) {
            addCriterion("pet_owner_tel <", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelLessThanOrEqualTo(String value) {
            addCriterion("pet_owner_tel <=", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelLike(String value) {
            addCriterion("pet_owner_tel like", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelNotLike(String value) {
            addCriterion("pet_owner_tel not like", value, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelIn(List<String> values) {
            addCriterion("pet_owner_tel in", values, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelNotIn(List<String> values) {
            addCriterion("pet_owner_tel not in", values, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelBetween(String value1, String value2) {
            addCriterion("pet_owner_tel between", value1, value2, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerTelNotBetween(String value1, String value2) {
            addCriterion("pet_owner_tel not between", value1, value2, "petOwnerTel");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgIsNull() {
            addCriterion("pet_owner_msg is null");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgIsNotNull() {
            addCriterion("pet_owner_msg is not null");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgEqualTo(String value) {
            addCriterion("pet_owner_msg =", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgNotEqualTo(String value) {
            addCriterion("pet_owner_msg <>", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgGreaterThan(String value) {
            addCriterion("pet_owner_msg >", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgGreaterThanOrEqualTo(String value) {
            addCriterion("pet_owner_msg >=", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgLessThan(String value) {
            addCriterion("pet_owner_msg <", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgLessThanOrEqualTo(String value) {
            addCriterion("pet_owner_msg <=", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgLike(String value) {
            addCriterion("pet_owner_msg like", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgNotLike(String value) {
            addCriterion("pet_owner_msg not like", value, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgIn(List<String> values) {
            addCriterion("pet_owner_msg in", values, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgNotIn(List<String> values) {
            addCriterion("pet_owner_msg not in", values, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgBetween(String value1, String value2) {
            addCriterion("pet_owner_msg between", value1, value2, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetOwnerMsgNotBetween(String value1, String value2) {
            addCriterion("pet_owner_msg not between", value1, value2, "petOwnerMsg");
            return (Criteria) this;
        }

        public Criteria andPetImgIsNull() {
            addCriterion("pet_img is null");
            return (Criteria) this;
        }

        public Criteria andPetImgIsNotNull() {
            addCriterion("pet_img is not null");
            return (Criteria) this;
        }

        public Criteria andPetImgEqualTo(String value) {
            addCriterion("pet_img =", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgNotEqualTo(String value) {
            addCriterion("pet_img <>", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgGreaterThan(String value) {
            addCriterion("pet_img >", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgGreaterThanOrEqualTo(String value) {
            addCriterion("pet_img >=", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgLessThan(String value) {
            addCriterion("pet_img <", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgLessThanOrEqualTo(String value) {
            addCriterion("pet_img <=", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgLike(String value) {
            addCriterion("pet_img like", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgNotLike(String value) {
            addCriterion("pet_img not like", value, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgIn(List<String> values) {
            addCriterion("pet_img in", values, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgNotIn(List<String> values) {
            addCriterion("pet_img not in", values, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgBetween(String value1, String value2) {
            addCriterion("pet_img between", value1, value2, "petImg");
            return (Criteria) this;
        }

        public Criteria andPetImgNotBetween(String value1, String value2) {
            addCriterion("pet_img not between", value1, value2, "petImg");
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

        public Criteria andEtIsNull() {
            addCriterion("et is null");
            return (Criteria) this;
        }

        public Criteria andEtIsNotNull() {
            addCriterion("et is not null");
            return (Criteria) this;
        }

        public Criteria andEtEqualTo(Date value) {
            addCriterion("et =", value, "et");
            return (Criteria) this;
        }

        public Criteria andEtNotEqualTo(Date value) {
            addCriterion("et <>", value, "et");
            return (Criteria) this;
        }

        public Criteria andEtGreaterThan(Date value) {
            addCriterion("et >", value, "et");
            return (Criteria) this;
        }

        public Criteria andEtGreaterThanOrEqualTo(Date value) {
            addCriterion("et >=", value, "et");
            return (Criteria) this;
        }

        public Criteria andEtLessThan(Date value) {
            addCriterion("et <", value, "et");
            return (Criteria) this;
        }

        public Criteria andEtLessThanOrEqualTo(Date value) {
            addCriterion("et <=", value, "et");
            return (Criteria) this;
        }

        public Criteria andEtIn(List<Date> values) {
            addCriterion("et in", values, "et");
            return (Criteria) this;
        }

        public Criteria andEtNotIn(List<Date> values) {
            addCriterion("et not in", values, "et");
            return (Criteria) this;
        }

        public Criteria andEtBetween(Date value1, Date value2) {
            addCriterion("et between", value1, value2, "et");
            return (Criteria) this;
        }

        public Criteria andEtNotBetween(Date value1, Date value2) {
            addCriterion("et not between", value1, value2, "et");
            return (Criteria) this;
        }

        public Criteria andCbIsNull() {
            addCriterion("cb is null");
            return (Criteria) this;
        }

        public Criteria andCbIsNotNull() {
            addCriterion("cb is not null");
            return (Criteria) this;
        }

        public Criteria andCbEqualTo(String value) {
            addCriterion("cb =", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotEqualTo(String value) {
            addCriterion("cb <>", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbGreaterThan(String value) {
            addCriterion("cb >", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbGreaterThanOrEqualTo(String value) {
            addCriterion("cb >=", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbLessThan(String value) {
            addCriterion("cb <", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbLessThanOrEqualTo(String value) {
            addCriterion("cb <=", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbLike(String value) {
            addCriterion("cb like", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotLike(String value) {
            addCriterion("cb not like", value, "cb");
            return (Criteria) this;
        }

        public Criteria andCbIn(List<String> values) {
            addCriterion("cb in", values, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotIn(List<String> values) {
            addCriterion("cb not in", values, "cb");
            return (Criteria) this;
        }

        public Criteria andCbBetween(String value1, String value2) {
            addCriterion("cb between", value1, value2, "cb");
            return (Criteria) this;
        }

        public Criteria andCbNotBetween(String value1, String value2) {
            addCriterion("cb not between", value1, value2, "cb");
            return (Criteria) this;
        }

        public Criteria andEbIsNull() {
            addCriterion("eb is null");
            return (Criteria) this;
        }

        public Criteria andEbIsNotNull() {
            addCriterion("eb is not null");
            return (Criteria) this;
        }

        public Criteria andEbEqualTo(String value) {
            addCriterion("eb =", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbNotEqualTo(String value) {
            addCriterion("eb <>", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbGreaterThan(String value) {
            addCriterion("eb >", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbGreaterThanOrEqualTo(String value) {
            addCriterion("eb >=", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbLessThan(String value) {
            addCriterion("eb <", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbLessThanOrEqualTo(String value) {
            addCriterion("eb <=", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbLike(String value) {
            addCriterion("eb like", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbNotLike(String value) {
            addCriterion("eb not like", value, "eb");
            return (Criteria) this;
        }

        public Criteria andEbIn(List<String> values) {
            addCriterion("eb in", values, "eb");
            return (Criteria) this;
        }

        public Criteria andEbNotIn(List<String> values) {
            addCriterion("eb not in", values, "eb");
            return (Criteria) this;
        }

        public Criteria andEbBetween(String value1, String value2) {
            addCriterion("eb between", value1, value2, "eb");
            return (Criteria) this;
        }

        public Criteria andEbNotBetween(String value1, String value2) {
            addCriterion("eb not between", value1, value2, "eb");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
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