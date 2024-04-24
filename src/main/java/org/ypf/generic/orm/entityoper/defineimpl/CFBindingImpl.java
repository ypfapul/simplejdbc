package org.ypf.generic.orm.entityoper.defineimpl;


import org.ypf.generic.orm.entityoper.*;
import org.ypf.generic.orm.entityoper.condition.BasicConditions;
import org.ypf.generic.orm.entityoper.condition.Conditions;
import org.ypf.generic.orm.entityoper.condition.DefaultValueExpress;
import org.ypf.generic.orm.entityoper.condition.Express;

import java.util.Objects;

/**
 * @date: 2022/6/1 15:45
 */
class CFBindingImpl implements ColumFieldBinding {
    boolean suspend;
    String cFBindingName;
    Column column;
    JavaField javaField;
    EntityMapper entityMapper;

    public CFBindingImpl(String cFBindingName, Column column, JavaField javaField) {
        this.cFBindingName = cFBindingName;
        this.column = column;
        this.javaField = javaField;
    }

    public void setEntityMapper(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    @Override
    public EntityMapper entityMapper() {
        return entityMapper;
    }

    @Override
    public boolean isPrimaryId() {
        return column.isPrimaryId();
    }

    @Override
    public PrimaryStrategy strategy() {
        return column.strategy();
    }

    @Override
    public IdGenerated idGenerated() {
        return column.idGenerated();
    }

    @Override
    public boolean isSuspend() {
        return suspend;
    }

    @Override
    public void suspend() {
        suspend = true;

    }

    @Override
    public void reload() {
        suspend = false;
    }


    @Override
    public Column column() {
        return column;
    }

    @Override
    public JavaField field() {
        return javaField;
    }

    @Override
    public Conditions newConditions(CompareSymbol compareSymbol, Object value) {
        Objects.requireNonNull(value);
        Express express = null;
        if (value instanceof Express) {
            express = (Express) value;

        } else {
            DefaultValueExpress defaultValueExpress = new DefaultValueExpress(value);
            if (compareSymbol == CompareSymbol.LIKE) {
                defaultValueExpress.setContext(112);
            }
            express = defaultValueExpress;
        }

        return newConditions(compareSymbol, express);
    }

    @Override
    public Conditions newConditions(CompareSymbol compareSymbol, Express express) {

        return new BasicConditions(this, express, compareSymbol);
    }


    @Override
    public String getName() {
        return cFBindingName;
    }

    @Override
    public String express() {
        return column.getName();
    }


}
