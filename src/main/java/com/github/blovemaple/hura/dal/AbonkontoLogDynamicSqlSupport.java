package com.github.blovemaple.hura.dal;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class AbonkontoLogDynamicSqlSupport {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: abonkonto_log")
	public static final AbonkontoLog abonkontoLog = new AbonkontoLog();
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.id")
	public static final SqlColumn<Long> id = abonkontoLog.id;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.time")
	public static final SqlColumn<Date> time = abonkontoLog.time;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.status")
	public static final SqlColumn<String> status = abonkontoLog.status;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.cost")
	public static final SqlColumn<Integer> cost = abonkontoLog.cost;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.openid")
	public static final SqlColumn<String> openid = abonkontoLog.openid;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.unionid")
	public static final SqlColumn<String> unionid = abonkontoLog.unionid;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.msg_type")
	public static final SqlColumn<String> msgType = abonkontoLog.msgType;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.request")
	public static final SqlColumn<String> request = abonkontoLog.request;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: abonkonto_log.response")
	public static final SqlColumn<String> response = abonkontoLog.response;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: abonkonto_log")
	public static final class AbonkontoLog extends SqlTable {
		public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);
		public final SqlColumn<Date> time = column("time", JDBCType.TIMESTAMP);
		public final SqlColumn<String> status = column("status", JDBCType.VARCHAR);
		public final SqlColumn<Integer> cost = column("cost", JDBCType.INTEGER);
		public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);
		public final SqlColumn<String> unionid = column("unionid", JDBCType.VARCHAR);
		public final SqlColumn<String> msgType = column("msg_type", JDBCType.VARCHAR);
		public final SqlColumn<String> request = column("request", JDBCType.VARCHAR);
		public final SqlColumn<String> response = column("response", JDBCType.VARCHAR);

		public AbonkontoLog() {
			super("abonkonto_log");
		}
	}
}