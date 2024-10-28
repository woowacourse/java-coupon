package coupon.membercoupon.dto;

import java.util.List;
import java.util.Map;

import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;

public record FindAllMemberCouponResponse(
        List<FindAllItemMemberCouponResponse> memberCoupons
) {
    public static FindAllMemberCouponResponse of(Map<MemberCoupon, Coupon> memberCoupons) {
        List<FindAllItemMemberCouponResponse> responses = memberCoupons.entrySet().stream()
                .map(memberCouponEntry -> FindAllItemMemberCouponResponse.of(memberCouponEntry.getKey(), memberCouponEntry.getValue()))
                .toList();

        return new FindAllMemberCouponResponse(responses);
    }
}
