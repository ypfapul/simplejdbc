package org.ypf.generic.orm.entityoper;

/**
 * 运算符
 *
 * @date: 2022/6/1 10:51
 */
public enum OperationSymbol implements OperateSymbol {

    ADD("+"), SUB("-"), MUL("*"), DIV("/"), REMAINDE("%");

    public final String name;

    OperationSymbol(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
