package coupon.service.dto;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.MemberCoupon;

public record MemberCouponResponse(MemberCoupon memberCoupon,
                                   Coupon coupon
) {
}
