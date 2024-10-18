package coupon.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@RequiredArgsConstructor
@Component
public class ReplicationDelayAspect {

    private static final TransactionDefinition transactionDefinition = notReadOnlyTransaction();

    private final PlatformTransactionManager transactionManager;

    private static TransactionDefinition notReadOnlyTransaction() {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        return transactionDefinition;
    }

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    private void transactionalPointCut() {
    }

    @Around("transactionalPointCut()")
    public Object handleReplicationDelay(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if (!isReadOnly) {
            return joinPoint.proceed();
        }

        try {
            return joinPoint.proceed();
        } catch (IllegalArgumentException e) {
            transactionManager.getTransaction(transactionDefinition);
            return joinPoint.proceed();
        }
    }
}

