package com.github.blovemaple.hura.dal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgrametoQueryLogExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public ProgrametoQueryLogExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
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
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table programeto_query_log
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

		public Criteria andQueryIsNull() {
			addCriterion("query is null");
			return (Criteria) this;
		}

		public Criteria andQueryIsNotNull() {
			addCriterion("query is not null");
			return (Criteria) this;
		}

		public Criteria andQueryEqualTo(String value) {
			addCriterion("query =", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryNotEqualTo(String value) {
			addCriterion("query <>", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryGreaterThan(String value) {
			addCriterion("query >", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryGreaterThanOrEqualTo(String value) {
			addCriterion("query >=", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryLessThan(String value) {
			addCriterion("query <", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryLessThanOrEqualTo(String value) {
			addCriterion("query <=", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryLike(String value) {
			addCriterion("query like", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryNotLike(String value) {
			addCriterion("query not like", value, "query");
			return (Criteria) this;
		}

		public Criteria andQueryIn(List<String> values) {
			addCriterion("query in", values, "query");
			return (Criteria) this;
		}

		public Criteria andQueryNotIn(List<String> values) {
			addCriterion("query not in", values, "query");
			return (Criteria) this;
		}

		public Criteria andQueryBetween(String value1, String value2) {
			addCriterion("query between", value1, value2, "query");
			return (Criteria) this;
		}

		public Criteria andQueryNotBetween(String value1, String value2) {
			addCriterion("query not between", value1, value2, "query");
			return (Criteria) this;
		}

		public Criteria andSectionKeyIsNull() {
			addCriterion("section_key is null");
			return (Criteria) this;
		}

		public Criteria andSectionKeyIsNotNull() {
			addCriterion("section_key is not null");
			return (Criteria) this;
		}

		public Criteria andSectionKeyEqualTo(String value) {
			addCriterion("section_key =", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyNotEqualTo(String value) {
			addCriterion("section_key <>", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyGreaterThan(String value) {
			addCriterion("section_key >", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyGreaterThanOrEqualTo(String value) {
			addCriterion("section_key >=", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyLessThan(String value) {
			addCriterion("section_key <", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyLessThanOrEqualTo(String value) {
			addCriterion("section_key <=", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyLike(String value) {
			addCriterion("section_key like", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyNotLike(String value) {
			addCriterion("section_key not like", value, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyIn(List<String> values) {
			addCriterion("section_key in", values, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyNotIn(List<String> values) {
			addCriterion("section_key not in", values, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyBetween(String value1, String value2) {
			addCriterion("section_key between", value1, value2, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andSectionKeyNotBetween(String value1, String value2) {
			addCriterion("section_key not between", value1, value2, "sectionKey");
			return (Criteria) this;
		}

		public Criteria andHasResultIsNull() {
			addCriterion("has_result is null");
			return (Criteria) this;
		}

		public Criteria andHasResultIsNotNull() {
			addCriterion("has_result is not null");
			return (Criteria) this;
		}

		public Criteria andHasResultEqualTo(Boolean value) {
			addCriterion("has_result =", value, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultNotEqualTo(Boolean value) {
			addCriterion("has_result <>", value, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultGreaterThan(Boolean value) {
			addCriterion("has_result >", value, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultGreaterThanOrEqualTo(Boolean value) {
			addCriterion("has_result >=", value, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultLessThan(Boolean value) {
			addCriterion("has_result <", value, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultLessThanOrEqualTo(Boolean value) {
			addCriterion("has_result <=", value, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultIn(List<Boolean> values) {
			addCriterion("has_result in", values, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultNotIn(List<Boolean> values) {
			addCriterion("has_result not in", values, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultBetween(Boolean value1, Boolean value2) {
			addCriterion("has_result between", value1, value2, "hasResult");
			return (Criteria) this;
		}

		public Criteria andHasResultNotBetween(Boolean value1, Boolean value2) {
			addCriterion("has_result not between", value1, value2, "hasResult");
			return (Criteria) this;
		}

		public Criteria andResultIsNull() {
			addCriterion("result is null");
			return (Criteria) this;
		}

		public Criteria andResultIsNotNull() {
			addCriterion("result is not null");
			return (Criteria) this;
		}

		public Criteria andResultEqualTo(String value) {
			addCriterion("result =", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultNotEqualTo(String value) {
			addCriterion("result <>", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultGreaterThan(String value) {
			addCriterion("result >", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultGreaterThanOrEqualTo(String value) {
			addCriterion("result >=", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultLessThan(String value) {
			addCriterion("result <", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultLessThanOrEqualTo(String value) {
			addCriterion("result <=", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultLike(String value) {
			addCriterion("result like", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultNotLike(String value) {
			addCriterion("result not like", value, "result");
			return (Criteria) this;
		}

		public Criteria andResultIn(List<String> values) {
			addCriterion("result in", values, "result");
			return (Criteria) this;
		}

		public Criteria andResultNotIn(List<String> values) {
			addCriterion("result not in", values, "result");
			return (Criteria) this;
		}

		public Criteria andResultBetween(String value1, String value2) {
			addCriterion("result between", value1, value2, "result");
			return (Criteria) this;
		}

		public Criteria andResultNotBetween(String value1, String value2) {
			addCriterion("result not between", value1, value2, "result");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table programeto_query_log
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
     * This class corresponds to the database table programeto_query_log
     *
     * @mbg.generated do_not_delete_during_merge Sat Dec 22 01:25:24 CST 2018
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}