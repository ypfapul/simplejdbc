package org.ypf.generic.orm.entityoper.critera;


import org.ypf.generic.orm.entityoper.ColumFieldBinding;
import org.ypf.generic.orm.entityoper.EntityMapper;
import org.ypf.generic.orm.entityoper.SqlKeywords;
import org.ypf.generic.orm.entityoper.condition.Conditions;
import org.ypf.generic.orm.entityoper.condition.ExpressContext;
import org.ypf.generic.orm.entityoper.condition.Variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 连接查询
 *
 * @date: 2022/6/30 9:33
 */
public class CompositeEntityJoin implements EntityJoin {
    List<BasicEntityJoin> basicEntityJoinList = new ArrayList<>();
    List<JoinSymbol> joinSymbols = new ArrayList<>();
    List<Conditions> joinRelations = new ArrayList<>();
    Conditions conditions = Conditions.FULL;
    List<Order> order = new ArrayList<>();

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<BasicEntityJoin> getBasicEntityJoinList() {
        return basicEntityJoinList;
    }

    public void setBasicEntityJoinList(List<BasicEntityJoin> basicEntityJoinList) {
        this.basicEntityJoinList = basicEntityJoinList;
    }

    public List<JoinSymbol> getJoinSymbols() {
        return joinSymbols;
    }

    public void setJoinSymbols(List<JoinSymbol> joinSymbols) {
        this.joinSymbols = joinSymbols;
    }


    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }


    /**
     * @param context 默认 0 会生成排序区
     * @return
     */
    @Override
    public String sql(int context) {

        int size = basicEntityJoinList.size();
        StringBuilder sb = new StringBuilder(SqlKeywords.SELECT.rightBlank());
        final Map<EntityMapper, String> entityMapperTableAliasNames = new HashMap<>();
        List<String> tableAliasNames = new ArrayList<>();
        //别名集
        List<String> columnAliasNames = new ArrayList<>();
        //List<String> conditonAliasNames = new ArrayList<>();
        List<Order> listOrder = new ArrayList<>();
        //生成字段
        for (int i = 0; i < size; i++) {
            BasicEntityJoin basicEntityJoin = basicEntityJoinList.get(i);

            String tableName = basicEntityJoin.getEntityMapper().table().getName();
            final String tableAliasName = tableName.toLowerCase() + "_" + i;
            tableAliasNames.add(tableAliasName);
            entityMapperTableAliasNames.put(basicEntityJoin.getEntityMapper(), tableAliasName);
//            Conditions conditions = basicEntityJoin.getConditions();
//            if (conditions != null && conditions != Conditions.FULL) {
//                SqlConditionsContext sqlConditionsContext = s -> new StringBuilder(tableAliasName).append(SqlKeywords.DOT.value).append(s.getSqlAtomObject().name()).toString();
//                conditonAliasNames.add(conditions.express(sqlConditionsContext));
//            }
            List<Order> order = basicEntityJoin.getOrder();
            if (order != null) {
                listOrder.addAll(order);
            }

            List<ColumFieldBinding> columFieldBindings = basicEntityJoin.getColumFieldBindings();
            int size_ = columFieldBindings.size();
            for (int j = 0; j < size_; j++) {
                sb.append(tableAliasName);
                sb.append(SqlKeywords.DOT.value);
                String cn = columFieldBindings.get(j).column().getName();
                sb.append(cn);
                sb.append(SqlKeywords.BLANK.value);

                StringBuilder columnAliasNamesb = new StringBuilder();
                columnAliasNamesb.append(cn.toLowerCase());
                columnAliasNamesb.append("_");
                columnAliasNamesb.append(i);
                sb.append(columnAliasNamesb);
                columnAliasNames.add(columnAliasNamesb.toString());
                if (i != size - 1) {// 不是最后一个
                    sb.append(SqlKeywords.COMMA.value);
                } else {
                    if (j != size_ - 1) {
                        sb.append(SqlKeywords.COMMA.value);
                    }
                }


            }
        }
        if (context == 2) {

            return columnAliasNames.stream().collect(Collectors.joining(","));//别名 a,b,c

        }
        sb.append(SqlKeywords.FROM.blank());
        sb.append(basicEntityJoinList.get(0).getEntityMapper().table().getName());
        sb.append(SqlKeywords.BLANK.value);
        sb.append(tableAliasNames.get(0));
        sb.append(SqlKeywords.BLANK.value);
        ExpressContext expressContext = new NameSpaceExpressContext() {
            @Override
            public String getTableAliasName(Variable variable) {
                ColumFieldBinding cdf0 = (ColumFieldBinding) variable;
                return entityMapperTableAliasNames.get(cdf0.entityMapper());
            }
        };

        // 生成连接
        for (int i = 1; i < size; i++) {
            BasicEntityJoin basicEntityJoin = basicEntityJoinList.get(i);
            String tableName = basicEntityJoin.getEntityMapper().table().getName();
            // String tableAliasName = tableAliasNames.get(i);
            int j = i - 1;
            JoinSymbol joinSymbol = joinSymbols.get(j);
            Conditions joinRelation = joinRelations.get(j);
            sb.append(joinSymbol.name);
            sb.append(SqlKeywords.BLANK.value);
            sb.append(tableName);
            sb.append(SqlKeywords.BLANK.value);
            sb.append(tableAliasNames.get(i));
            sb.append(SqlKeywords.BLANK.value);
            sb.append(SqlKeywords.ON.rightBlank());
            sb.append(joinRelation.express(expressContext));
            sb.append(SqlKeywords.BLANK.value);
        }

        // 生成条件区
        if (conditions != null && conditions != Conditions.FULL) {
            sb.append(SqlKeywords.WHERE.rightBlank());
            sb.append(SqlKeywords.BLANK.value);
            sb.append(conditions.express(expressContext));
        }

        // 生成排序区
        if (context == 0) {
            if (listOrder.size() > 0) {
                listOrder.sort((a, b) -> {
                    return a.getIndex() - a.getIndex();
                });
                sb.append(SqlKeywords.ORDERBY.blank());
                sb.append(listOrder.stream().map(e -> {
                    ColumFieldBinding cdf0 = e.getColumFieldBinding();
                    String tableAliasName = entityMapperTableAliasNames.get(cdf0.entityMapper());
                    return e.express(tableAliasName);


                }).collect(Collectors.joining(",")));


            }
        }


        return sb.toString();
    }


    @Override
    public EntityJoin join(EntityJoin target, Conditions joinRelation, JoinSymbol joinSymbol, Conditions conditions) {
        CompositeEntityJoin compositeEntityJoin = new CompositeEntityJoin();
        compositeEntityJoin.getOrder().addAll(this.order);
        compositeEntityJoin.basicEntityJoinList.addAll(this.basicEntityJoinList);
        compositeEntityJoin.joinSymbols.addAll(this.joinSymbols);
        compositeEntityJoin.joinRelations.addAll(this.joinRelations);
        if (target instanceof BasicEntityJoin) {
            BasicEntityJoin basicEntityJoin = (BasicEntityJoin) target;
            compositeEntityJoin.basicEntityJoinList.add((BasicEntityJoin) target);
            compositeEntityJoin.setConditions(getConditions().and(basicEntityJoin.getConditions()));
            if (conditions != null && conditions != Conditions.FULL) {
                compositeEntityJoin.setConditions(compositeEntityJoin.getConditions().and(conditions));
            }
            compositeEntityJoin.getOrder().addAll(basicEntityJoin.getOrder());
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
