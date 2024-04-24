package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.JdbcType;

import java.sql.PreparedStatement;

/**
 * @date: 2022/6/1 16:20
 */
public interface PreparedStatementSetMathcer {
    void setValues(PreparedStatement ps, ColumFieldBinding cfBinding, int cfBindingIndex, JdbcType jdbcType, Object value);
}
