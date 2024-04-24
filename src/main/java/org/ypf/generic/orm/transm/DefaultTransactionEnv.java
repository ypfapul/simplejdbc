package org.ypf.generic.orm.transm;

/**
 * @date: 2022/6/15 9:06
 */
public class DefaultTransactionEnv implements TransactionEnvrioment {
    int propagation;
    boolean readOnly;
    int isolation;
    int timeout;
    Class<? extends Throwable>[] rollbackFor;
    Class<? extends Throwable>[] noRollbackFor;

    public DefaultTransactionEnv(int propagation) {
        this.propagation = propagation;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setIsolation(int isolation) {
        this.isolation = isolation;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public void setRollbackFor(Class<? extends Throwable>[] rollbackFor) {
        this.rollbackFor = rollbackFor;
    }

    public void setNoRollbackFor(Class<? extends Throwable>[] noRollbackFor) {
        this.noRollbackFor = noRollbackFor;
    }

    public void setPropagation(int propagation) {
        this.propagation = propagation;
    }

    @Override
    public int propagation() {
        return propagation;
    }

    @Override
    public boolean readOnly() {
        return readOnly;
    }

    @Override
    public int isolation() {
        return isolation;
    }

    @Override
    public int timeout() {
        return 0;
    }

    @Override
    public Class<? extends Throwable>[] rollbackFor() {
        return rollbackFor;
    }

    @Override
    public Class<? extends Throwable>[] noRollbackFor() {
        return noRollbackFor;
    }
}
