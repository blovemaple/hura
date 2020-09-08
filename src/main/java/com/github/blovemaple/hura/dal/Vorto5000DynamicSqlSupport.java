package com.github.blovemaple.hura.dal;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class Vorto5000DynamicSqlSupport {
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: vorto5000")
    public static final Vorto5000 vorto5000 = new Vorto5000();

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: vorto5000.id")
    public static final SqlColumn<Integer> id = vorto5000.id;

    /**
     * Database Column Remarks:
     *   词根
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: vorto5000.radiko")
    public static final SqlColumn<String> radiko = vorto5000.radiko;

    /**
     * Database Column Remarks:
     *   词义
     */
    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source field: vorto5000.signifo")
    public static final SqlColumn<String> signifo = vorto5000.signifo;

    @Generated(value="org.mybatis.generator.api.MyBatisGenerator", comments="Source Table: vorto5000")
    public static final class Vorto5000 extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> radiko = column("radiko", JDBCType.VARCHAR);

        public final SqlColumn<String> signifo = column("signifo", JDBCType.LONGVARCHAR);

        public Vorto5000() {
            super("vorto5000");
        }
    }
}