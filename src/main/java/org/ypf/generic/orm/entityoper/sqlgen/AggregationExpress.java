package org.ypf.generic.orm.entityoper.sqlgen;


import org.ypf.generic.orm.entityoper.AggregationSymbol;

/**
 * 代表聚合值
 *
 * @date: 2022/6/23 17:19
 */
public class AggregationExpress {
    //字段
    SqlAtomObject column;
    //聚合符号
    AggregationSymbol aggregationSymbol;

    public SqlAtomObject getColumn() {
        return column;
    }

    public void setColumn(SqlAtomObject column) {
        this.column = column;
    }

    public AggregationSymbol getAggregationSymbol() {
        return aggregationSymbol;
    }

    public void setAggregationSymbol(AggregationSymbol aggregationSymbol) {
        this.aggregationSymbol = aggregationSymbol;
    }
}
