package coupon.membercoupon.application;

import java.util.List;
import java.util.Map;
import coupon.coupon.application.CouponResponse;
import coupon.membercoupon.domain.MemberCoupon;
import org.springframework.stereotype.Component;

@Component
public class MemberCouponMapper {

    public List<MemberCouponWithCouponResponse> toWithCouponResponses(
            List<MemberCoupon> memberCoupons,
            Map<Long, CouponResponse> couponMap
    ) {
        return memberCoupons.stream()
                .map(memberCoupon -> {
                    CouponResponse couponResponse = couponMap.get(memberCoupon.getCouponId());
                    return toWithCouponResponse(memberCoupon, couponResponse);
                })
                .toList();
    }

    public MemberCouponWithCouponResponse toWithCouponResponse(MemberCoupon memberCoupon, CouponResponse couponResponse) {
        return new MemberCouponWithCouponResponse(
                memberCoupon.getId(),
                memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt(),
                couponResponse
        );
    }
}
