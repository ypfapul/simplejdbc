package org.ypf.generic.orm.entityoper;


/**
 * 代表聚合值
 *
 * @date: 2022/6/23 17:19
 */
public class AggregationValue extends AggregationParams {

    Object value;


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
