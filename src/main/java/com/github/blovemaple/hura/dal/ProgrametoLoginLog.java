package com.github.blovemaple.hura.dal;

import java.util.Date;
import javax.annotation.Generated;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table programeto_login_log
 */
public class ProgrametoLoginLog {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.id")
	private Long id;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.time")
	private Date time;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.cost")
	private Integer cost;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.code")
	private String code;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.openid")
	private String openid;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.unionid")
	private String unionid;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.id")
	public Long getId() {
		return id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.id")
	public void setId(Long id) {
		this.id = id;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.time")
	public Date getTime() {
		return time;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.time")
	public void setTime(Date time) {
		this.time = time;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.cost")
	public Integer getCost() {
		return cost;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.cost")
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.code")
	public String getCode() {
		return code;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.code")
	public void setCode(String code) {
		this.code = code;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.openid")
	public String getOpenid() {
		return openid;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.openid")
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.unionid")
	public String getUnionid() {
		return unionid;
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.unionid")
	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}
}