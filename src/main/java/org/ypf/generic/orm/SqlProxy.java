package org.ypf.generic.orm;

import java.math.BigDecimal;
import java.sql.*;
import java.sql.Date;
import java.util.*;


/**
 * @date: 2021/6/23 16:11
 */
public class SqlProxy implements SqlParamsBind {
    String sql;
    Map<Integer, Object> indexData = new LinkedHashMap<>();

    public SqlProxy() {
    }

    public SqlProxy(String sql) {
        this.sql = sql;
    }

    public Map<Integer, Object> getIndexData() {
        return indexData;
    }


    public static SqlProxy newSqlProxy(String sql) {
        return new SqlProxy(sql);
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String sql() {
        return sql;
    }

    public SqlProxy setInt(int parameterIndex, int x) {
        indexData.put(parameterIndex, x);
        return this;


    }

    public SqlProxy setLong(int parameterIndex, long x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public SqlProxy setFloat(int parameterIndex, float x) {
        indexData.put(parameterIndex, x);
        return this;
    }

    public SqlProxy setDouble(int parameterIndex, double x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public SqlProxy setBigDecimal(int parameterIndex, BigDecimal x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public SqlProxy setString(int parameterIndex, String x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public SqlProxy setDate(int parameterIndex, Date x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public SqlProxy setTime(int parameterIndex, Time x) {
        indexData.put(parameterIndex, x);
        return this;
    }

    public SqlProxy setTimestamp(int parameterIndex, Timestamp x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public SqlProxy setObject(int parameterIndex, Object x) {
        indexData.put(parameterIndex, x);

        return this;
    }

    public ConsumerPreparedStatement mapConsumerPreparedStatement() {
        ConsumerPreparedStatement consumerPreparedStatement = new ConsumerPreparedStatement() {
            @Override
            public void accept(PreparedStatement p, Object con) throws Exception {
                int size = indexData.size();


                for (Map.Entry<Integer, Object> me : indexData.entrySet()) {
                    int i = me.getKey();
                    Object v = indexData.get(i);
                    if (v instanceof Integer) {
                        Integer integer = (Integer) v;
                        p.setInt(i, integer.intValue());


                    } else if (v instanceof Long) {
                        Long aLong = (Long) v;
                        p.setLong(i, aLong.longValue());

                    } else if (v instanceof Double) {
                        Double d = (Double) v;
                        p.setDouble(i, d.doubleValue());


                    } else if (v instanceof Float) {
                        Float fd = (Float) v;
                        p.setDouble(i, fd.floatValue());


                    } else if (v instanceof BigDecimal) {
                        BigDecimal bigDecimal = (BigDecimal) v;
                        p.setBigDecimal(i, bigDecimal);


                    } else if (v instanceof String) {
                        String st = (String) v;
                        p.setString(i, st);


                    } else if (v instanceof Date) {
                        Date date = (Date) v;

                        p.setDate(i, date);

                    } else if (v instanceof Time) {
                        Time time = (Time) v;
                        p.setTime(i, time);


                    } else if (v instanceof Timestamp) {
                        Timestamp timestamp = (Timestamp) v;

                        p.setTimestamp(i, timestamp);

                    } else {
                        p.setObject(i, v);


                    }


                }
            }
        };


        return consumerPreparedStatement;

    }


}
