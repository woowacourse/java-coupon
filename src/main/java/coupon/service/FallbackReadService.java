package coupon.service;

import coupon.exception.CouponErrorMessage;
import coupon.exception.CouponException;
import java.util.concurrent.Callable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class FallbackReadService {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> T read(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            handleException(e);
        }

        // unreachable
        return null;
    }

    private void handleException(Exception e) throws CouponException {
        if (e instanceof CouponException) {
            throw (CouponException) e;
        }

        log.error("Failed to read data", e);
        throw new CouponException(CouponErrorMessage.FAILED_TO_READ);
    }
}
