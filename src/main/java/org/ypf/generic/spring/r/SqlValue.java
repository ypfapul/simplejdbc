package org.ypf.generic.spring.r;

import com.alibaba.fastjson.JSON;


class SqlValue {
    String className;
    Object value;


//    public static void main(String[] args) {
//        SqlValue sqlValue = new SqlValue(Date.class.getName(),System.currentTimeMillis());
//        System.out.println(sqlValue.getValue());
//        sqlValue.reload();
//        System.out.println(sqlValue.getValue());
//
//    }

    public SqlValue() {
    }

    public SqlValue(String className, Object value) {
        this.className = className;
        this.value = value;
    }

    public void reload() {
        if (value == null)
            return;
        try {
            Class<?> cs = Class.forName(className);
            if (cs == String.class) {
                value = value.toString();
            } else {
                value = JSON.parseObject(JSON.toJSONString(value), cs);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
