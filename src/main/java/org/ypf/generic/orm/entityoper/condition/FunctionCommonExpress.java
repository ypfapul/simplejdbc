package org.ypf.generic.orm.entityoper.condition;

import org.ypf.generic.orm.entityoper.SqlKeywords;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @date: 2022/7/6 8:54
 */
public class FunctionCommonExpress implements FunctionExpress {

    //函数名
    private String functionName;
    private Express[] params;

    public FunctionCommonExpress(String functionName, Express[] params) {
        this.functionName = functionName;
        this.params = params;
    }

    public static FunctionCommonExpress newFunctionCommonExpress(String functionName, Express... params) {
        FunctionCommonExpress functionCommonExpress = new FunctionCommonExpress(functionName, params);
        return functionCommonExpress;
    }

    @Override
    public String getName() {
        return functionName;
    }

    @Override
    public String express() {
        StringBuilder stringBuilder = new StringBuilder(functionName);
        stringBuilder.append(SqlKeywords.LBRACKET.value);
        if (params != null && params.length > 0) {
            stringBuilder.append(Arrays.asList(params).stream().map(e -> e.express()).collect(Collectors.joining(",")));
        }
        stringBuilder.append(SqlKeywords.RBRACKET.value);
        return stringBuilder.toString();
    }

    @Override
    public String express(ExpressContext expressContext) {
        StringBuilder stringBuilder = new StringBuilder(functionName);
        stringBuilder.append(SqlKeywords.LBRACKET.value);
        if (params != null && params.length > 0) {
            stringBuilder.append(Arrays.asList(params).stream().map(e -> e.express(expressContext)).collect(Collectors.joining(",")));
        }
        stringBuilder.append(SqlKeywords.RBRACKET.value);
        return stringBuilder.toString();
    }


}
