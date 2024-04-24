package org.ypf.generic.orm.entityoper.critera;

import org.ypf.generic.orm.entityoper.SqlKeywords;
import org.ypf.generic.orm.entityoper.condition.Express;
import org.ypf.generic.orm.entityoper.condition.ExpressContext;
import org.ypf.generic.orm.entityoper.condition.Variable;

/**
 * 追加命名空间
 *
 * @date: 2022/7/4 10:51
 */
public abstract class NameSpaceExpressContext implements ExpressContext {
    public abstract String getTableAliasName(Variable variable);

    @Override
    public String newExpress(Express express, String originExpress) {
        if (express instanceof Variable) {
            return new StringBuilder(getTableAliasName((Variable) express))
                    .append(SqlKeywords.DOT.value)
                    .append(originExpress)
                    .toString();
        }
        return originExpress;

    }
}
