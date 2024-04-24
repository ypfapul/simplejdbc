package org.ypf.generic.orm.entityoper.defineimpl;


import org.ypf.generic.orm.entityoper.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @date: 2022/6/1 16:40
 */
public class EntityMapperToSqlImpl implements EntityMapperToSql {
    @Override
    public String updateByPrimarySql(EntityMapper entityMapper) {
        ColumFieldBinding primay = entityMapper.primay();
        StringBuilder stringBuilderSql = new StringBuilder("update ");
        stringBuilderSql.append(entityMapper.table().getName());
        stringBuilderSql.append(" set ");
        List<ColumFieldBinding> cfBindingList = entityMapper.exclusivePrimay();
        stringBuilderSql.append(cfBindingList.stream().map(e -> e.column().getName() + "= ?").collect(Collectors.joining(",")));
        stringBuilderSql.append(" where ");
        stringBuilderSql.append(primay.column().getName());
        stringBuilderSql.append(" = ?");
        return stringBuilderSql.toString();
    }

    @Override
    public String updateSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("update ");
        stringBuilderSql.append(entityMapper.table().getName());
        stringBuilderSql.append(" set ");
        List<ColumFieldBinding> cfBindingList = entityMapper.exclusivePrimay();
        stringBuilderSql.append(cfBindingList.stream().map(e -> e.column().getName() + "= ?").collect(Collectors.joining(",")));
        return stringBuilderSql.toString();
    }

    @Override
    public String insertSql(EntityMapper entityMapper) {
        List<ColumFieldBinding> cfBindingList = entityMapper.primay().strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();
        StringBuilder insertInto = new StringBuilder("insert into ");
        insertInto.append(entityMapper.table().getName());
        insertInto.append(" (");
        insertInto.append(cfBindingList.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));
        insertInto.append(")");
        insertInto.append(" values (");
        insertInto.append(cfBindingList.stream().map(e -> {
            if (e.isPrimaryId()) {
                IdGenerated localGenerated = null;
                if ((localGenerated = e.idGenerated()) != null) {
                    if (localGenerated.getStrategy() == 2) {// 序列化
                        return new StringBuilder(localGenerated.getGenerator()).append(SqlKeywords.DOT.value).append("nextval").toString();

                    }

                }
            }
            return "?";


        }).collect(Collectors.joining(",")));
        insertInto.append(")");
        return insertInto.toString();

    }

    @Override
    public String batchInsertSql(EntityMapper entityMapper, int size) {
        List<ColumFieldBinding> cfBindingList = entityMapper.primay().strategy() == PrimaryStrategy.DB ? entityMapper.exclusivePrimay() : entityMapper.cFBindings();
        StringBuilder insertInto = new StringBuilder("insert into ");
        insertInto.append(entityMapper.table().getName());
        insertInto.append(" (");
        insertInto.append(cfBindingList.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));
        insertInto.append(")");
        insertInto.append(" values ");

        StringBuilder insertInto0 = new StringBuilder("(");
        String columns = cfBindingList.stream().map(e -> {
            if (e.isPrimaryId()) {
                IdGenerated localGenerated = null;
                if ((localGenerated = e.idGenerated()) != null) {
                    if (localGenerated.getStrategy() == 2) {// 序列化
                        return new StringBuilder(localGenerated.getGenerator()).append(SqlKeywords.DOT.value).append("nextval").toString();

                    }

                }
            }
            return "?";
        }).collect(Collectors.joining(","));
        insertInto0.append(columns);
        insertInto0.append(")");
        int lastIndex = size - 1;
        for (int i = 0; i < size; i++) {
            if (i < lastIndex) {
                insertInto.append(insertInto0);
                insertInto.append(",");
            } else {
                insertInto.append(insertInto0);
            }

        }
        return insertInto.toString();
    }

    @Override
    public String selectAllSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("select ");
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        stringBuilderSql.append(cfBindingList.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));
        stringBuilderSql.append(" from ");
        stringBuilderSql.append(entityMapper.table().getName());
        return stringBuilderSql.toString();
    }

    @Override
    public String selectDistinctAllSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("select distinct ");
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        stringBuilderSql.append(cfBindingList.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));
        stringBuilderSql.append(" from ");
        stringBuilderSql.append(entityMapper.table().getName());
        return stringBuilderSql.toString();
    }

    @Override
    public String selectFieldSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("select ");
        List<ColumFieldBinding> cfBindingList = entityMapper.cFBindings();
        stringBuilderSql.append(cfBindingList.stream().map(e -> e.column().getName()).collect(Collectors.joining(",")));
        return stringBuilderSql.toString();
    }


    @Override
    public String selectCountSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("select count(*)");
        stringBuilderSql.append(" from ");
        stringBuilderSql.append(entityMapper.table().getName());
        return stringBuilderSql.toString();
    }

    @Override
    public String deleteSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("delete from ");
        stringBuilderSql.append(entityMapper.table().getName());
        return stringBuilderSql.toString();
    }

    @Override
    public String idMmaxSql(EntityMapper entityMapper) {
        StringBuilder stringBuilderSql = new StringBuilder("select max(");
        ColumFieldBinding primay = entityMapper.primay();
        stringBuilderSql.append(primay.column().getName());
        stringBuilderSql.append(") from ");
        stringBuilderSql.append(entityMapper.table().getName());
        return stringBuilderSql.toString();
    }
}
