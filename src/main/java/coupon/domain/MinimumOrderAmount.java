package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class MinimumOrderAmount {

    private static final int MINIMUM_ORDER_AMOUNT_MIN = 5_000;
    private static final int MINIMUM_ORDER_AMOUNT_MAX = 100_000;

    private int minimumOrderAmount;

    public MinimumOrderAmount(int minimumOrderAmount) {
        validateMinimumOrderAmount(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validateMinimumOrderAmount(int amount) {
        if (amount < MINIMUM_ORDER_AMOUNT_MIN || amount > MINIMUM_ORDER_AMOUNT_MAX) {
            throw new CouponException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
        }
    }
}
