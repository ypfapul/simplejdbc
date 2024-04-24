package org.ypf.generic.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ypf.generic.orm.transm.*;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


/**
 * 事务管理器切面
 */
@Aspect
public class SpTransactionManagerAop {

    /**
     * @Description: 定义切入点 为注解类
     */
    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void pointcut() {
    }


    /**
     * 事务处理逻辑
     *
     * @param ps
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object attention(ProceedingJoinPoint ps) throws Throwable {
        Object re = null;
        Signature signature = ps.getSignature();
        MethodSignature ms = (MethodSignature) signature;
        Method method = ms.getMethod();
        Object[] param = ps.getArgs();
        Transactional dataFaceAnnotation = method.getAnnotation(Transactional.class);
        if (dataFaceAnnotation == null) {
            Class<?> c = method.getDeclaringClass();
            dataFaceAnnotation = c.getAnnotation(Transactional.class);

        }
        DefaultTransactionEnv defaultTransactionEnv = null;

        if (dataFaceAnnotation.propagation() == Propagation.REQUIRED) {
            defaultTransactionEnv = new DefaultTransactionEnv(0);
        } else if (dataFaceAnnotation.propagation() == Propagation.REQUIRES_NEW) {
            defaultTransactionEnv = new DefaultTransactionEnv(1);

        } else if (dataFaceAnnotation.propagation() == Propagation.NOT_SUPPORTED) {
            defaultTransactionEnv = new DefaultTransactionEnv(2);

        } else if (dataFaceAnnotation.propagation() == Propagation.SUPPORTS) {
            defaultTransactionEnv = new DefaultTransactionEnv(3);
        }
        // 隔离级别
        Isolation isolation = dataFaceAnnotation.isolation();
        switch (isolation) {
            case DEFAULT:
                defaultTransactionEnv.setIsolation(Connection.TRANSACTION_NONE);
                break;
            case READ_UNCOMMITTED:
                defaultTransactionEnv.setIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
                break;
            case READ_COMMITTED:
                defaultTransactionEnv.setIsolation(Connection.TRANSACTION_READ_COMMITTED);
                break;
            case REPEATABLE_READ:
                defaultTransactionEnv.setIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                break;
            case SERIALIZABLE:
                defaultTransactionEnv.setIsolation(Connection.TRANSACTION_SERIALIZABLE);
                break;
            default:
                defaultTransactionEnv.setIsolation(Connection.TRANSACTION_NONE);
        }

        defaultTransactionEnv.setNoRollbackFor(dataFaceAnnotation.noRollbackFor());
        defaultTransactionEnv.setRollbackFor(dataFaceAnnotation.rollbackFor());

        List<TransactionStatus> transactionList = new ArrayList<>();
        List<TransactionManager> transactionManagerList = JdbcSessionTransactionManager.TRANSACTION_MANAGERS;
        for (TransactionManager transactionManager : transactionManagerList) {
            transactionList.add(transactionManager.getTransactionStatus(defaultTransactionEnv));
        }
        Class<? extends Throwable>[] noRollbackFor = dataFaceAnnotation.noRollbackFor();
        Class<? extends Throwable>[] rollbackFor = dataFaceAnnotation.rollbackFor();
        try {
            re = ps.proceed(param);
            for (TransactionStatus transactionStatus : transactionList) {
                transactionStatus.commit();
            }

        } catch (Throwable e) {
            e.printStackTrace();
            if (TransactionRunnableRunner.isRollback(e, noRollbackFor, rollbackFor)) {
                for (TransactionStatus transactionStatus : transactionList) {
                    transactionStatus.rollBack();
                }
            }


            throw e;
        } finally {
            for (TransactionStatus transactionStatus : transactionList) {
                transactionStatus.close();

            }
        }

        return re;

    }


}
