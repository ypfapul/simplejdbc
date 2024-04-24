package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;


/**
 * @date: 2022/6/21 11:41
 */
public interface GenEntitySource {

    /**
     * * 生成实体源码
     * * 包括实体和binds工具
     *
     * @param tableBean
     * @param supportJpa       支持jpa 增加注解
     * @param packageNameSpace 包名
     */
    String genEntity(TableBean tableBean, String packageNameSpace, boolean supportJpa);

    /**
     * 生成实体绑定工具源码
     *
     * @param tableBean
     * @param packageNameSpace
     * @return
     */
    String genEntityBinding(TableBean tableBean, String packageNameSpace, String entityPackageNameSpace);


}
