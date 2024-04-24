package org.ypf.generic.orm.entityoper.condition;


/**
 * 变量
 *
 * @date: 2022/7/1 15:22
 */
public interface Variable extends SingleExpress {
    default String express(ExpressContext expressContext) {
        return expressContext.newExpress(this, express());
    }
}
