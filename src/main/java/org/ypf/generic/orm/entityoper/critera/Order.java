package org.ypf.generic.orm.entityoper.critera;

import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.entityoper.SqlKeywords;
import org.ypf.generic.orm.entityoper.sqlgen.SqlAtomObject;

/**
 * 排序
 *
 * @date: 2022/6/30 14:50
 */
public class Order implements SqlAtomObject {


    // 排序的序号
    int index;
    // 升序或降序0 升序
    int sc;
    ColumFieldBinding columFieldBinding;
    String alias;

    public Order() {
    }

    public Order(int index, int sc, ColumFieldBinding columFieldBinding) {
        this.index = index;
        this.sc = sc;
        this.columFieldBinding = columFieldBinding;
    }

    public static Order descOrder(ColumFieldBinding columFieldBinding) {
        Order order = new Order();
        order.setColumFieldBinding(columFieldBinding);
        order.setSc(1);
        return order;
    }

    public static Order descOrder(ColumFieldBinding columFieldBinding, int index) {
        Order order = new Order();
        order.setColumFieldBinding(columFieldBinding);
        order.setIndex(index);
        order.setSc(1);
        return order;
    }

    public static Order ascOrder(ColumFieldBinding columFieldBinding) {
        Order order = new Order();
        order.setColumFieldBinding(columFieldBinding);
        return order;
    }

    public static Order ascOrder(ColumFieldBinding columFieldBinding, int index) {
        Order order = new Order();
        order.setColumFieldBinding(columFieldBinding);
        order.setIndex(index);
        return order;
    }


    public String express() {
        String name = columFieldBinding.column().getName();
        if (sc != 0) {
            return new StringBuilder(name).append(SqlKeywords.DESC.leftBlank()).toString();
        }
        return name;

    }

    public String express(String namespace) {
        String name = columFieldBinding.column().getName();
        StringBuilder stringBuilder = new StringBuilder(namespace);
        stringBuilder.append(SqlKeywords.DOT.value);
        stringBuilder.append(name);
        if (sc != 0) {
            stringBuilder.append(SqlKeywords.DESC.leftBlank());

        }
        return stringBuilder.toString();

    }


    public ColumFieldBinding getColumFieldBinding() {
        return columFieldBinding;
    }

    public void setColumFieldBinding(ColumFieldBinding columFieldBinding) {
        this.columFieldBinding = columFieldBinding;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public int getSc() {
        return sc;
    }

    public void setSc(int sc) {
        this.sc = sc;
    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String alias() {
        return null;
    }
}
