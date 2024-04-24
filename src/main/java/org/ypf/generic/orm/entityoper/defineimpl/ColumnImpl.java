package org.ypf.generic.orm.entityoper.defineimpl;

import org.ypf.generic.orm.entityoper.Column;
import org.ypf.generic.orm.entityoper.IdGenerated;
import org.ypf.generic.orm.entityoper.PrimaryStrategy;
import org.ypf.generic.orm.entityoper.Table;

/**
 * @date: 2022/6/1 15:41
 */
public class ColumnImpl implements Column {
    Table table;
    String columnName;
    String bindKey;
    boolean isPrimaryId;
    PrimaryStrategy primaryStrategy;
    IdGenerated idGenerated;

    public void setIdGenerated(IdGenerated idGenerated) {
        this.idGenerated = idGenerated;
    }

    public Table getTable() {
        return table;
    }

    public ColumnImpl(Table table, String columnName, String bindKey, boolean isPrimaryId, PrimaryStrategy primaryStrategy) {
        this.table = table;
        this.columnName = columnName;
        this.bindKey = bindKey;
        this.isPrimaryId = isPrimaryId;
        this.primaryStrategy = primaryStrategy;
    }

    @Override
    public Table table() {
        return table;
    }

    @Override
    public boolean isPrimaryId() {
        return isPrimaryId;
    }

    @Override
    public PrimaryStrategy strategy() {
        return primaryStrategy;
    }

    @Override
    public IdGenerated idGenerated() {
        return idGenerated;
    }

    @Override
    public String bindKey() {
        return bindKey;
    }


    @Override
    public String getName() {
        return columnName;
    }


}
