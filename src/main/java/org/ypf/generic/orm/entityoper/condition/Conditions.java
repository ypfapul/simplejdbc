package org.ypf.generic.orm.entityoper.condition;

/**
 * @date: 2022/5/31 9:16
 */
public interface Conditions extends Express {
    /**
     * 全集
     */
    public static final Conditions FULL = new FinalConditions("1 > 0");
    /**
     * 空集
     */
    public static final Conditions EMPTY = new FinalConditions("1 < 0");

    Conditions and(Conditions conditions);

    Conditions or(Conditions conditions);

    Conditions reverse();


    final class FinalConditions implements Conditions {

        String express;

        private FinalConditions(String express) {
        }

        @Override
        public Conditions and(Conditions conditions) {
            return conditions;
        }

        @Override
        public Conditions or(Conditions conditions) {
            return conditions;
        }

        @Override
        public Conditions reverse() {
            return FULL == this ? EMPTY : FULL;

        }

        @Override
        public String express() {
            return express;
        }

        @Override
        public String express(ExpressContext expressContext) {
            return express();
        }
    }

}
