package coupon.dto;

import coupon.domain.PublishedCoupon;

import java.time.LocalDateTime;

public record MemberCouponDto(
        Long couponId,
        String couponName,
        int discountAmount,
        int minOrderAmount,
        LocalDateTime issuanceStartDate,
        LocalDateTime issuanceEndDate,
        Long publishedCouponId,
        String memberName,
        boolean isUsed,
        LocalDateTime issuanceDate,
        LocalDateTime expirationDate
) {
    public static MemberCouponDto from(PublishedCoupon publishedCoupon) {
        return new MemberCouponDto(
                publishedCoupon.getCoupon().getId(),
                publishedCoupon.getCoupon().getName(),
                publishedCoupon.getCoupon().getDiscountAmount(),
                publishedCoupon.getCoupon().getMinOrderAmount(),
                publishedCoupon.getCoupon().getIssuanceStartDate(),
                publishedCoupon.getCoupon().getIssuanceEndDate(),
                publishedCoupon.getId(),
                publishedCoupon.getMember().getName(),
                publishedCoupon.isUsed(),
                publishedCoupon.getIssuanceDate(),
                publishedCoupon.getExpirationDate()
        );
    }
}
