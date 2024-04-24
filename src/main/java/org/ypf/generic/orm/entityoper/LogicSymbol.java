package org.ypf.generic.orm.entityoper;

/**
 * 逻辑符
 *
 * @date: 2022/6/1 10:51
 */
public enum LogicSymbol implements OperateSymbol {

    AND("and"), OR("or"), REVERSE("!");

    public final String name;

    LogicSymbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
