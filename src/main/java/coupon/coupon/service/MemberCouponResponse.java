package coupon.coupon.service;

import java.util.List;

public record MemberCouponResponse(
        List<CouponResponse> issuableCoupons,
        List<IssuedCouponResponse> issuedCoupons
) {
}
