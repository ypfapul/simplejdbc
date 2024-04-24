package org.ypf.generic.orm.entityoper;

import org.ypf.generic.orm.entityoper.readmapperconf.BindBean;
import org.ypf.generic.orm.entityoper.readmapperconf.EntityBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;

import java.util.List;

/**
 * @date: 2022/6/16 9:08
 */
public interface BindMapperSupplier {
    List<BindBean> bindBean();

    List<EntityBean> entityBean();

    List<TableBean> tableBean();
}
