package org.ypf.generic.orm.entityoper.condition;

import org.ypf.generic.orm.entityoper.OperateSymbol;


/**
 * 表达式
 *
 * @date: 2022/7/1 15:14
 */
public interface Express {


    /**
     * 表达式
     *
     * @return
     */
    String express();

    /**
     * 表达式
     */
    String express(ExpressContext expressContext);

    /**
     * 操作
     *
     * @param express
     * @param operateSymbol
     * @return
     */
    default Express operate(Express express, OperateSymbol operateSymbol) {
        return new CompositeExpress(this, express, operateSymbol);
    }
}
