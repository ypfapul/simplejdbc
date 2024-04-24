package org.ypf.generic.orm.entityoper;

/**
 * @date: 2022/6/1 16:15
 */
public interface EntityMapperToSql {
    String updateByPrimarySql(EntityMapper entityMapper);

    String updateSql(EntityMapper entityMapper);

    String insertSql(EntityMapper entityMapper);

    String batchInsertSql(EntityMapper entityMapper, int size);

    String selectAllSql(EntityMapper entityMapper);

    String selectDistinctAllSql(EntityMapper entityMapper);

    String selectFieldSql(EntityMapper entityMapper);

    String selectCountSql(EntityMapper entityMapper);

    String deleteSql(EntityMapper entityMapper);

    String idMmaxSql(EntityMapper entityMapper);
}
