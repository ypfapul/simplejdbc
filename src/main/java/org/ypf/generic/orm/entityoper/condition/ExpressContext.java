package org.ypf.generic.orm.entityoper.condition;

/**
 * @date: 2022/6/30 14:40
 */
public interface ExpressContext {
    /**
     * @param express       当前表达式
     * @param originExpress 当前表达式表达值
     * @return
     */
    String newExpress(Express express, String originExpress);

}
