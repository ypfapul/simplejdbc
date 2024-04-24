package org.ypf.generic.orm.entityoper;

/**
 * 数据库环境
 *
 * @date: 2022/7/7 14:50
 */
public interface DbEnviment {
    /**
     * 数据库类型
     * 默认oracle
     * 1 mysql
     */
    int dbType();

}
