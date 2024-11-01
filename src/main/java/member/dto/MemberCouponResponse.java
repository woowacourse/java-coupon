package member.dto;

import coupon.domain.Coupon;
import member.domain.MemberCoupon;

public record MemberCouponResponse (
	MemberCoupon memberCoupon,
	Coupon coupon
){

}
