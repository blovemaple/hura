package com.github.blovemaple.hura.dal;

import static com.github.blovemaple.hura.dal.Vorto5000DynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.github.blovemaple.hura.dal.Vorto5000;
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
public interface Vorto5000Mapper {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	BasicColumn[] selectList = BasicColumn.columnList(id, radiko, signifo);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "record.id", before = false, resultType = Integer.class)
	int insert(InsertStatementProvider<Vorto5000> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("Vorto5000Result")
	Optional<Vorto5000> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "Vorto5000Result", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.INTEGER, id = true),
			@Result(column = "radiko", property = "radiko", jdbcType = JdbcType.VARCHAR),
			@Result(column = "signifo", property = "signifo", jdbcType = JdbcType.LONGVARCHAR) })
	List<Vorto5000> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, vorto5000, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, vorto5000, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int deleteByPrimaryKey(Integer id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int insert(Vorto5000 record) {
		return MyBatis3Utils.insert(this::insert, record, vorto5000,
				c -> c.map(radiko).toProperty("radiko").map(signifo).toProperty("signifo"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int insertSelective(Vorto5000 record) {
		return MyBatis3Utils.insert(this::insert, record, vorto5000,
				c -> c.map(radiko).toPropertyWhenPresent("radiko", record::getRadiko).map(signifo)
						.toPropertyWhenPresent("signifo", record::getSignifo));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default Optional<Vorto5000> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, vorto5000, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default List<Vorto5000> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, vorto5000, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default List<Vorto5000> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, vorto5000, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default Optional<Vorto5000> selectByPrimaryKey(Integer id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, vorto5000, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	static UpdateDSL<UpdateModel> updateAllColumns(Vorto5000 record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(radiko).equalTo(record::getRadiko).set(signifo).equalTo(record::getSignifo);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(Vorto5000 record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(radiko).equalToWhenPresent(record::getRadiko).set(signifo)
				.equalToWhenPresent(record::getSignifo);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int updateByPrimaryKey(Vorto5000 record) {
		return update(c -> c.set(radiko).equalTo(record::getRadiko).set(signifo).equalTo(record::getSignifo).where(id,
				isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: vorto5000")
	default int updateByPrimaryKeySelective(Vorto5000 record) {
		return update(c -> c.set(radiko).equalToWhenPresent(record::getRadiko).set(signifo)
				.equalToWhenPresent(record::getSignifo).where(id, isEqualTo(record::getId)));
	}
}