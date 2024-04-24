package org.ypf.generic.orm;

import java.util.function.Consumer;

/**
 * @date: 2022/7/8 18:02
 */
public interface ConsumerJDBCExcuterContext extends Consumer<JDBCExcuterContext> {
    void accept(JDBCExcuterContext jdbcExcuterContext, Throwable e);
}
