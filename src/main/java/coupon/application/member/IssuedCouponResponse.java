package coupon.application.member;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IssuedCouponResponse(
        Long id,
        String name,
        String category,
        LocalDate start,
        LocalDate end,
        BigDecimal discountAmount,
        BigDecimal applicableAmount
) {
}
