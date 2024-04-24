package org.ypf.generic.orm.entityoper.defineimpl;

import org.ypf.generic.orm.entityoper.DaoEntity;
import org.ypf.generic.orm.entityoper.JavaField;
import org.ypf.generic.orm.entityoper.JavaClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2022/6/1 15:42
 */
public class JavaClassImpl implements JavaClass {
    boolean cacheSupport;
    String className;
    List<JavaField> fields;

    @Override
    public boolean cacheSupport() {
        return false;
    }

    @Override
    public DaoEntity newInstance() {
        DaoEntity r = null;
        try {
            r = (DaoEntity) Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();


        }
        return r;
    }

    @Override
    public List<JavaField> fields() {
        return fields;
    }

    public String getClassName() {
        return className;
    }

    public JavaClassImpl setClassName(String className) {
        this.className = className;
        return this;
    }

    public List<JavaField> getFields() {
        return fields;
    }

    public JavaClassImpl setFields(List<JavaField> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public String getName() {
        return className;
    }

    @Override
    public JavaClass mirror() {
        JavaClassImpl javaClassCopy = new JavaClassImpl();
        javaClassCopy.className = className;
        javaClassCopy.fields = new ArrayList<>();
        for (JavaField javaField : this.fields) {
            JavaField javaFieldCopy = new FieldImpl(javaClassCopy, javaField.getName(), javaField.jdbcType());
            javaClassCopy.fields.add(javaFieldCopy);
        }
        return javaClassCopy;
    }
}
