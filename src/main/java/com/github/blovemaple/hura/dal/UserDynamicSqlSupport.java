package com.github.blovemaple.hura.dal;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class UserDynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: user")
    public static final User user = new User();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.id")
    public static final SqlColumn<Long> id = user.id;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.openid")
    public static final SqlColumn<String> openid = user.openid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.unionid")
    public static final SqlColumn<String> unionid = user.unionid;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.nick_name")
    public static final SqlColumn<String> nickName = user.nickName;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.avatar_url")
    public static final SqlColumn<String> avatarUrl = user.avatarUrl;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.gender")
    public static final SqlColumn<Integer> gender = user.gender;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.country")
    public static final SqlColumn<String> country = user.country;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.province")
    public static final SqlColumn<String> province = user.province;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.city")
    public static final SqlColumn<String> city = user.city;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.language")
    public static final SqlColumn<String> language = user.language;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: user.addtime")
    public static final SqlColumn<Date> addtime = user.addtime;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: user")
    public static final class User extends SqlTable {
        public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);

        public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);

        public final SqlColumn<String> unionid = column("unionid", JDBCType.VARCHAR);

        public final SqlColumn<String> nickName = column("nick_name", JDBCType.VARCHAR);

        public final SqlColumn<String> avatarUrl = column("avatar_url", JDBCType.VARCHAR);

        public final SqlColumn<Integer> gender = column("gender", JDBCType.TINYINT);

        public final SqlColumn<String> country = column("country", JDBCType.VARCHAR);

        public final SqlColumn<String> province = column("province", JDBCType.VARCHAR);

        public final SqlColumn<String> city = column("city", JDBCType.VARCHAR);

        public final SqlColumn<String> language = column("language", JDBCType.VARCHAR);

        public final SqlColumn<Date> addtime = column("addtime", JDBCType.TIMESTAMP);

        public User() {
            super("user");
        }
    }
}