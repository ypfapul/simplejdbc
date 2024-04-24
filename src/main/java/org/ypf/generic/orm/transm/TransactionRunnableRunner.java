package org.ypf.generic.orm.transm;

/**
 * 事务环境运行
 *
 * @date: 2022/6/17 17:33
 */
public class TransactionRunnableRunner {
    protected TransactionManager transactionManager;

    public TransactionRunnableRunner(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public static boolean isRollback(Throwable e, Class<? extends Throwable>[] noRollbackFor, Class<? extends Throwable>[] rollbackFor) {
        boolean f0 = false;
        if (noRollbackFor != null) {
            for (Class c : noRollbackFor) {
                if (c.isAssignableFrom(e.getClass())) {
                    f0 = true;
                    break;
                }

            }
        }
        boolean f1 = false;
        if (rollbackFor != null) {
            for (Class c : rollbackFor) {
                if (c.isAssignableFrom(e.getClass())) {
                    f1 = true;
                    break;
                }

            }
        }

        return f1 ? f1 : f0 ? f1 : !f1;
    }


    public static void main(String[] args) {
//        Class[] noRollbackFor = {RuntimeException.class};
//        Class[] rollbackFor = {Exception.class};
//
//        System.out.println(isRollback(new RuntimeException(),noRollbackFor,rollbackFor));
//        BaseExcuter baseExcuter  = null;
//        Consumer<JDBCExcuterContext> contextConsumer =e->{
//            e.
//        };
//        baseExcuter.addWhenException(e->e.);
    }

    /**
     * 返回结果运行
     *
     * @param tTransactionRunnable
     * @param <T>
     * @return
     * @throws Throwable
     */
    public <T> T run(TransactionRunnableResult<T> tTransactionRunnable, TransactionEnvrioment transactionEnvrioment) throws Throwable {
        T r = null;
        TransactionStatus transactionStatus = transactionManager.getTransactionStatus(transactionEnvrioment);
        Class<? extends Throwable>[] noRollbackFor = transactionStatus.getTransactionEnvrioment().noRollbackFor();
        Class<? extends Throwable>[] rollbackFor = transactionStatus.getTransactionEnvrioment().rollbackFor();
        try {
            r = tTransactionRunnable.apply(transactionStatus);
            transactionStatus.commit();
        } catch (Throwable e) {
            if (isRollback(e, noRollbackFor, rollbackFor)) {
                transactionStatus.rollBack();
            }

            throw e;
        } finally {
            transactionStatus.close();

        }
        return r;
    }


    /**
     * 无结果运行
     *
     * @param tTransactionRunnable
     * @throws Throwable
     */
    public void run(TransactionRunnable tTransactionRunnable, TransactionEnvrioment transactionEnvrioment) throws Throwable {

        TransactionStatus transactionStatus = transactionManager.getTransactionStatus(transactionEnvrioment);
        Class<? extends Throwable>[] noRollbackFor = transactionStatus.getTransactionEnvrioment().noRollbackFor();
        Class<? extends Throwable>[] rollbackFor = transactionStatus.getTransactionEnvrioment().rollbackFor();
        try {
            tTransactionRunnable.apply(transactionStatus);
            transactionStatus.commit();
        } catch (Throwable e) {
            if (isRollback(e, noRollbackFor, rollbackFor)) {
                transactionStatus.rollBack();
            }
            throw e;
        } finally {
            transactionStatus.close();

        }

    }


}
