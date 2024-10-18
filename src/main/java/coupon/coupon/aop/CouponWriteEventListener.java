package coupon.coupon.aop;

import coupon.support.WriteTimeChecker;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CouponWriteEventListener {

    private static final Duration TIMEOUT = Duration.ofMinutes(2);

    private final WriteTimeChecker writeTimeChecker;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(WriteEvent event) {
        writeTimeChecker.renewKey("coupon", TIMEOUT);
    }
}
