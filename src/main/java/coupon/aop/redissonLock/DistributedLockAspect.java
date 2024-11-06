package coupon.aop.redissonLock;

import coupon.aop.AopForTransaction;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
public class DistributedLockAspect {

    private static final String REDISSON_LOCK_PREFIX = "LOCK:";

    private final RedissonClient redissonClient;
    private final AopForTransaction aopForTransaction;

    @Pointcut("@annotation(coupon.aop.redissonLock.DistributedLock)")
    private void redisLock() {}

    @Around("redisLock()")
    public Object doLock(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

        String keyName = distributedLock.key();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        String key = distributedLock.value() + ":" + getKey(parameters, args, keyName);

        RLock lock = redissonClient.getLock(key);
        try {
            boolean available = lock.tryLock(distributedLock.waitTime(), distributedLock.leaseTime(), distributedLock.timeUnit());
            if (!available) {
                System.out.println("redisson lock timeout");
                throw new IllegalArgumentException();
            }
            return aopForTransaction.getByWriter(joinPoint);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private Object getKey(Parameter[] parameters, Object[] args, String keyName) {
        return IntStream.range(0, parameters.length)
                       .filter(i -> parameters[i].getName().equals(keyName))
                       .mapToObj(i -> args[i])
                       .findFirst()
                       .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 파라미터입니다."));
    }
}
