package org.ypf.generic.orm;

import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据持久模板
 *
 * @date: 2021/6/28 18:19
 */
public interface DaoTemplete {
    <R, T> Qurey<R, T> getQureyBySqlPrepared(SqlParamsBind sql, FunctionResultSet<ResultSet, R> f);

    <T> Excuter<T> getExcuterBySqlPrepared(SqlParamsBind sql);

    List<Map<String, Object>> listQurey(SqlParamsBind sql);

    ResultSetProxy listQureyResultSetProxy(SqlParamsBind sql);

    <R, T> Qurey<R, T> getQurey(String sql, ConsumerPreparedStatement<T> pre, FunctionResultSet<ResultSet, R> f, T c);

    <R, T> Qurey<R, T> getQurey(String sql, FunctionResultSet<ResultSet, R> f);

    Object objectQurey(String sql) throws Throwable;

    <T> Object objectQurey(String sql, ConsumerPreparedStatement<T> pre, T c);

    Number numberQurey(String sql);

    Long longQurey(String sql);

    <T> Long longQurey(String sql, ConsumerPreparedStatement<T> pre, T c);

    <T> Number numberQurey(String sql, ConsumerPreparedStatement<T> pre, T c);

    List<Map<String, Object>> listQurey(String sql, ConsumerPreparedStatement<Object> pre);

    <T> List<Map<String, Object>> listQurey(String sql, ConsumerPreparedStatement<T> pre, T c);

    <T> List<LinkedHashMap<String, Object>> listLinkedHashMap(String sql, ConsumerPreparedStatement<T> pre, T c);

    <T> ResultSetProxy listQureyResultSetProxy(String sql, ConsumerPreparedStatement<T> pre, T c);

    List<Map<String, Object>> listQurey(String sql);

    List<JSONObject> listJSONObjectQurey(String sql);

    <T> List<JSONObject> listJSONObjectQurey(String sql, ConsumerPreparedStatement<T> pre, T c);

    <T> List<T> listQurey(String sql, Class<T> tClass);

    <R, T> Qurey<R, T> getQurey(String sql, ConsumerPreparedStatement<T> pre, FunctionResultSet<ResultSet, R> f);

    <T> Excuter<T> getExcuter(String sql, ConsumerPreparedStatement<T> pre, T c);

    Excuter getExcuter(String sql);

    <T> Excuter<T> getExcuter(String sql, ConsumerPreparedStatement<T> pre);

    <T> BatchExcuter<T> getBatchExcuter(String sql, ConsumerPreparedStatement<T> pre, T c);

    <T> ListBatchExcuter<T> getListBatchExcuter(String sql, BatchPreparedStatement<T> pre, List<T> c);

    <T> BatchExcuter<T> getBatchExcuter(String sql, ConsumerPreparedStatement<T> pre);
}
