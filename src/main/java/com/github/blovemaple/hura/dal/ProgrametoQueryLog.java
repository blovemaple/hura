package com.github.blovemaple.hura.dal;

import java.util.Date;
import javax.annotation.Generated;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table programeto_query_log
 */
public class ProgrametoQueryLog {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.time")
    private Date time;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.cost")
    private Integer cost;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.openid")
    private String openid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.unionid")
    private String unionid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.query")
    private String query;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.section_key")
    private String sectionKey;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.is_detail")
    private Boolean isDetail;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.has_result")
    private Boolean hasResult;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.result")
    private String result;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.time")
    public Date getTime() {
        return time;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.time")
    public void setTime(Date time) {
        this.time = time;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.cost")
    public Integer getCost() {
        return cost;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.cost")
    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.openid")
    public String getOpenid() {
        return openid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.openid")
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.unionid")
    public String getUnionid() {
        return unionid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.unionid")
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.query")
    public String getQuery() {
        return query;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.query")
    public void setQuery(String query) {
        this.query = query;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.section_key")
    public String getSectionKey() {
        return sectionKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.section_key")
    public void setSectionKey(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.is_detail")
    public Boolean getIsDetail() {
        return isDetail;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.is_detail")
    public void setIsDetail(Boolean isDetail) {
        this.isDetail = isDetail;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.has_result")
    public Boolean getHasResult() {
        return hasResult;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.has_result")
    public void setHasResult(Boolean hasResult) {
        this.hasResult = hasResult;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.result")
    public String getResult() {
        return result;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: programeto_query_log.result")
    public void setResult(String result) {
        this.result = result;
    }
}