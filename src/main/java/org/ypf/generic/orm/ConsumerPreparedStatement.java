package org.ypf.generic.orm;

import java.sql.PreparedStatement;


@FunctionalInterface
public interface ConsumerPreparedStatement<E> {
    void accept(PreparedStatement p, E con) throws Exception;

}
