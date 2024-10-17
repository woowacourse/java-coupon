package coupon.domain.coupon;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinimumOrderAmount {

    private static final BigDecimal MIN_MINIMUM_ORDER_AMOUNT = new BigDecimal(5_000);
    private static final BigDecimal MAX_MINIMUM_ORDER_AMOUNT = new BigDecimal(100_000);

    @Column(nullable = false)
    private BigDecimal value;

    public MinimumOrderAmount(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (value.compareTo(MIN_MINIMUM_ORDER_AMOUNT) < 0 || value.compareTo(MAX_MINIMUM_ORDER_AMOUNT) > 0) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }
    }
}
