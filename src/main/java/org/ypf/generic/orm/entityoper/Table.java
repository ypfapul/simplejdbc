package org.ypf.generic.orm.entityoper;

import java.util.List;

/**
 * @date: 2022/5/30 18:18
 */
public interface Table extends Naming, Mirror<Table> {

    public List<Column> column();
}
