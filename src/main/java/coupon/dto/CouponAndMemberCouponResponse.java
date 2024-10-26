package coupon.dto;

import coupon.domain.MemberCoupon;
import coupon.domain.coupon.Coupon;
import java.util.List;

public record CouponAndMemberCouponResponse(List<Coupon> coupons, List<MemberCoupon> memberCoupons) {
}
