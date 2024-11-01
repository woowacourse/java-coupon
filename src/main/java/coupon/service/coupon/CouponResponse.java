package coupon.service.coupon;

import coupon.entity.coupon.Coupon;
import coupon.entity.coupon.CouponCategory;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CouponResponse(Long id,
                             String name,
                             BigDecimal discountAmount,
                             BigDecimal minimumOrderAmount,
                             CouponCategory category,
                             LocalDate issuanceStartDate,
                             LocalDate issuanceEndDate) {

    public CouponResponse(Coupon coupon) {
        this(coupon.getId(),
                coupon.getName().getName(),
                coupon.getDiscount().getDiscountAmount(),
                coupon.getDiscount().getMinimumOrderAmount(),
                coupon.getCategory(),
                coupon.getPeriod().getIssuanceStartDate(),
                coupon.getPeriod().getIssuanceEndDate()
        );
    }
}
