package coupon.coupon.dto;

import coupon.coupon.domain.Coupon;
import java.time.LocalDateTime;

public record CouponCreateRequest(
        String name,
        Integer discountAmount,
        Integer minOrderAmount,
        LocalDateTime startedAt,
        LocalDateTime endedAt
) {

    public Coupon toCoupon() {
        return new Coupon(name, discountAmount, minOrderAmount, startedAt, endedAt);
    }
}
