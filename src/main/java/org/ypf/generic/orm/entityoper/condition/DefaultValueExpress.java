package org.ypf.generic.orm.entityoper.condition;

import org.ypf.generic.orm.entityoper.SqlKeywords;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @date: 2022/7/1 15:40
 */
public class DefaultValueExpress implements ValueExpress {
    protected Object value;
    // 环境
    protected int context;

    public DefaultValueExpress(Object value, int context) {
        this.value = value;
        this.context = context;
    }

    public DefaultValueExpress(Object value) {
        this.value = value;
    }

    public int getContext() {
        return context;
    }

    public void setContext(int context) {
        this.context = context;
    }

    public String express(Object v) {
        if (v == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        if (v instanceof String) {
            if (context == 110) {//标识like环境
                sb.append(SqlKeywords.QUOTATION.value);
                sb.append(SqlKeywords.PT.value);
                sb.append(v);
                sb.append(SqlKeywords.QUOTATION.value);
            } else if (context == 111) {
                sb.append(SqlKeywords.QUOTATION.value);
                sb.append(v);
                sb.append(SqlKeywords.PT.value);
                sb.append(SqlKeywords.QUOTATION.value);
            } else if (context == 112) {
                sb.append(SqlKeywords.QUOTATION.value);
                sb.append(SqlKeywords.PT.value);
                sb.append(v);
                sb.append(SqlKeywords.PT.value);
                sb.append(SqlKeywords.QUOTATION.value);
            } else {
                sb.append(SqlKeywords.QUOTATION.value);
                sb.append(v);
                sb.append(SqlKeywords.QUOTATION.value);
            }

        } else if (v instanceof Date) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            Date date = (Date) v;
            sb.append(SqlKeywords.QUOTATION.value);
            sb.append(simpleDateFormat.format(date));
            sb.append(SqlKeywords.QUOTATION.value);
        } else if (v instanceof Collection) {
            Collection collection = (Collection) v;
            sb.append(SqlKeywords.LBRACKET.value);
            sb.append(collection.stream().map(e -> express(e)).collect(Collectors.joining(",")));
            sb.append(SqlKeywords.RBRACKET.value);
        } else if (v.getClass().isArray()) {
            Object[] objs = (Object[]) v;
            sb.append(SqlKeywords.LBRACKET.value);
            Arrays.asList(objs).stream().map(e -> express(e)).collect(Collectors.joining(","));
            sb.append(SqlKeywords.RBRACKET.value);

        } else {
            sb.append(v.toString());
        }

        return sb.toString();
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return value.toString();
    }


    @Override
    public String express() {
        return express(value);
    }

    @Override
    public String express(ExpressContext expressContext) {
        return express();
    }
}
