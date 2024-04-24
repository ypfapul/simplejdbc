package org.ypf.generic.orm;

/**
 * @date: 2021/6/22 19:08
 */
@FunctionalInterface
public interface FunctionResultSet<ResultSet, R> {

    R apply(ResultSet t) throws Exception;


}
