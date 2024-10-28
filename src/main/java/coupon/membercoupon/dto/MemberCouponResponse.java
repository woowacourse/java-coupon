package coupon.membercoupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;

public record MemberCouponResponse(
        MemberCoupon memberCoupon,
        Coupon coupon
) {
}
