package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DiscountRate implements Serializable {

    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    @Column(name = "discount_rate", nullable = false)
    private int value;

    public DiscountRate(DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount) {
        int discountRate = calculateDiscountRate(discountAmount, minimumOrderAmount);
        validateRange(discountRate);
        this.value = discountRate;
    }

    private int calculateDiscountRate(DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount) {
        BigDecimal discountAmountValue = discountAmount.getValue();
        BigDecimal minimumOrderAmountValue = minimumOrderAmount.getValue();

        BigDecimal discountRate = discountAmountValue.divide(minimumOrderAmountValue, 3, RoundingMode.DOWN)
                .multiply(BigDecimal.valueOf(100));

        return discountRate.toBigInteger().intValue();
    }

    private void validateRange(int discountRate) {
        if (discountRate < MINIMUM_DISCOUNT_RATE || discountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new CouponException(String.format(
                    "할인율은 %d%% 이상 %d%% 이하 입니다.",
                    MINIMUM_DISCOUNT_RATE,
                    MAXIMUM_DISCOUNT_RATE
            ));
        }
    }
}
