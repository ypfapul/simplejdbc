package org.ypf.generic.orm.entityoper.condition;

import org.ypf.generic.orm.entityoper.LogicSymbol;

/**
 * @date: 2022/5/31 9:16
 */
public class CompositeConditions extends CompositeExpress implements Conditions {

    protected Conditions firstConditions;
    protected Conditions secondConditions;
    protected LogicSymbol logicSymbol;

    public CompositeConditions(Conditions first, Conditions second, LogicSymbol logicSymbol) {
        super(first, second, logicSymbol);
        this.firstConditions = first;
        this.secondConditions = second;
        this.logicSymbol = logicSymbol;
    }

    static LogicSymbol reverse_(LogicSymbol logicOper) {
        switch (logicOper) {
            case OR:
                return LogicSymbol.AND;
            case AND:
                return LogicSymbol.OR;
        }
        return null;

    }


    @Override
    public Conditions and(Conditions conditions) {
        if (conditions == FinalConditions.EMPTY)
            return FinalConditions.EMPTY;
        if (conditions == FinalConditions.FULL)
            return this;
        if (conditions.equals(this))
            return this;

        return new CompositeConditions(this, conditions, LogicSymbol.AND);
    }

    @Override
    public Conditions or(Conditions conditions) {
        if (conditions == FinalConditions.EMPTY)
            return this;
        if (conditions == FinalConditions.FULL)
            return FinalConditions.FULL;
        if (conditions.equals(this))
            return this;
        return new CompositeConditions(this, conditions, LogicSymbol.OR);
    }

    @Override
    public Conditions reverse() {
        //德摩根律
        return new CompositeConditions(firstConditions.reverse(), secondConditions.reverse(), reverse_(logicSymbol));

    }


}
