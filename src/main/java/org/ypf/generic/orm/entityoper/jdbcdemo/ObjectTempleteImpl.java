package org.ypf.generic.orm.entityoper.jdbcdemo;


import org.ypf.generic.orm.BatchExcuter;
import org.ypf.generic.orm.Excuter;
import org.ypf.generic.orm.JdbcTemplete;
import org.ypf.generic.orm.Qurey;
import org.ypf.generic.orm.entityoper.*;
import org.ypf.generic.orm.entityoper.condition.Conditions;
import org.ypf.generic.orm.entityoper.defineimpl.EntityMapperToSqlImpl;


import java.io.Serializable;
import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @date: 2022/6/2 15:17
 */
public class ObjectTempleteImpl implements ObjectTemplete, DbEnviment {
    EntityMapperToSql entityMapperToSql = new EntityMapperToSqlImpl();
    PreparedStatementSetMathcer preparedStatementSetMathcer = new PreparedStatementSetMathcerImpl();
    ResultSetMapper resultSetMapper = new ResultSetMapperImpl();
    JdbcTemplete jdbcTemplete;
    int dbType;

    public ObjectTempleteImpl(JdbcTemplete jdbcTemplete) {
        this.jdbcTemplete = jdbcTemplete;
    }

    public EntityMapperToSql getEntityMapperToSql() {
        return entityMapperToSql;
    }

    public PreparedStatementSetMathcer getPreparedStatementSetMathcer() {
        return preparedStatementSetMathcer;
    }

    public ResultSetMapper getResultSetMapper() {
        return resultSetMapper;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public JdbcTemplete getJdbcTemplete() {
        return jdbcTemplete;
    }

    @Override
    public void save(Object entity, EntityMapper entityMapper) {
        ColumFieldBinding primay = entityMapper.primay();
        List<ColumFieldBinding> cfBindingList0 = null;
        if (primay != null) {
            cfBindingList0 = primay.strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();
        } else {
            cfBindingList0 = entityMapper.cFBindings();
        }
        List<ColumFieldBinding> cfBindingList = cfBindingList0;

        Excuter excuter = jdbcTemplete.getExcuter(entityMapperToSql.insertSql(entityMapper), (PreparedStatement pst, DaoEntity e) -> {
            int j = 0;
            for (int i = 1; i <= cfBindingList.size(); i++) {
                j++;
                ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                JavaField javaField = cfBinding.field();
                Object value = javaField.getValue(entity);
                //主键策略处理
                if (cfBinding.isPrimaryId()) {
                    IdGenerated localGenerated = null;
                    if ((localGenerated = cfBinding.idGenerated()) != null) {
                        if (localGenerated.getStrategy() == 2) {// 序列化
                            j--;
                            continue;

                        } else if (localGenerated.getStrategy() == 1) {// 自增
                            Long idLong = null;
                            if (IdentityManager.init(entityMapper)) {
                                idLong = IdentityManager.next(entityMapper);
                            } else {

                                synchronized (entityMapper) {
                                    if (!IdentityManager.init(entityMapper)) {
                                        String sqlMaxId = entityMapperToSql.idMmaxSql(entityMapper);
                                        idLong = jdbcTemplete.longQurey(sqlMaxId);
                                        IdentityManager.init(entityMapper, idLong);
                                    } else {
                                        idLong = IdentityManager.next(entityMapper);
                                    }


                                }


                            }
                            value = idLong;

                        }

                    }
                }

                preparedStatementSetMathcer.setValues(pst, cfBinding, j, javaField.jdbcType(), value);
            }

        });
        excuter.excute();
    }

    @Override
    public void batchSave(List<?> entities, EntityMapper entityMapper) {

        ColumFieldBinding primay = entityMapper.primay();
        List<ColumFieldBinding> cfBindingList0 = null;
        if (primay != null) {
            cfBindingList0 = primay.strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();
        } else {
            cfBindingList0 = entityMapper.cFBindings();
        }
        List<ColumFieldBinding> cfBindingList = cfBindingList0;
        //List<ColumFieldBinding> cfBindingList = entityMapper.primay().strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();
        jdbcTemplete.getBatchExcuter(entityMapperToSql.insertSql(entityMapper), (PreparedStatement pst, DaoEntity e) -> {

            for (Object entity : entities) {
                int j = 0;
                for (int i = 1; i <= cfBindingList.size(); i++) {
                    j++;
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = javaField.getValue(entity);
                    //主键策略处理
                    if (cfBinding.isPrimaryId()) {
                        IdGenerated localGenerated = null;
                        if ((localGenerated = cfBinding.idGenerated()) != null) {
                            if (localGenerated.getStrategy() == 2) {// 序列化
                                j--;
                                continue;

                            } else if (localGenerated.getStrategy() == 1) {// 自增
                                Long idLong = null;
                                if (IdentityManager.init(entityMapper)) {
                                    idLong = IdentityManager.next(entityMapper);
                                } else {

                                    synchronized (entityMapper) {
                                        if (!IdentityManager.init(entityMapper)) {
                                            String sqlMaxId = entityMapperToSql.idMmaxSql(entityMapper);
                                            idLong = jdbcTemplete.longQurey(sqlMaxId);
                                            IdentityManager.init(entityMapper, idLong);
                                        } else {
                                            idLong = IdentityManager.next(entityMapper);
                                        }


                                    }


                                }
                                value = idLong;

                            }

                        }
                    }
                    preparedStatementSetMathcer.setValues(pst, cfBinding, j, javaField.jdbcType(), value);

                }
                pst.addBatch();
            }


        }).excute();
    }

    @Override
    public void saveGetId(Object entity, EntityMapper entityMapper) {
        ColumFieldBinding primay = entityMapper.primay();
        List<ColumFieldBinding> cfBindingList0 = null;
        boolean isrequireId = false;
        if (primay != null) {
            IdGenerated localGenerated = null;
            if (primay.strategy() == PrimaryStrategy.DB) {
                isrequireId = true;
            } else if ((localGenerated = primay.idGenerated()) != null) {
                if (localGenerated.getStrategy() == 2 || localGenerated.getStrategy() == 1) {
                    isrequireId = true;

                }

            }
            cfBindingList0 = primay.strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();

        } else {
            cfBindingList0 = entityMapper.cFBindings();
        }
        List<ColumFieldBinding> cfBindingList = cfBindingList0;

        Excuter excuter = jdbcTemplete.getExcuter(entityMapperToSql.insertSql(entityMapper), (PreparedStatement pst, DaoEntity e) -> {
            int j = 0;
            for (int i = 1; i <= cfBindingList.size(); i++) {
                j++;
                ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                JavaField javaField = cfBinding.field();
                Object value = javaField.getValue(entity);
                //主键策略处理
                if (cfBinding.isPrimaryId()) {
                    IdGenerated localGenerated = null;
                    if ((localGenerated = cfBinding.idGenerated()) != null) {
                        if (localGenerated.getStrategy() == 2) {// 序列化
                            j--;
                            continue;

                        } else if (localGenerated.getStrategy() == 1) {// 自增
                            Long idLong = null;
                            if (IdentityManager.init(entityMapper)) {
                                idLong = IdentityManager.next(entityMapper);
                            } else {

                                synchronized (entityMapper) {
                                    if (!IdentityManager.init(entityMapper)) {
                                        String sqlMaxId = entityMapperToSql.idMmaxSql(entityMapper);
                                        idLong = jdbcTemplete.longQurey(sqlMaxId);
                                        IdentityManager.init(entityMapper, idLong);
                                    } else {
                                        idLong = IdentityManager.next(entityMapper);
                                    }


                                }


                            }
                            value = idLong;

                        }

                    }
                }

                preparedStatementSetMathcer.setValues(pst, cfBinding, j, javaField.jdbcType(), value);

            }

        });
        if (isrequireId) {
            excuter.setDbType(dbType);
            excuter.setModel(1);
            if (dbType == 0) {
                excuter.getJdbcExcuterContext().put(Excuter.RETURN_GENERATED_KEYS_COLUMN_JDBCTYPE, primay.field().jdbcType());
                excuter.getJdbcExcuterContext().put(Excuter.RETURN_GENERATED_KEYS_COLUMN_NAME, primay.column().getName());
            }

        }
        excuter.excute();
        if (isrequireId) {
            Object id = excuter.getIds().get(0);
            primay.field().setValue(entity, id);
        }

    }

    @Override
    public void batchSaveGetId(List<?> entities, EntityMapper entityMapper) {

        ColumFieldBinding primay = entityMapper.primay();
        List<ColumFieldBinding> cfBindingList0 = null;
        boolean isrequireId = false;
        if (primay != null) {
            IdGenerated localGenerated = null;
            if (primay.strategy() == PrimaryStrategy.DB) {
                isrequireId = true;
            } else if ((localGenerated = primay.idGenerated()) != null) {
                if (localGenerated.getStrategy() == 2 || localGenerated.getStrategy() == 1) {
                    isrequireId = true;

                }

            }
            cfBindingList0 = primay.strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();
        } else {
            cfBindingList0 = entityMapper.cFBindings();
        }
        List<ColumFieldBinding> cfBindingList = cfBindingList0;
        int size = entities.size();
        BatchExcuter batchExcuter = jdbcTemplete.getBatchExcuter(entityMapperToSql.batchInsertSql(entityMapper, size), (PreparedStatement pst, DaoEntity e) -> {
            int j = 0;
            for (Object entity : entities) {
                for (int i = 1; i <= cfBindingList.size(); i++) {
                    j++;
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = javaField.getValue(entity);
                    //主键策略处理
                    if (cfBinding.isPrimaryId()) {
                        IdGenerated localGenerated = null;
                        if ((localGenerated = cfBinding.idGenerated()) != null) {
                            if (localGenerated.getStrategy() == 2) {// 序列化
                                j--;
                                continue;

                            } else if (localGenerated.getStrategy() == 1) {// 自增
                                Long idLong = null;
                                if (IdentityManager.init(entityMapper)) {
                                    idLong = IdentityManager.next(entityMapper);
                                } else {

                                    synchronized (entityMapper) {
                                        if (!IdentityManager.init(entityMapper)) {
                                            String sqlMaxId = entityMapperToSql.idMmaxSql(entityMapper);
                                            idLong = jdbcTemplete.longQurey(sqlMaxId);
                                            IdentityManager.init(entityMapper, idLong);
                                        } else {
                                            idLong = IdentityManager.next(entityMapper);
                                        }


                                    }

                                }
                                value = idLong;

                            }

                        }
                    }
                    preparedStatementSetMathcer.setValues(pst, cfBinding, j, javaField.jdbcType(), value);
                }

            }
            pst.addBatch();


        });
        if (isrequireId) {
            batchExcuter.setDbType(dbType);
            batchExcuter.setModel(1);
            if (dbType == 0) {
                batchExcuter.getJdbcExcuterContext().put(Excuter.RETURN_GENERATED_KEYS_COLUMN_JDBCTYPE, primay.field().jdbcType());
                batchExcuter.getJdbcExcuterContext().put(Excuter.RETURN_GENERATED_KEYS_COLUMN_NAME, primay.column().getName());
            }

        }
        batchExcuter.excute();
        if (isrequireId) {
            int index = 0;
            for (Object id : batchExcuter.getIds()) {
                primay.field().setValue(entities.get(index), id);
                index++;
            }

        }

    }

    @Override
    public <T> T get(Serializable entityId, EntityMapper entityMapper) {
        JavaClass javaClass = entityMapper.javaClass();
        StringBuilder sql = new StringBuilder(entityMapperToSql.selectAllSql(entityMapper));
        sql.append(" where ");
        ColumFieldBinding id = entityMapper.primay();
        Conditions cfBindingConditions = id.eq(entityId);
        sql.append(cfBindingConditions.express());
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        Qurey<T, Serializable> qurey = jdbcTemplete.getQurey(sql.toString(), resultSet -> {
            DaoEntity entity = null;
            while (resultSet.next()) {
                entity = javaClass.newInstance();
                for (int i = 1; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = resultSetMapper.getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(entity, value);
                }
                break;

            }

            return (T) entity;

        });
        return qurey.qurey();
    }

    @Override
    public <T> List<T> getList(Conditions condtions, EntityMapper entityMapper) {
        if (condtions.equals(Conditions.EMPTY))
            return Collections.emptyList();
        StringBuilder sql = new StringBuilder(entityMapperToSql.selectAllSql(entityMapper));

        if (condtions.equals(Conditions.FULL)) {
        } else {
            sql.append(" where ");
            sql.append(condtions.express());
        }

        JavaClass javaClass = entityMapper.javaClass();
        List<T> list = new ArrayList<>();
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        Qurey<List<T>, Serializable> qurey = jdbcTemplete.getQurey(sql.toString(), resultSet -> {
            while (resultSet.next()) {
                DaoEntity entity = javaClass.newInstance();
                for (int i = 1; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = resultSetMapper.getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(entity, value);
                }
                list.add((T) entity);
            }

            return list;

        });
        return qurey.qurey();
    }

    @Override
    public long getQuantity(Conditions condtions, EntityMapper entityMapper) {
        if (condtions.equals(Conditions.EMPTY))
            return 0l;

        StringBuilder sql = new StringBuilder("select count(*) from ");
        sql.append(entityMapper.table().getName());
        sql.append(" ");
        if (condtions.equals(Conditions.FULL)) {
        } else {
            sql.append("where ");
            sql.append(condtions.express());
        }

        JavaClass javaClass = entityMapper.javaClass();
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        return jdbcTemplete.longQurey(sql.toString());

    }

    @Override
    public <T> List<T> getList(Conditions condtions, EntityMapper entityMapper, String orderbys) {

        if (condtions.equals(Conditions.EMPTY))
            return Collections.emptyList();

        JavaClass javaClass = entityMapper.javaClass();
        StringBuilder sqlbuilder = new StringBuilder(entityMapperToSql.selectAllSql(entityMapper));

        if (!condtions.equals(Conditions.FULL)) {
            sqlbuilder.append(" where ");
            sqlbuilder.append(condtions.express());
        }
        if (orderbys != null) {
            sqlbuilder.append(" ");
            sqlbuilder.append(orderbys);
        }

        String sql = sqlbuilder.toString();
        List<T> list = new ArrayList<>();
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        Qurey<List<T>, Serializable> qurey = jdbcTemplete.getQurey(sql, resultSet -> {
            while (resultSet.next()) {
                DaoEntity entity = javaClass.newInstance();
                for (int i = 1; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = resultSetMapper.getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(entity, value);
                }
                list.add((T) entity);
            }

            return list;

        });
        return qurey.qurey();
    }

    @Override
    public <T> List<T> getDistinctList(Conditions condtions, EntityMapper entityMapper, String orderbys) {
        if (condtions.equals(Conditions.EMPTY))
            return Collections.emptyList();

        JavaClass javaClass = entityMapper.javaClass();
        StringBuilder sqlbuilder = new StringBuilder(entityMapperToSql.selectDistinctAllSql(entityMapper));
        if (condtions.equals(Conditions.FULL)) {
        } else {
            sqlbuilder.append(" where ");
            sqlbuilder.append(condtions.express());
            sqlbuilder.append(" ");
        }
        if (orderbys != null) {
            sqlbuilder.append(orderbys);
        }

        String sql = sqlbuilder.toString();
        List<T> list = new ArrayList<>();
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        Qurey<List<T>, Serializable> qurey = jdbcTemplete.getQurey(sql, resultSet -> {
            while (resultSet.next()) {
                DaoEntity entity = javaClass.newInstance();
                for (int i = 1; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = resultSetMapper.getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(entity, value);
                }
                list.add((T) entity);
            }

            return list;

        });
        return qurey.qurey();
    }

    @Override
    public <T> List<T> getList(Conditions condtions, EntityMapper entityMapper, String orderbys, Pagation pagation) {

        if (condtions.equals(Conditions.EMPTY))
            return Collections.emptyList();
        JavaClass javaClass = entityMapper.javaClass();
        StringBuilder sqlCountbuilder = new StringBuilder(entityMapperToSql.selectCountSql(entityMapper));

        if (condtions.equals(Conditions.FULL)) {
        } else {
            sqlCountbuilder.append(" where ");
            sqlCountbuilder.append(condtions.express());

        }
        StringBuilder sqlbuilder = new StringBuilder(entityMapperToSql.selectAllSql(entityMapper));
        if (condtions.equals(Conditions.FULL)) {
        } else {
            sqlbuilder.append(" where ");
            sqlbuilder.append(condtions.express());
        }
        sqlbuilder.append(" ");

        sqlbuilder.append(orderbys);
        if (dbType == 0) {
            StringBuilder sqlbuilder1 = new StringBuilder(entityMapperToSql.selectFieldSql(entityMapper));
            sqlbuilder1.append(" FROM (SELECT rownum r,a.* FROM ( ");
            sqlbuilder1.append(sqlbuilder.toString());
            sqlbuilder1.append(")");
            sqlbuilder1.append(" a where rownum <= ");// rownum 从1开始
            sqlbuilder1.append(pagation.getEndIndex() + 1);// rownum 从1开始
            sqlbuilder1.append(")");
            sqlbuilder1.append(" b where b.r >= ");
            sqlbuilder1.append((pagation.getRowBeginIndex() + 1));
            sqlbuilder = sqlbuilder1;
        } else {
            sqlbuilder.append(" ");
            sqlbuilder.append("limit");
            sqlbuilder.append(" ");
            sqlbuilder.append(pagation.getRowBeginIndex());
            sqlbuilder.append(",");
            sqlbuilder.append(pagation.getEndIndex());
        }
        long size = jdbcTemplete.longQurey(sqlCountbuilder.toString());
        pagation.setTotal((int) size);
        String sql = sqlbuilder.toString();
        List<T> list = new ArrayList<>();
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        Qurey<List<T>, Serializable> qurey = jdbcTemplete.getQurey(sql, resultSet -> {
            while (resultSet.next()) {
                DaoEntity entity = javaClass.newInstance();
                for (int i = 1; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = resultSetMapper.getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(entity, value);
                }
                list.add((T) entity);
            }

            return list;

        });
        return qurey.qurey();


    }

    @Override
    public <T> T get(Conditions condtions, EntityMapper entityMapper) {
        if (condtions.equals(Conditions.EMPTY)) {
            return null;
        }
        JavaClass javaClass = entityMapper.javaClass();
        StringBuilder sql = new StringBuilder(entityMapperToSql.selectAllSql(entityMapper));
        if (!condtions.equals(Conditions.FULL)) {
            sql.append(" where ");
            sql.append(condtions.express());
        }
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        int cfBindingListSize = cfBindingList.size();
        Qurey<T, Serializable> qurey = jdbcTemplete.getQurey(sql.toString(), resultSet -> {
            DaoEntity entity = null;
            while (resultSet.next()) {
                entity = javaClass.newInstance();
                for (int i = 1; i <= cfBindingListSize; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    Object value = resultSetMapper.getValue(resultSet, cfBinding, i, javaField.jdbcType());
                    javaField.setValue(entity, value);
                }
                break;

            }

            return (T) entity;

        });
        return qurey.qurey();
    }

    @Override
    public void batchDelete(List<?> entities, EntityMapper entityMapper) {

        ColumFieldBinding primay = entityMapper.primay();
        StringBuilder sqlb = new StringBuilder(entityMapperToSql.deleteSql(entityMapper));
        sqlb.append(" where ");
        sqlb.append(primay.column().getName());
        sqlb.append("= ?");
        JavaField javaField = primay.field();
        jdbcTemplete.getBatchExcuter(sqlb.toString(), (PreparedStatement pst, DaoEntity e) -> {
            for (Object entity : entities) {
                preparedStatementSetMathcer.setValues(pst, primay, 1, javaField.jdbcType(), javaField.getValue(entity));
                pst.addBatch();
            }

        }).excute();
    }

    @Override
    public void batchUpdate(List<?> entities, EntityMapper entityMapper) {
        ColumFieldBinding primay = entityMapper.primay();
        JavaField pjavaField = primay.field();
        String sql = entityMapperToSql.updateByPrimarySql(entityMapper);
        List<ColumFieldBinding> cfBindingList = entityMapper.exclusivePrimay();
        int size = cfBindingList.size();
        jdbcTemplete.getBatchExcuter(sql, (PreparedStatement pst, DaoEntity e) -> {
            for (Object entity : entities) {
                for (int i = 1; i <= size; i++) {
                    ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                    JavaField javaField = cfBinding.field();
                    preparedStatementSetMathcer.setValues(pst, cfBinding, i, javaField.jdbcType(), javaField.getValue(entity));
                }
                preparedStatementSetMathcer.setValues(pst, primay, size + 1, pjavaField.jdbcType(), pjavaField.getValue(entity));
                pst.addBatch();

            }

        }).excute();

    }

    @Override
    public void delete(Object entity, EntityMapper entityMapper) {
        ColumFieldBinding primay = entityMapper.primay();
        JavaField javaField = primay.field();
        StringBuilder stringBuilder = new StringBuilder(entityMapperToSql.deleteSql(entityMapper));
//        stringBuilder.append(SqlKeywords.BLANK.value);
//        stringBuilder.append(SqlKeywords.WHERE.value);
//        stringBuilder.append(SqlKeywords.BLANK.value);
//        stringBuilder.append(primay.column().getName());
//        stringBuilder.append(SqlKeywords.BLANK.value);
//        stringBuilder.append(SqlKeywords.EQ.value);
//        stringBuilder.append(SqlKeywords.BLANK.value);
//        stringBuilder.append(SqlKeywords.ASKMARK.value);
        String sql = entityMapperToSql.deleteSql(entityMapper) + " where " + primay.column().getName() + "= ?";
        jdbcTemplete.getExcuter(sql, (PreparedStatement pst, DaoEntity e) -> {
            preparedStatementSetMathcer.setValues(pst, primay, 1, javaField.jdbcType(), javaField.getValue(entity));
        }).excute();
    }

    @Override
    public void deleteAll(EntityMapper entityMapper) {
        String sql = entityMapperToSql.deleteSql(entityMapper);
        jdbcTemplete.getExcuter(sql).excute();
    }

    @Override
    public void delete(Conditions condtions, EntityMapper entityMapper) {
        if (condtions == Conditions.EMPTY)
            return;
        if (condtions == Conditions.FULL) {
            deleteAll(entityMapper);
            return;
        }
        String sql = entityMapperToSql.deleteSql(entityMapper) + " where ";
        sql += condtions.express();
        jdbcTemplete.getExcuter(sql).excute();
    }

    @Override
    public void update(Object entity, EntityMapper entityMapper) {
        ColumFieldBinding primay = entityMapper.primay();
        JavaField pjavaField = primay.field();
        String sql = entityMapperToSql.updateByPrimarySql(entityMapper);
        List<ColumFieldBinding> cfBindingList = entityMapper.exclusivePrimay();
        int size = cfBindingList.size();
        jdbcTemplete.getExcuter(sql, (PreparedStatement pst, DaoEntity e) -> {
            for (int i = 1; i <= size; i++) {
                ColumFieldBinding cfBinding = cfBindingList.get(i - 1);
                JavaField javaField = cfBinding.field();
                preparedStatementSetMathcer.setValues(pst, cfBinding, i, javaField.jdbcType(), javaField.getValue(entity));
            }
            preparedStatementSetMathcer.setValues(pst, primay, size + 1, pjavaField.jdbcType(), pjavaField.getValue(entity));
        }).excute();

    }

    @Override
    public void update(Conditions condtions, EntityMapper entityMapper, Assignment... assignments) {
        if (condtions == Conditions.EMPTY) {
            return;
        }
        List<Assignment> assignmentList = Arrays.asList(assignments);

        Set<String> bdst = assignmentList.stream().map(a -> a.getColumFieldBinding()).collect(Collectors.toSet());
        List<ColumFieldBinding> columFieldBindings = entityMapper.cFBindings().stream()
                .filter(b -> bdst.contains(b.getName())).collect(Collectors.toList());

        StringBuilder sqlBuilder = new StringBuilder("update ");
        sqlBuilder.append(entityMapper.table().getName());
        sqlBuilder.append("set ");
        sqlBuilder.append(columFieldBindings.stream().map(e -> e.column().getName() + "= ?").collect(Collectors.joining(",")));
        if (condtions != Conditions.FULL) {
            sqlBuilder.append(" where ");
            sqlBuilder.append(condtions.express());
        }
        jdbcTemplete.getExcuter(sqlBuilder.toString(), (PreparedStatement pst, DaoEntity e) -> {

            int j = 1;
            for (Assignment columFieldBinding : assignmentList) {
                ColumFieldBinding columFieldBinding1 = entityMapper.getColumFieldBinding(columFieldBinding.getColumFieldBinding());
                JavaField javaField = columFieldBinding1.field();
                preparedStatementSetMathcer.setValues(pst, columFieldBinding1, j, javaField.jdbcType(), columFieldBinding.getValue());
                j++;
            }

        }).excute();
    }

    @Override
    public int dbType() {
        return dbType;
    }
}
