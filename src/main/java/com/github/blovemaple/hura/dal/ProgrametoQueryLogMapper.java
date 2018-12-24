package com.github.blovemaple.hura.dal;

import com.github.blovemaple.hura.dal.ProgrametoQueryLog;
import com.github.blovemaple.hura.dal.ProgrametoQueryLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

@Mapper
public interface ProgrametoQueryLogMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@SelectProvider(type = ProgrametoQueryLogSqlProvider.class, method = "countByExample")
	long countByExample(ProgrametoQueryLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@DeleteProvider(type = ProgrametoQueryLogSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ProgrametoQueryLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Delete({ "delete from programeto_query_log", "where id = #{id,jdbcType=BIGINT}" })
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Insert({ "insert into programeto_query_log (time, cost, ", "openid, unionid, ", "query, section_key, ",
			"has_result, result)", "values (#{time,jdbcType=TIMESTAMP}, #{cost,jdbcType=INTEGER}, ",
			"#{openid,jdbcType=VARCHAR}, #{unionid,jdbcType=VARCHAR}, ",
			"#{query,jdbcType=VARCHAR}, #{sectionKey,jdbcType=VARCHAR}, ",
			"#{hasResult,jdbcType=BIT}, #{result,jdbcType=VARCHAR})" })
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
	int insert(ProgrametoQueryLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@InsertProvider(type = ProgrametoQueryLogSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
	int insertSelective(ProgrametoQueryLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@SelectProvider(type = ProgrametoQueryLogSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "cost", property = "cost", jdbcType = JdbcType.INTEGER),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "unionid", property = "unionid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "query", property = "query", jdbcType = JdbcType.VARCHAR),
			@Result(column = "section_key", property = "sectionKey", jdbcType = JdbcType.VARCHAR),
			@Result(column = "has_result", property = "hasResult", jdbcType = JdbcType.BIT),
			@Result(column = "result", property = "result", jdbcType = JdbcType.VARCHAR) })
	List<ProgrametoQueryLog> selectByExample(ProgrametoQueryLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Select({ "select", "id, time, cost, openid, unionid, query, section_key, has_result, result",
			"from programeto_query_log", "where id = #{id,jdbcType=BIGINT}" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "cost", property = "cost", jdbcType = JdbcType.INTEGER),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "unionid", property = "unionid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "query", property = "query", jdbcType = JdbcType.VARCHAR),
			@Result(column = "section_key", property = "sectionKey", jdbcType = JdbcType.VARCHAR),
			@Result(column = "has_result", property = "hasResult", jdbcType = JdbcType.BIT),
			@Result(column = "result", property = "result", jdbcType = JdbcType.VARCHAR) })
	ProgrametoQueryLog selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@UpdateProvider(type = ProgrametoQueryLogSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ProgrametoQueryLog record,
			@Param("example") ProgrametoQueryLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@UpdateProvider(type = ProgrametoQueryLogSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ProgrametoQueryLog record,
			@Param("example") ProgrametoQueryLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@UpdateProvider(type = ProgrametoQueryLogSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ProgrametoQueryLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_query_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Update({ "update programeto_query_log", "set time = #{time,jdbcType=TIMESTAMP},",
			"cost = #{cost,jdbcType=INTEGER},", "openid = #{openid,jdbcType=VARCHAR},",
			"unionid = #{unionid,jdbcType=VARCHAR},", "query = #{query,jdbcType=VARCHAR},",
			"section_key = #{sectionKey,jdbcType=VARCHAR},", "has_result = #{hasResult,jdbcType=BIT},",
			"result = #{result,jdbcType=VARCHAR}", "where id = #{id,jdbcType=BIGINT}" })
	int updateByPrimaryKey(ProgrametoQueryLog record);
}