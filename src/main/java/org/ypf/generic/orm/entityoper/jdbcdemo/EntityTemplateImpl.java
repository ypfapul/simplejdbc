package org.ypf.generic.orm.entityoper.jdbcdemo;

import org.ypf.generic.orm.JdbcTemplete;
import org.ypf.generic.orm.Qurey;
import org.ypf.generic.orm.SessionFactory;

import org.ypf.generic.orm.entityoper.*;
import org.ypf.generic.orm.entityoper.condition.Conditions;

import org.ypf.generic.orm.entityoper.critera.BasicEntityJoin;
import org.ypf.generic.orm.entityoper.critera.CompositeEntityJoin;
import org.ypf.generic.orm.entityoper.critera.EntityJoin;
import org.ypf.generic.orm.entityoper.critera.EntityJoinResult;
import org.ypf.generic.orm.entityoper.defineimpl.EntityMapperImpl;
import org.ypf.generic.orm.entityoper.defineimpl.TableImpl;
import org.ypf.generic.orm.exception.JdbcException;


import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date: 2022/6/1 16:12
 */
public class EntityTemplateImpl implements EntityTemplete {

    ObjectTemplete objectTemplete;


    public EntityTemplateImpl(ObjectTemplete objectTemplete) {
        this.objectTemplete = objectTemplete;
    }

    public static EntityTemplete getEntityTemplete(DataSource dataSource) {
        Objects.requireNonNull(dataSource);
        SessionFactory sessionFactory = new SessionFactory(dataSource);
        JdbcTemplete jdbcTemplete = new JdbcTemplete(sessionFactory);
        ObjectTempleteImpl objectTemplete = new ObjectTempleteImpl(jdbcTemplete);
        return new EntityTemplateImpl(objectTemplete);
    }

    public ObjectTemplete getObjectTemplete() {
        return objectTemplete;
    }

    @Override
    public <T extends DaoEntity> T get(Serializable entityId, Class<T> tClass) {
        EntityMapper entityMapper = EntityMapperRepository.get(tClass.getName());
        Objects.requireNonNull(entityMapper);
        return objectTemplete.get(entityId, entityMapper);
    }

    @Override
    public <T extends DaoEntity> T get(Serializable entityId, Class<T> tClass, String... bindNames) {
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass).mirror();
        entityMapperMirror.suspendAll();
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }

        }
        return objectTemplete.get(entityId, entityMapperMirror);
    }

    @Override
    public <T extends DaoEntity> List<T> getList(Conditions condtions, Class<T> tClass) {
        EntityMapper entityMapper = EntityMapperRepository.get(tClass.getName());
        Objects.requireNonNull(entityMapper);
        return objectTemplete.getList(condtions, entityMapper);
    }

    @Override
    public long getQuantity(Conditions condtions, Class<?> tClass) {
        EntityMapper entityMapper = EntityMapperRepository.get(tClass.getName());
        Objects.requireNonNull(entityMapper);
        return objectTemplete.getQuantity(condtions, entityMapper);
    }

    @Override
    public void copyData(Conditions sourceCondtions, Class<? extends DaoEntity> source, Class<? extends DaoEntity> target) {
        List<? extends DaoEntity> list = getList(sourceCondtions, source);
        ObjToOtherFunction<DaoEntity> objToOtherFunction = new ObjToOtherFunction<>();
        batchSave(list.stream().map(e -> objToOtherFunction.apply(e, target)).collect(Collectors.toList()));
    }

    @Override
    public void copyData(Class<? extends DaoEntity> source, Class<? extends DaoEntity> target) {
        copyData(Conditions.FULL, source, target);
    }

    @Override
    public <T extends DaoEntity> List<T> getList(Conditions condtions, Class<T> tClass, String orderbys, Pagation pagation) {
        EntityMapper entityMapper = EntityMapperRepository.get(tClass.getName());
        Objects.requireNonNull(entityMapper);
        return objectTemplete.getList(condtions, entityMapper, orderbys, pagation);
    }

    @Override
    public <T extends DaoEntity> List<T> getListOnBindNames(Conditions condtions, Class<T> tClass, String... bindNames) {
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass).mirror();
        entityMapperMirror.suspendAll();
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }

        }
        return objectTemplete.getList(condtions, entityMapperMirror);
    }

    @Override
    public <T extends DaoEntity> List<T> getListOnOrder(Conditions condtions, Class<T> tClass, String orderbys) {
        EntityMapper entityMapper = EntityMapperRepository.get(tClass.getName());
        Objects.requireNonNull(entityMapper);
        return objectTemplete.getList(condtions, entityMapper, orderbys);
    }

    @Override
    public <T extends DaoEntity> List<T> getListOnOrderbysAndBindNames(Conditions condtions, Class<T> tClass, String orderbys, String... bindNames) {
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass).mirror();
        entityMapperMirror.suspendAll();
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }

        }
        return objectTemplete.getList(condtions, entityMapperMirror, orderbys);
    }

    @Override
    public <T extends DaoEntity> List<T> getDistinctListOnBindNames(Conditions condtions, Class<T> tClass, String... bindNames) {
        return getDistinctListOnOrderbysAndBindNames(condtions, tClass, null, bindNames);
    }

    @Override
    public <T extends DaoEntity> List<T> getDistinctListOnOrderbysAndBindNames(Conditions condtions, Class<T> tClass, String orderbys, String... bindNames) {
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass).mirror();
        entityMapperMirror.suspendAll();
        ColumFieldBinding primay = entityMapperMirror.primay();
        if (primay != null) {
            primay.suspend();
        }
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }

        }
        return objectTemplete.getDistinctList(condtions, entityMapperMirror, orderbys);
    }

    @Override
    public <T extends DaoEntity> T get(Conditions condtions, Class<T> tClass) {
        EntityMapper entityMapper = EntityMapperRepository.get(tClass.getName());
        Objects.requireNonNull(entityMapper);
        return objectTemplete.get(condtions, entityMapper);
    }

    @Override
    public <T extends DaoEntity> T get(Conditions condtions, Class<T> tClass, String... bindNames) {
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass).mirror();
        entityMapperMirror.suspendAll();
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }
        }
        return objectTemplete.get(condtions, entityMapperMirror);
    }

    @Override
    public void saveSectionTableEntity(SectionTableEntity entity) {
        EntityMapper entityMapper = entity.entityMapper().mirror();
        EntityMapperImpl entityMapperImpl = (EntityMapperImpl) entityMapper;
        TableImpl table = (TableImpl) entityMapperImpl.table();
        table.setTableName(new StringBuilder(table.getName()).append(entity.tableSuffix()).toString());
        objectTemplete.saveGetId(entity, entityMapper);
    }

    @Override
    public <T extends SectionTableEntity> void batchSaveSectionTableEntity(List<T> entities) {
        if (entities.size() > 0) {
            Map<String, List<T>> groups = entities.stream().collect(Collectors.groupingBy(e -> e.tableSuffix()));
            for (Map.Entry<String, List<T>> md : groups.entrySet()) {
                List<T> listEntities = md.getValue();
                SectionTableEntity entity = listEntities.get(0);
                EntityMapper entityMapper = entity.entityMapper().mirror();
                EntityMapperImpl entityMapperImpl = (EntityMapperImpl) entityMapper;
                TableImpl table = (TableImpl) entityMapperImpl.table();
                table.setTableName(new StringBuilder(table.getName()).append(md.getKey()).toString());
                objectTemplete.batchSave(listEntities, entityMapper);
            }
        }

    }

    @Override
    public void save(DaoEntity entity) {
        EntityMapper entityMapper = entity.entityMapper();
        objectTemplete.save(entity, entityMapper);

    }

    @Override
    public void saveGetId(DaoEntity entity) {
        EntityMapper entityMapper = entity.entityMapper();
        objectTemplete.saveGetId(entity, entityMapper);
    }

    @Override
    public <T extends DaoEntity> void batchSave(List<T> entities) {
        if (entities.size() > 0) {
            EntityMapper entityMapper = entities.get(0).entityMapper();
            objectTemplete.batchSave(entities, entityMapper);
        }


    }

    @Override
    public <T extends DaoEntity> void batchSaveGetId(List<T> entities) {
        if (entities.size() > 0) {
            EntityMapper entityMapper = entities.get(0).entityMapper();
            objectTemplete.batchSaveGetId(entities, entityMapper);
        }
    }

    @Override
    public void update(DaoEntity entity) {
        EntityMapper entityMapper = entity.entityMapper();
        objectTemplete.update(entity, entityMapper);

    }

    @Override
    public void updateByConditionsIgnoreNull(DaoEntity target, Conditions conditions) {
        if (conditions == Conditions.EMPTY) {
            return;
        }
        EntityMapper entityMapper = target.entityMapper();
        ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
        JdbcTemplete jdbcTemplete = objectTemplete1.getJdbcTemplete();
        // EntityMapperToSql entityMapperToSql = objectTemplete1.getEntityMapperToSql();
        PreparedStatementSetMathcer preparedStatementSetMathcer = objectTemplete1.getPreparedStatementSetMathcer();
        List<ColumFieldBinding> columFieldBindings = entityMapper.cFBindings();
        StringBuilder sqlBuilder = new StringBuilder("update ");
        sqlBuilder.append(entityMapper.table().getName());
        sqlBuilder.append(" set ");
        Map<ColumFieldBinding, Object> mapv = new LinkedHashMap<>();
        for (ColumFieldBinding columFieldBinding : columFieldBindings) {
            JavaField javaField = columFieldBinding.field();
            Object val = javaField.getValue(target);
            if (val != null) {
                mapv.put(columFieldBinding, val);
            }
        }
        int size = mapv.size();
        int i = 0;
        for (Map.Entry<ColumFieldBinding, Object> me : mapv.entrySet()) {
            if (i < size - 1) {
                sqlBuilder.append(me.getKey().column().getName());
                sqlBuilder.append(" = ?,");

            } else {
                sqlBuilder.append(me.getKey().column().getName());
                sqlBuilder.append(" = ?");
            }
            i++;
        }
        if (conditions != Conditions.FULL) {
            sqlBuilder.append(" where ");
            sqlBuilder.append(conditions.express());
        }
        jdbcTemplete.getExcuter(sqlBuilder.toString(), (PreparedStatement pst, DaoEntity e) -> {

            int j = 1;
            for (Map.Entry<ColumFieldBinding, Object> me : mapv.entrySet()) {
                ColumFieldBinding cfBinding = me.getKey();
                JavaField javaField = cfBinding.field();
                preparedStatementSetMathcer.setValues(pst, cfBinding, j, javaField.jdbcType(), javaField.getValue(target));
                j++;
            }

        }).excute();


    }

    @Override
    public void updateByConditions(DaoEntity target, Conditions conditions) {
        if (conditions == Conditions.EMPTY) {
            return;
        }
        EntityMapper entityMapper = target.entityMapper();
        ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
        JdbcTemplete jdbcTemplete = objectTemplete1.getJdbcTemplete();
        //EntityMapperToSql entityMapperToSql = objectTemplete1.getEntityMapperToSql();
        PreparedStatementSetMathcer preparedStatementSetMathcer = objectTemplete1.getPreparedStatementSetMathcer();
        List<ColumFieldBinding> columFieldBindings = entityMapper.cFBindings();
        StringBuilder sqlBuilder = new StringBuilder("update ");
        sqlBuilder.append(entityMapper.table().getName());
        sqlBuilder.append(" set ");
        sqlBuilder.append(columFieldBindings.stream().map(e -> e.column().getName() + "= ?").collect(Collectors.joining(",")));
        if (conditions != Conditions.FULL) {
            sqlBuilder.append(" where ");
            sqlBuilder.append(conditions.express());
        }
        jdbcTemplete.getExcuter(sqlBuilder.toString(), (PreparedStatement pst, DaoEntity e) -> {

            int j = 1;
            for (ColumFieldBinding columFieldBinding : columFieldBindings) {
                JavaField javaField = columFieldBinding.field();
                preparedStatementSetMathcer.setValues(pst, columFieldBinding, j, javaField.jdbcType(), javaField.getValue(target));
                j++;
            }

        }).excute();

    }

    @Override
    public void updateByBinds(DaoEntity entity, String... bindNames) {

        EntityMapper entityMapperMirror = entity.entityMapper().mirror();
        entityMapperMirror.suspendAll();
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }

        }

        objectTemplete.update(entity, entityMapperMirror);
    }

    @Override
    public void delete(DaoEntity entity) {
        EntityMapper entityMapper = entity.entityMapper();
        objectTemplete.delete(entity, entityMapper);

    }

    @Override
    public <T extends DaoEntity> void delete(Conditions condtions, Class<T> tClass) {
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass);
        objectTemplete.delete(condtions, entityMapperMirror);
    }

    @Override
    public <T extends DaoEntity> void deleteAll(Class<T>... tClass) {
        for (Class c : tClass) {
            objectTemplete.deleteAll(EntityMapperRepository.get(c));
        }

    }

    @Override
    public <T extends DaoEntity> void batchDelete(List<T> entities) {
        if (entities == null || entities.size() < 1)
            return;
        EntityMapper entityMapper = entities.get(0).entityMapper();
        objectTemplete.batchDelete(entities, entityMapper);
    }

    @Override
    public <T extends DaoEntity> void batchUpdate(List<T> entities) {
        if (entities == null || entities.size() < 1)
            return;
        EntityMapper entityMapper = entities.get(0).entityMapper();
        objectTemplete.batchUpdate(entities, entityMapper);
    }

    @Override
    public <T extends DaoEntity> void batchUpdate(List<T> entities, String... bindNames) {
        if (entities == null || entities.size() < 1)
            return;
        EntityMapper entityMapper = entities.get(0).entityMapper();
        EntityMapper entityMapperMirror = entityMapper.mirror();
        entityMapperMirror.suspendAll();
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }

        }

        objectTemplete.batchUpdate(entities, entityMapperMirror);
    }

    @Override
    public <T extends DaoEntity> void truncate(Class<T>... tClass) {
        for (Class c : tClass) {
            EntityMapper entityMapper = EntityMapperRepository.get(c);
            ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
            JdbcTemplete jdbcTemplete = objectTemplete1.getJdbcTemplete();
            StringBuilder sql = new StringBuilder(SqlKeywords.TRUNCATE.rightBlank());
            sql.append(SqlKeywords.TABLE.rightBlank());
            sql.append(entityMapper.table().getName());
            jdbcTemplete.getExcuter(sql.toString()).excute();
        }

    }

    @Override
    public Object aggregationValue(Conditions condtions, Class<? extends DaoEntity> tClass, AggregationParams aggregationParams) {

        if (condtions.equals(Conditions.EMPTY))
            return null;
        EntityMapper entityMapper = EntityMapperRepository.get(tClass);
        StringBuilder sql = new StringBuilder("select ");
        sql.append(aggregationParams.getAggregationSymbol().name());
        sql.append("(");
        sql.append(entityMapper.getColumFieldBinding(aggregationParams.getBindName()).column().getName());
        sql.append(")");
        sql.append(" from ");
        sql.append(entityMapper.table().getName());
        if (!condtions.equals(Conditions.FULL)) {
            sql.append(" where ");
            sql.append(condtions.express());
        }
        ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
        return objectTemplete1.getJdbcTemplete().objectQurey(sql.toString());
    }

    @Override
    public List<AggregationEntity> groupBy(Conditions condtions, Class<? extends DaoEntity> tClass, String[] bindNames, AggregationParams... aggregationParams) {

        return groupBy(condtions, tClass, bindNames, null, aggregationParams);
    }

    @Override
    public List<AggregationEntity> groupBy(Conditions condtions, Class<? extends DaoEntity> tClass, String[] bindNames, Conditions havingConditons, AggregationParams... aggregationParams) {
        Objects.requireNonNull(bindNames);
        if (havingConditons == Conditions.EMPTY) {
            return Collections.emptyList();
        }
        if (condtions.equals(Conditions.EMPTY))
            return Collections.emptyList();
        if (bindNames.length < 1) {
            throw new JdbcException();
        }
        EntityMapper entityMapperMirror = EntityMapperRepository.get(tClass).mirror();
        entityMapperMirror.suspendAll();
        ColumFieldBinding primay = entityMapperMirror.primay();
        if (primay != null) {
            primay.suspend();
        }
        for (String bindName : bindNames) {
            ColumFieldBinding columFieldBinding = entityMapperMirror.getColumFieldBinding(bindName);
            if (columFieldBinding != null) {
                columFieldBinding.reload();
            }
        }

        StringBuilder stringBuilderSql = new StringBuilder("select ");
        List<ColumFieldBinding> cfBindingList = entityMapperMirror.cFBindings();
        stringBuilderSql.append(cfBindingList.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));

        for (AggregationParams aggregationParams1 : aggregationParams) {
            stringBuilderSql.append(",");
            stringBuilderSql.append(aggregationParams1.getAggregationSymbol().name());
            stringBuilderSql.append("(");
            stringBuilderSql.append(entityMapperMirror.getColumFieldBinding(aggregationParams1.getBindName()).column().getName());
            stringBuilderSql.append(")");
            stringBuilderSql.append(SqlKeywords.BLANK.value);
            if (aggregationParams1.getAlias() != null) {
                stringBuilderSql.append(SqlKeywords.AS.getValue());
                stringBuilderSql.append(SqlKeywords.BLANK.getValue());
                stringBuilderSql.append(aggregationParams1.getAlias());
            }

        }
        stringBuilderSql.append(" from ");
        stringBuilderSql.append(entityMapperMirror.table().getName());

        if (!condtions.equals(Conditions.FULL)) {
            stringBuilderSql.append(" where ");
            stringBuilderSql.append(condtions.express());
        }
        stringBuilderSql.append(" group by ");

        int j = 0;
        for (String bindName : bindNames) {
            if (j == bindNames.length - 1) {
                stringBuilderSql.append(entityMapperMirror.getColumFieldBinding(bindName).column().getName());
            } else {
                stringBuilderSql.append(entityMapperMirror.getColumFieldBinding(bindName).column().getName());
                stringBuilderSql.append(",");
            }
            j++;
        }
        if (havingConditons != null && havingConditons != Conditions.FULL) {
            stringBuilderSql.append(SqlKeywords.BLANK.getValue());
            stringBuilderSql.append(havingConditons.express());
        }
        ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
        JdbcTemplete jdbcTemplete = objectTemplete1.getJdbcTemplete();
        JavaClass javaClass = entityMapperMirror.javaClass();
        List<AggregationEntity> list = new ArrayList<>();
        int cfBindingListSize = cfBindingList.size();
        Qurey<List<AggregationEntity>, Serializable> qurey = jdbcTemplete.getQurey(stringBuilderSql.toString(), resultSet -> {
            while (resultSet.next()) {
                AggregationEntity aggregationEntity = new AggregationEntity();
                DaoEntity daoEntity = javaClass.newInstance();
                aggregationEntity.setDaoEntity(daoEntity);
                int i = 1;
                for (; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = objectTemplete1.getResultSetMapper().getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(daoEntity, value);
                }
                for (AggregationParams aggregationParams1 : aggregationParams) {
                    AggregationValue aggregationValue = new AggregationValue();
                    aggregationValue.setAlias(aggregationParams1.getAlias());
                    aggregationValue.setAggregationSymbol(aggregationParams1.getAggregationSymbol());
                    aggregationValue.setValue(resultSet.getObject(i));
                    aggregationEntity.getAggregationValues().add(aggregationValue);
                    i++;
                }
                list.add(aggregationEntity);
            }
            return list;
        });
        return qurey.qurey();
    }

    @Override
    public List<EntityJoinResult> entityJoin(EntityJoin entityJoin) {
        ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
        JdbcTemplete jdbcTemplete = objectTemplete1.getJdbcTemplete();
        ResultSetMapper resultSetMapper = objectTemplete1.getResultSetMapper();
        String sql = entityJoin.sql(0);
        List<BasicEntityJoin> basicEntityJoinList = null;
        if (entityJoin instanceof BasicEntityJoin) {
            basicEntityJoinList = new ArrayList<>();
            BasicEntityJoin basicEntityJoin = (BasicEntityJoin) entityJoin;
            basicEntityJoinList.add(basicEntityJoin);
        } else {
            CompositeEntityJoin compositeEntityJoin = (CompositeEntityJoin) entityJoin;
            basicEntityJoinList = compositeEntityJoin.getBasicEntityJoinList();
        }
        int size = basicEntityJoinList.size();
        final List<BasicEntityJoin> basicEntityJoinList1 = basicEntityJoinList;
        Qurey<List<EntityJoinResult>, Serializable> qurey = jdbcTemplete.getQurey(sql.toString(), resultSet -> {

            List<EntityJoinResult> listEntityJoinResult = new ArrayList<>();
            while (resultSet.next()) {
                EntityJoinResult entityJoinResult = new EntityJoinResult();
                listEntityJoinResult.add(entityJoinResult);
                // 第几个字段从1开始
                int totalindex = 1;
                for (int j = 0; j < size; j++) {
                    BasicEntityJoin basicEntityJoin = basicEntityJoinList1.get(j);
                    EntityMapper entityMapper = basicEntityJoin.getEntityMapper();
                    JavaClass javaClass = entityMapper.javaClass();
                    DaoEntity entity = javaClass.newInstance();
                    entityJoinResult.getDaoEntities().add(entity);
                    List<ColumFieldBinding> columFieldBindings = basicEntityJoin.getColumFieldBindings();
                    int cfBindingListSize = columFieldBindings.size();
                    for (int i = 0; i < cfBindingListSize; i++) {
                        ColumFieldBinding cfBinding = columFieldBindings.get(i);
                        JavaField javaField = cfBinding.field();
                        Object value = resultSetMapper.getValue(resultSet, cfBinding, totalindex, javaField.jdbcType());
                        javaField.setValue(entity, value);
                        totalindex++;
                    }

                }


            }

            return listEntityJoinResult;

        });
        return qurey.qurey();

    }

    @Override
    public List<EntityJoinResult> entityJoin(EntityJoin entityJoin, Pagation pagation) {

        ObjectTempleteImpl objectTemplete1 = (ObjectTempleteImpl) objectTemplete;
        JdbcTemplete jdbcTemplete = objectTemplete1.getJdbcTemplete();
        ResultSetMapper resultSetMapper = objectTemplete1.getResultSetMapper();
        String sql0 = entityJoin.sql(1);// 排除排序
        String sql1 = entityJoin.sql(0);// 原始sql
        String sql2 = entityJoin.sql(2);// 只有别名字段
        StringBuilder countsb = new StringBuilder("select count(*) from (");
        countsb.append(sql0);
        countsb.append(SqlKeywords.RBRACKET.blank());
        countsb.append("a");

        String sql = null;
        if (objectTemplete1.getDbType() == 0) {
            StringBuilder sqlbuilder1 = new StringBuilder(SqlKeywords.SELECT.rightBlank());
            sqlbuilder1.append(sql2);
            sqlbuilder1.append(" FROM (SELECT rownum r,a.* FROM ( ");
            sqlbuilder1.append(sql1);
            sqlbuilder1.append(")");
            sqlbuilder1.append(" a where rownum <= ");// rownum 从1开始
            sqlbuilder1.append(pagation.getEndIndex() + 1);// rownum 从1开始
            sqlbuilder1.append(")");
            sqlbuilder1.append(" b where b.r >= ");
            sqlbuilder1.append((pagation.getRowBeginIndex() + 1));
            sql = sqlbuilder1.toString();
        } else {
            StringBuilder sqlbuilder = new StringBuilder(sql1);
            sqlbuilder.append(" ");
            sqlbuilder.append("limit");
            sqlbuilder.append(" ");
            sqlbuilder.append(pagation.getRowBeginIndex());
            sqlbuilder.append(",");
            sqlbuilder.append(pagation.getEndIndex());
        }
        long total = jdbcTemplete.longQurey(countsb.toString());
        //System.out.println(countsb.toString());
        pagation.setTotal((int) total);

        List<BasicEntityJoin> basicEntityJoinList = null;
        if (entityJoin instanceof BasicEntityJoin) {
            basicEntityJoinList = new ArrayList<>();
            BasicEntityJoin basicEntityJoin = (BasicEntityJoin) entityJoin;
            basicEntityJoinList.add(basicEntityJoin);
        } else {
            CompositeEntityJoin compositeEntityJoin = (CompositeEntityJoin) entityJoin;
            basicEntityJoinList = compositeEntityJoin.getBasicEntityJoinList();
        }
        int size = basicEntityJoinList.size();
        final List<BasicEntityJoin> basicEntityJoinList1 = basicEntityJoinList;
        // System.out.println(sql);
        Qurey<List<EntityJoinResult>, Serializable> qurey = jdbcTemplete.getQurey(sql, resultSet -> {

            List<EntityJoinResult> listEntityJoinResult = new ArrayList<>();
            while (resultSet.next()) {
                EntityJoinResult entityJoinResult = new EntityJoinResult();
                listEntityJoinResult.add(entityJoinResult);
                // 第几个字段从1开始
                int totalindex = 1;
                for (int j = 0; j < size; j++) {
                    BasicEntityJoin basicEntityJoin = basicEntityJoinList1.get(j);
                    EntityMapper entityMapper = basicEntityJoin.getEntityMapper();
                    JavaClass javaClass = entityMapper.javaClass();
                    DaoEntity entity = javaClass.newInstance();
                    entityJoinResult.getDaoEntities().add(entity);
                    List<ColumFieldBinding> columFieldBindings = basicEntityJoin.getColumFieldBindings();
                    int cfBindingListSize = columFieldBindings.size();
                    for (int i = 0; i < cfBindingListSize; i++) {
                        ColumFieldBinding cfBinding = columFieldBindings.get(i);
                        JavaField javaField = cfBinding.field();
                        Object value = resultSetMapper.getValue(resultSet, cfBinding, totalindex, javaField.jdbcType());
                        javaField.setValue(entity, value);
                        totalindex++;
                    }

                }


            }

            return listEntityJoinResult;

        });
        return qurey.qurey();
    }

    @Override
    public int dbType() {
        return ((DbEnviment) objectTemplete).dbType();
    }
}
