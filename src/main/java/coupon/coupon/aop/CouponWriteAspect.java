package coupon.coupon.aop;

import coupon.config.RoutingContext;
import coupon.support.WriteTimeChecker;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Aspect
@Component
@RequiredArgsConstructor
public class CouponWriteAspect {

    private static final String TABLE_NAME = "coupon";

    private final ApplicationEventPublisher applicationEventPublisher;
    private final WriteTimeChecker writeTimeChecker;

    @Before("execution(* coupon.coupon.service.CouponService.*(..))")
    public void before() {
        // Read-only인 경우에만 write으로 라우팅할지를 판단하면 된다. Write인 경우 따로 처리할 필요가 없다.
        if (!TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return;
        }
        if (writeTimeChecker.isAvailableToRead(TABLE_NAME)) {
            RoutingContext.setRoutableToReadSource();
        }
    }

    @AfterReturning("execution(* coupon.coupon.service.CouponService.*(..))")
    public void afterReturning() {
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            return;
        }
        applicationEventPublisher.publishEvent(new WriteEvent());
    }

    @After("execution(* coupon.coupon.service.CouponService.*(..))")
    public void after() {
        RoutingContext.clear();
    }
}
