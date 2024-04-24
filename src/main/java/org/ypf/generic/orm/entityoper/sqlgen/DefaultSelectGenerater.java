package org.ypf.generic.orm.entityoper.sqlgen;

import org.ypf.generic.orm.entityoper.condition.Conditions;

import java.util.List;

/**
 * @date: 2022/6/29 8:54
 */
public class DefaultSelectGenerater implements SelectSqlGenerater {
    @Override
    public String select(boolean distinct, List<SqlAtomObject> columnList, List<AggregationExpress> aggregationParams, SqlAtomObject table, Conditions conditions, List<SqlAtomObject> groupColumnList, String order, Conditions havingConditons) {
        return null;
    }
}
