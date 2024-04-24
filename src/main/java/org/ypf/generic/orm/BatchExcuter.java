package org.ypf.generic.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.function.Supplier;

/**
 * @date: 2021/6/22 20:29
 */
public class BatchExcuter<T> extends Excuter<T> {
    static Logger logger = LoggerFactory.getLogger(BatchExcuter.class);

    public BatchExcuter(Supplier<Connection> connectionSupplier, String sql, ConsumerPreparedStatement<T> consumerPreparedStatement, T condition) {
        super(connectionSupplier, sql, consumerPreparedStatement, condition);
    }

    @Override
    protected Integer result() throws Exception {
        int[] ints = preparedStatement.executeBatch();
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        logger.debug("excute result {}", sum);
        return sum;


    }
}
