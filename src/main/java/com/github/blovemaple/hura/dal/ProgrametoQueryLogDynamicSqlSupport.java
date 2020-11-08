package com.github.blovemaple.hura.dal;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class ProgrametoQueryLogDynamicSqlSupport {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_query_log")
	public static final ProgrametoQueryLog programetoQueryLog = new ProgrametoQueryLog();
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.id")
	public static final SqlColumn<Long> id = programetoQueryLog.id;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.time")
	public static final SqlColumn<Date> time = programetoQueryLog.time;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.cost")
	public static final SqlColumn<Integer> cost = programetoQueryLog.cost;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.openid")
	public static final SqlColumn<String> openid = programetoQueryLog.openid;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.unionid")
	public static final SqlColumn<String> unionid = programetoQueryLog.unionid;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.query")
	public static final SqlColumn<String> query = programetoQueryLog.query;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.section_key")
	public static final SqlColumn<String> sectionKey = programetoQueryLog.sectionKey;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.is_detail")
	public static final SqlColumn<Boolean> isDetail = programetoQueryLog.isDetail;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.has_result")
	public static final SqlColumn<Boolean> hasResult = programetoQueryLog.hasResult;
	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source field: programeto_query_log.result")
	public static final SqlColumn<String> result = programetoQueryLog.result;

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_query_log")
	public static final class ProgrametoQueryLog extends SqlTable {
		public final SqlColumn<Long> id = column("id", JDBCType.BIGINT);
		public final SqlColumn<Date> time = column("time", JDBCType.TIMESTAMP);
		public final SqlColumn<Integer> cost = column("cost", JDBCType.INTEGER);
		public final SqlColumn<String> openid = column("openid", JDBCType.VARCHAR);
		public final SqlColumn<String> unionid = column("unionid", JDBCType.VARCHAR);
		public final SqlColumn<String> query = column("query", JDBCType.VARCHAR);
		public final SqlColumn<String> sectionKey = column("section_key", JDBCType.VARCHAR);
		public final SqlColumn<Boolean> isDetail = column("is_detail", JDBCType.BIT);
		public final SqlColumn<Boolean> hasResult = column("has_result", JDBCType.BIT);
		public final SqlColumn<String> result = column("result", JDBCType.VARCHAR);

		public ProgrametoQueryLog() {
			super("programeto_query_log");
		}
	}
}