package org.ypf.generic.orm.transm;

import org.ypf.generic.orm.Session;
import org.ypf.generic.orm.SessionFactory;
import org.ypf.generic.orm.exception.JdbcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ypf.generic.dependency.Id3sGen;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @date: 2022/6/15 9:05
 */
public class JdbcSessionTransactionManager implements TransactionManager {
    public final static List<TransactionManager> TRANSACTION_MANAGERS = new ArrayList<>();
    private static Logger logger = LoggerFactory.getLogger(JdbcSessionTransactionManager.class);
    final ThreadLocal<Stack<JdbcSessionTransactionStatus>> transactionStatusStack = new ThreadLocal<Stack<JdbcSessionTransactionStatus>>() {
        @Override
        protected Stack<JdbcSessionTransactionStatus> initialValue() {
            return new Stack<>();
        }
    };
    SessionFactory sessionFactory;

    public JdbcSessionTransactionManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        TRANSACTION_MANAGERS.add(this);
    }

    @Override
    public TransactionStatus getTransactionStatus(TransactionEnvrioment transactionEnv) {
        JdbcSessionTransactionStatus jdbcSessionTransactionStatus = new JdbcSessionTransactionStatus();
        jdbcSessionTransactionStatus.transactionEnv = transactionEnv;
        int propagation = transactionEnv.propagation();
        //事务隔离级别
        int isolation = transactionEnv.isolation();
//          * 事务传播行为
//                * 0 有加入 无 新建
//                * 1 总是新建
//                * 2 总是无事务
//                * 3 支持当前事务 如果没有则无事务
        if (propagation == 0) {
            JdbcSessionTransactionStatus lastSessionTransactionStatus = getLastSessionTransactionStatus();
            if (lastSessionTransactionStatus != null) {
                if (lastSessionTransactionStatus.session.transaction()) {// 如果开启了事务
                    jdbcSessionTransactionStatus.session = lastSessionTransactionStatus.session;
                    jdbcSessionTransactionStatus.isNew = false;
                } else {//无事务
                    //新建会话
                    jdbcSessionTransactionStatus.session = sessionFactory.getSession();
                    jdbcSessionTransactionStatus.isNew = true;
                    //jdbcSessionTransactionStatus.session.getTransaction();//开启事务
                    if (isolation != Connection.TRANSACTION_NONE) {
                        jdbcSessionTransactionStatus.session.getTransaction(isolation);//开启事务
                    } else {
                        jdbcSessionTransactionStatus.session.getTransaction();//开启事务
                    }

                }

            } else {
                //新建会话
                jdbcSessionTransactionStatus.session = sessionFactory.getSession();
                jdbcSessionTransactionStatus.isNew = true;
                //jdbcSessionTransactionStatus.session.getTransaction();//开启新的事务
                if (isolation != Connection.TRANSACTION_NONE) {
                    jdbcSessionTransactionStatus.session.getTransaction(isolation);//开启事务
                } else {
                    jdbcSessionTransactionStatus.session.getTransaction();//开启事务
                }
            }

        } else if (propagation == 1) {
            //新建会话
            jdbcSessionTransactionStatus.session = sessionFactory.getSession();
            jdbcSessionTransactionStatus.isNew = true;
            //jdbcSessionTransactionStatus.session.getTransaction();//开启新的事务
            if (isolation != Connection.TRANSACTION_NONE) {
                jdbcSessionTransactionStatus.session.getTransaction(isolation);//开启事务
            } else {
                jdbcSessionTransactionStatus.session.getTransaction();//开启事务
            }
        } else if (propagation == 2) {
            //新建会话
            JdbcSessionTransactionStatus lastSessionTransactionStatus = getLastSessionTransactionStatus();
            if (lastSessionTransactionStatus != null) {
                if (lastSessionTransactionStatus.session.transaction()) {// 如果开启了事务
                    jdbcSessionTransactionStatus.session = sessionFactory.getSession();
                    jdbcSessionTransactionStatus.isNew = true;

                } else {//无事务
                    jdbcSessionTransactionStatus.session = lastSessionTransactionStatus.session;
                    jdbcSessionTransactionStatus.isNew = false;

                }

            } else {
                //新建无事务会话
                jdbcSessionTransactionStatus.session = sessionFactory.getSession();
                jdbcSessionTransactionStatus.isNew = true;

            }


        } else if (propagation == 3) {
            JdbcSessionTransactionStatus lastSessionTransactionStatus = getLastSessionTransactionStatus();
            if (lastSessionTransactionStatus != null) {
                if (lastSessionTransactionStatus.session.transaction()) {// 如果开启了事务
                    jdbcSessionTransactionStatus.session = lastSessionTransactionStatus.session;
                    jdbcSessionTransactionStatus.isNew = false;
                } else {//无事务
                    //新建无事务会话
                    jdbcSessionTransactionStatus.session = sessionFactory.getSession();
                    jdbcSessionTransactionStatus.isNew = true;

                }

            } else {
                //新建会话
                jdbcSessionTransactionStatus.session = sessionFactory.getSession();
                jdbcSessionTransactionStatus.isNew = true;
            }
        }

        getStack().push(jdbcSessionTransactionStatus);
        logger.debug("transactionStatus id {} get propagation {} sessionId {} isNew {} ", jdbcSessionTransactionStatus.id, propagation, jdbcSessionTransactionStatus.isNew, jdbcSessionTransactionStatus.getSession().id());
        return jdbcSessionTransactionStatus;
    }

    /**
     * 获取最近的状态
     *
     * @return
     */
    JdbcSessionTransactionStatus getLastSessionTransactionStatus() {
        if (getStack().empty())
            return null;
        return getStack().peek();
    }

    Stack<JdbcSessionTransactionStatus> getStack() {
        return transactionStatusStack.get();
    }

    public class JdbcSessionTransactionStatus implements TransactionStatus {

        Session session;
        /**
         * 表示是否新建
         */
        boolean isNew;
        TransactionEnvrioment transactionEnv;
        long id = Id3sGen.idGen().id();


        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        @Override
        public long id() {
            return id;
        }

        @Override
        public TransactionEnvrioment getTransactionEnvrioment() {
            return transactionEnv;
        }

        @Override
        public void commit() {
            if (isNew) {
                if (session.transaction()) {
                    session.getTransaction().commit();
                }
            }
            logger.debug("transactionStatus id {} commit", id);
        }

        @Override
        public void rollBack() {
            if (isNew) {
                if (session.transaction()) {
                    session.getTransaction().rollBack();
                }
            }
            logger.debug("transactionStatus id {} rollBack", id);
        }

        public void close() {
            //检查栈顶是否是本身
            if (getStack().peek() == this) {
                getStack().pop();
            } else {
                throw new JdbcException("transactionStatus close is not self");
            }
            if (isNew) {
                if (session.transaction()) {
                    session.getTransaction().close();
                }
            }
            logger.debug("transactionStatus id {} close", id);
        }


    }
}
