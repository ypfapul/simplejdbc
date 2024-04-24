package org.ypf.generic.orm.entityoper.readmapperconf;

import java.util.List;

/**
 * @date: 2022/6/2 17:19
 */
public class TableBean {
    /**
     * 表名
     */
    String name;
    List<ColumnBean> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ColumnBean> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnBean> columns) {
        this.columns = columns;
    }
}
