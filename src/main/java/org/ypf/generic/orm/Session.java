package org.ypf.generic.orm;

/**
 * 数据持久模板
 *
 * @date: 2021/6/28 18:19
 */
public interface Session extends DaoTemplete {
    long id();

    /**
     * 是否开启事务
     *
     * @return
     */
    boolean transaction();

    /**
     * 获取事务对象
     * 开启事务
     *
     * @return
     */
    Transaction getTransaction();

    /**
     * 获取隔离级别事务对象
     * 开启事务
     *
     * @return
     */
    Transaction getTransaction(int isolation);

}
