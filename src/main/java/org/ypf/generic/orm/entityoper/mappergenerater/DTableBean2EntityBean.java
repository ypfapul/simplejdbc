package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.entityoper.readmapperconf.ColumnBean;
import org.ypf.generic.orm.entityoper.readmapperconf.EntityBean;
import org.ypf.generic.orm.entityoper.readmapperconf.FieldBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;

import java.util.ArrayList;

/**
 * @date: 2022/6/21 16:02
 */
public class DTableBean2EntityBean implements TableBean2EntityBean {

    @Override
    public EntityBean map(TableBean tableBean, String entityPackageNameSpace) {
        EntityBean entityBean = new EntityBean();
        entityBean.setFields(new ArrayList<>());
        entityBean.setName(entityPackageNameSpace + "." + Utils.tableName2EntityName(tableBean.getName()));
        for (ColumnBean columnBean : tableBean.getColumns()) {
            FieldBean fieldBean = new FieldBean();
            fieldBean.setName(Utils.columnName2FiledName(columnBean.getName()));
            fieldBean.setType(columnBean.getType());
            fieldBean.setBindKey(fieldBean.getBindKey());
            entityBean.getFields().add(fieldBean);
        }
        return entityBean;
    }
}
