package org.ypf.generic.orm.entityoper;

import org.springframework.util.StringUtils;
import org.ypf.generic.orm.JdbcType;
import org.ypf.generic.orm.entityoper.defineimpl.*;
import org.ypf.generic.orm.entityoper.readmapperconf.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @date: 2022/6/2 16:53
 */
public class EntityMapperRepository {

    private final static Map<String, EntityMapper> ENTITY_MAPPER_MAP = new ConcurrentHashMap<>();
    private final static List<BindBean> BIND_BEANS = Collections.synchronizedList(new ArrayList<>());
    private final static List<EntityBean> ENTITY_BEANS = Collections.synchronizedList(new ArrayList<>());
    private final static List<TableBean> TABLE_BEANS = Collections.synchronizedList(new ArrayList<>());

    static {
        init();
    }


    static void init() {
        ServiceLoader<BindMapperSupplier> loader = ServiceLoader.load(BindMapperSupplier.class);
        loader.forEach(e -> {
            BIND_BEANS.addAll(e.bindBean());
            ENTITY_BEANS.addAll(e.entityBean());
            TABLE_BEANS.addAll(e.tableBean());
        });


    }

    /**
     * 从配置中读取
     *
     * @param entityMapperName
     * @return
     */
    static EntityMapper readEntityMapper(String entityMapperName) {
        EntityMapper entityMapper = null;
        for (BindBean bindBean : BIND_BEANS) {
            if (bindBean.getName().equals(entityMapperName)) {
                String javaclass = bindBean.getJavaclass();
                String table = bindBean.getTable();
                EntityBean entityBean = ENTITY_BEANS.stream().filter(e -> e.getName().equals(javaclass)).findFirst().get();
                TableBean tableBean = TABLE_BEANS.stream().filter(e -> e.getName().equals(table)).findFirst().get();
                TableImpl tableImpl = new TableImpl();
                tableImpl.setTableName(tableBean.getName());
                List<Column> columns = tableBean.getColumns().stream().map(c -> {
                            ColumnImpl column = new ColumnImpl(tableImpl, c.getName(), c.getBindKey(), c.getPrimary() == 1 ? true : false, c.getPrimaryStrategy() == 0 ? PrimaryStrategy.LOCAL : PrimaryStrategy.DB);
                            column.setIdGenerated(c.getIdGenerated());
                            return column;
                        }

                ).collect(Collectors.toList());
                tableImpl.setColumn(columns);

                JavaClassImpl javaClass = new JavaClassImpl();
                javaClass.setClassName(entityBean.getName());
                List<JavaField> javaField = entityBean.getFields().stream().map(c ->
                        new FieldImpl(javaClass, c.getName(), JdbcType.codeTojdbcType(c.getType()))
                ).collect(Collectors.toList());
                javaClass.setFields(javaField);
                entityMapper = new EntityMapperImpl(tableImpl, javaClass);
                break;
            }


        }


        return entityMapper;
    }


    /**
     * jpa规划解析
     *
     * @param entityClass
     */
    synchronized static void jpaAnalysis(Class<?> entityClass) {
        String cn = entityClass.getName();
        //jpa 规范
        BindBean bindBean = new BindBean();
        bindBean.setJavaclass(cn);
        bindBean.setName(cn);
        EntityBean entityBean = new EntityBean();
        List<FieldBean> fieldBeans = new ArrayList<>();
        entityBean.setFields(fieldBeans);
        entityBean.setName(cn);
        TableBean tableBean = new TableBean();
        List<ColumnBean> columnBeans = new ArrayList<>();
        tableBean.setColumns(columnBeans);
        Table table = entityClass.getAnnotation(Table.class);

        if (StringUtils.isEmpty(table.name())) {
            tableBean.setName(entityClass.getSimpleName().toUpperCase());
        } else {
            tableBean.setName(table.name());
        }

        bindBean.setTable(tableBean.getName());
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            javax.persistence.Column column = field.getDeclaredAnnotation(javax.persistence.Column.class);
            if (column != null) {
                FieldBean fieldBean = new FieldBean();
                fieldBeans.add(fieldBean);
                fieldBean.setBindKey(field.getName());
                fieldBean.setName(field.getName());
                Class type = field.getType();
                JdbcType jdbcType = JdbcType.classToJdbcType(type);
                fieldBean.setType(jdbcType.code());
                ColumnBean columnBean = new ColumnBean();
                columnBeans.add(columnBean);
                if (StringUtils.isEmpty(column.name())) {
                    columnBean.setName(field.getName().toUpperCase());
                } else {
                    columnBean.setName(column.name());
                }

                columnBean.setBindKey(field.getName());
                javax.persistence.Id id = field.getDeclaredAnnotation(javax.persistence.Id.class);
                if (id != null) {//主键
                    columnBean.setPrimary(1);

                    GeneratedValue generatedValue = field.getDeclaredAnnotation(GeneratedValue.class);
                    if (generatedValue == null) {
                        IdGenerated idGenerated = new IdGenerated();
                        columnBean.setIdGenerated(idGenerated);
                        columnBean.setPrimaryStrategy(0);
                    } else {
                        if (generatedValue.strategy() == GenerationType.TABLE) {// 数据库负责生成
                            columnBean.setPrimaryStrategy(1);
                        } else {
                            IdGenerated idGenerated = new IdGenerated();
                            columnBean.setIdGenerated(idGenerated);
                            columnBean.setPrimaryStrategy(0);
                            if (generatedValue.strategy() == GenerationType.AUTO) {// 本地化实体负责


                            } else if (generatedValue.strategy() == GenerationType.IDENTITY) {
                                idGenerated.setStrategy(1);

                            } else if (generatedValue.strategy() == GenerationType.SEQUENCE) {
                                idGenerated.setStrategy(2);
                                idGenerated.setGenerator(generatedValue.generator());
                            }


                        }


                    }

                }


            }

        }
        BIND_BEANS.add(bindBean);
        ENTITY_BEANS.add(entityBean);
        TABLE_BEANS.add(tableBean);


    }

    public static EntityMapper get(Class<?> entityClass) {
        return get(entityClass.getName());
    }


    /**
     * 优先使用spi规范查找
     * 最后尝试解析jpa规范
     *
     * @param entityMapperName
     * @return
     */
    public static EntityMapper get(String entityMapperName) {
        EntityMapper entityMapper = ENTITY_MAPPER_MAP.get(entityMapperName);
        if (entityMapper == null) {
            synchronized (EntityMapperRepository.class) {
                if (entityMapper == null) {
                    entityMapper = readEntityMapper(entityMapperName);
                    if (entityMapper == null) {
                        try {
                            Class<?> c = Class.forName(entityMapperName);
                            Entity entity = c.getAnnotation(Entity.class);
                            if (entity != null) {
                                jpaAnalysis(c);
                                entityMapper = readEntityMapper(entityMapperName);
                            }

                        } catch (ClassNotFoundException e) {
                        }

                    }
                    if (entityMapper != null) {
                        ENTITY_MAPPER_MAP.put(entityMapperName, entityMapper);
                    }

                } else {
                    entityMapper = ENTITY_MAPPER_MAP.get(entityMapperName);
                }

            }

        }
        return entityMapper;
    }


}
