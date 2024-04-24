package org.ypf.generic.orm.entityoper;


import java.io.Serializable;

/**
 * @date: 2022/6/24 17:00
 */
public enum SqlKeywords implements BaseProperty<String> {
    TABLE("table"),
    TRUNCATE("truncate"),
    SELECT("select"),
    UPDATE("update"),
    INSERT("insert"),
    DELETE("delete"),
    FROM("from"),
    GROUPBY("group by"),
    ORDERBY("order by"),
    HAVING("having"),
    WHERE("where"),
    AS("as"),
    COMMA(","),
    AND("and"),
    OR("or"),
    REVERSE("!"),
    IS("is"),
    NULL("null"),
    EXIST("exist"),
    EQ("="),
    LT("<"),
    MT(">"),
    IN("in"),
    NOT("not"),
    LIKE("like"),
    QUOTATION("'"),
    JION("join"),
    LEFT("left"),
    RIGTH("right"),
    ROWNUM("rownum"),
    COUNT("count"),
    SUM("sum"),
    AVERAGE("average"),
    MIN("min"),
    MAX("max"),
    LBRACKET("("),
    RBRACKET(")"),
    DOT("."),
    MULT("*"),
    BLANK(" "),
    ASKMARK("?"),
    ASC("asc"),
    DESC("desc"),
    ON("on"), PT("%");
    public final String value;

    SqlKeywords(String value) {
        this.value = value;
    }


    /**
     * 左边添加空格
     *
     * @return
     */
    public String leftBlank() {
        StringBuilder strb = new StringBuilder(SqlKeywords.BLANK.value);
        strb.append(value);
        return strb.toString();
    }

    /**
     * 右侧添加空格
     *
     * @return
     */
    public String rightBlank() {
        StringBuilder strb = new StringBuilder(value);
        strb.append(SqlKeywords.BLANK.value);
        return strb.toString();

    }

    /**
     * 左右添加空格
     *
     * @return
     */
    public String blank() {
        StringBuilder strb = new StringBuilder(SqlKeywords.BLANK.value);
        strb.append(value);
        strb.append(SqlKeywords.BLANK.value);
        return strb.toString();
    }

    @Override
    public Serializable getId() {
        return null;
    }

    @Override
    public void setId(Serializable id) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String setName(String name) {
        return null;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String ovj) {

    }


}
