package coupon.config;

import coupon.coupon.domain.Coupon;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class CouponRoutingAspect {

    @Around("execution(* coupon.coupon.service..*(..)) && @annotation(transactional)")
    public Object setCouponContext(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        Coupon result = (Coupon) joinPoint.proceed();
        CouponRoutingContext.setCurrentCouponId(result.getId());
        if (transactional.readOnly()) {
            return result;
        }
        CouponRoutingContext.recordCouponCreatedAt(result.getId());
        return result;
    }

    @After("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void clearCouponContext() {
        CouponRoutingContext.clearCurrentCouponId();
    }
}
