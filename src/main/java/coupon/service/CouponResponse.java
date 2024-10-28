package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.MemberCoupon;

public record CouponResponse(Coupon coupon, MemberCoupon memberCoupon) {
}
