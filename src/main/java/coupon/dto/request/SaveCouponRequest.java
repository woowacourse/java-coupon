package coupon.dto.request;

import java.time.LocalDate;

public record SaveCouponRequest(
        String name,
        long discountMoney,
        long minimumOrderMoney,
        LocalDate sinceDate,
        LocalDate untilDate,
        String category
) {

}
