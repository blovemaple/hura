package com.github.blovemaple.hura.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * @author blovemaple <blovemaple2010(at)gmail.com>
 *
 * @param <E>
 */
public class EnumCodeTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {
	private static final String METHOD_NAME_CODE = "code";
	private static final String METHOD_NAME_OF_CODE = "ofCode";

	private final Class<E> type;
	private final E[] enums;

	public EnumCodeTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null) {
			throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		Integer code;
		try {
			code = (Integer) MethodUtils.invokeMethod(parameter, METHOD_NAME_CODE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		ps.setInt(i, code);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int i = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			try {
				return (E) MethodUtils.invokeStaticMethod(type, METHOD_NAME_OF_CODE, i);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int i = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			try {
				return (E) MethodUtils.invokeStaticMethod(type, METHOD_NAME_OF_CODE, i);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int i = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			try {
				return (E) MethodUtils.invokeStaticMethod(type, METHOD_NAME_OF_CODE, i);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

}