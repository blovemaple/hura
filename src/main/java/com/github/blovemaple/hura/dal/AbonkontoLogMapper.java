package com.github.blovemaple.hura.dal;

import static com.github.blovemaple.hura.dal.AbonkontoLogDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import com.github.blovemaple.hura.dal.AbonkontoLog;
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
public interface AbonkontoLogMapper {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    BasicColumn[] selectList = BasicColumn.columnList(id, time, status, cost, openid, unionid, msgType, request, response);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="record.id", before=false, resultType=Long.class)
    int insert(InsertStatementProvider<AbonkontoLog> insertStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("AbonkontoLogResult")
    Optional<AbonkontoLog> selectOne(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="AbonkontoLogResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="cost", property="cost", jdbcType=JdbcType.INTEGER),
        @Result(column="openid", property="openid", jdbcType=JdbcType.VARCHAR),
        @Result(column="unionid", property="unionid", jdbcType=JdbcType.VARCHAR),
        @Result(column="msg_type", property="msgType", jdbcType=JdbcType.VARCHAR),
        @Result(column="request", property="request", jdbcType=JdbcType.VARCHAR),
        @Result(column="response", property="response", jdbcType=JdbcType.VARCHAR)
    })
    List<AbonkontoLog> selectMany(SelectStatementProvider selectStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, abonkontoLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, abonkontoLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int deleteByPrimaryKey(Long id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int insert(AbonkontoLog record) {
        return MyBatis3Utils.insert(this::insert, record, abonkontoLog, c ->
            c.map(time).toProperty("time")
            .map(status).toProperty("status")
            .map(cost).toProperty("cost")
            .map(openid).toProperty("openid")
            .map(unionid).toProperty("unionid")
            .map(msgType).toProperty("msgType")
            .map(request).toProperty("request")
            .map(response).toProperty("response")
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int insertSelective(AbonkontoLog record) {
        return MyBatis3Utils.insert(this::insert, record, abonkontoLog, c ->
            c.map(time).toPropertyWhenPresent("time", record::getTime)
            .map(status).toPropertyWhenPresent("status", record::getStatus)
            .map(cost).toPropertyWhenPresent("cost", record::getCost)
            .map(openid).toPropertyWhenPresent("openid", record::getOpenid)
            .map(unionid).toPropertyWhenPresent("unionid", record::getUnionid)
            .map(msgType).toPropertyWhenPresent("msgType", record::getMsgType)
            .map(request).toPropertyWhenPresent("request", record::getRequest)
            .map(response).toPropertyWhenPresent("response", record::getResponse)
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default Optional<AbonkontoLog> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, abonkontoLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default List<AbonkontoLog> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, abonkontoLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default List<AbonkontoLog> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, abonkontoLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default Optional<AbonkontoLog> selectByPrimaryKey(Long id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, abonkontoLog, completer);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    static UpdateDSL<UpdateModel> updateAllColumns(AbonkontoLog record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalTo(record::getTime)
                .set(status).equalTo(record::getStatus)
                .set(cost).equalTo(record::getCost)
                .set(openid).equalTo(record::getOpenid)
                .set(unionid).equalTo(record::getUnionid)
                .set(msgType).equalTo(record::getMsgType)
                .set(request).equalTo(record::getRequest)
                .set(response).equalTo(record::getResponse);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(AbonkontoLog record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(time).equalToWhenPresent(record::getTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(cost).equalToWhenPresent(record::getCost)
                .set(openid).equalToWhenPresent(record::getOpenid)
                .set(unionid).equalToWhenPresent(record::getUnionid)
                .set(msgType).equalToWhenPresent(record::getMsgType)
                .set(request).equalToWhenPresent(record::getRequest)
                .set(response).equalToWhenPresent(record::getResponse);
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int updateByPrimaryKey(AbonkontoLog record) {
        return update(c ->
            c.set(time).equalTo(record::getTime)
            .set(status).equalTo(record::getStatus)
            .set(cost).equalTo(record::getCost)
            .set(openid).equalTo(record::getOpenid)
            .set(unionid).equalTo(record::getUnionid)
            .set(msgType).equalTo(record::getMsgType)
            .set(request).equalTo(record::getRequest)
            .set(response).equalTo(record::getResponse)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: abonkonto_log")
    default int updateByPrimaryKeySelective(AbonkontoLog record) {
        return update(c ->
            c.set(time).equalToWhenPresent(record::getTime)
            .set(status).equalToWhenPresent(record::getStatus)
            .set(cost).equalToWhenPresent(record::getCost)
            .set(openid).equalToWhenPresent(record::getOpenid)
            .set(unionid).equalToWhenPresent(record::getUnionid)
            .set(msgType).equalToWhenPresent(record::getMsgType)
            .set(request).equalToWhenPresent(record::getRequest)
            .set(response).equalToWhenPresent(record::getResponse)
            .where(id, isEqualTo(record::getId))
        );
    }
}