package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinimumOrderAmount {

    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;

    @Column(name = "minimum_order_amount", nullable = false)
    private int amount;

    public MinimumOrderAmount(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    private void validateAmount(int amount) {
        validateMinimumAmount(amount);
        validateMaximumAmount(amount);
    }

    private void validateMinimumAmount(int amount) {
        if (amount < MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MIN_ORDER_AMOUNT + "원 이상이어야 합니다.");
        }
    }

    private void validateMaximumAmount(int amount) {
        if (amount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 " + MAX_ORDER_AMOUNT + "원 이하여야 합니다.");
        }
    }
}
