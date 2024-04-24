package org.ypf.generic.orm;

/**
 * 事务会话
 */
public interface Transaction {

    // 提交
    public void commit();

    // 回滚
    public void rollBack();

    // 关闭资源
    public void close();


}
