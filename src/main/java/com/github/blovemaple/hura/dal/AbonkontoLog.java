package com.github.blovemaple.hura.dal;

import java.util.Date;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table abonkonto_log
 */
public class AbonkontoLog {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.id
	 * @mbg.generated
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.time
	 * @mbg.generated
	 */
	private Date time;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.status
	 * @mbg.generated
	 */
	private String status;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.cost
	 * @mbg.generated
	 */
	private Integer cost;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.openid
	 * @mbg.generated
	 */
	private String openid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.unionid
	 * @mbg.generated
	 */
	private String unionid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.msg_type
	 * @mbg.generated
	 */
	private String msgType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.request
	 * @mbg.generated
	 */
	private String request;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column abonkonto_log.response
	 * @mbg.generated
	 */
	private String response;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.id
	 * @return  the value of abonkonto_log.id
	 * @mbg.generated
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.id
	 * @param id  the value for abonkonto_log.id
	 * @mbg.generated
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.time
	 * @return  the value of abonkonto_log.time
	 * @mbg.generated
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.time
	 * @param time  the value for abonkonto_log.time
	 * @mbg.generated
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.status
	 * @return  the value of abonkonto_log.status
	 * @mbg.generated
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.status
	 * @param status  the value for abonkonto_log.status
	 * @mbg.generated
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.cost
	 * @return  the value of abonkonto_log.cost
	 * @mbg.generated
	 */
	public Integer getCost() {
		return cost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.cost
	 * @param cost  the value for abonkonto_log.cost
	 * @mbg.generated
	 */
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.openid
	 * @return  the value of abonkonto_log.openid
	 * @mbg.generated
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.openid
	 * @param openid  the value for abonkonto_log.openid
	 * @mbg.generated
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.unionid
	 * @return  the value of abonkonto_log.unionid
	 * @mbg.generated
	 */
	public String getUnionid() {
		return unionid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.unionid
	 * @param unionid  the value for abonkonto_log.unionid
	 * @mbg.generated
	 */
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.msg_type
	 * @return  the value of abonkonto_log.msg_type
	 * @mbg.generated
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.msg_type
	 * @param msgType  the value for abonkonto_log.msg_type
	 * @mbg.generated
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.request
	 * @return  the value of abonkonto_log.request
	 * @mbg.generated
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.request
	 * @param request  the value for abonkonto_log.request
	 * @mbg.generated
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column abonkonto_log.response
	 * @return  the value of abonkonto_log.response
	 * @mbg.generated
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column abonkonto_log.response
	 * @param response  the value for abonkonto_log.response
	 * @mbg.generated
	 */
	public void setResponse(String response) {
		this.response = response;
	}
}