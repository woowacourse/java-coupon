package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.Member;

public record IssuedCouponResponse(
        CouponResponse couponResponse,
        MemberResponse memberResponse
) {
    public static IssuedCouponResponse from(Coupon coupon, Member member) {
        return new IssuedCouponResponse(
                CouponResponse.from(coupon),
                MemberResponse.from(member)
        );
    }
}
