package org.ypf.generic.orm.entityoper.readmapperconf;

/**
 * @date: 2022/6/2 17:19
 */
public class BindBean {
    /**
     * 绑定名称
     * 建议为实体类名
     */
    String name;
    /**
     * 建议实体类名
     */
    String javaclass;
    /**
     * 表明
     */
    String table;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaclass() {
        return javaclass;
    }

    public void setJavaclass(String javaclass) {
        this.javaclass = javaclass;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }
}
