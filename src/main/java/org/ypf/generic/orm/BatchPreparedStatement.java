package org.ypf.generic.orm;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * @date: 2022/7/8 12:03
 */
public interface BatchPreparedStatement<E> extends ConsumerPreparedStatement<List<E>> {
    void acc(PreparedStatement p, E con);

    @Override
    default void accept(PreparedStatement p, List<E> con) throws Exception {
        for (E e : con) {
            acc(p, e);
            p.addBatch();
        }
    }
}
