package coupon.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.NoSuchElementException;

@Component
@Aspect
public class ReplicationDelayAspect {

    private static final TransactionDefinition transactionDefinition = notReadOnlyNewTransaction();

    private final PlatformTransactionManager transactionManager;

    public ReplicationDelayAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    private void transactionalPointCut() {}

    @Around("transactionalPointCut()")
    public Object preventReplicationDelay(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return joinPoint.proceed();
        }
        try {
            return joinPoint.proceed();
        } catch (NoSuchElementException e) {
            transactionManager.getTransaction(transactionDefinition);
            return joinPoint.proceed();
        }
    }

    private static TransactionDefinition notReadOnlyNewTransaction() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        return transactionDefinition;
    }
}
