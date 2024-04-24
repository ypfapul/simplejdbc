package org.ypf.generic.orm;

import com.alibaba.fastjson.JSONObject;
import org.ypf.generic.orm.exception.JdbcException;
import org.ypf.generic.orm.transm.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 支持事务管理
 * JDBC模板
 *
 * @date: 2021/6/28 18:26
 */

public class JdbcTemplete implements DaoTemplete {
    final static TransactionEnvrioment TRANSACTION_ENV = new DefaultTransactionEnv(3);
    private JdbcSessionTransactionManager transactionManager;

    public JdbcTemplete(JdbcSessionTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public JdbcTemplete(SessionFactory sessionFactory) {
        transactionManager = new JdbcSessionTransactionManager(sessionFactory);
    }

    public JdbcTemplete(DataSource dataSource) {
        SessionFactory sessionFactory = new SessionFactory(dataSource);
        transactionManager = new JdbcSessionTransactionManager(sessionFactory);
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public JdbcSessionTransactionManager.JdbcSessionTransactionStatus getTransactionStatus() {
        return (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionManager.getTransactionStatus(TRANSACTION_ENV);
    }

    @Override
    public <R, T> Qurey<R, T> getQureyBySqlPrepared(SqlParamsBind sql, FunctionResultSet<ResultSet, R> f) {
        return new ResultTemp<Qurey<R, T>>(0) {
            @Override
            public Qurey<R, T> r(Session session) {
                return session.getQureyBySqlPrepared(sql, f);
            }
        }.run();

    }

    @Override
    public <T> Excuter<T> getExcuterBySqlPrepared(SqlParamsBind sql) {
        return new ResultTemp<Excuter<T>>(1) {
            @Override
            public Excuter<T> r(Session session) {
                return session.getExcuterBySqlPrepared(sql);
            }
        }.run();
    }

    @Override
    public List<Map<String, Object>> listQurey(SqlParamsBind sql) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQurey(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }

    }

    @Override
    public ResultSetProxy listQureyResultSetProxy(SqlParamsBind sql) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQureyResultSetProxy(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <R, T> Qurey<R, T> getQurey(String sql, ConsumerPreparedStatement<T> pre, FunctionResultSet<ResultSet, R> f, T c) {
        return new ResultTemp<Qurey<R, T>>(0) {
            @Override
            public Qurey<R, T> r(Session session) {
                return session.getQurey(sql, pre, f, c);
            }
        }.run();
    }

    @Override
    public <R, T> Qurey<R, T> getQurey(String sql, FunctionResultSet<ResultSet, R> f) {
        return new ResultTemp<Qurey<R, T>>(0) {
            @Override
            public Qurey<R, T> r(Session session) {
                return session.getQurey(sql, f);
            }
        }.run();
    }


    @Override
    public Object objectQurey(String sql) {
        Object r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().objectQurey(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }

    }

    @Override
    public <T> Object objectQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {

        Object r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().objectQurey(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }


    }

    @Override
    public Number numberQurey(String sql) {


        Number r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().numberQurey(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public Long longQurey(String sql) {


        Long r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().longQurey(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <T> Long longQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {


        Long r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().longQurey(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <T> Number numberQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {


        Number r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().numberQurey(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }

    }

    @Override
    public List<Map<String, Object>> listQurey(String sql, ConsumerPreparedStatement<Object> pre) {


        List<Map<String, Object>> r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQurey(sql, pre);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }

    }

    @Override
    public <T> List<Map<String, Object>> listQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {


        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQurey(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }

    }

    @Override
    public <T> List<LinkedHashMap<String, Object>> listLinkedHashMap(String sql, ConsumerPreparedStatement<T> pre, T c) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listLinkedHashMap(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <T> ResultSetProxy listQureyResultSetProxy(String sql, ConsumerPreparedStatement<T> pre, T c) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQureyResultSetProxy(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public List<Map<String, Object>> listQurey(String sql) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQurey(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }


    }

    @Override
    public List<JSONObject> listJSONObjectQurey(String sql) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listJSONObjectQurey(sql);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <T> List<JSONObject> listJSONObjectQurey(String sql, ConsumerPreparedStatement<T> pre, T c) {
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listJSONObjectQurey(sql, pre, c);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <T> List<T> listQurey(String sql, Class<T> tClass) {


        List<T> r = null;
        TransactionRunnableRunner transactionRunnableRunner = new TransactionRunnableRunner(transactionManager);
        try {
            return transactionRunnableRunner.run(transactionStatus -> {
                JdbcSessionTransactionManager.JdbcSessionTransactionStatus jt = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionStatus;
                return jt.getSession().listQurey(sql, tClass);
            }, TRANSACTION_ENV);
        } catch (Throwable e) {
            throw new JdbcException(e);

        }
    }

    @Override
    public <R, T> Qurey<R, T> getQurey(String sql, ConsumerPreparedStatement<T> pre, FunctionResultSet<ResultSet, R> f) {

        return new ResultTemp<Qurey<R, T>>(0) {
            @Override
            public Qurey<R, T> r(Session session) {
                return session.getQurey(sql, pre, f);
            }
        }.run();

    }

    @Override
    public <T> Excuter getExcuter(String sql, ConsumerPreparedStatement<T> pre, T c) {


        return new ResultTemp<Excuter>(1) {
            @Override
            public Excuter r(Session session) {
                return session.getExcuter(sql, pre, c);
            }
        }.run();
    }

    @Override
    public Excuter getExcuter(String sql) {

        return new ResultTemp<Excuter>(1) {
            @Override
            public Excuter r(Session session) {
                return session.getExcuter(sql);
            }
        }.run();
    }

    @Override
    public <T> Excuter getExcuter(String sql, ConsumerPreparedStatement<T> pre) {

        return new ResultTemp<Excuter>(1) {
            @Override
            public Excuter r(Session session) {
                return session.getExcuter(sql, pre);
            }
        }.run();
    }

    @Override
    public <T> BatchExcuter getBatchExcuter(String sql, ConsumerPreparedStatement<T> pre, T c) {
        return new ResultTemp<BatchExcuter>(1) {
            @Override
            public BatchExcuter r(Session session) {
                return session.getBatchExcuter(sql, pre, c);
            }
        }.run();
    }

    @Override
    public <T> ListBatchExcuter<T> getListBatchExcuter(String sql, BatchPreparedStatement<T> pre, List<T> c) {
        return new ResultTemp<ListBatchExcuter>(1) {
            @Override
            public ListBatchExcuter r(Session session) {
                return session.getListBatchExcuter(sql, pre, c);
            }
        }.run();
    }

    @Override
    public <T> BatchExcuter getBatchExcuter(String sql, ConsumerPreparedStatement<T> pre) {


        return new ResultTemp<BatchExcuter>(1) {
            @Override
            public BatchExcuter r(Session session) {
                return session.getBatchExcuter(sql, pre);
            }
        }.run();
    }

    abstract class ResultTemp<E extends BaseExcuter> {
        // 0读 1写
        int readWriteState;

        public ResultTemp(int readWriteState) {
            this.readWriteState = readWriteState;
        }

        public abstract E r(Session session);

        public E run() {
            E r = null;
            JdbcSessionTransactionManager.JdbcSessionTransactionStatus transactionStatus = (JdbcSessionTransactionManager.JdbcSessionTransactionStatus) transactionManager.getTransactionStatus(TRANSACTION_ENV);
            Class<? extends Throwable>[] noRollbackFor = transactionStatus.getTransactionEnvrioment().noRollbackFor();
            Class<? extends Throwable>[] rollbackFor = transactionStatus.getTransactionEnvrioment().rollbackFor();
            if (transactionStatus.getTransactionEnvrioment().readOnly() && readWriteState == 1) {
                throw new JdbcException("TransactionEnvrioment only read");

            }
            r = r(transactionStatus.getSession());
            r.addWhenAfter(e -> transactionStatus.commit());
            r.addWhenException(e -> {
                if (TransactionRunnableRunner.isRollback(e.getWhenThrowable(), noRollbackFor, rollbackFor)) {
                    transactionStatus.rollBack();
                }
            });
            r.addWhenFinally(e -> transactionStatus.close());
            return r;
        }
    }


}
