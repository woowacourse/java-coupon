package coupon.application.coupon;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.CouponCategory;

public record CouponResponse(
        Long id,
        String name,
        BigDecimal discountAmount,
        BigDecimal minimumOrderAmount,
        CouponCategory category,
        LocalDateTime startDate,
        LocalDateTime endDate
) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getCategory(),
                coupon.getPeriod().getStartDate(),
                coupon.getPeriod().getEndDate()
        );
    }
}
