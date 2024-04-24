package org.ypf.generic.orm;

import org.ypf.generic.orm.entityoper.DbEnviment;
import org.ypf.generic.orm.exception.JdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @date: 2021/6/23 8:45
 */
public abstract class BaseExcuter<R, T> implements DbEnviment {
    private static Logger logger = LoggerFactory.getLogger(BaseExcuter.class);
    protected final JDBCExcuterContext jdbcExcuterContext = new JDBCExcuterContext();
    protected Supplier<Connection> connectionSupplier;
    protected PreparedStatement preparedStatement;
    protected String sql;
    protected ConsumerPreparedStatement<T> consumerPreparedStatement;
    protected T condition;
    protected int dbType;
    boolean isPreparedStatementProxy;
    List<JDBCExcuterContextConsumer> whenAfter;
    List<JDBCExcuterContextConsumer> whenBefore;
    List<JDBCExcuterContextConsumer> whenException;
    List<JDBCExcuterContextConsumer> whenFinally;

    {
        if (GConfig.preparedStatementProxy)
            isPreparedStatementProxy = true;
    }

    public BaseExcuter() {
    }

    public BaseExcuter(Supplier<Connection> connectionSupplier, String sql, ConsumerPreparedStatement<T> consumerPreparedStatement, T condition) {
        this.connectionSupplier = connectionSupplier;
        this.sql = sql;
        this.consumerPreparedStatement = consumerPreparedStatement;
        this.condition = condition;
        jdbcExcuterContext.setCondition(condition);
        jdbcExcuterContext.setSql(sql);
    }

    protected abstract R result() throws Exception;

    protected PreparedStatement genPreparedStatement() throws SQLException {
        return connectionSupplier.get().prepareStatement(sql);
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    @Override
    public int dbType() {
        return dbType;
    }

    public JDBCExcuterContext getJdbcExcuterContext() {
        return jdbcExcuterContext;
    }

    public void addWhenAfter(JDBCExcuterContextConsumer contextConsumer) {

        if (whenAfter == null) {
            whenAfter = new ArrayList<>();
        }
        whenAfter.add(contextConsumer);
    }

    public void addWhenBefore(JDBCExcuterContextConsumer contextConsumer) {

        if (whenBefore == null) {
            whenBefore = new ArrayList<>();
        }
        whenBefore.add(contextConsumer);
    }

    public void addWhenException(JDBCExcuterContextConsumer contextConsumer) {
        if (whenException == null) {
            whenException = new ArrayList<>();
        }
        whenException.add(contextConsumer);
    }

    public void addWhenFinally(JDBCExcuterContextConsumer contextConsumer) {
        if (whenFinally == null) {
            whenFinally = new ArrayList<>();
        }
        whenFinally.add(contextConsumer);
    }

    public void setConnectionSupplier(Supplier<Connection> connectionSupplier) {
        this.connectionSupplier = connectionSupplier;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    public void setPreparedStatementProxy(boolean preparedStatementProxy) {
        isPreparedStatementProxy = preparedStatementProxy;
    }

    public R excute() {
        LocalDateTime be = LocalDateTime.now();
        logger.debug("excute sql > {}", sql);
        if (whenBefore != null) {
            whenBefore.forEach(e -> e.accept(jdbcExcuterContext));
        }
        R re = null;
        try {
            if (isPreparedStatementProxy) {
                preparedStatement = new PreparedStatementWrapper(1, genPreparedStatement());
            } else {
                preparedStatement = genPreparedStatement();
            }

            if (consumerPreparedStatement != null) {
                consumerPreparedStatement.accept(preparedStatement, condition);
            }

            re = result();
            if (isPreparedStatementProxy && preparedStatement instanceof PreparedStatementWrapper) {
                PreparedStatementWrapper preparedStatementProxy = (PreparedStatementWrapper) preparedStatement;
                logger.debug("excute params > {}", preparedStatementProxy.getIndexParams());

            }
            if (whenAfter != null) {
                whenAfter.forEach(e -> e.accept(jdbcExcuterContext));
            }
            LocalDateTime en = LocalDateTime.now();
            logger.debug("excute time {} ", ChronoUnit.MILLIS.between(be, en));
        } catch (Throwable e) {
            logger.error("error", e);
            jdbcExcuterContext.whenThrowable = e;
            if (whenException != null) {
                whenException.forEach(e1 -> e1.accept(jdbcExcuterContext));
            }
            throw new JdbcException(e);

        } finally {

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new JdbcException(e);
                }
            }

            if (whenFinally != null) {
                whenFinally.forEach(e -> e.accept(jdbcExcuterContext));
            }

        }

        return re;

    }
}
