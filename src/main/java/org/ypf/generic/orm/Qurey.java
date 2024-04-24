package org.ypf.generic.orm;

import org.ypf.generic.orm.exception.JdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.function.Supplier;

/**
 * 查询对象
 * R 返回类型
 * T 条件参数
 *
 * @date: 2021/6/22 18:25
 */
public class Qurey<R, T> extends BaseExcuter<R, T> {
    static Logger logger = LoggerFactory.getLogger(Qurey.class);
    FunctionResultSet<ResultSet, R> functionResultSet;

    public Qurey(Supplier<Connection> connectionSupplier, String sql, ConsumerPreparedStatement<T> consumerPreparedStatement, T condition, FunctionResultSet<ResultSet, R> functionResultSet) {
        super(connectionSupplier, sql, consumerPreparedStatement, condition);
        this.functionResultSet = functionResultSet;
    }

    @Override
    protected R result() throws Exception {
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
            if (isPreparedStatementProxy) {
                resultSet = new ResultSetWrapper(resultSet);
            }

            R r = functionResultSet.apply(resultSet);
            if (isPreparedStatementProxy) {
                ResultSetWrapper resultSetWrapper = (ResultSetWrapper) resultSet;
                logger.debug("qurey result rowtotal {}", resultSetWrapper.getResultSetRowNum());
            }
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error", e);
            throw new JdbcException(e);

        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

        }

    }

    public void setFunctionResultSet(FunctionResultSet<ResultSet, R> functionResultSet) {
        this.functionResultSet = functionResultSet;
    }

    public R qurey() {
        return excute();
    }

}
