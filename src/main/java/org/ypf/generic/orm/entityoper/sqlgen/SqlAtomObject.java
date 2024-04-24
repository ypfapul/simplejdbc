package org.ypf.generic.orm.entityoper.sqlgen;

/**
 * sql 原子对象语义
 * 如表 字段
 *
 * @date: 2022/6/28 18:28
 */
public interface SqlAtomObject {
    // 字段名称
    String name();

    //别名
    String alias();
}
