package coupon.membercoupon.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;

public record FindAllMemberCouponResponse(
        List<FindAllItemMemberCouponResponse> memberCoupons
) {
    public static FindAllMemberCouponResponse of(List<MemberCoupon> memberCoupons, List<Coupon> coupons) {
        Map<Long, Coupon> couponMap = toCouponMap(coupons);
        List<FindAllItemMemberCouponResponse> responses = toFindAllItemMemberCouponResponses(memberCoupons, couponMap);

        return new FindAllMemberCouponResponse(responses);
    }

    private static Map<Long, Coupon> toCouponMap(List<Coupon> coupons) {
        return coupons.stream()
                .collect(Collectors.toMap(Coupon::getId, coupon -> coupon));
    }

    private static List<FindAllItemMemberCouponResponse> toFindAllItemMemberCouponResponses(List<MemberCoupon> memberCoupons, Map<Long, Coupon> couponMap) {
        return memberCoupons.stream()
                .map(mc -> FindAllItemMemberCouponResponse.of(mc, couponMap.get(mc.getCouponId())))
                .toList();
    }
}
