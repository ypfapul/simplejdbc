package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;

import java.util.List;

/**
 * 表管理器
 *
 * @date: 2022/6/21 11:35
 */
public interface TableDefinitionGetter {
    /**
     * 所有表
     *
     * @return
     */
    List<TableBean> allTable();

    /**
     * 根据
     *
     * @param table
     * @return
     */
    TableBean table(String table);

    /**
     * @param like
     * @return
     */
    List<TableBean> likeTable(String like);

    /**
     * 正则表达式匹配
     *
     * @param regular
     * @return
     */
    List<TableBean> regular(String regular);
}
