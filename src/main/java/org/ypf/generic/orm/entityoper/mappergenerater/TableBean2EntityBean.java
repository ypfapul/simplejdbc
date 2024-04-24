package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.entityoper.readmapperconf.EntityBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;


/**
 * @date: 2022/6/21 14:51
 */
public interface TableBean2EntityBean {
    EntityBean map(TableBean tableBean, String entityPackageNameSpace);
}
