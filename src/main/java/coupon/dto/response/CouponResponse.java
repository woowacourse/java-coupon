package coupon.dto.response;

import coupon.domain.Coupon;
import java.time.LocalDateTime;

public record CouponResponse(
        long couponId,
        String name,
        long discountAmount,
        long minOrderAmount,
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
