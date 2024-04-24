package org.ypf.generic.orm.entityoper.mappergenerater;

import org.ypf.generic.orm.entityoper.readmapperconf.BindBean;
import org.ypf.generic.orm.entityoper.readmapperconf.EntityBean;
import org.ypf.generic.orm.entityoper.readmapperconf.TableBean;

import java.util.List;

/**
 * @date: 2022/6/21 15:18
 */
public class Mappingbinds {
    List<BindBean> mapping;
    List<EntityBean> entity;
    List<TableBean> tables;

    public List<BindBean> getMapping() {
        return mapping;
    }

    public void setMapping(List<BindBean> mapping) {
        this.mapping = mapping;
    }

    public List<EntityBean> getEntity() {
        return entity;
    }

    public void setEntity(List<EntityBean> entity) {
        this.entity = entity;
    }

    public List<TableBean> getTables() {
        return tables;
    }

    public void setTables(List<TableBean> tables) {
        this.tables = tables;
    }
}
