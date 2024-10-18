package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinimumOrderAmount {

    private static final int MINIMUM_OF_AMOUNT = 5000;
    private static final int MAXIMUM_OF_AMOUNT = 100000;

    private int minimumOrderAmount;

    public MinimumOrderAmount(int minimumOrderAmount) {
        validateRange(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validateRange(int minimumOrderAmount) {
        if (MINIMUM_OF_AMOUNT <= minimumOrderAmount && minimumOrderAmount <= MAXIMUM_OF_AMOUNT) {
            return;
        }
        throw new IllegalArgumentException(
                "최소 주문 금액은 " + MINIMUM_OF_AMOUNT + "원 이상 " + MAXIMUM_OF_AMOUNT + "원 이하여야 합니다.");
    }
}
