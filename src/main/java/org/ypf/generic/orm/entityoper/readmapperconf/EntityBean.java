package org.ypf.generic.orm.entityoper.readmapperconf;

import java.util.List;

/**
 * @date: 2022/6/2 17:19
 */
public class EntityBean {
    /**
     * 建议实体类名
     */
    String name;
    List<FieldBean> fields;

    public String getName() {
        return name;
    }

    public EntityBean setName(String name) {
        this.name = name;
        return this;
    }

    public List<FieldBean> getFields() {
        return fields;
    }

    public EntityBean setFields(List<FieldBean> fields) {
        this.fields = fields;
        return this;
    }
}
