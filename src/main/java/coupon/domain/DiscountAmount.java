package coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final BigDecimal MIN_DISCOUNT_AMOUNT = new BigDecimal(1_000);
    private static final BigDecimal MAX_DISCOUNT_AMOUNT = new BigDecimal(10_000);
    private static final BigDecimal UNIT = new BigDecimal(500);
    private static final BigDecimal MIN_RATE = new BigDecimal("0.03");
    private static final BigDecimal MAX_RATE = new BigDecimal("0.20");

    @Column(nullable = false)
    private BigDecimal value;

    public DiscountAmount(BigDecimal value) {
        validateAmount(value);
        validateUnit(value);
        this.value = value;
    }

    private void validateAmount(BigDecimal value) {
        if (value.compareTo(MIN_DISCOUNT_AMOUNT) < 0 || value.compareTo(MAX_DISCOUNT_AMOUNT) > 0) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000 이하여야 합니다.");
        }
    }

    private void validateUnit(BigDecimal value) {
        if (value.remainder(UNIT).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    public boolean isValidDiscountRate(BigDecimal minimumOrderAmount) {
        BigDecimal discountRate = value.divide(minimumOrderAmount, 2, RoundingMode.DOWN);
        return discountRate.compareTo(MIN_RATE) >= 0 && discountRate.compareTo(MAX_RATE) <= 0;
    }
}
