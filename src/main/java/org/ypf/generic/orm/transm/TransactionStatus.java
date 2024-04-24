package org.ypf.generic.orm.transm;


import org.ypf.generic.orm.Transaction;

/**
 * 事务
 *
 * @date: 2022/6/15 9:08
 */
public interface TransactionStatus extends Transaction {
    long id();

    /**
     * 事务环境
     *
     * @return
     */
    TransactionEnvrioment getTransactionEnvrioment();


}
