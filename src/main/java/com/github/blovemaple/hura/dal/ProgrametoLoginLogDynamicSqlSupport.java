package com.github.blovemaple.hura.dal;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ProgrametoLoginLogDynamicSqlSupport {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	public static final ProgrametoLoginLog programetoLoginLog = new ProgrametoLoginLog();
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.id")
	public static final SqlColumn<Long> id = programetoLoginLog.id;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.time")
	public static final SqlColumn<Date> time = programetoLoginLog.time;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.cost")
	public static final SqlColumn<Integer> cost = programetoLoginLog.cost;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.code")
	public static final SqlColumn<String> code = programetoLoginLog.code;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.openid")
	public static final SqlColumn<String> openid = programetoLoginLog.openid;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_login_log.unionid")
	public static final SqlColumn<String> unionid = programetoLoginLog.unionid;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	public static final class ProgrametoLoginLog extends SqlTable {
		public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);
		public final SqlColumn<Date> time = column("time", JDBCType.TIMESTAMP);
		public final SqlColumn<Integer> cost = column("cost", JDBCType.INTEGER);
		public final SqlColumn<String> code = column("code", JDBCType.VARCHAR);
		public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);
		public final SqlColumn<String> unionid = column("unionid", JDBCType.VARCHAR);

		public ProgrametoLoginLog() {
			super("programeto_login_log");
		}
	}
}