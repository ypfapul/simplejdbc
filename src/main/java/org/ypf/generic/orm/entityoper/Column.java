package org.ypf.generic.orm.entityoper;

/**
 * @date: 2022/5/30 18:18
 */
public interface Column extends Naming {
    Table table();

    /**
     * 是否是主键
     *
     * @return
     */
    boolean isPrimaryId();

    /**
     * 主键策略
     *
     * @return
     */
    PrimaryStrategy strategy();

    /**
     * 主键本地生成策略
     */
    IdGenerated idGenerated();

    String bindKey();

}
