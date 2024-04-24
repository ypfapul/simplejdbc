package org.ypf.generic.orm.entityoper.readmapperconf;

/**
 * @date: 2022/6/2 17:20
 */
public class FieldBean {
    /**
     * 实体字段名称
     */
    String name;
    /**
     * 数据类型
     */
    int type;
    /**
     * 绑定名称建议和name一样
     */
    String bindKey;

    public String getBindKey() {
        return bindKey;
    }

    public void setBindKey(String bindKey) {
        this.bindKey = bindKey;
    }

    public String getName() {
        return name;
    }

    public FieldBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getType() {
        return type;
    }

    public FieldBean setType(int type) {
        this.type = type;
        return this;
    }
}
