package coupon.domain.coupon;

import java.math.BigDecimal;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class MinimumOrderAmount {

    private static final BigDecimal MIN_ORDER_AMOUNT = new BigDecimal(5000);
    private static final BigDecimal MAX_ORDER_AMOUNT = new BigDecimal(100000);

    @Column(name = "minimum_order_amount")
    private BigDecimal value;

    public MinimumOrderAmount(BigDecimal value) {
        validateRange(value);
        this.value = value;
    }

    private void validateRange(BigDecimal value) {
        if (value.compareTo(MIN_ORDER_AMOUNT) < 0 || value.compareTo(MAX_ORDER_AMOUNT) > 0) {
            throw new CouponException(ExceptionType.COUPON_MINIMUM_ORDER_AMOUNT_INVALID);
        }
    }
}
