package org.ypf.generic.orm.entityoper;

/**
 * 比较符
 *
 * @date: 2022/6/1 10:51
 */
public enum CompareSymbol implements OperateSymbol {
    IS("is"),
    NOIS("is not"),
    MT(">"),
    ME(">="),
    EQ("="),
    NOEQ("!="),
    LT("<"),
    LE("<="),
    IN("in"),
    NOIN("not in"),
    CONTAINS("like"),
    NOCONTAINS("not like"),
    LIKE("like"),
    NOLIKE("not like");
    public final String name;

    CompareSymbol(String name) {
        this.name = name;
    }


    public CompareSymbol reverse() {
        switch (this) {
            case MT:
                return LE;
            case ME:
                return LT;
            case EQ:
                return NOEQ;
            case NOEQ:
                return EQ;
            case LT:
                return ME;
            case LE:
                return MT;
            case IN:
                return NOIN;
            case NOIN:
                return IN;
            case CONTAINS:
                return NOCONTAINS;
            case NOCONTAINS:
                return CONTAINS;
            case LIKE:
                return NOLIKE;
            case NOLIKE:
                return LIKE;
            case IS:
                return NOIS;
            case NOIS:
                return IS;

        }
        return null;

    }

    @Override
    public String getName() {
        return name;
    }
}
