package org.ypf.generic.orm;

import java.util.HashMap;
import java.util.Map;

/**
 * Excuter 执行上下文环境
 *
 * @date: 2022/6/15 15:47
 */
public final class JDBCExcuterContext {
    Throwable whenThrowable;
    String sql;
    Object condition;

    void setWhenThrowable(Throwable whenThrowable) {
        this.whenThrowable = whenThrowable;
    }

    void setSql(String sql) {
        this.sql = sql;
    }

    void setCondition(Object condition) {
        this.condition = condition;
    }

    public String getSql() {
        return sql;
    }

    public Object getCondition() {
        return condition;
    }

    private Map<String, Object> data = new HashMap<>();

    JDBCExcuterContext() {

    }

    public Throwable getWhenThrowable() {
        return whenThrowable;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void put(String key, Object v) {
        data.put(key, v);
    }
}
