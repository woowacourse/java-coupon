package coupon.coupon.service.dto;

import java.util.List;

public record MemberCouponResponse(
        List<CouponResponse> issuableCoupons,
        List<IssuedCouponResponse> issuedCoupons
) {
}
