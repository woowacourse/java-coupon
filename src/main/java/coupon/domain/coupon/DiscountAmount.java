package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MINIMUM_AMOUNT = 1_000;
    private static final int MAXIMUM_AMOUNT = 10_000;
    private static final int DISCOUNT_UNIT = 500;

    private int discountAmount;

    public DiscountAmount(int discountAmount) {
        validateAmount(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateAmount(int amount) {
        if (amount < MINIMUM_AMOUNT || amount > MAXIMUM_AMOUNT) {
            String message = "할인 금액은 %d 이상 %d 이하여야 합니다. 입력 값: ".formatted(MINIMUM_AMOUNT, MAXIMUM_AMOUNT);
            throw new IllegalArgumentException(message + amount);
        }

        if (amount % DISCOUNT_UNIT != 0) {
            String message = "할인 금액은 %d원 단위로 설정해야 합니다. 입력 값: ".formatted(DISCOUNT_UNIT);
            throw new IllegalArgumentException(message + amount);
        }
    }

    public int calcDiscountPercent(MinimumAmount minimumAmount) {
        return minimumAmount.calcDiscountPercent(discountAmount);
    }
}

