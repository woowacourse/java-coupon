package coupon.coupon.service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IssuedCouponLimitExceededException extends RuntimeException {

    public IssuedCouponLimitExceededException(long memberId, int issueCouponLimit) {
        super("발급할 수 있는 쿠폰 제한을 넘었습니다. 제한: %d".formatted(issueCouponLimit));
        log.info("Maximum coupon issue limit exceeded for memberId {}, limit: {}", memberId, issueCouponLimit);
    }
}
