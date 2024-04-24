package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.entityoper.condition.Conditions;

import java.io.Serializable;
import java.util.List;

/**
 * 实体操作
 *
 * @date: 2022/5/31 9:02
 */
public interface EntityTemplete extends CriteraTemplete, DbEnviment {
    <T extends DaoEntity> T get(Serializable entityId, Class<T> tClass);

    <T extends DaoEntity> T get(Serializable entityId, Class<T> tClass, String... bindNames);

    <T extends DaoEntity> List<T> getList(Conditions condtions, Class<T> tClass);


    /**
     * 数量
     *
     * @param condtions
     * @param tClass
     * @return
     */
    long getQuantity(Conditions condtions, Class<?> tClass);


    /**
     * 拷贝数据
     *
     * @param sourceCondtions
     * @param source
     * @param target
     */
    void copyData(Conditions sourceCondtions, Class<? extends DaoEntity> source, Class<? extends DaoEntity> target);

    /**
     * 拷贝数据
     *
     * @param source
     * @param target
     */
    void copyData(Class<? extends DaoEntity> source, Class<? extends DaoEntity> target);


    /**
     * 分页查询
     *
     * @param condtions
     * @param tClass
     * @param orderbys
     * @param pagation  分页参数
     * @param <T>
     * @return
     */
    <T extends DaoEntity> List<T> getList(Conditions condtions, Class<T> tClass, String orderbys, Pagation pagation);

    <T extends DaoEntity> List<T> getListOnBindNames(Conditions condtions, Class<T> tClass, String... bindNames);

    <T extends DaoEntity> List<T> getListOnOrder(Conditions condtions, Class<T> tClass, String orderbys);

    /**
     * 查询实体列表
     *
     * @param condtions 条件
     * @param tClass    实体类
     * @param orderbys  排序sql段
     * @param bindNames 指定实体字段绑定映射
     * @param <T>
     * @return
     */
    <T extends DaoEntity> List<T> getListOnOrderbysAndBindNames(Conditions condtions, Class<T> tClass, String orderbys, String... bindNames);

    <T extends DaoEntity> List<T> getDistinctListOnBindNames(Conditions condtions, Class<T> tClass, String... bindNames);

    /**
     * distinct 查询
     * 指定字段
     *
     * @param condtions
     * @param tClass
     * @param orderbys
     * @param bindNames
     * @param <T>
     * @return
     */
    <T extends DaoEntity> List<T> getDistinctListOnOrderbysAndBindNames(Conditions condtions, Class<T> tClass, String orderbys, String... bindNames);

    <T extends DaoEntity> T get(Conditions condtions, Class<T> tClass);


    <T extends DaoEntity> T get(Conditions condtions, Class<T> tClass, String... bindNames);


    //分表保存
    void saveSectionTableEntity(SectionTableEntity entity);

    // 批量分表保存
    <T extends SectionTableEntity> void batchSaveSectionTableEntity(List<T> entities);

    void save(DaoEntity entity);

    void saveGetId(DaoEntity entity);

    <T extends DaoEntity> void batchSave(List<T> entities);

    //oralce 序列 批处理保存只能获得最后一个id
    <T extends DaoEntity> void batchSaveGetId(List<T> entities);

    void update(DaoEntity entity);

    /**
     * 根据条件更新
     * 忽略target 字段 null值
     *
     * @param target
     * @param conditions
     */
    void updateByConditionsIgnoreNull(DaoEntity target, Conditions conditions);

    /**
     * 根据条件更新全字段更新
     *
     * @param target
     * @param conditions
     */
    void updateByConditions(DaoEntity target, Conditions conditions);

    /**
     * 根据指定的字段绑定映射更新
     *
     * @param entity
     * @param bindNames
     */
    void updateByBinds(DaoEntity entity, String... bindNames);

    void delete(DaoEntity entity);

    <T extends DaoEntity> void delete(Conditions condtions, Class<T> tClass);

    <T extends DaoEntity> void deleteAll(Class<T>... tClass);

    <T extends DaoEntity> void batchDelete(List<T> entities);

    <T extends DaoEntity> void batchUpdate(List<T> entities);

    <T extends DaoEntity> void batchUpdate(List<T> entities, String... bindNames);

    //清除对应表 注意事务不能回滚影响
    <T extends DaoEntity> void truncate(Class<T>... tClass);


}
