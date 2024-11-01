package coupon.coupon.controller.dto;

import java.util.List;

import coupon.coupon.domain.Coupon;

public record CouponResponses(
        List<CouponResponse> couponResponses
) {
    public static CouponResponses from(List<Coupon> coupons) {
        return new CouponResponses(coupons.stream()
                .map(CouponResponse::new)
                .toList());
    }
}
