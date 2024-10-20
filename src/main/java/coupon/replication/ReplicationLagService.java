package coupon.replication;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplicationLagService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Object getByWriter(ProceedingJoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }
}
