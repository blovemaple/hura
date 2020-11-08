package com.github.blovemaple.hura.dal;

import static com.github.blovemaple.hura.dal.UserDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.github.blovemaple.hura.dal.User;
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
public interface UserMapper {

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	BasicColumn[] selectList = BasicColumn.columnList(id, openid, unionid, nickName, avatarUrl, gender, country,
			province, city, language, addtime);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	long count(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	@DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
	int delete(DeleteStatementProvider deleteStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	@InsertProvider(type = SqlProviderAdapter.class, method = "insert")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "record.id", before = false, resultType = Long.class)
	int insert(InsertStatementProvider<User> insertStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@ResultMap("UserResult")
	Optional<User> selectOne(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	@SelectProvider(type = SqlProviderAdapter.class, method = "select")
	@Results(id = "UserResult", value = {
			@Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "unionid", property = "unionid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "nick_name", property = "nickName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "avatar_url", property = "avatarUrl", jdbcType = JdbcType.VARCHAR),
			@Result(column = "gender", property = "gender", jdbcType = JdbcType.TINYINT),
			@Result(column = "country", property = "country", jdbcType = JdbcType.VARCHAR),
			@Result(column = "province", property = "province", jdbcType = JdbcType.VARCHAR),
			@Result(column = "city", property = "city", jdbcType = JdbcType.VARCHAR),
			@Result(column = "language", property = "language", jdbcType = JdbcType.VARCHAR),
			@Result(column = "addtime", property = "addtime", jdbcType = JdbcType.TIMESTAMP) })
	List<User> selectMany(SelectStatementProvider selectStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	@UpdateProvider(type = SqlProviderAdapter.class, method = "update")
	int update(UpdateStatementProvider updateStatement);

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default long count(CountDSLCompleter completer) {
		return MyBatis3Utils.countFrom(this::count, user, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int delete(DeleteDSLCompleter completer) {
		return MyBatis3Utils.deleteFrom(this::delete, user, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int deleteByPrimaryKey(Long id_) {
		return delete(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int insert(User record) {
		return MyBatis3Utils.insert(this::insert, record, user,
				c -> c.map(openid).toProperty("openid").map(unionid).toProperty("unionid").map(nickName)
						.toProperty("nickName").map(avatarUrl).toProperty("avatarUrl").map(gender).toProperty("gender")
						.map(country).toProperty("country").map(province).toProperty("province").map(city)
						.toProperty("city").map(language).toProperty("language").map(addtime).toProperty("addtime"));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int insertSelective(User record) {
		return MyBatis3Utils.insert(this::insert, record, user,
				c -> c.map(openid).toPropertyWhenPresent("openid", record::getOpenid).map(unionid)
						.toPropertyWhenPresent("unionid", record::getUnionid).map(nickName)
						.toPropertyWhenPresent("nickName", record::getNickName).map(avatarUrl)
						.toPropertyWhenPresent("avatarUrl", record::getAvatarUrl).map(gender)
						.toPropertyWhenPresent("gender", record::getGender).map(country)
						.toPropertyWhenPresent("country", record::getCountry).map(province)
						.toPropertyWhenPresent("province", record::getProvince).map(city)
						.toPropertyWhenPresent("city", record::getCity).map(language)
						.toPropertyWhenPresent("language", record::getLanguage).map(addtime)
						.toPropertyWhenPresent("addtime", record::getAddtime));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default Optional<User> selectOne(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectOne(this::selectOne, selectList, user, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default List<User> select(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectList(this::selectMany, selectList, user, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default List<User> selectDistinct(SelectDSLCompleter completer) {
		return MyBatis3Utils.selectDistinct(this::selectMany, selectList, user, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default Optional<User> selectByPrimaryKey(Long id_) {
		return selectOne(c -> c.where(id, isEqualTo(id_)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int update(UpdateDSLCompleter completer) {
		return MyBatis3Utils.update(this::update, user, completer);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	static UpdateDSL<UpdateModel> updateAllColumns(User record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(openid).equalTo(record::getOpenid).set(unionid).equalTo(record::getUnionid).set(nickName)
				.equalTo(record::getNickName).set(avatarUrl).equalTo(record::getAvatarUrl).set(gender)
				.equalTo(record::getGender).set(country).equalTo(record::getCountry).set(province)
				.equalTo(record::getProvince).set(city).equalTo(record::getCity).set(language)
				.equalTo(record::getLanguage).set(addtime).equalTo(record::getAddtime);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	static UpdateDSL<UpdateModel> updateSelectiveColumns(User record, UpdateDSL<UpdateModel> dsl) {
		return dsl.set(openid).equalToWhenPresent(record::getOpenid).set(unionid).equalToWhenPresent(record::getUnionid)
				.set(nickName).equalToWhenPresent(record::getNickName).set(avatarUrl)
				.equalToWhenPresent(record::getAvatarUrl).set(gender).equalToWhenPresent(record::getGender).set(country)
				.equalToWhenPresent(record::getCountry).set(province).equalToWhenPresent(record::getProvince).set(city)
				.equalToWhenPresent(record::getCity).set(language).equalToWhenPresent(record::getLanguage).set(addtime)
				.equalToWhenPresent(record::getAddtime);
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int updateByPrimaryKey(User record) {
		return update(c -> c.set(openid).equalTo(record::getOpenid).set(unionid).equalTo(record::getUnionid)
				.set(nickName).equalTo(record::getNickName).set(avatarUrl).equalTo(record::getAvatarUrl).set(gender)
				.equalTo(record::getGender).set(country).equalTo(record::getCountry).set(province)
				.equalTo(record::getProvince).set(city).equalTo(record::getCity).set(language)
				.equalTo(record::getLanguage).set(addtime).equalTo(record::getAddtime)
				.where(id, isEqualTo(record::getId)));
	}

	@Generated(value = "org.mybatis.generator.api.MyBatisGenerator", comments = "Source Table: user")
	default int updateByPrimaryKeySelective(User record) {
		return update(c -> c.set(openid).equalToWhenPresent(record::getOpenid).set(unionid)
				.equalToWhenPresent(record::getUnionid).set(nickName).equalToWhenPresent(record::getNickName)
				.set(avatarUrl).equalToWhenPresent(record::getAvatarUrl).set(gender)
				.equalToWhenPresent(record::getGender).set(country).equalToWhenPresent(record::getCountry).set(province)
				.equalToWhenPresent(record::getProvince).set(city).equalToWhenPresent(record::getCity).set(language)
				.equalToWhenPresent(record::getLanguage).set(addtime).equalToWhenPresent(record::getAddtime)
				.where(id, isEqualTo(record::getId)));
	}
}