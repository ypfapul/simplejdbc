package org.ypf.generic.orm;

import java.math.BigDecimal;
import java.sql.Date;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * sql参数预绑定
 *
 * @date: 2022/6/15 16:19
 */
public interface SqlParamsBind {

    String sql();

    SqlParamsBind setInt(int parameterIndex, int x);

    SqlParamsBind setLong(int parameterIndex, long x);

    SqlParamsBind setFloat(int parameterIndex, float x);

    SqlParamsBind setDouble(int parameterIndex, double x);

    SqlParamsBind setBigDecimal(int parameterIndex, BigDecimal x);

    SqlParamsBind setString(int parameterIndex, String x);

    SqlParamsBind setDate(int parameterIndex, Date x);

    SqlParamsBind setTime(int parameterIndex, Time x);

    SqlParamsBind setTimestamp(int parameterIndex, Timestamp x);

    SqlParamsBind setObject(int parameterIndex, Object x);
}
