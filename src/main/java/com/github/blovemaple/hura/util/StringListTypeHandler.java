package com.github.blovemaple.hura.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 *
 * @param <E>
 */
public class StringListTypeHandler extends BaseTypeHandler<List<String>> {
	private static final String DELIMITER = ",";

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, List<String> parameter, JdbcType jdbcType)
			throws SQLException {
		String dbValue = String.join(DELIMITER, parameter);
		ps.setString(i, dbValue);
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String dbValue = rs.getString(columnName);
		if (dbValue == null) {
			return null;
		}
		String[] parts = dbValue.split(Pattern.quote(DELIMITER));
		return List.of(parts);
	}

	@Override
	public List<String> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String dbValue = rs.getString(columnIndex);
		if (dbValue == null) {
			return null;
		}
		String[] parts = dbValue.split(Pattern.quote(DELIMITER));
		return List.of(parts);
	}

	@Override
	public List<String> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String dbValue = cs.getString(columnIndex);
		if (dbValue == null) {
			return null;
		}
		String[] parts = dbValue.split(Pattern.quote(DELIMITER));
		return List.of(parts);
	}

}