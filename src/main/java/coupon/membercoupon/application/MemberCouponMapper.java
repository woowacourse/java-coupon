package coupon.membercoupon.application;

import coupon.coupon.application.CouponResponse;
import coupon.membercoupon.domain.MemberCoupon;
import org.springframework.stereotype.Component;

@Component
public class MemberCouponMapper {


    public MemberCouponWithCouponResponse toWithCouponResponse(
            MemberCoupon memberCoupon,
            CouponResponse couponResponse
    ) {
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
