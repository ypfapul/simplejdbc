package org.ypf.generic.orm.entityoper.sqlgen;

;
import org.ypf.generic.orm.entityoper.condition.Conditions;

import java.util.List;

/**
 * sql 查询语句生成器
 *
 * @date: 2022/6/28 18:21
 */
public interface SelectSqlGenerater {
    /**
     * @param distinct          去重标识
     * @param columnList        字段列
     * @param aggregationParams 聚合函数列
     * @param table             表
     * @param conditions        条件
     * @param groupColumnList   分组定义
     * @param order             排序
     * @param havingConditons   having 条件
     * @return
     */
    String select(boolean distinct, List<SqlAtomObject> columnList, List<AggregationExpress> aggregationParams, SqlAtomObject table, Conditions conditions, List<SqlAtomObject> groupColumnList, String order, Conditions havingConditons);


}
