package coupon.coupon.dto;

import coupon.coupon.domain.Coupon;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CouponResponse(
        String name,
        Integer discountAmount,
        Integer discountRate,
        Integer minOrderAmount,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {

    public static CouponResponse from(Coupon coupon) {
        return CouponResponse.builder()
                .name(coupon.getName())
                .discountAmount(coupon.getDiscountAmount())
                .discountRate(coupon.getDiscountRate())
                .minOrderAmount(coupon.getMinOrderAmount())
                .startedAt(coupon.getStartedAt())
                .endedAt(coupon.getEndedAt())
                .build();
    }
}
