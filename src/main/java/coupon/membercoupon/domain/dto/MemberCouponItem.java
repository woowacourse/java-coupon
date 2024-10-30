package coupon.membercoupon.domain.dto;

import coupon.coupon.domain.Coupon;
import coupon.membercoupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponItem(
        Long id,
        Long couponId,
        Long memberId,
        Boolean isUsed,
        LocalDateTime issuedAt,
        LocalDateTime expiredAt,
        String couponName,
        Integer discountAmount,
        Integer minimumOrderAmount,
        String category,
        LocalDateTime startAt,
        LocalDateTime endAt
) {

    public MemberCouponItem(MemberCoupon memberCoupon, Coupon coupon) {
        this(memberCoupon.getId(),
                memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.getIsUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt(),
                coupon.getCouponName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getCategory().name(),
                coupon.getIssuablePeriod().getStartAt(),
                coupon.getIssuablePeriod().getEndAt());
    }
}
