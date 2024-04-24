package org.ypf.generic.orm.entityoper.defineimpl;

import org.ypf.generic.orm.entityoper.Column;
import org.ypf.generic.orm.entityoper.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2022/6/1 15:41
 */
public class TableImpl implements Table {
    String tableName;
    List<Column> column;

    @Override
    public List<Column> column() {
        return column;
    }


    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Column> getColumn() {
        return column;
    }

    public TableImpl setColumn(List<Column> column) {
        this.column = column;
        return this;
    }

    @Override
    public String getName() {
        return tableName;
    }

    @Override
    public Table mirror() {
        TableImpl tableCopy = new TableImpl();
        tableCopy.tableName = tableName;
        tableCopy.column = new ArrayList<>();
        for (Column column1 : this.column) {
            ColumnImpl columncopy = new ColumnImpl(tableCopy, column1.getName(), column1.bindKey(), column1.isPrimaryId(), column1.strategy());
            columncopy.setIdGenerated(column1.idGenerated());
            tableCopy.column.add(columncopy);
        }
        return tableCopy;
    }
}
