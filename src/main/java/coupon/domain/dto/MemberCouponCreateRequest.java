package coupon.domain.dto;

import coupon.domain.MemberCoupon;

import java.time.LocalDateTime;

public record MemberCouponCreateRequest(Long couponId, Long memberId) {

    public MemberCoupon toEntity() {
        return new MemberCoupon(couponId, memberId, LocalDateTime.now());
    }
}
