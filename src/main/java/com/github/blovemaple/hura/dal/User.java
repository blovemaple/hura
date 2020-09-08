package com.github.blovemaple.hura.dal;

import java.util.Date;
import javax.annotation.Generated;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table user
 */
public class User {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.id")
    private Long id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.openid")
    private String openid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.unionid")
    private String unionid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.nick_name")
    private String nickName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.avatar_url")
    private String avatarUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.gender")
    private Integer gender;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.country")
    private String country;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.province")
    private String province;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.city")
    private String city;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.language")
    private String language;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.addtime")
    private Date addtime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.id")
    public Long getId() {
        return id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.id")
    public void setId(Long id) {
        this.id = id;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.openid")
    public String getOpenid() {
        return openid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.openid")
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.unionid")
    public String getUnionid() {
        return unionid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.unionid")
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.nick_name")
    public String getNickName() {
        return nickName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.nick_name")
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.avatar_url")
    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.avatar_url")
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.gender")
    public Integer getGender() {
        return gender;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.gender")
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.country")
    public String getCountry() {
        return country;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.country")
    public void setCountry(String country) {
        this.country = country;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.province")
    public String getProvince() {
        return province;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.province")
    public void setProvince(String province) {
        this.province = province;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.city")
    public String getCity() {
        return city;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.city")
    public void setCity(String city) {
        this.city = city;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.language")
    public String getLanguage() {
        return language;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.addtime")
    public Date getAddtime() {
        return addtime;
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.addtime")
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}