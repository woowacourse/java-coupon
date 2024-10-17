package coupon.config;

import coupon.coupon.domain.Coupon;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class CouponRoutingAspect {

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object setCouponContext(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Transactional transactional = method.getAnnotation(Transactional.class);
        Coupon result = (Coupon) joinPoint.proceed();
        if (!transactional.readOnly()) {
            CouponRoutingContext.recordCouponCreatedAt(result.getId());
        }
        CouponRoutingContext.setCurrentCouponId(result.getId());
        return result;
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void clearCouponContext() {
        CouponRoutingContext.clearCurrentCouponId();
    }
}
