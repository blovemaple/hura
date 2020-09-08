package com.github.blovemaple.hura.dal;

import static com.github.blovemaple.hura.dal.ProgrametoLoginLogDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.github.blovemaple.hura.dal.ProgrametoLoginLog;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

@Mapper
public interface ProgrametoLoginLogMapper {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	BasicColumn[] selectList = BasicColumn.columnList(id, time, cost, code, openid, unionid);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "record.id", before = false, resultType = Long.class)
	int insert(InsertStatementProvider<ProgrametoLoginLog> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("ProgrametoLoginLogResult")
	Optional<ProgrametoLoginLog> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "ProgrametoLoginLogResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "cost", property = "cost", jdbcType = JdbcType.INTEGER),
			@Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "unionid", property = "unionid", jdbcType = JdbcType.VARCHAR) })
	List<ProgrametoLoginLog> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, programetoLoginLog, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, programetoLoginLog, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int deleteByPrimaryKey(Long id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int insert(ProgrametoLoginLog record) {
		return MyBatis3Utils.insert(this::insert, record, programetoLoginLog,
				c -> c.map(time).toProperty("time").map(cost).toProperty("cost").map(code).toProperty("code")
						.map(openid).toProperty("openid").map(unionid).toProperty("unionid"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int insertSelective(ProgrametoLoginLog record) {
		return MyBatis3Utils.insert(this::insert, record, programetoLoginLog,
				c -> c.map(time).toPropertyWhenPresent("time", record::getTime).map(cost)
						.toPropertyWhenPresent("cost", record::getCost).map(code)
						.toPropertyWhenPresent("code", record::getCode).map(openid)
						.toPropertyWhenPresent("openid", record::getOpenid).map(unionid)
						.toPropertyWhenPresent("unionid", record::getUnionid));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default Optional<ProgrametoLoginLog> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, programetoLoginLog, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default List<ProgrametoLoginLog> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, programetoLoginLog, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default List<ProgrametoLoginLog> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, programetoLoginLog, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default Optional<ProgrametoLoginLog> selectByPrimaryKey(Long id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, programetoLoginLog, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	static UpdateDSL<UpdateModel> updateAllColumns(ProgrametoLoginLog record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(time).equalTo(record::getTime).set(cost).equalTo(record::getCost).set(code)
				.equalTo(record::getCode).set(openid).equalTo(record::getOpenid).set(unionid)
				.equalTo(record::getUnionid);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(ProgrametoLoginLog record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(time).equalToWhenPresent(record::getTime).set(cost).equalToWhenPresent(record::getCost).set(code)
				.equalToWhenPresent(record::getCode).set(openid).equalToWhenPresent(record::getOpenid).set(unionid)
				.equalToWhenPresent(record::getUnionid);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int updateByPrimaryKey(ProgrametoLoginLog record) {
		return update(c -> c.set(time).equalTo(record::getTime).set(cost).equalTo(record::getCost).set(code)
				.equalTo(record::getCode).set(openid).equalTo(record::getOpenid).set(unionid)
				.equalTo(record::getUnionid).where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: programeto_login_log")
	default int updateByPrimaryKeySelective(ProgrametoLoginLog record) {
		return update(c -> c.set(time).equalToWhenPresent(record::getTime).set(cost).equalToWhenPresent(record::getCost)
				.set(code).equalToWhenPresent(record::getCode).set(openid).equalToWhenPresent(record::getOpenid)
				.set(unionid).equalToWhenPresent(record::getUnionid).where(id, isEqualTo(record::getId)));
	}
}