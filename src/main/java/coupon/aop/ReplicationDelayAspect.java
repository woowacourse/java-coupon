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

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional) || " +
            "@within(org.springframework.transaction.annotation.Transactional)")
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
            //read-only이고 IllegalArgumentException이 발생하는 경우
            //reader가 아닌 writer DB에서 데이터를 읽어온다.
            transactionManager.getTransaction(transactionDefinition);
            return joinPoint.proceed();
        }
    }
}

