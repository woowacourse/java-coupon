package coupon.coupon.ui.dto;

import coupon.coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponResponse(Long id, Long memberId, LocalDateTime expiredAt, int discountAmount,
                                   int minimumOrderPrice) {
    public static MemberCouponResponse from(MemberCoupon memberCoupon) {
        return new MemberCouponResponse(memberCoupon.getId(), memberCoupon.getMemberId(),
                memberCoupon.getUseEndedAt(), memberCoupon.getCoupon().getDiscountAmount(),
                memberCoupon.getCoupon().getMinimumOrderPrice());
    }
}
