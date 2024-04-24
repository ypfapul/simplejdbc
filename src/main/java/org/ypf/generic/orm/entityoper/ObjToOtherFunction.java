package org.ypf.generic.orm.entityoper;

import java.lang.reflect.Field;


/**
 * 一个对象装换为另一个对象
 *
 * @date: 2022/6/23 8:52
 */
public class ObjToOtherFunction<R> {
    public R apply(Object t, Class<? extends R> rc) {
        R r = null;
        try {
            r = rc.newInstance();
            Class<?> dt = t.getClass();
            Field[] fields0 = dt.getDeclaredFields();
            Field[] fields1 = rc.getDeclaredFields();
            for (Field f0 : fields0) {
                for (Field f1 : fields1) {
                    if (f1.getName().toLowerCase().equals(f0.getName().toLowerCase())) {
                        if (f1.getType().isAssignableFrom(f0.getType())) {
                            f0.setAccessible(true);
                            f1.setAccessible(true);
                            f1.set(r, f0.get(t));
                        }


                    }

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return r;


    }

//    static  boolean isBasicNumberDataType(Type type){
//        return type.getTypeName().equals("byte") || type.getTypeName().equals("short")
//                || type.getTypeName().equals("int")||type.getTypeName().equals("long")
//                || type.getTypeName().equals("float")||type.getTypeName().equals("double");
//
//    }


}
