package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.entityoper.condition.Conditions;

import java.io.Serializable;
import java.util.List;

/**
 * @date: 2022/5/31 9:02
 */
public interface ObjectTemplete {
    void save(Object entity, EntityMapper entityMapper);

    void batchSave(List<?> entities, EntityMapper entityMapper);


    //保存获取id
    void saveGetId(Object entity, EntityMapper entityMapper);

    //保存获取id
    void batchSaveGetId(List<?> entities, EntityMapper entityMapper);

    <T> T get(Serializable entityId, EntityMapper entityMapper);

    <T> List<T> getList(Conditions condtions, EntityMapper entityMapper);

    /**
     * 数量
     *
     * @param condtions
     * @param entityMapper
     * @return
     */
    long getQuantity(Conditions condtions, EntityMapper entityMapper);

    /**
     * 排序查询
     *
     * @param condtions
     * @param entityMapper
     * @param orderbys     sql 排序语法
     * @param <T>
     * @return
     */
    <T> List<T> getList(Conditions condtions, EntityMapper entityMapper, String orderbys);

    /**
     * 去重查询
     *
     * @param condtions
     * @param entityMapper
     * @param orderbys
     * @param <T>
     * @return
     */
    <T> List<T> getDistinctList(Conditions condtions, EntityMapper entityMapper, String orderbys);

    <T> List<T> getList(Conditions condtions, EntityMapper entityMapper, String orderbys, Pagation pagation);

    <T> T get(Conditions condtions, EntityMapper entityMapper);


    void batchDelete(List<?> entities, EntityMapper entityMapper);

    void batchUpdate(List<?> entities, EntityMapper entityMapper);

    void delete(Object entity, EntityMapper entityMapper);

    void deleteAll(EntityMapper entityMapper);

    void delete(Conditions condtions, EntityMapper entityMapper);

    void update(Object entity, EntityMapper entityMapper);

    /**
     * 根据条件更新指定字段
     *
     * @param condtions
     * @param entityMapper
     * @param assignments
     */
    void update(Conditions condtions, EntityMapper entityMapper, Assignment... assignments);


}
