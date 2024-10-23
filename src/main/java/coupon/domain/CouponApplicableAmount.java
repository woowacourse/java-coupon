package coupon.domain;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
record CouponApplicableAmount(@Column(name = "coupon_applicable_amount", nullable = false) BigDecimal value) {

    private static final BigDecimal MAXIMUM_APPLICABLE_MONEY = new BigDecimal("100000");
    private static final BigDecimal MINIMUM_APPLICABLE_MONEY = new BigDecimal("5000");

    CouponApplicableAmount {
        if (value.compareTo(MINIMUM_APPLICABLE_MONEY) < 0) {
            String message = "해당 쿠폰의 최소 적용 가능 금액은 " + MINIMUM_APPLICABLE_MONEY + "원 이상입니다.";
            throw new IllegalArgumentException(message);
        }

        if (value.compareTo(MAXIMUM_APPLICABLE_MONEY) > 0) {
            String message = "해당 쿠폰의 최대 적용 가능 금액은 " + MAXIMUM_APPLICABLE_MONEY + "원 이하입니다.";
            throw new IllegalArgumentException(message);
        }
    }
}
