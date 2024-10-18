package coupon.dto.response;

import coupon.domain.Coupon;
import java.time.LocalDateTime;

public record CouponResponse(
        Long couponId,
        String name,
        Long discountAmount,
        Long minOrderAmount,
        String category,
        LocalDateTime issuanceStartDate,
        LocalDateTime issuanceEndDate
) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getAmount(),
                coupon.getMinOderAmount().getAmount(),
                coupon.getCategory().name(),
                coupon.getIssuanceDate().getStartDate(),
                coupon.getIssuanceDate().getEndDate()
        );
    }
}
