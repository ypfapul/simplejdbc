package org.ypf.generic.orm;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.ypf.generic.orm.exception.JdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ypf.generic.dependency.Id3sGen;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date: 2022/6/17 8:49
 */
public class JdbcSession implements Session {
    private static Logger logger = LoggerFactory.getLogger(JdbcSession.class);
    DataSource dataSource;
    DefaultTransaction transaction;
    long id = Id3sGen.idGen().id();

    JdbcSession(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static <T> T map2obj(Map<String, Object> map, Class<T> tClass) {
        if (map == null)
            return null;
        T t = null;
        try {
            t = tClass.newInstance();
        } catch (Exception e) {

        }

        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            String fn = field.getName().replaceAll("_", "")
                    .toLowerCase();
            for (String key : map.keySet()) {
                String mk = key.replaceAll("_", "").replaceAll("-", "")
                        .toLowerCase();
                if (fn.equals(mk)) {
                    Object v = map.get(key);
                    try {
                        if (v instanceof Number) {
                            v = JSON.parseObject(v.toString(), field.getType());
                        } else if (v instanceof Date) {
                            if (field.getType() == String.class) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
                                v = simpleDateFormat.format((Date) v);
                            }
                        }
                        field.setAccessible(true);
                        field.set(t, v);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new JdbcException(e);

                    }
                    break;

                }

            }

        }


        return t;
    }

    private static List<JSONObject> genJsonObjList(ResultSet rset) {

        List<JSONObject> li = new ArrayList<>();

        try {
            ResultSetMetaData resultSetMetaData = rset.getMetaData();
            int sumc = resultSetMetaData.getColumnCount();
            while (rset.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= sumc; i++) {
                    String cname = resultSetMetaData.getColumnName(i);
                    jsonObject.put(cname, rset.getObject(cname));
                }

                li.add(jsonObject);

            }
        } catch (SQLException e) {
            throw new JdbcException(e);

        }


        return li;
    }


    private static List<LinkedHashMap<String, Object>> genLinkedHashMapList(ResultSet rset) {

        List<LinkedHashMap<String, Object>> li = new ArrayList<>();

        try {
            ResultSetMetaData resultSetMetaData = rset.getMetaData();
            int sumc = resultSetMetaData.getColumnCount();
            while (rset.next()) {
                LinkedHashMap<String, Object> emap = new LinkedHashMap<>();
                for (int i = 1; i <= sumc; i++) {
                    String cname = resultSetMetaData.getColumnName(i);
                    emap.put(cname, rset.getObject(cname));

                }

                li.add(emap);

            }
        } catch (SQLException e) {
            throw new JdbcException(e);

        }


        return li;
    }

    private static List<Map<String, Object>> genMapList(ResultSet rset) {

        List<Map<String, Object>> li = new ArrayList<>();

        try {
            ResultSetMetaData resultSetMetaData = rset.getMetaData();
            int sumc = resultSetMetaData.getColumnCount();
            while (rset.next()) {
                Map<String, Object> emap = new HashMap<>();
                for (int i = 1; i <= sumc; i++) {
                    String cname = resultSetMetaData.getColumnName(i);
                    emap.put(cname, rset.getObject(cname));

                }

                li.add(emap);

            }
        } catch (SQLException e) {
            throw new JdbcException(e);

        }


        return li;
    }

    private Connection getConnection() {
        Connection connection = null;
        try {
            if (transaction != null) {

                connection = transaction.connection;
            } else {

                connection = dataSource.getConnection();
                logger.debug("Connection get {} ", connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }

    private void closeConnection(BaseExcuter baseExcuter, Connection connection) {
        if (!transaction()) {
            baseExcuter.addWhenFinally(ev -> {
                try {
                    connection.close();
                    logger.debug("Connection close {}", connection);
                } catch (SQLException e) {
                    throw new JdbcException(e);
                }
            });
        }
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public boolean transaction() {
        return transaction != null;
    }

    @Override
    public Transaction getTransaction() {
        if (transaction == null) {
            transaction = new DefaultTransaction();
            transaction.open();
        }

        return transaction;
    }

    @Override
    public Transaction getTransaction(int isolation) {
        if (transaction == null) {
            transaction = new DefaultTransaction(isolation);
            transaction.open();
        }

        return transaction;
    }

    @Override
    public <R, T> Qurey<R, T> getQureyBySqlPrepared(SqlParamsBind sql, FunctionResultSet<ResultSet, R> f) {
        SqlProxy sqlProxy = (SqlProxy) sql;
        Connection connection = getConnection();
        Qurey<R, T> excuter = new Qurey<>(() -> connection, sqlProxy.sql(), sqlProxy.mapConsumerPreparedStatement(), null, f);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <T> Excuter<T> getExcuterBySqlPrepared(SqlParamsBind sql) {
        SqlProxy sqlProxy = (SqlProxy) sql;
        Connection connection = getConnection();
        Excuter<T> excuter = new Excuter<>(() -> connection, sql.sql(), sqlProxy.mapConsumerPreparedStatement(), null);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public List<Map<String, Object>> listQurey(SqlParamsBind sql) {
        SqlProxy sqlProxy = (SqlProxy) sql;
        return listQurey(sqlProxy.sql, sqlProxy.mapConsumerPreparedStatement());
    }

    @Override
    public ResultSetProxy listQureyResultSetProxy(SqlParamsBind sql) {
        SqlProxy sqlProxy = (SqlProxy) sql;
        return listQureyResultSetProxy(sqlProxy.sql, sqlProxy.mapConsumerPreparedStatement(), null);
    }

    @Override
    public <R, T> Qurey<R, T> getQurey(String sql, ConsumerPreparedStatement<T> pre, FunctionResultSet<ResultSet, R> f, T c) {
        Connection connection = getConnection();
        Qurey<R, T> excuter = new Qurey<>(() -> connection, sql, pre, c, f);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <R, T> Qurey<R, T> getQurey(String sql, FunctionResultSet<ResultSet, R> f) {
        Connection connection = getConnection();
        Qurey<R, T> excuter = new Qurey<>(() -> connection, sql, null, null, f);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public Object objectQurey(String sql) {
        List<Map<String, Object>> list = listQurey(sql);
        if (list.size() > 0) {
            return list.get(0).values().stream().findFirst().get();
        }
        return null;

    }

    @Override
    public <T> Object objectQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {
        List<Map<String, Object>> list = listQurey(sql, pre, c);
        if (list.size() > 0) {
            return list.get(0).values().stream().findFirst().get();

        }
        return null;
    }

    @Override
    public Number numberQurey(String sql) {
        Object res = objectQurey(sql);
        if (res instanceof Number) {
            return (Number) res;
        }

        return null;
    }

    @Override
    public Long longQurey(String sql) {
        Number number = numberQurey(sql);
        if (number instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) number;
            return bigDecimal.longValue();
        } else if (number instanceof Integer) {
            Integer integer = (Integer) number;
            return Long.parseLong(integer.toString());
        }
        return (Long) number;
    }

    @Override
    public <T> Long longQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {
        Number number = numberQurey(sql, pre, c);
        if (number instanceof BigDecimal) {
            BigDecimal bigDecimal = (BigDecimal) number;
            return bigDecimal.longValue();
        }

        return (Long) number;
    }

    @Override
    public <T> Number numberQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {
        Object res = objectQurey(sql, pre, c);
        if (res instanceof Number) {

            return (Number) res;
        }

        return null;
    }

    @Override
    public List<Map<String, Object>> listQurey(String sql, ConsumerPreparedStatement<Object> pre) {
        Connection connection = getConnection();
        Qurey<List<Map<String, Object>>, Object> excuter = new Qurey<>(() -> connection, sql, pre, null, (rset) -> {
            return genMapList(rset);
        });
        closeConnection(excuter, connection);
        return excuter.qurey();
    }

    @Override
    public <T> List<Map<String, Object>> listQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {

        Connection connection = getConnection();

        Qurey<List<Map<String, Object>>, T> excuter = new Qurey<>(() -> connection, sql, pre, c, (rset) -> {
            return genMapList(rset);

        });

        closeConnection(excuter, connection);
        return excuter.qurey();

    }

    @Override
    public <T> List<LinkedHashMap<String, Object>> listLinkedHashMap(String sql, ConsumerPreparedStatement<T> pre, T c) {
        Connection connection = getConnection();

        Qurey<List<LinkedHashMap<String, Object>>, T> excuter = new Qurey<>(() -> connection, sql, pre, c, (rset) -> {
            return genLinkedHashMapList(rset);

        });

        closeConnection(excuter, connection);
        return excuter.qurey();
    }

    @Override
    public <T> ResultSetProxy listQureyResultSetProxy(String sql, ConsumerPreparedStatement<T> pre, T c) {
        ResultSetProxy resultSetProxy = new ResultSetProxy();
        resultSetProxy.setLinkedHashMapList(listLinkedHashMap(sql, pre, c));
        return resultSetProxy;
    }

    @Override
    public List<Map<String, Object>> listQurey(String sql) {
        Connection connection = getConnection();
        Qurey<List<Map<String, Object>>, Object> excuter = new Qurey<>(() -> connection, sql, null, null, (rset) -> {
            return genMapList(rset);
        });
        closeConnection(excuter, connection);
        return excuter.qurey();
    }

    @Override
    public List<JSONObject> listJSONObjectQurey(String sql) {
        Connection connection = getConnection();
        Qurey<List<JSONObject>, Object> excuter = new Qurey<>(() -> connection, sql, null, null, (rset) -> {
            return genJsonObjList(rset);
        });
        closeConnection(excuter, connection);
        return excuter.qurey();
    }

    @Override
    public <T> List<JSONObject> listJSONObjectQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {
        Connection connection = getConnection();
        Qurey<List<JSONObject>, T> excuter = new Qurey<>(() -> connection, sql, pre, c, rset -> genJsonObjList(rset));
        closeConnection(excuter, connection);
        return excuter.qurey();
    }

    @Override
    public <T> List<T> listQurey(String sql, Class<T> tClass) {
        return listQurey(sql).stream().map(m -> map2obj(m, tClass)).collect(Collectors.toList());
    }

    @Override
    public <R, T> Qurey<R, T> getQurey(String sql, ConsumerPreparedStatement<T> pre, FunctionResultSet<ResultSet, R> f) {
        Connection connection = getConnection();
        Qurey<R, T> excuter = new Qurey<>(() -> connection, sql, pre, null, f);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <T> Excuter<T> getExcuter(String sql, ConsumerPreparedStatement<T> pre, T c) {
        Connection connection = getConnection();
        Excuter<T> excuter = new Excuter<>(() -> connection, sql, pre, c);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public Excuter getExcuter(String sql) {
        Connection connection = getConnection();
        Excuter excuter = new Excuter(() -> connection, sql, null, null);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <T> Excuter<T> getExcuter(String sql, ConsumerPreparedStatement<T> pre) {
        Connection connection = getConnection();
        Excuter<T> excuter = new Excuter<>(() -> connection, sql, pre, null);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <T> BatchExcuter<T> getBatchExcuter(String sql, ConsumerPreparedStatement<T> pre, T c) {
        Connection connection = getConnection();
        BatchExcuter<T> excuter = new BatchExcuter<>(() -> connection, sql, pre, c);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <T> ListBatchExcuter<T> getListBatchExcuter(String sql, BatchPreparedStatement<T> pre, List<T> c) {
        Connection connection = getConnection();
        ListBatchExcuter<T> excuter = new ListBatchExcuter<>(() -> connection, sql, pre, c);
        closeConnection(excuter, connection);
        return excuter;
    }

    @Override
    public <T> BatchExcuter<T> getBatchExcuter(String sql, ConsumerPreparedStatement<T> pre) {
        Connection connection = getConnection();
        BatchExcuter<T> excuter = new BatchExcuter<>(() -> connection, sql, pre, null);
        closeConnection(excuter, connection);
        return excuter;
    }

    private class DefaultTransaction implements Transaction {
        Long id = Id3sGen.idGen().id();
        int isolation;
        Connection connection;
        //事务开启 0 不开启 1开启 -1 不可用
        int status;

        public DefaultTransaction(int isolation) {
            try {
                connection = dataSource.getConnection();
                connection.setTransactionIsolation(isolation);
            } catch (SQLException e) {
                throw new JdbcException(e);
            }
        }


        public DefaultTransaction() {
            try {
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                throw new JdbcException(e);
            }
        }


        public void commit() {
            if (status == 1) {
                try {
                    connection.commit();
                } catch (SQLException e) {
                    throw new JdbcException(e);
                }
                logger.debug("Transaction commit id {}", id);
            }

        }

        public void rollBack() {

            if (status == 1) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new JdbcException(e);
                } finally {
                    status = -1;
                }
                logger.debug("Transaction rollBack id {}", id);

            }

        }

        public void close() {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                throw new JdbcException(e);
            } finally {
                status = -1;
            }
            logger.debug("Transaction close id {}", id);
        }

        void open() {
            if (status == 0) {
                try {
                    connection.setAutoCommit(false);
                    status = 1;
                } catch (SQLException e) {
                    throw new JdbcException(e);

                }
                logger.debug("Transaction open id {}", id);

            }


        }


    }

}
