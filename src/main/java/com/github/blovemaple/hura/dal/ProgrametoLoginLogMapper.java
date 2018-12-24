package com.github.blovemaple.hura.dal;

import com.github.blovemaple.hura.dal.ProgrametoLoginLog;
import com.github.blovemaple.hura.dal.ProgrametoLoginLogExample;
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
public interface ProgrametoLoginLogMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@SelectProvider(type = ProgrametoLoginLogSqlProvider.class, method = "countByExample")
	long countByExample(ProgrametoLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@DeleteProvider(type = ProgrametoLoginLogSqlProvider.class, method = "deleteByExample")
	int deleteByExample(ProgrametoLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Delete({ "delete from programeto_login_log", "where id = #{id,jdbcType=BIGINT}" })
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Insert({ "insert into programeto_login_log (time, cost, ", "code, openid, unionid)",
			"values (#{time,jdbcType=TIMESTAMP}, #{cost,jdbcType=INTEGER}, ",
			"#{code,jdbcType=VARCHAR}, #{openid,jdbcType=VARCHAR}, #{unionid,jdbcType=VARCHAR})" })
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
	int insert(ProgrametoLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@InsertProvider(type = ProgrametoLoginLogSqlProvider.class, method = "insertSelective")
	@SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", before = false, resultType = Long.class)
	int insertSelective(ProgrametoLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@SelectProvider(type = ProgrametoLoginLogSqlProvider.class, method = "selectByExample")
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "cost", property = "cost", jdbcType = JdbcType.INTEGER),
			@Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "unionid", property = "unionid", jdbcType = JdbcType.VARCHAR) })
	List<ProgrametoLoginLog> selectByExample(ProgrametoLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Select({ "select", "id, time, cost, code, openid, unionid", "from programeto_login_log",
			"where id = #{id,jdbcType=BIGINT}" })
	@Results({ @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
			@Result(column = "time", property = "time", jdbcType = JdbcType.TIMESTAMP),
			@Result(column = "cost", property = "cost", jdbcType = JdbcType.INTEGER),
			@Result(column = "code", property = "code", jdbcType = JdbcType.VARCHAR),
			@Result(column = "openid", property = "openid", jdbcType = JdbcType.VARCHAR),
			@Result(column = "unionid", property = "unionid", jdbcType = JdbcType.VARCHAR) })
	ProgrametoLoginLog selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@UpdateProvider(type = ProgrametoLoginLogSqlProvider.class, method = "updateByExampleSelective")
	int updateByExampleSelective(@Param("record") ProgrametoLoginLog record,
			@Param("example") ProgrametoLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@UpdateProvider(type = ProgrametoLoginLogSqlProvider.class, method = "updateByExample")
	int updateByExample(@Param("record") ProgrametoLoginLog record,
			@Param("example") ProgrametoLoginLogExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@UpdateProvider(type = ProgrametoLoginLogSqlProvider.class, method = "updateByPrimaryKeySelective")
	int updateByPrimaryKeySelective(ProgrametoLoginLog record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table programeto_login_log
	 * @mbg.generated  Sun Dec 23 22:48:53 CST 2018
	 */
	@Update({ "update programeto_login_log", "set time = #{time,jdbcType=TIMESTAMP},",
			"cost = #{cost,jdbcType=INTEGER},", "code = #{code,jdbcType=VARCHAR},",
			"openid = #{openid,jdbcType=VARCHAR},", "unionid = #{unionid,jdbcType=VARCHAR}",
			"where id = #{id,jdbcType=BIGINT}" })
	int updateByPrimaryKey(ProgrametoLoginLog record);
}