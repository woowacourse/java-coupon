package coupon.membercoupon.controller.dto;

import java.util.List;

import coupon.membercoupon.domain.MemberCoupon;

public record MemberCouponResponses(
        List<MemberCouponResponse> memberCouponResponses
) {
    public static MemberCouponResponses from(List<MemberCoupon> memberCoupons) {
        return new MemberCouponResponses(memberCoupons.stream()
                .map(MemberCouponResponse::new)
                .toList());
    }
}
