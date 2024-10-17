package coupon;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
record CouponDiscountAmount(@Column(name = "coupon_discount_amount", nullable = false) BigDecimal value) {

    private static final BigDecimal DISCOUNT_AMOUNT_UNIT = new BigDecimal(500);
    private static final BigDecimal MAXIMUM_DISCOUNT_MONEY = new BigDecimal("10000");
    private static final BigDecimal MINIMUM_DISCOUNT_MONEY = new BigDecimal("1000");

    CouponDiscountAmount {
        validateMinimumAmount(value);
        validateMaximumAmount(value);
        validateAmountUnit(value);
    }

    private void validateMinimumAmount(BigDecimal amount) {
        if (amount.compareTo(MINIMUM_DISCOUNT_MONEY) < 0) {
            String message = "쿠폰의 최소 할인 금액은 " + MINIMUM_DISCOUNT_MONEY + "원 이상입니다.";
            throw new IllegalArgumentException(message);
        }
    }

    private void validateMaximumAmount(BigDecimal amount) {
        if (amount.compareTo(MAXIMUM_DISCOUNT_MONEY) > 0) {
            String message = "쿠폰의 최대 할인 금액은 " + MAXIMUM_DISCOUNT_MONEY + "원 이하입니다.";
            throw new IllegalArgumentException(message);
        }
    }

    private void validateAmountUnit(BigDecimal amount) {
        BigDecimal remain = amount.remainder(DISCOUNT_AMOUNT_UNIT);
        if (remain.compareTo(BigDecimal.ZERO) != 0) {
            String message = "할인 금액은 " + DISCOUNT_AMOUNT_UNIT + "원 단위로만 설정할 수 있습니다.";
            throw new IllegalArgumentException(message);
        }
    }
}
