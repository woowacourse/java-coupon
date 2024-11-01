package coupon.membercoupon.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import coupon.coupon.domain.Coupons;
import coupon.membercoupon.domain.MemberCoupons;

public record FindAllMemberCouponResponse(
        List<FindAllItemMemberCouponResponse> memberCoupons
) {
    public static FindAllMemberCouponResponse of(MemberCoupons memberCoupons, Coupons coupons) {
        return IntStream.range(0, memberCoupons.getSize())
                .mapToObj(i -> new FindAllItemMemberCouponResponse(memberCoupons.getMemberCouponByOrder(i), coupons.getCouponByOrder(i)))
                .collect(Collectors.collectingAndThen(Collectors.toList(), FindAllMemberCouponResponse::new));
    }
}
