package org.ypf.generic.orm.entityoper;

import java.util.ArrayList;
import java.util.List;

/**
 * 聚合实体
 *
 * @date: 2022/6/23 17:17
 */
public class AggregationEntity {
    //实体
    DaoEntity daoEntity;
    List<AggregationValue> aggregationValues = new ArrayList<>();

    public DaoEntity getDaoEntity() {
        return daoEntity;
    }

    public void setDaoEntity(DaoEntity daoEntity) {
        this.daoEntity = daoEntity;
    }

    public List<AggregationValue> getAggregationValues() {
        return aggregationValues;
    }

    public void setAggregationValues(List<AggregationValue> aggregationValues) {
        this.aggregationValues = aggregationValues;
    }
}
