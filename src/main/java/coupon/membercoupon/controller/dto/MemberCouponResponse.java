package coupon.membercoupon.controller.dto;

import java.time.LocalDateTime;

import coupon.membercoupon.domain.MemberCoupon;

public record MemberCouponResponse(
        Long id,
        Long memberId,
        Long couponId,
        Boolean isUsed,
        LocalDateTime issuedAt,
        LocalDateTime expiresAt
) {
    public MemberCouponResponse(MemberCoupon memberCoupon) {
        this(
                memberCoupon.getId(),
                memberCoupon.getMemberId(),
                memberCoupon.getCouponId(),
                memberCoupon.getIsUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiresAt()
        );
    }
}
