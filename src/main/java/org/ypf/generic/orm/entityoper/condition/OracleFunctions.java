package org.ypf.generic.orm.entityoper.condition;

/**
 * oracle 常用函数
 *
 * @date: 2022/7/6 9:24
 */
public class OracleFunctions {
    public static FunctionExpress concat(Express... params) {
        return new FunctionCommonExpress("CONCAT", params);
    }


}
