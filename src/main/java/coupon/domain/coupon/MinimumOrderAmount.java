package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MinimumOrderAmount {

    private static final BigDecimal MIN_VALUE = new BigDecimal("5000");
    private static final BigDecimal MAX_VALUE = new BigDecimal("100000");

    @Column(name = "minimum_order_amount", nullable = false)
    private BigDecimal value;

    public MinimumOrderAmount(String minimumOrderAmount) {
        this.value = new BigDecimal(minimumOrderAmount);
        validateRange();
    }

    private void validateRange() {
        if (value.compareTo(MIN_VALUE) < 0 || value.compareTo(MAX_VALUE) > 0) {
            throw new CouponException(String.format(
                    "최소 주문 금액은 %s 이상 %s 미만입니다.",
                    MIN_VALUE.toPlainString(),
                    MAX_VALUE.toPlainString())
            );
        }
    }
}

