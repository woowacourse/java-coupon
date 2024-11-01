package coupon.fixture;

import coupon.membercoupon.dto.MemberCouponRequest;

import java.time.LocalDate;

public abstract class MemberCouponFixture {

    public static MemberCouponRequest MEMBER_COUPON_REQUEST(long couponId, long memberId) {
        return MEMBER_COUPON_REQUEST(couponId, memberId, LocalDate.now());
    }

    public static MemberCouponRequest MEMBER_COUPON_REQUEST(long couponId, long memberId, LocalDate issuanceDate) {
        return new MemberCouponRequest(
                couponId,
                memberId,
                issuanceDate
        );
    }
}
