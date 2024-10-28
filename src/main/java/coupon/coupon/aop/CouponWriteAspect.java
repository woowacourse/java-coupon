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

    private static final String COUPON_SERVICE_POINTCUT = "execution(* coupon.coupon.service..*Service.*(..))";
    private static final String TABLE_NAME = "coupon";

    private final ApplicationEventPublisher applicationEventPublisher;
    private final WriteTimeChecker writeTimeChecker;

    @Before(COUPON_SERVICE_POINTCUT)
    public void before() {
        // Read-only인 경우에만 write으로 라우팅할지 판단한다. Write인 경우 따로 처리할 필요가 없다.
        if (isCurrentTransactionReadOnly() && writeTimeChecker.isAvailableToRead(TABLE_NAME)) {
            RoutingContext.setRoutableToReadSource();
        }
    }

    @AfterReturning(COUPON_SERVICE_POINTCUT)
    public void afterReturning() {
        if (isCurrentTransactionReadOnly()) {
            return;
        }
        applicationEventPublisher.publishEvent(new WriteEvent());
    }

    @After(COUPON_SERVICE_POINTCUT)
    public void after() {
        RoutingContext.clear();
    }

    private boolean isCurrentTransactionReadOnly() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly();
    }
}
