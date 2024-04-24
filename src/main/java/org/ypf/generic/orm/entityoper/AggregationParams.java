package org.ypf.generic.orm.entityoper;


/**
 * 代表聚合值
 *
 * @date: 2022/6/23 17:19
 */
public class AggregationParams {
    //被聚合的字段
    String bindName;
    // 聚合字段别名
    String alias;
    AggregationSymbol aggregationSymbol;


    public String getBindName() {
        return bindName;
    }

    public void setBindName(String bindName) {
        this.bindName = bindName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public AggregationSymbol getAggregationSymbol() {
        return aggregationSymbol;
    }

    public void setAggregationSymbol(AggregationSymbol aggregationSymbol) {
        this.aggregationSymbol = aggregationSymbol;
    }
}
