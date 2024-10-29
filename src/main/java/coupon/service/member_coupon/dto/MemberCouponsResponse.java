package coupon.service.member_coupon.dto;

import coupon.domain.member_coupon.MemberCoupon;
import java.util.List;
import java.util.Set;

public record MemberCouponsResponse(
        List<MemberCouponResponse> memberCoupons
) {
    public static MemberCouponsResponse from(List<MemberCoupon> memberCoupons) {
        List<MemberCouponResponse> response = memberCoupons.stream()
                .map(MemberCouponResponse::from)
                .toList();

        return new MemberCouponsResponse(response);
    }
}
