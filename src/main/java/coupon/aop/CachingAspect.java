package coupon.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Aspect
public class CachingAspect {

    private final ConcurrentMap<String, Object> cache = new ConcurrentHashMap<>();

    @Around("@annotation(Cacheable)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        String key = generateKey(joinPoint);
        Object cachedValue = cache.get(key);

        if (cachedValue != null) {
            return cachedValue;
        }

        Object result = joinPoint.proceed();
        cache.put(key, result);
        return result;
    }

    private String generateKey(ProceedingJoinPoint joinPoint) {
        return joinPoint.getSignature().toString() + Arrays.toString(joinPoint.getArgs());
    }
}
