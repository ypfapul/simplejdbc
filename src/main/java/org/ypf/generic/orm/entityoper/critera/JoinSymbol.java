package org.ypf.generic.orm.entityoper.critera;

import org.ypf.generic.orm.entityoper.Naming;

/**
 * 连接符
 *
 * @date: 2022/6/30 9:08
 */
public enum JoinSymbol implements Naming {
    RIGHT("right join"), LEFT("left join"), INNER(" inner join");

    public final String name;

    JoinSymbol(String name) {
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }
}
