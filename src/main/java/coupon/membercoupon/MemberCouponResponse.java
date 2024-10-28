package coupon.membercoupon;

import java.util.List;

public record MemberCouponResponse(
        String memberName,
        List<CouponResponse> coupons
) {
}
