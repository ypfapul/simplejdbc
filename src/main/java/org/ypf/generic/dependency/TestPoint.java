package org.ypf.generic.dependency;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestPoint {
    // 序号
    int sort() default 0;
}
