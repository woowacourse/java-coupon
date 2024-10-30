package coupon.dto;

import coupon.domain.Coupon;
import coupon.domain.IssuedCoupon;
import coupon.domain.Member;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
