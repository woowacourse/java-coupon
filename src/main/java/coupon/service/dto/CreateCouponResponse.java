package coupon.service.dto;

import java.time.LocalDateTime;

import coupon.repository.CouponEntity;

public record CreateCouponResponse(
        long id,
        String couponName,
        long discountAmount,
        long discountRate,
        long minimumOrderAmount,
        LocalDateTime startDate,
        LocalDateTime expirationDate
) {

    public static CreateCouponResponse from(final CouponEntity couponEntity) {
        return new CreateCouponResponse(
                couponEntity.getId(),
                couponEntity.getName(),
                couponEntity.getDiscountAmount(),
                couponEntity.getDiscountRate(),
                couponEntity.getMinimumOrderAmount(),
                couponEntity.getStartDate(),
                couponEntity.getExpirationDate()
        );
    }
}
