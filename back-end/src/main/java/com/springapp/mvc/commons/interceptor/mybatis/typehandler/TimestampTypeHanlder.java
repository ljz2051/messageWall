package com.springapp.mvc.commons.interceptor.mybatis.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;

/**
 * 将字符串格式的时间:<code>yyyy-mm-dd hh:mm:ss[.f...]</code>转换为JDBC能够识别的类型。
 * 
 * @author david
 * 
 */
public class TimestampTypeHanlder extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        Timestamp timestamp = Timestamp.valueOf(parameter);
        ps.setTimestamp(i, timestamp);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        String dateTime = timestamp.toString();
        dateTime = dateTime.substring(0, dateTime.lastIndexOf("."));
        return dateTime;
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        String dateTime = timestamp.toString();
        dateTime = dateTime.substring(0, dateTime.lastIndexOf("."));
        return dateTime;
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        String dateTime = timestamp.toString();
        dateTime = dateTime.substring(0, dateTime.lastIndexOf("."));
        return dateTime;
    }

}
