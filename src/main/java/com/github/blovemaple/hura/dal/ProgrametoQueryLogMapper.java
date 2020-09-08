package com.github.blovemaple.hura.dal;

import static com.github.blovemaple.hura.dal.ProgrametoQueryLogDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.github.blovemaple.hura.dal.ProgrametoQueryLog;
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
public interface ProgrametoQueryLogMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    BasicColumn[] selectList = BasicColumn.columnList(id, time, cost, openid, unionid, query, sectionKey, isDetail, hasResult, result);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<ProgrametoQueryLog> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("ProgrametoQueryLogResult")
    Optional<ProgrametoQueryLog> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="ProgrametoQueryLogResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="cost", property="cost", jdbcType=JdbcType.INTEGER),
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR),
        @Result(column="unionid", property="unionid", jdbcType=JdbcType.VARCHAR),
        @Result(column="query", property="query", jdbcType=JdbcType.VARCHAR),
        @Result(column="section_key", property="sectionKey", jdbcType=JdbcType.VARCHAR),
        @Result(column="is_detail", property="isDetail", jdbcType=JdbcType.BIT),
        @Result(column="has_result", property="hasResult", jdbcType=JdbcType.BIT),
        @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR)
    })
    List<ProgrametoQueryLog> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, programetoQueryLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, programetoQueryLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int insert(ProgrametoQueryLog record) {
        return MyBatis3Utils.insert(this::insert, record, programetoQueryLog, c ->
            c.map(time).toProperty("time")
            .map(cost).toProperty("cost")
            .map(openid).toProperty("openid")
            .map(unionid).toProperty("unionid")
            .map(query).toProperty("query")
            .map(sectionKey).toProperty("sectionKey")
            .map(isDetail).toProperty("isDetail")
            .map(hasResult).toProperty("hasResult")
            .map(result).toProperty("result")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int insertSelective(ProgrametoQueryLog record) {
        return MyBatis3Utils.insert(this::insert, record, programetoQueryLog, c ->
            c.map(time).toPropertyWhenPresent("time", record::getTime)
            .map(cost).toPropertyWhenPresent("cost", record::getCost)
            .map(openid).toPropertyWhenPresent("openid", record::getOpenid)
            .map(unionid).toPropertyWhenPresent("unionid", record::getUnionid)
            .map(query).toPropertyWhenPresent("query", record::getQuery)
            .map(sectionKey).toPropertyWhenPresent("sectionKey", record::getSectionKey)
            .map(isDetail).toPropertyWhenPresent("isDetail", record::getIsDetail)
            .map(hasResult).toPropertyWhenPresent("hasResult", record::getHasResult)
            .map(result).toPropertyWhenPresent("result", record::getResult)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default Optional<ProgrametoQueryLog> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, programetoQueryLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default List<ProgrametoQueryLog> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, programetoQueryLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default List<ProgrametoQueryLog> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, programetoQueryLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default Optional<ProgrametoQueryLog> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, programetoQueryLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    static UpdateDSL<UpdateModel> updateAllColumns(ProgrametoQueryLog record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalTo(record::getTime)
                .set(cost).equalTo(record::getCost)
                .set(openid).equalTo(record::getOpenid)
                .set(unionid).equalTo(record::getUnionid)
                .set(query).equalTo(record::getQuery)
                .set(sectionKey).equalTo(record::getSectionKey)
                .set(isDetail).equalTo(record::getIsDetail)
                .set(hasResult).equalTo(record::getHasResult)
                .set(result).equalTo(record::getResult);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(ProgrametoQueryLog record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalToWhenPresent(record::getTime)
                .set(cost).equalToWhenPresent(record::getCost)
                .set(openid).equalToWhenPresent(record::getOpenid)
                .set(unionid).equalToWhenPresent(record::getUnionid)
                .set(query).equalToWhenPresent(record::getQuery)
                .set(sectionKey).equalToWhenPresent(record::getSectionKey)
                .set(isDetail).equalToWhenPresent(record::getIsDetail)
                .set(hasResult).equalToWhenPresent(record::getHasResult)
                .set(result).equalToWhenPresent(record::getResult);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int updateByPrimaryKey(ProgrametoQueryLog record) {
        return update(c ->
            c.set(time).equalTo(record::getTime)
            .set(cost).equalTo(record::getCost)
            .set(openid).equalTo(record::getOpenid)
            .set(unionid).equalTo(record::getUnionid)
            .set(query).equalTo(record::getQuery)
            .set(sectionKey).equalTo(record::getSectionKey)
            .set(isDetail).equalTo(record::getIsDetail)
            .set(hasResult).equalTo(record::getHasResult)
            .set(result).equalTo(record::getResult)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: programeto_query_log")
    default int updateByPrimaryKeySelective(ProgrametoQueryLog record) {
        return update(c ->
            c.set(time).equalToWhenPresent(record::getTime)
            .set(cost).equalToWhenPresent(record::getCost)
            .set(openid).equalToWhenPresent(record::getOpenid)
            .set(unionid).equalToWhenPresent(record::getUnionid)
            .set(query).equalToWhenPresent(record::getQuery)
            .set(sectionKey).equalToWhenPresent(record::getSectionKey)
            .set(isDetail).equalToWhenPresent(record::getIsDetail)
            .set(hasResult).equalToWhenPresent(record::getHasResult)
            .set(result).equalToWhenPresent(record::getResult)
            .where(id, isEqualTo(record::getId))
        );
    }
}