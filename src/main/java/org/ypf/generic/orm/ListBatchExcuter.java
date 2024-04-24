package org.ypf.generic.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @date: 2021/6/22 20:29
 */
public class ListBatchExcuter<T> extends BatchExcuter<List<T>> {
    static Logger logger = LoggerFactory.getLogger(ListBatchExcuter.class);

    public ListBatchExcuter(Supplier<Connection> connectionSupplier, String sql, BatchPreparedStatement<T> consumerPreparedStatement, List<T> condition) {
        super(connectionSupplier, sql, consumerPreparedStatement, condition);
    }
}
