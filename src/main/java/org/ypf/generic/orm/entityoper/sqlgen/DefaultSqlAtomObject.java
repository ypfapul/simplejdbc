package org.ypf.generic.orm.entityoper.sqlgen;

/**
 * @date: 2022/6/29 15:39
 */
public class DefaultSqlAtomObject implements SqlAtomObject {
    String name;
    String alias;

    public DefaultSqlAtomObject() {
    }

    public DefaultSqlAtomObject(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String alias() {
        return alias;
    }
}
