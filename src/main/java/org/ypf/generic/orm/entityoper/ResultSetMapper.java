package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.JdbcType;

import java.sql.ResultSet;

/**
 * @date: 2022/6/1 17:44
 */
public interface ResultSetMapper {
    Object getValue(ResultSet ps, ColumFieldBinding cfBinding, int cfBindingIndex, JdbcType jdbcType);
}
