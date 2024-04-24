package org.ypf.generic.orm.entityoper;

/**
 * 赋值
 *
 * @date: 2022/6/28 16:45
 */
public class Assignment {
    String columFieldBinding;
    Object value;

    public Assignment(String columFieldBinding, Object value) {
        this.columFieldBinding = columFieldBinding;
        this.value = value;
    }

    public String getColumFieldBinding() {
        return columFieldBinding;
    }

    public void setColumFieldBinding(String columFieldBinding) {
        this.columFieldBinding = columFieldBinding;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
