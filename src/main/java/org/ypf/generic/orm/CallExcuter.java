package org.ypf.generic.orm;

import java.sql.*;
import java.util.function.Supplier;

/**
 * 储存过程调用
 *
 * @date: 2021/6/22 19:27
 */
public class CallExcuter<R, T> extends BaseExcuter<R, T> {

    public CallExcuter(Supplier<Connection> connectionSupplier, String sql, ConsumerPreparedStatement<T> consumerPreparedStatement, T condition) {
        super(connectionSupplier, sql, consumerPreparedStatement, condition);
    }

    protected R getOut(CallableStatement callableStatement) throws Exception {
        return null;

    }


    @Override
    protected R result() throws Exception {
        preparedStatement.executeUpdate();
        return getOut((CallableStatement) preparedStatement);
    }

    protected PreparedStatement genPreparedStatement() throws SQLException {
        return connectionSupplier.get().prepareCall(sql);
    }


}
