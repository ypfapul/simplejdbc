package org.ypf.generic.orm.entityoper.condition;

import org.ypf.generic.orm.entityoper.OperateSymbol;
import org.ypf.generic.orm.entityoper.SqlKeywords;

/**
 * 复合的表达式
 *
 * @date: 2022/7/1 15:46
 */
public class CompositeExpress implements Express {


    // 表达式格式模式
    /**
     * 0 通用模式 即相同操作符省略括号
     * 1 严格模式 严格结构模式
     */

    public final static int EXPRESS_FORMAT_MODEL_STANDARD = 0;
    public final static int EXPRESS_FORMAT_MODEL_STRICT = 1;
    public static int EXPRESS_FORMAT_MODEL = EXPRESS_FORMAT_MODEL_STANDARD;
    protected Express firstExpress;
    protected Express secondExpress;
    protected OperateSymbol operateSymbol;
    /**
     * 是否看做原子表达
     */
    boolean atom;

    public CompositeExpress() {
    }

    public CompositeExpress(Express firstExpress, Express secondExpress, OperateSymbol operateSymbol) {
        this.firstExpress = firstExpress;
        this.secondExpress = secondExpress;
        this.operateSymbol = operateSymbol;
    }

    /**
     * 表达式在复合表达时是否可以看做整体不可再分
     *
     * @param ex
     * @return
     */
    public static boolean isAtom(Express ex) {
        if (ex instanceof SingleExpress) {
            return true;
        } else if (ex instanceof CompositeExpress) {
            if (EXPRESS_FORMAT_MODEL == EXPRESS_FORMAT_MODEL_STRICT) {
                return false;
            }
            CompositeExpress compositeExpress = (CompositeExpress) ex;
            if (compositeExpress.isAtom()) {// 不可再分
                return true;


            }

        }
        return false;

    }

    /**
     * 表达式所有的结构是否与指定的操作符一样
     *
     * @param ex
     * @param operateSymbol
     * @return
     */
    public static boolean checkExpressStructureSameOper(Express ex, OperateSymbol operateSymbol) {
        if (ex instanceof SingleExpress) {
            return true;
        } else if (ex instanceof CompositeExpress) {
            if (EXPRESS_FORMAT_MODEL == EXPRESS_FORMAT_MODEL_STRICT) {
                return false;
            }
            CompositeExpress compositeExpress = (CompositeExpress) ex;
            if (compositeExpress.isAtom() || compositeExpress.getOperateSymbol().equals(operateSymbol)) {
                return true;
            }


        }
        return false;

    }

    public boolean isAtom() {
        return atom;
    }

    public void setAtom(boolean atom) {
        this.atom = atom;
    }


    public Express getFirstExpress() {
        return firstExpress;
    }

    public void setFirstExpress(Express firstExpress) {
        this.firstExpress = firstExpress;
    }

    public Express getSecondExpress() {
        return secondExpress;
    }

    public void setSecondExpress(Express secondExpress) {
        this.secondExpress = secondExpress;
    }

    public OperateSymbol getOperateSymbol() {
        return operateSymbol;
    }

    public void setOperateSymbol(OperateSymbol operateSymbol) {
        this.operateSymbol = operateSymbol;
    }

    @Override
    public String express() {
        StringBuilder express = new StringBuilder();

        if (secondExpress == null) {
            boolean f0 = !(firstExpress instanceof SingleExpress);//是否不是单表达式
            express.append(operateSymbol.getName());
            express.append(SqlKeywords.LBRACKET.rightBlank());
            express.append(firstExpress.express());
            express.append(SqlKeywords.RBRACKET.leftBlank());
        } else {
            boolean f0 = !checkExpressStructureSameOper(firstExpress, operateSymbol);//是否不是单表达式
            //boolean f1 = !(secondExpress instanceof SingleExpress);
            if (f0) {
                express.append(SqlKeywords.LBRACKET.rightBlank());
            }

            express.append(firstExpress.express());
            if (f0) {
                express.append(SqlKeywords.RBRACKET.leftBlank());
            }
            express.append(SqlKeywords.BLANK.value);
            express.append(operateSymbol.getName());
            express.append(SqlKeywords.BLANK.value);
            boolean f1 = !checkExpressStructureSameOper(secondExpress, operateSymbol);//是否不是单表达式
            if (f1) {
                express.append(SqlKeywords.LBRACKET.rightBlank());
            }
            express.append(secondExpress.express());
            if (f1) {
                express.append(SqlKeywords.RBRACKET.leftBlank());
            }


        }

        return express.toString();
    }

    @Override
    public String express(ExpressContext expressContext) {
        StringBuilder express = new StringBuilder();

        if (secondExpress == null) {
            boolean f0 = !(firstExpress instanceof SingleExpress);//是否不是单表达式
            express.append(operateSymbol.getName());
            express.append(SqlKeywords.LBRACKET.rightBlank());
            express.append(firstExpress.express(expressContext));
            express.append(SqlKeywords.RBRACKET.leftBlank());
        } else {
            boolean f0 = !checkExpressStructureSameOper(firstExpress, operateSymbol);//是否不是单表达式
            if (f0) {
                express.append(SqlKeywords.LBRACKET.rightBlank());
            }
            express.append(firstExpress.express(expressContext));
            if (f0) {
                express.append(SqlKeywords.RBRACKET.leftBlank());
            }
            express.append(SqlKeywords.BLANK.value);
            express.append(operateSymbol.getName());
            express.append(SqlKeywords.BLANK.value);
            boolean f1 = !checkExpressStructureSameOper(secondExpress, operateSymbol);//是否不是单表达式
            if (f1) {
                express.append(SqlKeywords.LBRACKET.rightBlank());
            }
            express.append(secondExpress.express(expressContext));
            if (f1) {
                express.append(SqlKeywords.RBRACKET.leftBlank());
            }


        }

        return express.toString();
    }


}
