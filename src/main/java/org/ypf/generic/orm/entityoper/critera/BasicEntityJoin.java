package org.ypf.generic.orm.entityoper.critera;

import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.entityoper.EntityMapper;
import org.ypf.generic.orm.entityoper.EntityMapperRepository;
import org.ypf.generic.orm.entityoper.SqlKeywords;
import org.ypf.generic.orm.entityoper.condition.Conditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 基本的连接查询
 *
 * @date: 2022/6/30 9:11
 */
public class BasicEntityJoin implements EntityJoin {

    private EntityMapper entityMapper;
    private Conditions conditions;
    private List<Order> order = new ArrayList<>();
    //关注的字段
    private List<ColumFieldBinding> columFieldBindings;

    public static EntityJoin newBasicEntityJoin(Class<?> c, ColumFieldBinding... columFieldBindings) {
        return newBasicEntityJoin(c, null, null, Arrays.asList(columFieldBindings));
    }

    public static EntityJoin newBasicEntityJoin(Class<?> c, Conditions conditions, ColumFieldBinding... columFieldBindings) {
        return newBasicEntityJoin(c, conditions, null, Arrays.asList(columFieldBindings));
    }

    public static EntityJoin newBasicEntityJoin(Class<?> c, Conditions conditions, List<Order> order, List<ColumFieldBinding> columFieldBindings) {
        BasicEntityJoin basicEntityJoin = new BasicEntityJoin();
        basicEntityJoin.setEntityMapper(EntityMapperRepository.get(c));
        basicEntityJoin.setConditions(conditions);
        if (order != null) {
            basicEntityJoin.getOrder().addAll(order);
        }
        if (columFieldBindings != null) {
            basicEntityJoin.setColumFieldBindings(columFieldBindings);
        }

        return basicEntityJoin;
    }


    public static EntityJoin newBasicEntityJoin(Class<?> c) {
        return newBasicEntityJoin(c, null, null, null);
    }

    public static EntityJoin newBasicEntityJoin(Class<?> c, List<Order> order) {
        return newBasicEntityJoin(c, null, order, null);
    }

    public static EntityJoin newBasicEntityJoin(Class<?> c, Conditions conditions, List<Order> order) {
        return newBasicEntityJoin(c, conditions, order, null);
    }

    public static EntityJoin newBasicEntityJoin(Class<?> c, Conditions conditions) {
        return newBasicEntityJoin(c, conditions, null, null);
    }

    public EntityMapper getEntityMapper() {
        return entityMapper;
    }

    public void setEntityMapper(EntityMapper entityMapper) {
        this.entityMapper = entityMapper;
    }

    public Conditions getConditions() {
        return conditions == null ? Conditions.FULL : conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<ColumFieldBinding> getColumFieldBindings() {
        return columFieldBindings == null ? entityMapper.cFBindings() : columFieldBindings;
    }

    public void setColumFieldBindings(List<ColumFieldBinding> columFieldBindings) {
        this.columFieldBindings = columFieldBindings;
    }

    @Override
    public String sql(int context) {
        StringBuilder sb = new StringBuilder();
        sb.append(SqlKeywords.SELECT.rightBlank());
        if (columFieldBindings != null && columFieldBindings.size() > 0) {
            sb.append(columFieldBindings.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));
        } else {
            sb.append(entityMapper.cFBindings().stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));

        }

        sb.append(SqlKeywords.FROM.blank());
        sb.append(entityMapper.table().getName());

        if (conditions != null && conditions != Conditions.FULL) {
            sb.append(SqlKeywords.WHERE.blank());
            sb.append(conditions.express());
        }
        if (context == 0) {
            if (order != null && order.size() > 0) {
                sb.append(SqlKeywords.ORDERBY.blank());
                sb.append(order.stream().map(e -> e.express()).collect(Collectors.joining(",")));
            }
        }


        return sb.toString();
    }

    @Override
    public EntityJoin join(EntityJoin target, Conditions joinRelation, JoinSymbol joinSymbol, Conditions conditions) {
        CompositeEntityJoin compositeEntityJoin = new CompositeEntityJoin();
        compositeEntityJoin.getOrder().addAll(this.order);
        if (target instanceof BasicEntityJoin) {
            BasicEntityJoin basicEntityJoin = (BasicEntityJoin) target;
            compositeEntityJoin.basicEntityJoinList.add(this);
            compositeEntityJoin.basicEntityJoinList.add((BasicEntityJoin) target);

            compositeEntityJoin.setConditions(getConditions().and(basicEntityJoin.getConditions()));
            if (conditions != null && conditions != Conditions.FULL) {
                compositeEntityJoin.setConditions(compositeEntityJoin.getConditions().and(conditions));

            }


            compositeEntityJoin.getOrder().addAll(basicEntityJoin.order);
        } else {// 复合
            CompositeEntityJoin compositeEntityJoin1 = (CompositeEntityJoin) target;
            compositeEntityJoin.basicEntityJoinList.addAll(compositeEntityJoin1.basicEntityJoinList);
            compositeEntityJoin.joinSymbols.addAll(compositeEntityJoin1.joinSymbols);
            compositeEntityJoin.joinRelations.addAll(compositeEntityJoin1.joinRelations);
            compositeEntityJoin.setConditions(getConditions().and(compositeEntityJoin1.getConditions()));
            if (conditions != null && conditions != Conditions.FULL) {
                compositeEntityJoin.setConditions(compositeEntityJoin.getConditions().and(conditions));
            }
            compositeEntityJoin.getOrder().addAll(compositeEntityJoin1.order);
        }

        compositeEntityJoin.joinSymbols.add(joinSymbol);
        compositeEntityJoin.joinRelations.add(joinRelation);
        return compositeEntityJoin;
    }


}
