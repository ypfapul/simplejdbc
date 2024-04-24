package org.ypf.generic.orm.entityoper.readmapperconf;

import org.ypf.generic.orm.entityoper.IdGenerated;

/**
 * @date: 2022/6/2 17:20
 */
public class ColumnBean {
    /**
     * 字段名称
     */
    String name;
    /**
     * 是否主键
     * 1是
     * 0非
     */
    int primary;
    /**
     * 0 标识为程序生成主键
     * 1 数据库生成主键
     */
    int primaryStrategy;
    /**
     * 主键本地生成策略
     */
    IdGenerated idGenerated;
    /**
     * 建议用实体的字段名称
     */
    String bindKey;
    //数据类型
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBindKey() {
        return bindKey;
    }

    public void setBindKey(String bindKey) {
        this.bindKey = bindKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getPrimaryStrategy() {
        return primaryStrategy;
    }

    public void setPrimaryStrategy(int primaryStrategy) {
        this.primaryStrategy = primaryStrategy;
    }

    public IdGenerated getIdGenerated() {
        return idGenerated;
    }

    public void setIdGenerated(IdGenerated idGenerated) {
        this.idGenerated = idGenerated;
    }
}
