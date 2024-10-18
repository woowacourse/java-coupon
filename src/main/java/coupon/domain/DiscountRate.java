package coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DiscountRate {
    private final int rate;

    public DiscountRate(int discountAmount, int minimumOrderAmount) {
        int discountRate = new BigDecimal(discountAmount)
            .divide(new BigDecimal(minimumOrderAmount), 2, RoundingMode.FLOOR)
            .multiply(BigDecimal.valueOf(100)).intValue();

        validate(discountRate);
        this.rate = discountRate;
    }

    private void validate(int discountRate) {
        if (discountRate < 3) {
            throw new IllegalArgumentException("할인율은 3% 이상이어야 합니다.");
        }
        if (discountRate > 20) {
            throw new IllegalArgumentException("할인율은 20% 이하여야 합니다.");
        }
    }

    public int intValue() {
        return rate;
    }
}
