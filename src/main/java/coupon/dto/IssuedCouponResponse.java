package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.IssuedCoupon;
import java.time.LocalDate;

public record IssuedCouponResponse(
        CouponResponse couponResponse,
        MemberResponse memberResponse,
        Long id,
        Boolean isUsed,
        LocalDate createdAt,
        LocalDate expiredAt
) {
    public static IssuedCouponResponse from(Coupon coupon, IssuedCoupon issuedCoupon) {
        return new IssuedCouponResponse(
                CouponResponse.from(coupon),
                MemberResponse.from(issuedCoupon.getMember()),
                issuedCoupon.getId(),
                issuedCoupon.getIsUsed(),
                issuedCoupon.getCreatedAt(),
                issuedCoupon.getExpiredAt()
        );
    }
}
