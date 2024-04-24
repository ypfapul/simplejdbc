package org.ypf.generic.orm.transm;


/**
 * 事务定义环境
 *
 * @date: 2022/6/15 9:02
 */
public interface TransactionEnvrioment {
    /**
     * 事务传播行为
     * 0 有加入 无 新建
     * 1 总是新建
     * 2 无事务
     * 3 支持当前事务 如果没有则无事务
     *
     * @return
     */
    int propagation();

    boolean readOnly();

    int isolation();

    int timeout();

    Class<? extends Throwable>[] rollbackFor();


    Class<? extends Throwable>[] noRollbackFor();


}
