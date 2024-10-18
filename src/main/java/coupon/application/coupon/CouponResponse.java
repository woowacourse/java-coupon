package coupon.application.coupon;

import coupon.domain.coupon.Coupon;
import java.time.format.DateTimeFormatter;

public record CouponResponse(
        Long id,
        String name,
        Integer discountMoney,
        Integer minOrderMoney,
        String category,
        String startDate,
        String endDate
) {
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getRawName(),
                coupon.getDiscountMoney(),
                coupon.getMinOrderMoney(),
                coupon.getRawCategory(),
                coupon.getStartDate().format(DateTimeFormatter.ISO_DATE),
                coupon.getEndDate().format(DateTimeFormatter.ISO_DATE)
        );
    }
}
