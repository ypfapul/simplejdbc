package org.ypf.generic.orm.entityoper;

import java.util.List;

/**
 * @date: 2022/6/1 10:20
 */
public interface JavaClass extends Naming, Mirror<JavaClass> {
    //是否支持缓存
    boolean cacheSupport();

    DaoEntity newInstance();

    public List<JavaField> fields();
}
