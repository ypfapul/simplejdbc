package org.ypf.generic.orm.entityoper.condition;


import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.entityoper.CompareSymbol;
import org.ypf.generic.orm.entityoper.LogicSymbol;

import java.util.Objects;


/**
 * 条件构造
 *
 * @date: 2022/5/30 18:17
 */
public class BasicConditions extends CompositeExpress implements Conditions {
    protected CompareSymbol compareSymbol;

    public BasicConditions(Express firstExpress, Express secondExpress, CompareSymbol compareSymbol) {
        super(firstExpress, secondExpress, compareSymbol);
        this.compareSymbol = compareSymbol;
        this.atom = true;
    }

    public static BasicConditions newJoinRelation(CompareSymbol compareSymbol, ColumFieldBinding first, ColumFieldBinding second) {
        BasicConditions basicConditions = new BasicConditions(first, second, compareSymbol);
        return new BasicConditions(first, second, compareSymbol);

    }

    public static BasicConditions mt(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.MT, first, second);
    }

    public static BasicConditions me(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.ME, first, second);
    }

    public static BasicConditions eq(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.EQ, first, second);
    }

    public static BasicConditions noeq(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.NOEQ, first, second);
    }

    public static BasicConditions lt(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.LT, first, second);
    }

    public static BasicConditions le(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.LE, first, second);
    }

    public static BasicConditions like(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.LIKE, first, second);
    }

    public static BasicConditions nolike(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.NOLIKE, first, second);
    }

    public static BasicConditions contains(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.CONTAINS, first, second);
    }

    public static BasicConditions nocontains(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.NOCONTAINS, first, second);
    }

    public static BasicConditions in(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.IN, first, second);
    }

    public static BasicConditions noin(ColumFieldBinding first, ColumFieldBinding second) {
        return newJoinRelation(CompareSymbol.NOIN, first, second);
    }

    public static BasicConditions isNull(ColumFieldBinding first) {
        return newJoinRelation(CompareSymbol.IS, first, null);
    }

    public static BasicConditions isnotNull(ColumFieldBinding first) {
        return newJoinRelation(CompareSymbol.NOIS, first, null);
    }

    public BasicConditions newJoinRelation(Express first, Express second, CompareSymbol compareSymbol) {
        Objects.requireNonNull(firstExpress);
        Objects.requireNonNull(firstExpress);
        Objects.requireNonNull(compareSymbol);
        return new BasicConditions(first, second, compareSymbol);
    }

    public void setCompareSymbol(CompareSymbol compareSymbol) {
        this.compareSymbol = compareSymbol;
        this.operateSymbol = compareSymbol;
    }

    public BasicConditions copy(CompareSymbol compareSymbol) {
        return new BasicConditions(firstExpress, secondExpress, compareSymbol);
    }


    @Override
    public Conditions and(Conditions conditions) {
        if (conditions == EMPTY)
            return EMPTY;
        if (conditions == FULL)
            return this;
        if (conditions.equals(this))
            return this;


        return new CompositeConditions(this, conditions, LogicSymbol.AND);
    }

    @Override
    public Conditions or(Conditions conditions) {
        if (conditions == EMPTY)
            return this;
        if (conditions == FULL)
            return FULL;
        if (conditions.equals(this))
            return this;
        return new CompositeConditions(this, conditions, LogicSymbol.OR);
    }

    @Override
    public Conditions reverse() {
        return copy(compareSymbol.reverse());
    }


}
