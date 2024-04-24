package org.ypf.generic.dependency;

/**
 * 处理器
 *
 * @date: 2022/4/3 15:27
 */
public abstract class AbstractProcessor {
    protected abstract void run() throws Exception;

    protected void before() {
    }

    protected void after() {
    }

    protected void afterNormal() {
    }


    protected void exception(Exception e) {
    }


    public void start() {
        try {
            before();
            run();
            afterNormal();
        } catch (Exception e) {
            exception(e);

        } finally {
            after();
        }


    }
}
