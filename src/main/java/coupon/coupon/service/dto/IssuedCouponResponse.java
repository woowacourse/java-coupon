package coupon.coupon.service.dto;

import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.IssuedCouponEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record IssuedCouponResponse(long issueCouponId, CouponResponse coupon, boolean isUsed,
                                   LocalDateTime issuedAt, LocalDate expirationDate) {

    public static IssuedCouponResponse from(IssuedCouponEntity issuedCoupon, CouponEntity coupon) {
        return new IssuedCouponResponse(
                issuedCoupon.getId(),
                CouponResponse.from(coupon),
                issuedCoupon.isUsed(),
                issuedCoupon.getIssuedAt(),
                issuedCoupon.getExpirationDate()
        );
    }
}
