package org.ypf.generic.orm.entityoper;


/**
 * id生成器
 *
 * @date: 2022/7/6 11:02
 */
public class IdGenerated {
    /**
     * TABLE,3     数据库自动生成 jdbc字段忽略  DB
     * SEQUENCE,2  数据库提供序列化支持id       LOCAL
     * IDENTITY,1 本地维护自增长               LOCAL
     * AUTO;0  程序实体指定                    LOCAL
     */
    private int strategy;
    /**
     * SEQUENCE,对应序列化名称
     */
    private String generator;

    public IdGenerated() {
    }

    public IdGenerated(int strategy) {
        this.strategy = strategy;
    }

    public IdGenerated(int strategy, String generator) {
        this.strategy = strategy;
        this.generator = generator;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }
}
