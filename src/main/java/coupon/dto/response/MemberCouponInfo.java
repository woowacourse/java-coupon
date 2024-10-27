package coupon.dto.response;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponInfo(
        long memberCouponId,
        long couponId,
        long memberId,
        boolean isUsed,
        LocalDateTime createdAt,
        LocalDateTime expiredAt,
        String couponName,
        long discountAmount,
        long minOrderAmount,
        String category,
        LocalDateTime issuanceStartDate,
        LocalDateTime issuanceEndDate

) {

    public static MemberCouponInfo of(MemberCoupon memberCoupon, Coupon coupon) {
        return new MemberCouponInfo(
                memberCoupon.getId(),
                memberCoupon.getCouponId(),
                memberCoupon.getMemberId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt(),
                coupon.getName().getName(),
                coupon.getDiscountAmount().getAmount(),
                coupon.getMinOderAmount().getAmount(),
                coupon.getCategory().name(),
                coupon.getIssuablePeriod().getStartAt(),
                coupon.getIssuablePeriod().getEndAt()
        );
    }
}
