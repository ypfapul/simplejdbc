package org.ypf.generic.orm.entityoper;


import org.ypf.generic.orm.entityoper.condition.Conditions;
import org.ypf.generic.orm.entityoper.condition.DefaultValueExpress;
import org.ypf.generic.orm.entityoper.condition.Express;
import org.ypf.generic.orm.entityoper.condition.Variable;
import org.ypf.generic.orm.entityoper.critera.Order;

/**
 * @date: 2022/5/30 18:17
 */
public interface ColumFieldBinding extends Variable, Naming {

    // 关联的EntityMapper
    EntityMapper entityMapper();

    /**
     * 是否是主键
     *
     * @return
     */
    boolean isPrimaryId();

    /**
     * 主键策略
     *
     * @return
     */
    PrimaryStrategy strategy();

    /**
     * 本地主键策略
     *
     * @return
     */
    IdGenerated idGenerated();

    /**
     * 暂时失效
     *
     * @return
     */
    boolean isSuspend();

    /**
     * 暂时失效
     *
     * @return
     */
    void suspend();

    /**
     * 重新生效
     */
    void reload();

    Column column();

    JavaField field();

    /**
     * 构建一个条件
     *
     * @param compareSymbol
     * @param value
     * @return
     */
    Conditions newConditions(CompareSymbol compareSymbol, Object value);

    Conditions newConditions(CompareSymbol compareSymbol, Express express);

    default Order order(int index) {
        return Order.ascOrder(this, index);
    }

    default Order order() {
        return Order.ascOrder(this);
    }

    default Order descOrder(int index) {
        return Order.descOrder(this, index);
    }

    default Order descOrder() {
        return Order.descOrder(this);
    }

    default Conditions mt(Object value) {
        return newConditions(CompareSymbol.MT, value);
    }

    default Conditions me(Object value) {
        return newConditions(CompareSymbol.ME, value);
    }

    default Conditions eq(Object value) {
        return newConditions(CompareSymbol.EQ, value);
    }

    default Conditions noeq(Object value) {
        return newConditions(CompareSymbol.NOEQ, value);
    }

    default Conditions lt(Object value) {
        return newConditions(CompareSymbol.LT, value);
    }

    default Conditions le(Object value) {
        return newConditions(CompareSymbol.LE, value);
    }

    default Conditions like(Object value) {
        return newConditions(CompareSymbol.LIKE, value);
    }

    default Conditions nolike(Object value) {
        return newConditions(CompareSymbol.NOLIKE, value);
    }

    default Conditions contains(Object value) {
        return newConditions(CompareSymbol.CONTAINS, value);
    }

    default Conditions nocontains(Object value) {
        return newConditions(CompareSymbol.NOCONTAINS, value);
    }

    default Conditions in(Object value) {
        return newConditions(CompareSymbol.IN, value);
    }

    default Conditions noin(Object value) {
        return newConditions(CompareSymbol.NOIN, value);
    }

    default Conditions isNull() {
        return newConditions(CompareSymbol.IS, new DefaultValueExpress(null));
    }

    default Conditions isnotNull() {
        return newConditions(CompareSymbol.NOIS, new DefaultValueExpress(null));
    }

    // value 为空则忽略条件
    default Conditions mtIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.MT, value);
    }

    default Conditions meIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.ME, value);
    }

    default Conditions eqIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.EQ, value);
    }

    default Conditions noeqIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.NOEQ, value);
    }

    default Conditions ltIgnoreNull(Object value) {

        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.LT, value);
    }

    default Conditions leIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.LE, value);
    }

    default Conditions likeIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.LIKE, value);
    }

    default Conditions nolikeIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.NOLIKE, value);
    }

    default Conditions containsIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.CONTAINS, value);
    }

    default Conditions nocontainsIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.NOCONTAINS, value);
    }

    default Conditions inIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.IN, value);
    }

    default Conditions noinIgnoreNull(Object value) {
        if (value == null)
            return Conditions.FULL;
        return newConditions(CompareSymbol.NOIN, value);
    }


}
