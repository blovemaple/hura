package com.github.blovemaple.hura.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AbonkontoLogExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public AbonkontoLogExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
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

		public Criteria andTimeIsNull() {
			addCriterion("time is null");
			return (Criteria) this;
		}

		public Criteria andTimeIsNotNull() {
			addCriterion("time is not null");
			return (Criteria) this;
		}

		public Criteria andTimeEqualTo(Date value) {
			addCriterion("time =", value, "time");
			return (Criteria) this;
		}

		public Criteria andTimeNotEqualTo(Date value) {
			addCriterion("time <>", value, "time");
			return (Criteria) this;
		}

		public Criteria andTimeGreaterThan(Date value) {
			addCriterion("time >", value, "time");
			return (Criteria) this;
		}

		public Criteria andTimeGreaterThanOrEqualTo(Date value) {
			addCriterion("time >=", value, "time");
			return (Criteria) this;
		}

		public Criteria andTimeLessThan(Date value) {
			addCriterion("time <", value, "time");
			return (Criteria) this;
		}

		public Criteria andTimeLessThanOrEqualTo(Date value) {
			addCriterion("time <=", value, "time");
			return (Criteria) this;
		}

		public Criteria andTimeIn(List<Date> values) {
			addCriterion("time in", values, "time");
			return (Criteria) this;
		}

		public Criteria andTimeNotIn(List<Date> values) {
			addCriterion("time not in", values, "time");
			return (Criteria) this;
		}

		public Criteria andTimeBetween(Date value1, Date value2) {
			addCriterion("time between", value1, value2, "time");
			return (Criteria) this;
		}

		public Criteria andTimeNotBetween(Date value1, Date value2) {
			addCriterion("time not between", value1, value2, "time");
			return (Criteria) this;
		}

		public Criteria andStatusIsNull() {
			addCriterion("status is null");
			return (Criteria) this;
		}

		public Criteria andStatusIsNotNull() {
			addCriterion("status is not null");
			return (Criteria) this;
		}

		public Criteria andStatusEqualTo(String value) {
			addCriterion("status =", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotEqualTo(String value) {
			addCriterion("status <>", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThan(String value) {
			addCriterion("status >", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusGreaterThanOrEqualTo(String value) {
			addCriterion("status >=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThan(String value) {
			addCriterion("status <", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLessThanOrEqualTo(String value) {
			addCriterion("status <=", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusLike(String value) {
			addCriterion("status like", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotLike(String value) {
			addCriterion("status not like", value, "status");
			return (Criteria) this;
		}

		public Criteria andStatusIn(List<String> values) {
			addCriterion("status in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotIn(List<String> values) {
			addCriterion("status not in", values, "status");
			return (Criteria) this;
		}

		public Criteria andStatusBetween(String value1, String value2) {
			addCriterion("status between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andStatusNotBetween(String value1, String value2) {
			addCriterion("status not between", value1, value2, "status");
			return (Criteria) this;
		}

		public Criteria andCostIsNull() {
			addCriterion("cost is null");
			return (Criteria) this;
		}

		public Criteria andCostIsNotNull() {
			addCriterion("cost is not null");
			return (Criteria) this;
		}

		public Criteria andCostEqualTo(Integer value) {
			addCriterion("cost =", value, "cost");
			return (Criteria) this;
		}

		public Criteria andCostNotEqualTo(Integer value) {
			addCriterion("cost <>", value, "cost");
			return (Criteria) this;
		}

		public Criteria andCostGreaterThan(Integer value) {
			addCriterion("cost >", value, "cost");
			return (Criteria) this;
		}

		public Criteria andCostGreaterThanOrEqualTo(Integer value) {
			addCriterion("cost >=", value, "cost");
			return (Criteria) this;
		}

		public Criteria andCostLessThan(Integer value) {
			addCriterion("cost <", value, "cost");
			return (Criteria) this;
		}

		public Criteria andCostLessThanOrEqualTo(Integer value) {
			addCriterion("cost <=", value, "cost");
			return (Criteria) this;
		}

		public Criteria andCostIn(List<Integer> values) {
			addCriterion("cost in", values, "cost");
			return (Criteria) this;
		}

		public Criteria andCostNotIn(List<Integer> values) {
			addCriterion("cost not in", values, "cost");
			return (Criteria) this;
		}

		public Criteria andCostBetween(Integer value1, Integer value2) {
			addCriterion("cost between", value1, value2, "cost");
			return (Criteria) this;
		}

		public Criteria andCostNotBetween(Integer value1, Integer value2) {
			addCriterion("cost not between", value1, value2, "cost");
			return (Criteria) this;
		}

		public Criteria andOpenidIsNull() {
			addCriterion("openid is null");
			return (Criteria) this;
		}

		public Criteria andOpenidIsNotNull() {
			addCriterion("openid is not null");
			return (Criteria) this;
		}

		public Criteria andOpenidEqualTo(String value) {
			addCriterion("openid =", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidNotEqualTo(String value) {
			addCriterion("openid <>", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidGreaterThan(String value) {
			addCriterion("openid >", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidGreaterThanOrEqualTo(String value) {
			addCriterion("openid >=", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidLessThan(String value) {
			addCriterion("openid <", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidLessThanOrEqualTo(String value) {
			addCriterion("openid <=", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidLike(String value) {
			addCriterion("openid like", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidNotLike(String value) {
			addCriterion("openid not like", value, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidIn(List<String> values) {
			addCriterion("openid in", values, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidNotIn(List<String> values) {
			addCriterion("openid not in", values, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidBetween(String value1, String value2) {
			addCriterion("openid between", value1, value2, "openid");
			return (Criteria) this;
		}

		public Criteria andOpenidNotBetween(String value1, String value2) {
			addCriterion("openid not between", value1, value2, "openid");
			return (Criteria) this;
		}

		public Criteria andUnionidIsNull() {
			addCriterion("unionid is null");
			return (Criteria) this;
		}

		public Criteria andUnionidIsNotNull() {
			addCriterion("unionid is not null");
			return (Criteria) this;
		}

		public Criteria andUnionidEqualTo(String value) {
			addCriterion("unionid =", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidNotEqualTo(String value) {
			addCriterion("unionid <>", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidGreaterThan(String value) {
			addCriterion("unionid >", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidGreaterThanOrEqualTo(String value) {
			addCriterion("unionid >=", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidLessThan(String value) {
			addCriterion("unionid <", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidLessThanOrEqualTo(String value) {
			addCriterion("unionid <=", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidLike(String value) {
			addCriterion("unionid like", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidNotLike(String value) {
			addCriterion("unionid not like", value, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidIn(List<String> values) {
			addCriterion("unionid in", values, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidNotIn(List<String> values) {
			addCriterion("unionid not in", values, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidBetween(String value1, String value2) {
			addCriterion("unionid between", value1, value2, "unionid");
			return (Criteria) this;
		}

		public Criteria andUnionidNotBetween(String value1, String value2) {
			addCriterion("unionid not between", value1, value2, "unionid");
			return (Criteria) this;
		}

		public Criteria andMsgTypeIsNull() {
			addCriterion("msg_type is null");
			return (Criteria) this;
		}

		public Criteria andMsgTypeIsNotNull() {
			addCriterion("msg_type is not null");
			return (Criteria) this;
		}

		public Criteria andMsgTypeEqualTo(String value) {
			addCriterion("msg_type =", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeNotEqualTo(String value) {
			addCriterion("msg_type <>", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeGreaterThan(String value) {
			addCriterion("msg_type >", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeGreaterThanOrEqualTo(String value) {
			addCriterion("msg_type >=", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeLessThan(String value) {
			addCriterion("msg_type <", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeLessThanOrEqualTo(String value) {
			addCriterion("msg_type <=", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeLike(String value) {
			addCriterion("msg_type like", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeNotLike(String value) {
			addCriterion("msg_type not like", value, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeIn(List<String> values) {
			addCriterion("msg_type in", values, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeNotIn(List<String> values) {
			addCriterion("msg_type not in", values, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeBetween(String value1, String value2) {
			addCriterion("msg_type between", value1, value2, "msgType");
			return (Criteria) this;
		}

		public Criteria andMsgTypeNotBetween(String value1, String value2) {
			addCriterion("msg_type not between", value1, value2, "msgType");
			return (Criteria) this;
		}

		public Criteria andRequestIsNull() {
			addCriterion("request is null");
			return (Criteria) this;
		}

		public Criteria andRequestIsNotNull() {
			addCriterion("request is not null");
			return (Criteria) this;
		}

		public Criteria andRequestEqualTo(String value) {
			addCriterion("request =", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestNotEqualTo(String value) {
			addCriterion("request <>", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestGreaterThan(String value) {
			addCriterion("request >", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestGreaterThanOrEqualTo(String value) {
			addCriterion("request >=", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestLessThan(String value) {
			addCriterion("request <", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestLessThanOrEqualTo(String value) {
			addCriterion("request <=", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestLike(String value) {
			addCriterion("request like", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestNotLike(String value) {
			addCriterion("request not like", value, "request");
			return (Criteria) this;
		}

		public Criteria andRequestIn(List<String> values) {
			addCriterion("request in", values, "request");
			return (Criteria) this;
		}

		public Criteria andRequestNotIn(List<String> values) {
			addCriterion("request not in", values, "request");
			return (Criteria) this;
		}

		public Criteria andRequestBetween(String value1, String value2) {
			addCriterion("request between", value1, value2, "request");
			return (Criteria) this;
		}

		public Criteria andRequestNotBetween(String value1, String value2) {
			addCriterion("request not between", value1, value2, "request");
			return (Criteria) this;
		}

		public Criteria andResponseIsNull() {
			addCriterion("response is null");
			return (Criteria) this;
		}

		public Criteria andResponseIsNotNull() {
			addCriterion("response is not null");
			return (Criteria) this;
		}

		public Criteria andResponseEqualTo(String value) {
			addCriterion("response =", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseNotEqualTo(String value) {
			addCriterion("response <>", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseGreaterThan(String value) {
			addCriterion("response >", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseGreaterThanOrEqualTo(String value) {
			addCriterion("response >=", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseLessThan(String value) {
			addCriterion("response <", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseLessThanOrEqualTo(String value) {
			addCriterion("response <=", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseLike(String value) {
			addCriterion("response like", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseNotLike(String value) {
			addCriterion("response not like", value, "response");
			return (Criteria) this;
		}

		public Criteria andResponseIn(List<String> values) {
			addCriterion("response in", values, "response");
			return (Criteria) this;
		}

		public Criteria andResponseNotIn(List<String> values) {
			addCriterion("response not in", values, "response");
			return (Criteria) this;
		}

		public Criteria andResponseBetween(String value1, String value2) {
			addCriterion("response between", value1, value2, "response");
			return (Criteria) this;
		}

		public Criteria andResponseNotBetween(String value1, String value2) {
			addCriterion("response not between", value1, value2, "response");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table abonkonto_log
	 * @mbg.generated
	 */
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table abonkonto_log
     *
     * @mbg.generated do_not_delete_during_merge Sat Dec 22 01:25:24 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}