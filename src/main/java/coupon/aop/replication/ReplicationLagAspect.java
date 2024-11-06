package coupon.aop.replication;

import coupon.aop.AopForTransaction;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
public class ReplicationLagAspect {

    private final AopForTransaction aopForTransaction;

    @Pointcut("@annotation(coupon.aop.replication.ReplicationLag)")
    private void replicationLag() {}

    @Around("replicationLag()")
    public Object doReplicationLag(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            return aopForTransaction.getByWriter(joinPoint);
        }
    }
}
