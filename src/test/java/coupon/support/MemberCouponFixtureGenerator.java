package coupon.support;

import coupon.domain.membercoupon.MemberCoupon;
import java.time.LocalDateTime;

public class MemberCouponFixtureGenerator {

    public static MemberCoupon generate(long couponId, long memberId) {
        LocalDateTime issuedAt = LocalDateTime.now();
        return new MemberCoupon(1L, couponId, memberId, false, issuedAt);
    }
}
