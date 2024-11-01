package coupon.domain.dto;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;

public record CouponInfo(Long memberId, Coupon coupon, MemberCoupon memberCoupon) {
}
