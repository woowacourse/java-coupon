package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinimumAmount {

    public static final int MINIMUM_AMOUNT = 5_000;
    private static final int MAXIMUM_AMOUNT = 100_000;

    private int minimumAmount;

    public MinimumAmount(int amount) {
        validateAmount(amount);
        this.minimumAmount = amount;
    }

    private void validateAmount(int amount) {
        if (amount < MINIMUM_AMOUNT || amount > MAXIMUM_AMOUNT) {
            String message = "최소 주문 금액은 %d 이상 %d 이하여야 합니다. 입력값 : ".formatted(MINIMUM_AMOUNT, MAXIMUM_AMOUNT);
            throw new IllegalArgumentException(message + amount);
        }
    }

    public int calcDiscountPercent(int discountAmount) {
        return (int) Math.floor((double) discountAmount / minimumAmount);
    }
}
