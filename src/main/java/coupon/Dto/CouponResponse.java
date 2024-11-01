package coupon.Dto;

import coupon.domain.coupon.Coupon;
import java.time.LocalDate;

public record CouponResponse(
        Long id,
        String name,
        int minimumOrderAmount,
        int discountAmount,
        String category,
        LocalDate startDate,
        LocalDate endDate
) {

    public CouponResponse(Coupon coupon) {
        this(
                coupon.getId(),
                coupon.getName().getName(),
                coupon.getMinimumOrderAmount().getMinimumOrderAmount(),
                coupon.getDiscountAmount().getDiscountAmount(),
                coupon.getCategory().toString(),
                coupon.getPeriodOfIssuance().getStartDate(),
                coupon.getPeriodOfIssuance().getEndDate()
        );
    }
}
