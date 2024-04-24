package org.ypf.generic.dependency;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;


/**
 * 测试接口
 */
public interface Test {
    // 初始化上下文
    default TestContext init() {
        return null;
    }


    default void test() {
        TestContext testContext = init();
        Class c = this.getClass();
        Method[] methods = c.getDeclaredMethods();
        Arrays.asList(methods).stream().filter(method -> method.getAnnotation(TestPoint.class) != null &&
                        !Modifier.isStatic(method.getModifiers())
                        && !method.getName().equals("test"))
                .sorted((m1, m2) -> m1.getAnnotation(TestPoint.class).sort() - m2.getAnnotation(TestPoint.class).sort())
                .forEach(method -> {
                    Object[] p = null;
                    if (testContext != null) {
                        Class[] types = method.getParameterTypes();
                        p = new Object[types.length];
                        for (int i = 0; i < types.length; i++) {
                            if (TestContext.class.isAssignableFrom(types[i])) {
                                p[i] = testContext;
                            }

                        }
                    }
                    try {
                        method.invoke(this, p);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                });


    }
}
