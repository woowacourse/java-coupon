package coupon.service.dto;

import coupon.domain.Category;
import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import java.time.LocalDateTime;

public record MemberCouponDto(Long couponId,
                              String name,
                              int minOrderAmount,
                              int discountAmount,
                              Category category,
                              Long issuedCouponId,
                              boolean isUsed,
                              LocalDateTime issuedAt,
                              LocalDateTime expiredAt) {

    public static MemberCouponDto from(Coupon coupon, MemberCoupon memberCoupon) {
        return new MemberCouponDto(
                coupon.getId(),
                coupon.getName(),
                coupon.getMinOrderAmount(),
                coupon.getDiscountAmount(),
                coupon.getCategory(),
                memberCoupon.getId(),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt());
    }
}
