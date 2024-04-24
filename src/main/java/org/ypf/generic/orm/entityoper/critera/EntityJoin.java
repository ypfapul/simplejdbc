package org.ypf.generic.orm.entityoper.critera;


import org.ypf.generic.orm.entityoper.condition.Conditions;

/**
 * 连接查询
 *
 * @date: 2022/6/30 9:18
 */
public interface EntityJoin {
    /**
     * 0 生成完成sql
     * 1 排除排序sql
     * 2
     *
     * @param context
     * @return
     */
    String sql(int context);

    EntityJoin join(EntityJoin target, Conditions joinRelation, JoinSymbol joinSymbol, Conditions conditions);

    default EntityJoin join(EntityJoin target, Conditions joinRelation, JoinSymbol joinSymbol) {
        return join(target, joinRelation, joinSymbol, Conditions.FULL);
    }

    default EntityJoin leftJoin(EntityJoin target, Conditions joinRelation) {
        return join(target, joinRelation, JoinSymbol.LEFT, Conditions.FULL);
    }

    default EntityJoin rightJoin(EntityJoin target, Conditions joinRelation) {
        return join(target, joinRelation, JoinSymbol.RIGHT, Conditions.FULL);
    }

    default EntityJoin innerJoin(EntityJoin target, Conditions joinRelation) {
        return join(target, joinRelation, JoinSymbol.INNER, Conditions.FULL);
    }

    default EntityJoin leftJoin(EntityJoin target, Conditions joinRelation, Conditions conditions) {
        return join(target, joinRelation, JoinSymbol.LEFT, conditions);
    }

    default EntityJoin rightJoin(EntityJoin target, Conditions joinRelation, Conditions conditions) {
        return join(target, joinRelation, JoinSymbol.RIGHT, conditions);
    }

    default EntityJoin innerJoin(EntityJoin target, Conditions joinRelation, Conditions conditions) {
        return join(target, joinRelation, JoinSymbol.INNER, conditions);
    }


}
