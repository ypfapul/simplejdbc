package org.ypf.generic.orm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * 数据类型定义
 *
 * @date: 2022/7/7 9:33
 */
public interface DataTypeDefinition {

    int code();

    void setValue(Object value, int index, PreparedStatement rs) throws Exception;

    Object getValue(int index, ResultSet rs) throws Exception;

    /**
     * 返回空标识匹配所有
     *
     * @return
     */
    List<Class> classesMapper();
}
