package org.ypf.generic.orm.transm;

/**
 * 事务管理器
 *
 * @date: 2022/6/15 8:55
 */
public interface TransactionManager {

    public TransactionStatus getTransactionStatus(TransactionEnvrioment transactionEnv);


}
