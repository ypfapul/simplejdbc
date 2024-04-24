package org.ypf.generic.orm.entityoper;

import java.io.Serializable;

/**
 * @date: 2022/6/24 16:57
 */
public interface BaseProperty<T> {
    Serializable getId();

    void setId(Serializable id);

    String getName();

    String setName(String name);

    T getValue();

    void setValue(T ovj);

}
