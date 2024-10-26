package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscountAmount implements Serializable {

    private static final BigDecimal MIN_VALUE = new BigDecimal("1000");
    private static final BigDecimal MAX_VALUE = new BigDecimal("30000");
    private static final BigDecimal UNIT = new BigDecimal("500");

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal value;

    public DiscountAmount(String discountAmount) {
        this.value = new BigDecimal(discountAmount);
        validateRange();
        validateValueDivisibleByUnit();
    }

    private void validateRange() {
        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new CouponException(String.format(
                    "할인금액은 %s원 이상 %s원 미만입니다.",
                    MIN_VALUE.toPlainString(),
                    MAX_VALUE.toPlainString())
            );
        }
    }

    private void validateValueDivisibleByUnit() {
        if (value.remainder(UNIT).compareTo(BigDecimal.ZERO) != 0) {
            throw new CouponException(String.format(
                    "할인금액은 %s원 단위입니다.",
                    UNIT.toPlainString()
            ));
        }
    }
}
