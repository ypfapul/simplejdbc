package org.ypf.generic.orm.entityoper.defineimpl;

import org.ypf.generic.orm.JdbcType;

import org.ypf.generic.orm.entityoper.JavaClass;
import org.ypf.generic.orm.entityoper.JavaField;

import java.lang.reflect.Field;

/**
 * @date: 2022/6/1 15:42
 */
public class FieldImpl implements JavaField {
    JavaClass javaClass;
    String fieldName;
    JdbcType jdbcType;

    public FieldImpl(JavaClass javaClass, String fieldName, JdbcType jdbcType) {
        this.javaClass = javaClass;
        this.fieldName = fieldName;
        this.jdbcType = jdbcType;
    }

    @Override
    public JavaClass javaClass() {
        return javaClass;
    }

    @Override
    public String bindKey() {
        return fieldName;
    }

    @Override
    public Object getValue(Object entity) {
        Object o = null;

        try {
            Field field = findField();
            field.setAccessible(true);
            o = field.get(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return o;
    }

    @Override
    public void setValue(Object entity, Object value) {
        Field field = findField();
        field.setAccessible(true);
        try {
            field.set(entity, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    public JdbcType jdbcType() {
        return jdbcType;
    }


    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public FieldImpl setJdbcType(JdbcType jdbcType) {
        this.jdbcType = jdbcType;
        return this;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }

    public FieldImpl setJavaClass(JavaClass javaClass) {
        this.javaClass = javaClass;
        return this;
    }

    public String getFieldName() {
        return fieldName;
    }

    public FieldImpl setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }

    @Override
    public String getName() {
        return fieldName;
    }


    Field findField() {

        try {
            Class c = Class.forName(javaClass.getName());
            return c.getDeclaredField(getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
