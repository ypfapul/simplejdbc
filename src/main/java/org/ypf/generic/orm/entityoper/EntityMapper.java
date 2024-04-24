package org.ypf.generic.orm.entityoper;

import java.util.List;

/**
 * @date: 2022/5/30 18:42
 */
public interface EntityMapper extends Mirror<EntityMapper>, Naming {


    Table table();

    JavaClass javaClass();

    /**
     * 所有的绑定
     *
     * @return
     */
    List<ColumFieldBinding> cFBindings();

    /**
     * 排除主键的
     *
     * @return
     */
    List<ColumFieldBinding> exclusivePrimay();

    /**
     * 主键
     *
     * @return
     */
    ColumFieldBinding primay();

    /**
     * 根据命名获取绑定对象
     *
     * @return
     */
    ColumFieldBinding getColumFieldBinding(String bindKey);

    boolean hasColumFieldBinding(String bindKey);

    /**
     * 挂起所有除了主键
     */
    default void suspendAll() {
        exclusivePrimay().forEach(e -> e.suspend());
    }

    /**
     * 启用所有
     */
    default void reloadAll() {
        exclusivePrimay().forEach(e -> e.reload());
    }

}
