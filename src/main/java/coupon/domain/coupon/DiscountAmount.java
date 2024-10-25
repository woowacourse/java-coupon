package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MINIMUM_OF_AMOUNT = 1000;
    private static final int MAXIMUM_OF_AMOUNT = 10000;
    private static final int DIVISOR = 500;
    private static final int MINIMUM_OF_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_OF_DISCOUNT_RATE = 20;

    private int discountAmount;

    public DiscountAmount(int discountAmount, int minimumOrderAmount) {
        validate(discountAmount, minimumOrderAmount);
        this.discountAmount = discountAmount;
    }

    private void validate(int discountAmount, int minimumOrderAmount) {
        validateRange(discountAmount);
        validateMultiple(discountAmount);
        int discountRate = Calculator.calculateDiscountRateOfPercentage(discountAmount, minimumOrderAmount);
        validateDiscountRate(discountRate);
    }

    private void validateRange(int discountAmount) {
        if (MINIMUM_OF_AMOUNT <= discountAmount && discountAmount <= MAXIMUM_OF_AMOUNT) {
            return;
        }
        throw new IllegalArgumentException("할인 금액은 " + MINIMUM_OF_AMOUNT + "원 이상 " + MAXIMUM_OF_AMOUNT + "원 이하여야 합니다.");
    }

    private void validateMultiple(int discountAmount) {
        if (discountAmount % DIVISOR != 0) {
            throw new IllegalArgumentException("할인 금액은 " + DIVISOR + "원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateDiscountRate(int discountRate) {
        if (MINIMUM_OF_DISCOUNT_RATE <= discountRate && discountRate <= MAXIMUM_OF_DISCOUNT_RATE) {
            return;
        }
        throw new IllegalArgumentException(
                "할인율은 " + MINIMUM_OF_DISCOUNT_RATE + "% 이상 " + MAXIMUM_OF_DISCOUNT_RATE + "% 이하여야 합니다.");
    }
}
