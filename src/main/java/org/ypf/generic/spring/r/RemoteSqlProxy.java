package org.ypf.generic.spring.r;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * SQL代理
 */
public class RemoteSqlProxy {
    String sql;
    Map<Integer, SqlValue> indexData = new LinkedHashMap<>();

    public RemoteSqlProxy() {
    }

    public RemoteSqlProxy(String sql) {
        this.sql = sql;
    }

    private void put(Integer key, Object v) {
        indexData.put(key, new SqlValue(v.getClass().getName(), v));
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    public Map<Integer, SqlValue> getIndexData() {
        return indexData;
    }

    public RemoteSqlProxy setInt(int parameterIndex, int x) {
        put(parameterIndex, x);
        return this;


    }

    public RemoteSqlProxy setLong(int parameterIndex, long x) {
        put(parameterIndex, x);

        return this;
    }

    public RemoteSqlProxy setFloat(int parameterIndex, float x) {
        put(parameterIndex, x);
        return this;
    }

    public RemoteSqlProxy setDouble(int parameterIndex, double x) {
        put(parameterIndex, x);

        return this;
    }

    public RemoteSqlProxy setBigDecimal(int parameterIndex, BigDecimal x) {
        put(parameterIndex, x);

        return this;
    }

    public RemoteSqlProxy setString(int parameterIndex, String x) {
        put(parameterIndex, x);

        return this;
    }

    public RemoteSqlProxy setDate(int parameterIndex, Date x) {
        put(parameterIndex, x);

        return this;
    }

    public RemoteSqlProxy setTime(int parameterIndex, Time x) {
        put(parameterIndex, x);
        return this;
    }

    public RemoteSqlProxy setTimestamp(int parameterIndex, Timestamp x) {
        put(parameterIndex, x);

        return this;
    }

    public RemoteSqlProxy setObject(int parameterIndex, Object x) {
        put(parameterIndex, x);

        return this;
    }


}
