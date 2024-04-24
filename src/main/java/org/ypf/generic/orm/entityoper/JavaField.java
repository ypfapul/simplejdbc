package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.JdbcType;

/**
 * @date: 2022/5/30 18:30
 */
public interface JavaField extends Naming {
    JavaClass javaClass();

    String bindKey();

    /**
     * 获取值
     *
     * @param entity
     * @return
     */
    Object getValue(Object entity);

    void setValue(Object entity, Object value);

    JdbcType jdbcType();

}
