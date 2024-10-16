package coupon.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountAmount {

    private static final int MIN_AMOUNT = 1000;
    private static final int MAX_AMOUNT = 10000;
    private static final int DEFAULT_UNIT = 500;

    private int amount;

    public DiscountAmount(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        validateMinimumAmount(amount);
        validateMaximumAmount(amount);
        validateMultipleOf(amount);
    }

    private void validateMinimumAmount(int amount) {
        if (amount < MIN_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MIN_AMOUNT + "원 이상이어야 합니다.");
        }
    }

    private void validateMaximumAmount(int amount) {
        if (amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 " + MAX_AMOUNT + "원 이하여야 합니다.");
        }
    }

    private void validateMultipleOf(int amount) {
        if (amount % DEFAULT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 " + DEFAULT_UNIT + "원 단위로만 설정할 수 있습니다.");
        }
    }
}
