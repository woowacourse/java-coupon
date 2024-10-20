package coupon.config;

import coupon.coupon.domain.Coupon;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class CouponRoutingAspect {

    @Around("execution(* coupon.coupon.service..*(..)) && @annotation(transactional)")
    public Object setCouponContext(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        Object result = joinPoint.proceed();
        if (result instanceof Coupon coupon) {
            CouponRoutingContext.setCurrentCouponId(coupon.getId());
            recordCouponCreatedAt(transactional, coupon.getId());
        }
        return result;
    }

    private void recordCouponCreatedAt(Transactional transactional, long couponId) {
        if (isNotReadOnlyTransaction(transactional)) {
            CouponRoutingContext.recordCouponCreatedAt(couponId);
        }
    }

    private boolean isNotReadOnlyTransaction(Transactional transactional) {
        return !transactional.readOnly();
    }
}
