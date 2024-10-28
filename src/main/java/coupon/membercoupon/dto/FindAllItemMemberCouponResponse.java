package coupon.membercoupon.dto;

import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;

public record FindAllItemMemberCouponResponse(
        MemberCoupon memberCoupon,
        Coupon coupon
) {

    public static FindAllItemMemberCouponResponse of(MemberCoupon memberCoupon, Coupon coupon) {
        return new FindAllItemMemberCouponResponse(memberCoupon, coupon);
    }
}
