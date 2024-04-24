package org.ypf.generic.orm.entityoper.jdbcdemo;

import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.JdbcType;
import org.ypf.generic.orm.entityoper.ResultSetMapper;
import org.ypf.generic.orm.exception.JdbcException;

import java.sql.ResultSet;

/**
 * @date: 2022/6/1 17:46
 */
public class ResultSetMapperImpl implements ResultSetMapper {
    @Override
    public Object getValue(ResultSet resultSet, ColumFieldBinding cfBinding, int index, JdbcType jdbcType) {
        try {
            return jdbcType.getValue(index, resultSet);
        } catch (Exception e) {
            throw new JdbcException(e);
        }
    }
}
