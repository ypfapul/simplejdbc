package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.entityoper.condition.Conditions;
import org.ypf.generic.orm.entityoper.critera.EntityJoin;
import org.ypf.generic.orm.entityoper.critera.EntityJoinResult;

import java.util.List;

/**
 * 查询接口
 *
 * @date: 2022/6/23 17:04
 */
public interface CriteraTemplete {

    /**
     * 统计符合条件下实体某字段的聚合函数值
     *
     * @param condtions
     * @param tClass
     * @return
     */
    Object aggregationValue(Conditions condtions, Class<? extends DaoEntity> tClass, AggregationParams aggregationParams);

    /**
     * 分组查询
     *
     * @param condtions
     * @param tClass
     * @param groupByBindNames  分组字段
     * @param aggregationParams 哪些字段作为聚合项
     * @return
     */
    List<AggregationEntity> groupBy(Conditions condtions, Class<? extends DaoEntity> tClass, String[] groupByBindNames, AggregationParams... aggregationParams);

    /**
     * 分组查询
     *
     * @param condtions
     * @param tClass
     * @param groupByBindNames  分组字段
     * @param aggregationParams 哪些字段作为聚合项
     * @param havingConditons   having 条件
     * @return
     */
    List<AggregationEntity> groupBy(Conditions condtions, Class<? extends DaoEntity> tClass, String[] groupByBindNames, Conditions havingConditons, AggregationParams... aggregationParams);


    /**
     * 连接查询
     *
     * @param entityJoin
     * @return
     */
    List<EntityJoinResult> entityJoin(EntityJoin entityJoin);

    /**
     * 连接查询分页
     *
     * @param entityJoin
     * @return
     */
    List<EntityJoinResult> entityJoin(EntityJoin entityJoin, Pagation pagation);


}
