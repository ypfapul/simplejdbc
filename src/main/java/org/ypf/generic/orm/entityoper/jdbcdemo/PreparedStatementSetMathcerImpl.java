package org.ypf.generic.orm.entityoper.jdbcdemo;

import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.JdbcType;
import org.ypf.generic.orm.entityoper.PreparedStatementSetMathcer;
import org.ypf.generic.orm.exception.JdbcException;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @date: 2022/6/1 16:53
 */
public class PreparedStatementSetMathcerImpl implements PreparedStatementSetMathcer {
    @Override
    public void setValues(PreparedStatement ps, ColumFieldBinding cfBinding, int index, JdbcType jdbcType, Object value) {
        try {
            jdbcType.set(value, index, ps);
        } catch (SQLException e) {
            throw new JdbcException(e);
        }
    }
}
