package org.ypf.generic.orm.transm;

/**
 * 事务运行环境
 *
 * @date: 2022/6/20 9:19
 */
public interface TransactionRunnable {
    void apply(TransactionStatus transactionStatus) throws Throwable;
}
